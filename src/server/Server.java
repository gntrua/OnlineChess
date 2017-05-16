import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

class Server {
    int port = 10101;
    Server(int port){
	this.port = port;
	ServerSocket server = null;
	ChatMultiThreadServer multi_server = null;
	WatchMultiThreadServer multi_watch_server = null;
	Socket connect1 = null;
	Socket connect2 = null;
	Controller controller = new Controller();
	ChatClient watch_server = null;
		
	try{
	    multi_server = new ChatMultiThreadServer(port+1);
	    multi_server.start();
	    multi_watch_server = new WatchMultiThreadServer(port+2);
	    multi_watch_server.start();
	    watch_server = new ChatClient("localhost", port+2);
	    server = new ServerSocket(port);
	    System.out.println("port : " + server.getLocalPort());
	    connect1 = server.accept();
	    connect2 = server.accept();
	    PrintStream out1 = new PrintStream(connect1.getOutputStream());
	    PrintStream out2 = new PrintStream(connect2.getOutputStream());
	    BufferedReader in1 = new BufferedReader(new InputStreamReader(connect1.getInputStream()));
	    BufferedReader in2 = new BufferedReader(new InputStreamReader(connect2.getInputStream()));
	    out1.println("W");
	    out2.println("B");
	    connect1.setSoTimeout(50);
	    connect2.setSoTimeout(50);
	    while(true){
		try{
		    String echo = null;
		    if((echo = in1.readLine()) != null){
			System.out.println("echo = "+echo);
			String board = controller.move(echo);
			System.out.println("board = " + board);
			if(board.equals("promotion")){
			    out1.println(board);
			    String promo = in1.readLine();
			    board = controller.move(promo);
			}else if(board.equals("draw?")){
			    out2.println(board);
			    String draw_flag = in2.readLine();
			    board = controller.move(draw_flag);
			}
			out1.println(board);
			out2.println(board);
			watch_server.send(board);
		    }
		}catch(SocketTimeoutException e){}
		try{
		    String echo = null;
		    if((echo = in2.readLine()) != null){
			System.out.println("echo = "+ echo);
			String board = controller.move(echo);
			System.out.println("board = " + board);
			if(board.equals("promotion")){
			    out2.println(board);
			    String promo = in2.readLine();
			    board = controller.move(promo);
			}else if(board.equals("draw?")){
			    out1.println(board);
			    String draw_flag = in1.readLine();
			    board = controller.move(draw_flag);
			}
			out2.println(board);
			out1.println(board);
			watch_server.send(board);
		    }
		}catch(SocketTimeoutException e){}
	    }
	}catch(Exception e){
	    System.err.println(e);
	}
    }

    
    public static void main(String args[]){
	if(args.length == 1){
	    new Server(Integer.parseInt(args[0]));
	}else{
	    new Server(10101);
	}
    }

}


