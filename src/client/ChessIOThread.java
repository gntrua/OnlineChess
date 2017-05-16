import javax.swing.*;

class ChessIOThread extends Thread{
    ChatClient connect;
    Board temp;
    int mycolor;
    String selectvalues[]={"Rook", "Knight", "Bishop", "Queen"}; //used by promotion
    ChessIOThread(ChatClient connect, Board temp, int mycolor){
	this.connect = connect; this.temp = temp; this.mycolor = mycolor;
    }
   	
    public void run(){
	while(true){
	    String msg = null;
	    msg = connect.recv();
	    if(msg != null){
		if(msg.equals("promotion")){
		    int promo;
		    do{
			promo = JOptionPane.showOptionDialog(temp,
							     "Promotion",
							     "choose",
							     JOptionPane.YES_NO_OPTION, 
							     JOptionPane.QUESTION_MESSAGE,
							     null,
							     selectvalues,
							     selectvalues[0]);
		    }while(promo == -1);
		    switch(promo){
		    case 0: msg = "4"; break;
		    case 1: msg = "3"; break;
		    case 2: msg = "2"; break;
		    case 3: msg = "5"; break;
		    default: break;
		    }
		    connect.send(msg);
		}else if(msg.equals("win," + mycolor)){
		    JOptionPane.showMessageDialog(null, "you win");
		    connect.send("restart");
		}else if(msg.equals("win,"+(5 - mycolor))){
		    JOptionPane.showMessageDialog(null, "you lose");
		    connect.send("restart");
		}else if(msg.equals("draw")){
		    JOptionPane.showMessageDialog(null, "draw");
		    connect.send("restart");
		}else if(msg.equals("draw?")){

		    int option = JOptionPane.showConfirmDialog(temp, 
							       "Do you want to draw?",
							       "draw", 
							       JOptionPane.YES_NO_OPTION, 
							       JOptionPane.WARNING_MESSAGE);
		    if (option == JOptionPane.YES_OPTION){
			connect.send("draw," + mycolor);
		    }else if (option == JOptionPane.NO_OPTION){
			connect.send("notdraw");
		    }
		}else{
		    temp.recvBoard(msg);
		    System.out.println("board = " + msg);
		}
	    }
	}
    }
}
