package Server;

import Server.ClientInfo;

import java.net.Socket;
import java.util.Vector;

public class Despetcher extends Thread{

    private Vector messages = new Vector();
    private Vector<ClientInfo> clients = new Vector<>();

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

    private synchronized void setMessageAllClients(String message){
        for(int i = 0;i<clients.size();i++){
            ClientInfo clientInfo = clients.get(i);
            clientInfo.send.addMessage(message);
        }
    }

    public synchronized void despechMessage(ClientInfo clientInfo,String message){
        Socket socket = clientInfo.socket;
        String name = socket.getInetAddress().getHostAddress();
        messages.add(name+" : "+message);
        notify();
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
            setMessageAllClients(message);
        }
    }

    public Vector getVectorClient(){//----------
        return clients;
    }
}
