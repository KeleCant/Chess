package requests;

public class JoinGameRequest {
    private String authToken;
    private String clientColor;
    private int gameID;

    JoinGameRequest(String authToken, String clientColor, int gameID){
        this.authToken = authToken;
        this.clientColor = clientColor;
        this.gameID = gameID;
    }
    JoinGameRequest(String clientColor, int gameID){
        this.clientColor = clientColor;
        this.gameID = gameID;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public String authToken(){
        return authToken;
    }

    public String clientColor(){
        return clientColor;
    }

    public int gameID(){
        return gameID;
    }



}
