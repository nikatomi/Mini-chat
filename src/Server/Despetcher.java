package Server;

import Server.ClientInfo;

import java.net.Socket;
import java.util.Vector;

public class Despetcher extends Thread{

    private Vector messages = new Vector();
    private Vector<ClientInfo> clients = new Vector<>();
    private Vector<ClientInfo>tempClient = new Vector<>();

    private synchronized String getMessagQueue(){
        while(messages.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String message =  (String) messages.get(0);
        messages.removeElementAt(0);
        return message;
    }

    private synchronized void setMessageAllClients(String message,ClientInfo client){
        for(int i = 0;i<clients.size();i++){
            if(clients.get(i) == client){
                clients.get(i).send.addMessage(message+" 074");
            }else {
                ClientInfo clientInfo = clients.get(i);
                String name = client.name.substring(0,client.name.indexOf("@name027!"));
                clientInfo.send.addMessage(name+" : "+message);
            }
        }
    }

    public synchronized void despechMessage(ClientInfo clientInfo,String message){
        Socket socket = clientInfo.socket;
        int index = message.indexOf("@name027!");
        if(index != -1){
            clientInfo.name = message;
            notify();
        }else {
            messages.add(message);
            tempClient.add(clientInfo);
            notify();
        }
    }

    public synchronized void addClient(ClientInfo clientInfo){
        clients.add(clientInfo);
    }

    public synchronized void deleteClient(ClientInfo clientInfo){
        int index = clients.indexOf(clientInfo);
        if(index != -1){
            clients.removeElementAt(index);
        }
    }

    @Override
    public void run() {

        while (true){
            String message = getMessagQueue();
            ClientInfo client = getTempClient();
            setMessageAllClients(message,client);
        }
    }

    private ClientInfo getTempClient() {
        ClientInfo clientInfo = tempClient.get(0);
        tempClient.removeElementAt(0);
        return clientInfo;
    }

    public Vector getVectorClient(){//----------
        return clients;
    }
}
