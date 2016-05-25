package Client;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class FrameTCP extends JFrame{
    public JTextPane textArea;
    public String message;
    public String name;
    public TextArea listClient;
    public Style defaultStyle;
    public Style yourMessage;
    public FrameTCP(){

        setSize(new Dimension(500,260));
        setSize(500,260);
        setTitle("Chat");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel outputPanel = new JPanel(new BorderLayout());
        textArea = new JTextPane();
        textArea.setPreferredSize(new Dimension(335,100));
        style();
        styleYourMessage();
        JScrollPane scrollPane = new JScrollPane(textArea);
       // textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.setBackground(Color.LIGHT_GRAY);
        outputPanel.add(scrollPane,BorderLayout.WEST);
        listClient = new TextArea(11,18);
        listClient.setBackground(Color.LIGHT_GRAY);
        outputPanel.add(listClient,BorderLayout.EAST);
        add(outputPanel,BorderLayout.NORTH);
        JPanel panel = new JPanel(new FlowLayout());
        final JTextField text = new JTextField(30);
        panel.add(text);
        JButton button = new JButton("OK");
        panel.add(button);
        add(panel,BorderLayout.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 message = text.getText();
                 text.setText("");
            }
        });
       // pack();

        setVisible(true);
    }
    private void style(){
        defaultStyle = textArea.addStyle("default",null);
        StyleConstants.setFontFamily(defaultStyle,"comic sans");
        StyleConstants.setFontSize(defaultStyle,18);
        StyleConstants.setForeground(defaultStyle,Color.magenta);
    }

    private void styleYourMessage(){
        yourMessage = textArea.addStyle("yourMessage",defaultStyle);
        StyleConstants.setForeground(yourMessage,Color.BLUE);
        StyleConstants.setItalic(yourMessage,true);
    }
}
