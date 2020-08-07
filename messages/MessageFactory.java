package server.messages;

public class MessageFactory {

    public static Message create(String Code, String Content) {
        return new Message(Code, Content);
    }

}
