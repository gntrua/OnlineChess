import java.util.*;
import java.net.*;
import java.io.*;

class WatchMultiThreadServer extends Thread{
    int port = 22;
    WatchMultiThreadServer(int port){this.port = port;}

    public void run(){
	ServerObservable so = new ServerObservable();
	MultiServer server = new MultiServer(port);
	new WatchServerThread(server, so).start();
	while(true){
	    server = new MultiServer(server);
	    new WatchServerThread(server, so).start();
	}
    }
}

class WatchServerThread extends Thread implements Observer{
    MultiServer server;
    ServerObservable so;
    static String board = "24,23,22,25,26,22,23,24,21,21,21,21,21,21,21,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,31,31,31,31,31,31,31,31,34,33,32,35,36,32,33,34,";
;
    WatchServerThread(MultiServer server, ServerObservable so){
	this.server = server;
	this.so = so;
	so.addObserver(this);
    }

    public void update(Observable o, Object arg){
	String msg = so.get();
	server.send(msg);
    }

    public void run(){
	String msg;
	so.send(board);
	while((msg = server.recv()) != null){
	    board = msg;
	    so.send(msg);
	}
    }
    
}
