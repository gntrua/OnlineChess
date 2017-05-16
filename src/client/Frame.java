import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
import java.io.*;

public class Frame extends JFrame  {
    int mycolor = 0;
    int port = 22;
    String host;
    String name;
    String recv = null;
    ChatClient cl;
    ChatClient chat_client;
    ChessIOThread ioThread;
    int game_mode = 0;
    Entrance entrance;
    String data[];
    public Frame(String host, int port){
	this(host, port, 0);
    }

    public Frame(String h, int p, int g) {
	this.port = p;
	this.host = h;
	this.game_mode = g;
	
	entrance = new Entrance();
	this.add(entrance);
	JButton submit = new JButton("submit");
	entrance.add(submit);

	submit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    data = entrance.getInfo();
		    System.out.println(data);
		    if(data != null){
			name = data[0];
			game_mode = Integer.parseInt(data[1]);
			port += Integer.parseInt(data[2]) - 1;
			chat_client = new ChatClient(host, port+1);
			chat_client.send(name);
			if(game_mode == 0){
			    cl = new ChatClient(host,port);
			    while(mycolor == 0){
				String color = cl.recv();
				if(color != null){
				    mycolor = (color.equals("W"))? 3 : 2;
				    System.out.println(mycolor);
				}
			    }
			    Board JBoard = new Board(mycolor, cl, chat_client);	
			    ChessIOThread ioThread = new ChessIOThread(cl ,JBoard,mycolor);
			    ioThread.start();
			    Frame.this.add(JBoard);
			    JBoard.setVisible(true);
			}else if(game_mode == 1){
			    mycolor = 3;
			    cl = new ChatClient(host, port + 2);
			    WatchBoard JBoard = new WatchBoard(mycolor, cl, chat_client);
			    WatchIOThread ioThread = new WatchIOThread(cl, JBoard, mycolor);
			    ioThread.start();
			    Frame.this.add(JBoard);
			    JBoard.setVisible(true);
			}
			entrance.setVisible(false);

			Frame.this.setSize(1200,850);
		    }
		}
	    });

	this.setSize(200,300);
	this.setTitle("chess vs");
	setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    


    public static void main(String args[]) {
	try{
	    if(args.length == 4){
		new Frame(args[0], Integer.parseInt(args[1]),Integer.parseInt(args[3]));
	    }else if(args.length == 2){
		new Frame(args[0],Integer.parseInt(args[1]));
	    }else if(args.length == 1){
		new Frame(args[0],10101);
	    }else{
		new Frame("localhost",10101);
	    }
	}catch(ArrayIndexOutOfBoundsException e){
	    new Frame("localhost", 10101);
	}
    }
}




