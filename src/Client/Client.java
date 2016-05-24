package Client;



import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static void main(String[] args) throws IOException {
        FrameTCP frameTCP = new FrameTCP();
        Socket socket = new Socket("127.0.0.1",8080);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        ClientThread clientThread = new ClientThread(printStream,frameTCP);
        clientThread.setDaemon(true);
        clientThread.start();


        String messag;
        while((messag = reader.readLine())!= null){
            Pattern p = Pattern.compile("([0-9]{3}\\.)([0-9]\\.|[0-9]{3}\\.|[0-9]{2}\\.)([0-9]\\.|[0-9]{3}\\.|[0-9]{2}\\.)([0-9]|[0-9]{3}|[0-9]{2})");
            Matcher mm = p.matcher(messag);
            boolean b = mm.matches();
           // int index = messag.indexOf("Server.ClientInfo@");
            System.out.println(messag);
            if(b){
                frameTCP.listClient.insert(messag+"\n",0);
            }else {
                String m = messag + "\n";
                Document doc = frameTCP.textArea.getDocument();
                try {
                    doc.insertString(doc.getLength(), m, frameTCP.defaultStyle);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

