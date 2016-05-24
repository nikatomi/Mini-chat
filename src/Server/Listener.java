package Server;

import Server.ClientInfo;
import Server.Despetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Listener extends Thread {

    private BufferedReader reader;
    private ClientInfo clientInfo;
    private Despetcher despetcher;

    public Listener(ClientInfo clientInfo, Despetcher despetcher) throws IOException {
        this.clientInfo = clientInfo;
        this.despetcher = despetcher;
        reader = new BufferedReader(new InputStreamReader(clientInfo.socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while(!isInterrupted()) {
                String message = reader.readLine();
               // System.out.println("+++++");
                if(message == null)
                    break;
                despetcher.despechMessage(clientInfo, message);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("Связь разорвана");
        }
        clientInfo.send.interrupt();
        despetcher.deleteClient(clientInfo);
    }
}
