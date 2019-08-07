GitHandleManagement
===================

Simple [Vaadin](https://vaadin.com/docs/index.html) based Githandle Management application. This project demonstrates:
 * REST API to query GitHub profiles using Git Handle.
 * REST API to retrieve past search history (stores on DB).
 * REST API to delete selected search result from DB cache.

### Usage:

Make sure you have installed [redis](https://redis.io/download) and have it up and running

Make sure you have installed [Maven](http://maven.apache.org/) and [Git](http://git-scm.com/).

    git clone https://github.com/hegdevageesh90/GitHandleManagement.git
    cd GitHandleManagement
    mvn clean install
    mvn jetty:run
    
After these steps you have the application up and running at http://localhost:8080/

You can find the code for the application in [GitUI.java](src/main/java/org/vaadin/samples/githandlemanagement/GitUI.java).

### Notes:

* Please use 0aceb1bd514c60fa62ff86a745428b036eb77a01 or enter a valid Git OAuth Personal Access Token when prompted for in the first screen
* Steps to generate Git OAuth Personal Access Token : Profile -> Settings -> Developer settings -> Personal access tokens -> Generate new token
* For the purpose of simplicity, only past 100 past searches are supported

### License

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to [http://unlicense.org/](http://unlicense.org/)
