import javax.swing.*;

class WatchIOThread extends Thread{
    ChatClient connect;
    WatchBoard temp;
    int mycolor;

    WatchIOThread(ChatClient connect, WatchBoard temp, int mycolor){
	this.connect = connect; this.temp = temp; this.mycolor = mycolor;
    }
   	
    public void run(){
	while(true){
	    String msg = null;
	    msg = connect.recv();
	    if(msg != null){
		temp.recvBoard(msg);
		System.out.println("board = " + msg);		
	    }
	}
    }
}
