package Server;

import Server.ClientInfo;
import Server.Despetcher;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

public class Send extends Thread {
    private Vector messages = new Vector();
    private PrintStream printStream;
    private ClientInfo clientInfo;
    private Despetcher despetcher;

    public Send(ClientInfo clientInfo,Despetcher despetcher) throws IOException {
        this.clientInfo = clientInfo;
        this.despetcher = despetcher;
        printStream = new PrintStream(clientInfo.socket.getOutputStream());
    }

    public synchronized void addMessage(String message){
        messages.add(message);
        notify();
    }

    private void sendMessageToClient(String message){
        printStream.println(message);
        printStream.flush();
    }

    private synchronized String getMessageQueue(){
        while(messages.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String message = (String) messages.get(0);
        messages.removeElementAt(0);
        return message;
    }


    @Override
    public void run() {
        while(!interrupted()) {
            String message = getMessageQueue();
            sendMessageToClient(message);
        }
        clientInfo.listener.interrupt();
        despetcher.deleteClient(clientInfo);
    }
}
