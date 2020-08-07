package server.client;

import java.util.ArrayList;

public class ClientList {

    private static ClientList instance;
    private ArrayList<Client> list;

    private ClientList() {
        list = new ArrayList<Client>();
    }

    public static void load() {
        instance = new ClientList();
    }

    public static ClientList getInstance() {
        return instance;
    }

    public void add(Client client) {
        list.add(client);
    }

    public void remove(Client client) {
        list.remove(client);
    }

    public int size() {
        return list.size();
    }

    public String getClientIDs() {
        String result = "";
        for (Client C : list) {
            result += C.getID();
        }
        return result;
    }

}
