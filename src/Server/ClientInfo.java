package Server;

import java.net.InetAddress;
import java.net.Socket;

public class ClientInfo {
    public Socket socket;
    public Listener listener;
    public Send send;
    public SendClient sendClient;//------
}
