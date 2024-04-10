package ui;

import com.google.gson.Gson;
import dataAccess.DataAccessException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private String serverID;

    //this is where functionality begins from the client side. This will make requests
    public ServerFacade(int port) {
        serverID = "http://localhost:" + port + "/";
    }

    public String getServerID(){
        return serverID;
    }

    //Create Request to send to the server
    public <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String header) throws Exception {
        try {
            URL url = (new URI(serverID + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setRequestProperty("Authorization", header);

            //Creates the Body for Request
            if (request != null) {
                http.addRequestProperty("Content-Type", "application/json");
                String reqData = new Gson().toJson(request);
                try (OutputStream reqBody =http.getOutputStream()) {
                    reqBody.write(reqData.getBytes());
                }
            }

            //sends request to server
            http.connect();

            //assures response is 200 / valid
            if (http.getResponseCode() != 200) {
                throw new Exception("failure: " + http.getResponseCode());
            }

            //This is where we create the file to be returned
            T response = null;
            if (http.getContentLength() < 0) {
                try (InputStream respBody = http.getInputStream()) {
                    InputStreamReader reader = new InputStreamReader(respBody);
                    if (responseClass != null) {
                        response = new Gson().fromJson(reader, responseClass);
                    }
                }
            }
            return response;

        } catch (DataAccessException exception) { throw new DataAccessException(exception.getMessage()); }
    }
}

