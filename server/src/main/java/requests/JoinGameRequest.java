package requests;

public class JoinGameRequest {
    private String authToken;
    private String clientColor;
    private int gameID;

    JoinGameRequest(String authToken, String ClientColor, int gameID){
        this.authToken = authToken;
        this.clientColor = ClientColor;
        this.gameID = gameID;
    }
    JoinGameRequest(String ClientColor, int gameID){
        this.clientColor = ClientColor;
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
