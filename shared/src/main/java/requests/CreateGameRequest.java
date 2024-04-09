package requests;

public class CreateGameRequest {
    private String authToken;
    private String gameName;


    public CreateGameRequest(String gameName){
        this.gameName = gameName;
    }

    public CreateGameRequest(String authToken, String gameName){
        this.authToken = authToken;
        this.gameName = gameName;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public String getAuthToken(){
        return authToken;
    }

    public String getGameName(){
        return gameName;
    }
}
