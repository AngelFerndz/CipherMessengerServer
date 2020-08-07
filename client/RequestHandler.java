package server.client;

import server.messages.Message;
import server.messages.MessageList;

public class RequestHandler {

    private Client client;
    private MessageList messages;

    public RequestHandler() {
        messages = MessageList.getInstance();
    }

    public void setClient(Client Client) {
        client = Client;
    }

    public void run() {
        try {
            String str = client.get();
            String[] array = str.split(" ");
            send(array);
            get(array);
        } catch (Exception e) {
            client.end();
        }
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message getMessage(String ConnectionCode) {
        return messages.get(ConnectionCode);
    }

    //PRIVATE METHODS
    private void send(String[] array) {
        if (array[0].equals("send")) {
            String text = array[1];
            String code = array[2];
            Message message = new Message(code, text);
            addMessage(message);
            client.send("_");
        }
    }

    private void get(String[] array) {
        if (array[0].equals("get")) {
            String code = array[1];
            Message m = getMessage(code);
            String text = m.content;
            client.send(text);
        }
    }

}
