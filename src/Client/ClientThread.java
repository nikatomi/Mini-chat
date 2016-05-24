package Client;


import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class ClientThread extends Thread{

    private PrintStream printStream;
    private FrameTCP frameTCP;
    public ClientThread(PrintStream printStream,FrameTCP frameTCP){
        this.printStream = printStream;
        this.frameTCP = frameTCP;
    }

    @Override
    public void run() {
        //Scanner sc = new Scanner(System.in);
        while (!isInterrupted()){
           // String messag = sc.nextLine();
            String messag = frameTCP.message;
            if(messag != null) {
                printStream.println(messag);
                printStream.flush();
                frameTCP.message = null;
            }
        }
    }
}
