package Server;

import Client.Client;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

public class SendClient extends Thread{

    private PrintStream print;
    private Vector<ClientInfo>clients = new Vector<>();
    private ClientInfo clientInfo;


    public SendClient(ClientInfo clientInfo) throws IOException {
        this.clientInfo = clientInfo;
        Socket socket = clientInfo.socket;
        print = new PrintStream(socket.getOutputStream());

    }

    public synchronized void addClient(ClientInfo clientInfo){
        clients.add(clientInfo);
        notify();
    }

    @Override
    public void run() {
        while (true){
            ClientInfo client = getStringNameClient();
            sendNameClientBar(client);
        }
    }

    private synchronized void sendNameClientBar(ClientInfo client) {
        if(client.name == "unknow"){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String st = client.name;
        print.println(st);
        print.flush();
    }

    private synchronized ClientInfo getStringNameClient() {
        if(clients.size()==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ClientInfo clientInfo = clients.get(0);
        //String client = clientInfo.socket.getInetAddress().getHostName();
        clients.removeElementAt(0);
        return clientInfo;
    }
}
