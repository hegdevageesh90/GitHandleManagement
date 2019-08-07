package org.vaadin.samples.githandlemanagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vageeshhegde
 */
public class RequestGithandle {
    public static List<JSONObject> getUsers(String token, String query) throws Exception {

        String url = "https://api.github.com/search/users?q=" + query;
        HttpURLConnection httpURLConnection = httpGetConnection(url, token);

        JSONObject gitResponse = gitResponseHandler(httpURLConnection);

        if (gitResponse.optInt("total_count") == 0)
            throw new Exception("No users exist");

        JSONArray usersArray = gitResponse.optJSONArray("items");
        List<JSONObject> retUsersList = new ArrayList<>();
        for (int i = 0; i < usersArray.length(); i++){
            retUsersList.add(usersArray.optJSONObject(i));
        }
        return retUsersList;
    }

    private static JSONObject gitResponseHandler(HttpURLConnection con) throws IOException, JSONException {
        int httpResult = con.getResponseCode();
        if (httpResult != HttpURLConnection.HTTP_OK) {
            return null;
        }
        String responseData = getInputStreamData(con.getInputStream());
        JSONObject responseJson = new JSONObject(responseData);
        return responseJson;
    }

    public static String getInputStreamData(InputStream inputStream) throws IOException {
        BufferedReader br = null;
        try {
            String line = null;
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static HttpURLConnection httpGetConnection(String url, String token) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "token " + token);
        con.setDoOutput(true);
        return con;
    }
}
