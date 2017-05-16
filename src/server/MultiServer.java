import java.util.*;
import java.net.*;
import java.io.*;

class ServerObservable extends Observable{
    String msg = null;
    public void send(String msg){
	this.msg = msg;
	setChanged();
	notifyObservers();
    }
    
    public String get(){
	return msg;
    }
}

class MultiServer{
    ServerSocket server = null;
    Socket connect = null;
    PrintWriter out = null;
    BufferedReader in = null;
    int port = 22;
    MultiServer(){}
    MultiServer(int port){open(port);}
    MultiServer(MultiServer cs){server = cs.getServerSocket(); open(cs.getPortNo());}
    ServerSocket getServerSocket(){return server;}
    int getPortNo(){return port;}

    boolean open(int port){
	this.port = port;
	try{
	    if(server == null){ server = new ServerSocket(port);}
	}catch(IOException e){System.err.println(e); System.exit(1);}
	try{
	    connect = server.accept();
	    out = new PrintWriter(connect.getOutputStream(),true);
	    in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
	}catch(IOException e){System.err.println(e); System.exit(1);}
	return true;
    }

    boolean send(String msg){
	if(out == null){return false;}
	out.println(msg);
	return true;
    }

    String recv(){
	String msg = null;
	if(in == null){return null;}
	try{
	    msg = in.readLine();
	}catch(SocketTimeoutException e){return null;
	}catch(IOException e){System.err.println(e); System.exit(1);}
	return msg;
    }

    int setTimeout(int time){
	try{
	    connect.setSoTimeout(time);
	}catch(SocketException e){System.err.println(e); System.exit(1);}
	return time;
    }

    void close(){
	try{
	    in.close(); out.close();
	    connect.close(); server.close();
	}catch(IOException e){System.err.println(e); System.exit(1);}
	in = null; out = null;
	connect = null; server = null;
    }    
}
