package server.messages;

import java.util.ArrayList;

public class MessageList {

    private static MessageList instance;
    private ArrayList<Message> list;

    public MessageList() {
        list = new ArrayList<Message>();
    }

    public static void load() {
        instance = new MessageList();
    }

    public static MessageList getInstance() {
        return instance;
    }

    public int size() {
        return list.size();
    }

    public void add(Message message) {
        list.add(message);
    }

    public Message get(String Code) {
        clean();
        for (Message M : list) {
            if (M.code.equals(Code) && !M.read) {
                M.read = true;
                return M;
            }
        }
        return new Message("0", "_");
    }
    
    public void reset(){
        list = new ArrayList<Message>();
    }

    private void clean() {
        for (int i = 0; i < list.size(); i++) {
            remove(list.get(i));
        }
    }

    private void remove(Message message) {
        if (message.read) {
            list.remove(message);
        }
    }

}
