import java.net.*;
import java.io.*;
import javax.swing.*;

class ChatIOThread extends Thread{
    ChatClient connect = null;
    BufferedReader in = null;
    JTextArea text = null;
    public ChatIOThread(ChatClient connect, JTextArea text){
	this.connect = connect;
	this.text = text;	
    }

    public void run(){
	try{
	    while(true){
		String msg = null;
		if((msg = connect.recv()) != null){
		    text.setText(text.getText() + "\n"+msg);
		}
	    }
	}catch(Exception e){System.err.println(e); System.exit(1);
	}
    }
}
