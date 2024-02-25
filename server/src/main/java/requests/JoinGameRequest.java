package requests;

public record JoinGameRequest(String authToken, String ClientColor, int gameID) {}
