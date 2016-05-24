package Server;


import java.util.Vector;

public class ClientDespetcher extends Thread{

    Vector<ClientInfo>clients = new Vector<>();
    private Despetcher despetcher;

    public ClientDespetcher(Despetcher despetcher){
        this.despetcher = despetcher;
    }

    private synchronized ClientInfo getClientQueue(){
        if(clients.size()==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ClientInfo clientInfo = clients.get(0);
        clients.removeElementAt(0);
        return clientInfo;
    }

    private synchronized void setClientAllClient(ClientInfo clientInfo){
        Vector clients = despetcher.getVectorClient();
        for(int i = 0;i < clients.size();i++){
            if(clients.get(clients.size()-1)==clients.get(i)){
                ClientInfo clientVip = (ClientInfo) clients.get(i);
                for(int j = 0; j<clients.size();j++){
                    clientVip.sendClient.addClient((ClientInfo) clients.get(j));
                }
            }else {
                ClientInfo client = (ClientInfo) clients.get(i);
                client.sendClient.addClient(clientInfo);
            }
        }
    }

    @Override
    public void run() {
        while (true){
            ClientInfo clientInfo = getClientQueue();
            setClientAllClient(clientInfo);
        }
    }

    public synchronized void  addClientBar(ClientInfo clientInfo){
        clients.add(clientInfo);
        notify();
    }
}
