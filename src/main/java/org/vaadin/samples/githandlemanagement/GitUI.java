package org.vaadin.samples.githandlemanagement;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.json.JSONObject;

import java.util.List;

/**
 * @author vageeshhegde
 */

@Theme("valo")
@SuppressWarnings("serial")
public class GitUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = GitUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(final VaadinRequest request) {

        final GithandlesCache githandlesCache = new GithandlesCache();

        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        layout.addComponent(new Label("Login to search githandles"));

        String caption = "OAuth Personal access token : ";
        final TextField token = new TextField(caption);

        layout.addComponent(token);

        Button button = new Button("Login");
        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                Notification.show("You're logged in!", Notification.Type.WARNING_MESSAGE);
                final VerticalLayout loggedInLayout = new VerticalLayout();
                loggedInLayout.setMargin(true);
                loggedInLayout.setSpacing(true);
                setContent(loggedInLayout);

                String caption = "Enter githandle to be searched : ";
                final TextField githandle = new TextField(caption);
                loggedInLayout.addComponent(githandle);

                Button searchButton = new Button("Search");
                searchButton.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        try {
                            List<JSONObject> res = RequestGithandle.getUsers(token.getValue(), githandle.getValue());

                            VerticalLayout gridLayout = new VerticalLayout();
                            gridLayout.setMargin(true);
                            gridLayout.setSpacing(true);
                            setContent(gridLayout);

                            gridLayout.addComponent(new Label("User/s found!"));
                            int count = 1;
                            for (JSONObject aUser : res) {
                                gridLayout.addComponent(new Label(count + " : " + aUser.optString("login")));
                                count++;
                            }

                            if (res.size() == 30)
                                gridLayout.addComponent(new Label("Displaying only the first 30 matches. More users exist"));

                            List<String> searches = githandlesCache.getJedis().lrange("history", 0, 100);

                            if (!searches.contains(githandle.getValue())) {
                                githandlesCache.getJedis().lpush("history", githandle.getValue());
                                Notification.show("Pattern : " + githandle.getValue() + " is now saved to past searches", Notification.Type.WARNING_MESSAGE);
                            }

                            Button backToSearchButton1 = new Button("Back to search");
                            backToSearchButton1.addClickListener(new Button.ClickListener() {

                                @Override
                                public void buttonClick(ClickEvent event) {
                                    setContent(loggedInLayout);
                                }
                            });
                            gridLayout.addComponent(backToSearchButton1);

                        } catch (Exception e){
                            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
                        }
                    }
                });

                Button historyButton = new Button("Past Searches");
                historyButton.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        final VerticalLayout historyLayout = new VerticalLayout();
                        historyLayout.setMargin(true);
                        historyLayout.setSpacing(true);
                        setContent(historyLayout);

                        historyLayout.addComponent(new Label("Below are your past searches"));

                        List<String> searches = githandlesCache.getJedis().lrange("history", 0, 100);
                        int count  = 1;
                        for (String aSearch : searches){
                            historyLayout.addComponent(new Label(count + " : " + aSearch));
                            count++;
                        }

                        Button backToSearchButton = new Button("Back to search");
                        backToSearchButton.addClickListener(new Button.ClickListener() {

                            @Override
                            public void buttonClick(ClickEvent event) {
                                setContent(loggedInLayout);
                            }
                        });

                        historyLayout.addComponent(backToSearchButton);

                        final TextField deleteTerm = new TextField("Enter the term to delete from history");
                        Button deleteSearchTermButton = new Button("Delete");
                        deleteSearchTermButton.addClickListener(new Button.ClickListener() {

                            @Override
                            public void buttonClick(ClickEvent event) {
                                boolean deleted = false;
                                List<String> searches = githandlesCache.getJedis().lrange("history", 0, 100);
                                for (String s : searches) {
                                    if (s.equals(deleteTerm.getValue())) {
                                        githandlesCache.getJedis().lrem("history", 1, deleteTerm.getValue());
                                        deleted = true;
                                    }
                                }
                                if (deleted) {
                                    Notification.show("Successfully deleted", Notification.Type.WARNING_MESSAGE);
                                    setContent(loggedInLayout);
                                }
                            }
                        });
                        historyLayout.addComponent(deleteTerm);
                        historyLayout.addComponent(deleteSearchTermButton);
                    }
                });

                loggedInLayout.addComponent(searchButton);
                loggedInLayout.addComponent(historyButton);
            }
        });

        layout.addComponent(button);
    }

}