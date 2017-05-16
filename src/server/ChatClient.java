import java.io.*;
import java.net.*;
import java.util.*;

class ChatClient{
    Socket sock = null;
    BufferedReader in = null;
    PrintWriter out = null;

    ChatClient(){}
    ChatClient(String host, int port){open(host, port);}

    boolean open(String host, int port){
	try{
	    sock = new Socket(InetAddress.getByName(host), port);
	    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	    out = new PrintWriter(sock.getOutputStream(),true);
	}catch(UnknownHostException e){System.out.println(e); return false;
	}catch(ConnectException e){System.out.println(e); return false;
	}catch(IOException e){System.out.println(e); return false;}
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
	    sock.setSoTimeout(time);
	}catch(SocketException e){System.err.println(e); System.exit(1);}
	return time;
    }

    void close(){
	try{
	    in.close(); out.close(); sock.close();
	}catch(IOException e){System.err.println(e); System.exit(1);}
	in = null; out = null; sock = null;
    }
}
