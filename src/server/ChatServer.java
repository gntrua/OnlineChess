import java.util.*;
import java.net.*;
import java.io.*;

class ChatMultiThreadServer extends Thread{
    int port = 22;
    ChatMultiThreadServer(int port){this.port = port;}
    public void run(){
	ServerObservable so = new ServerObservable();
	MultiServer server = new MultiServer(port);
	new ChatServerThread(server, so).start();
	while(true){
	    server = new MultiServer(server);
	    new ChatServerThread(server, so).start();
	}
    }
}


class ChatServerThread extends Thread implements Observer{
    MultiServer server;
    ServerObservable so;
    String name = null;
    public ChatServerThread(MultiServer server, ServerObservable so){
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
	if(name == null){
	    name = server.recv();
	    so.send("["+name+" has been joined !]");
	}
	while((msg = server.recv())!=null){
	    so.send("["+name+"] "+msg);
	}
    }
}
