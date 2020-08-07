package server.messages;

public class Message {

    public String code;
    public String content;
    public boolean read;

    public Message(String Code, String Content) {
        code = Code;
        content = Content;
        read = false;
    }
}
