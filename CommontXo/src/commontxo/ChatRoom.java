package commontxo;

import java.util.ArrayList;

public class ChatRoom {

    private String chat;
    private ClientCallBack otherClient;

    /**
     * @return the otherClients
     */
    public ClientCallBack getOtherClients() {
        return otherClient;
    }

    /**
     * @param otherClient the otherClients to set
     */
    public void setOtherClient(ClientCallBack otherClients) {
        this.otherClient = otherClients;
    }

    public ChatRoom(String myChat, ClientCallBack other) {
        chat = myChat;
        otherClient = other;

    }

    /**
     * @return the chat
     */
    public String getChat() {
        return chat;
    }

    /**
     * @param chat the chat to set
     */
    public void setChat(String chat) {
        this.chat = chat;
    }

}
