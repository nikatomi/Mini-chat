package Server;

import Server.ClientInfo;
import Server.Despetcher;
import Server.Listener;
import Server.Send;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Serv{
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        Despetcher despetcher = new Despetcher();

        ClientDespetcher clientDespetcher = new ClientDespetcher(despetcher); //-------
        despetcher.start();
        clientDespetcher.start();//-------

        while(true){
            System.out.println("Connect...");
            Socket serverSocket = server.accept();
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.socket = serverSocket;
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            System.out.println(InetAddress.getLocalHost().getHostAddress());

            SendClient sendClient = new SendClient(clientInfo);//-------
            clientInfo.sendClient = sendClient;//--------

            Listener listener = new Listener(clientInfo,despetcher);
            clientInfo.listener = listener;
            Send send = new Send(clientInfo,despetcher);
            clientInfo.send = send;
            listener.start();
            send.start();
            sendClient.start();//-------
            despetcher.addClient(clientInfo);
            clientDespetcher.addClientBar(clientInfo);
        }

    }
}