package ui;

public class ServerFacade {
    private String authToken;
    private final String url = "http://localhost:8080/";
    int port;

    //this is where functionality begins from the client side. This will make requests
    public ServerFacade(int port) {
        this.port = port;
    }

    //I will either need a function for reach endpoint or for each command type
    public <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw new ResponseException(ex.getMessage(), 500);
        }
    }

}

