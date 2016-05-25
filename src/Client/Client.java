package Client;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
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

            int indexName = messag.indexOf("@name027!");
            if(indexName != -1){
                String name = messag.substring(0,indexName);
                frameTCP.listClient.insert(name+"\n",0);
            }else {
                int index = messag.indexOf(" 074");
                    if(index != -1) {
                        String m = messag.substring(0,index)+"\n";
                        insertMessage(frameTCP,m,frameTCP.yourMessage);
                    }else {
                        String m = messag + "\n";
                        insertMessage(frameTCP, m, frameTCP.defaultStyle);
                    }
            }
        }
    }


    private static void insertMessage(FrameTCP frameTCP, String m, Style style){
        Document doc = frameTCP.textArea.getDocument();
        try {
            doc.insertString(doc.getLength(), m, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    }


