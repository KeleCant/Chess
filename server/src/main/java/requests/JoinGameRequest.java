package requests;

public class JoinGameRequest {
    private String authToken;
    private String ClientColor;
    private int gameID;

    JoinGameRequest(String authToken, String ClientColor, int gameID){
        this.authToken = authToken;
        this.ClientColor = ClientColor;
        this.gameID = gameID;
    }
    JoinGameRequest(String ClientColor, int gameID){
        this.ClientColor = ClientColor;
        this.gameID = gameID;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public String AuthToken(){
        return authToken;
    }

    public String ClientColor(){
        return ClientColor;
    }

    public int gameID(){
        return gameID;
    }



}
