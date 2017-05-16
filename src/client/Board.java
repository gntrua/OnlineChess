import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
import java.io.*;

class Board extends JPanel implements MouseListener,MouseMotionListener,ActionListener{
    public static final int BLACK = 2;
    public static final int WHITE = 3;
    public static final int WALL = -1;
    public static final int WHITE_PONE = 31;
    public static final int WHITE_BISHOP = 32;
    public static final int WHITE_KNIGHT = 33;
    public static final int WHITE_ROOK = 34;
    public static final int WHITE_QUEEN = 35;
    public static final int WHITE_KING = 36;
    public static final int BLACK_PONE = 21;
    public static final int BLACK_BISHOP = 22;
    public static final int BLACK_KNIGHT = 23;
    public static final int BLACK_ROOK = 24;
    public static final int BLACK_QUEEN = 25;
    public static final int BLACK_KING = 26;
    private static final int WIDTH = 800;
    private static final int square_WIDTH = 100;
    private static final int square = 10;
    private static final int BLANK = 0; 
    Image wp = Toolkit.getDefaultToolkit().createImage("./piece/wpawn.gif");
    Image wb = Toolkit.getDefaultToolkit().createImage("./piece/wbishop.gif");
    Image wn = Toolkit.getDefaultToolkit().createImage("./piece/wknight.gif");
    Image wr = Toolkit.getDefaultToolkit().createImage("./piece/wrook.gif");
    Image wq = Toolkit.getDefaultToolkit().createImage("./piece/wqueen.gif");
    Image wk = Toolkit.getDefaultToolkit().createImage("./piece/wking.gif");
    Image bp = Toolkit.getDefaultToolkit().createImage("./piece/bpawn.gif");
    Image bb = Toolkit.getDefaultToolkit().createImage("./piece/bbishop.gif");
    Image bn = Toolkit.getDefaultToolkit().createImage("./piece/bknight.gif");
    Image br = Toolkit.getDefaultToolkit().createImage("./piece/brook.gif");
    Image bq = Toolkit.getDefaultToolkit().createImage("./piece/bqueen.gif");
    Image bk = Toolkit.getDefaultToolkit().createImage("./piece/bking.gif");
    private int[][] board = new int[square][square];
    int px,py;
    int pre_x, pre_y;
    int mycolor;
    boolean cflag = true; 
    boolean color_flag = false;
    int temp;
    ChatClient cl;
    int int_current_turn=3;
    AudioClip bgm = Applet.newAudioClip(getClass().getResource("Deep_Wound.wav"));
    AudioClip se = Applet.newAudioClip(getClass().getResource("button58.wav"));
    JButton resign;
    JButton draw;
    
    
    public Board(int mycolor, ChatClient cl, ChatClient chat_client) {
	this.cl = cl;
	this.mycolor = mycolor;
	setLayout(null);
	this.setBackground(new Color(152,92,39));
	initBoard();
	JTextArea area = new JTextArea(10,10);
	TextFieldInput text = new TextFieldInput(chat_client);
	resign = new JButton("resign");
	draw = new JButton("draw");
	text.setBounds(WIDTH + 150, WIDTH - 50, 200, 30);
	area.setBounds(WIDTH + 150, 20, 200, WIDTH - 100);
	resign.setBounds(WIDTH + 30, WIDTH/4, 100, 50);
	draw.setBounds(WIDTH + 30, WIDTH/4 + 80, 100, 50);
	area.setEditable(false);
	this.add(area);
	this.add(text);
	this.add(resign);
	this.add(draw);
	this.setOpaque(true);
	paintImmediately(0,0,1200,850);
	super.setVisible(true);
	resign.addActionListener(this);
	draw.addActionListener(this);
	this.addMouseListener(this);
	this.addMouseMotionListener(this);
	Thread input_thread = new Thread(new ChatIOThread(chat_client, area));
	input_thread.start();
      	bgm.loop();
    }
	    
    public void mousePressed(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) {
	int x = e.getX() / square_WIDTH + 1;
	int y = e.getY() / square_WIDTH + 1;
	int xx = x, yy = y;
	yy = (mycolor == BLACK)? 9 - y : y;
	xx = (mycolor == BLACK)? 9 - x : x;

	System.out.println("clicked ="+x+","+y+"\n"+"mycolor = "+mycolor+",current_turn = "+int_current_turn+",cflag = " + cflag);
	if(cflag == true && board[x][y] / 10 == mycolor){
	    color_flag = true;
	    cflag=!cflag;
	    px = xx;
	    py = yy;
	    pre_x = x;
	    pre_y = y;
	    repaint();
	}else if (cflag == false && board[x][y]/10 != mycolor){
	    color_flag = false;
	    cflag= !cflag;
	    System.out.println("send = " +px+","+py+","+xx+","+yy+","+2);
	    cl.send(px+","+py+","+xx+","+yy+","+2);
	    se.play();
	}else if(cflag == false && board[x][y] / 10 == mycolor){
	    color_flag = true;
	    pre_x = x;
	    pre_y = y;
	    px = xx;
	    py = yy;
	    repaint();
	}
    }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e)  { }
    public void mouseDragged(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }

    public void actionPerformed(ActionEvent e){
	if(e.getSource() == resign){
	    int option = JOptionPane.showConfirmDialog(this, 
						       "really?",
						       "resign", 
						       JOptionPane.YES_NO_OPTION, 
						       JOptionPane.WARNING_MESSAGE);
	    
	    if (option == JOptionPane.YES_OPTION){
		cl.send("resign," + mycolor);
	    }else if (option == JOptionPane.NO_OPTION){}
	}else if(e.getSource() == draw ){
	    int option = JOptionPane.showConfirmDialog(this, 
						       "really?",
						       "draw", 
						       JOptionPane.YES_NO_OPTION, 
						       JOptionPane.WARNING_MESSAGE);
	    
	    if (option == JOptionPane.YES_OPTION){
		cl.send("draw," + mycolor);
	    }else if (option == JOptionPane.NO_OPTION){}
	}	
    }

    public void recvBoard(String echo){
	String recvBoard[] = echo.split(",",-1);
	int count = 0;
	for(int y = 1; y < 9; y++){
	    for(int x = 1; x < 9; x++){
		board[(mycolor == WHITE)?x:9-x][(mycolor == WHITE)?y:9-y] = Integer.parseInt(recvBoard[count]);
		count++;
	    }
	}
	repaint();
    }

    private void initBoard() { // Initialization
	for (int y = 1; y < square - 1; y++) {
	    for (int x = 1; x < square - 1; x++) {
		board[y][x] = BLANK;
	    }
	}
	if(mycolor == WHITE){
	    for (int i = 1; i < square - 1; i++) {
		board[i][2] = BLACK_PONE;
		board[i][7] = WHITE_PONE;
	    }
	    board[1][1] = BLACK_ROOK;
	    board[2][1] = BLACK_KNIGHT;
	    board[3][1] = BLACK_BISHOP;
	    board[4][1] = BLACK_QUEEN;
	    board[5][1] = BLACK_KING;
	    board[6][1] = BLACK_BISHOP;
	    board[7][1] = BLACK_KNIGHT;
	    board[8][1] = BLACK_ROOK;

	    board[1][8] = WHITE_ROOK;
	    board[2][8] = WHITE_KNIGHT;
	    board[3][8] = WHITE_BISHOP;
	    board[4][8] = WHITE_QUEEN;
	    board[5][8] = WHITE_KING;
	    board[6][8] = WHITE_BISHOP;
	    board[7][8] = WHITE_KNIGHT;
	    board[8][8] = WHITE_ROOK;
	}else if(mycolor == BLACK){
	    for (int i = 1; i < square - 1; i++) {
		board[i][2] = WHITE_PONE;
		board[i][7] = BLACK_PONE;
	    }
	    board[1][1] = WHITE_ROOK;
	    board[2][1] = WHITE_KNIGHT;
	    board[3][1] = WHITE_BISHOP;
	    board[4][1] = WHITE_KING;
	    board[5][1] = WHITE_QUEEN;
	    board[6][1] = WHITE_BISHOP;
	    board[7][1] = WHITE_KNIGHT;
	    board[8][1] = WHITE_ROOK;

	    board[1][8] = BLACK_ROOK;
	    board[2][8] = BLACK_KNIGHT;
	    board[3][8] = BLACK_BISHOP;
	    board[4][8] = BLACK_KING;
	    board[5][8] = BLACK_QUEEN;
	    board[6][8] = BLACK_BISHOP;
	    board[7][8] = BLACK_KNIGHT;
	    board[8][8] = BLACK_ROOK;
	}
    }

    public void paintComponent(Graphics g) {  //draw Board
	super.paintComponent(g);
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, WIDTH, WIDTH);

	g.setColor(Color.BLACK);
	for (int y = 1; y < 9; y++) {
	    for (int x = 1; x < 9; x++) {
		if ((x + y) % 2 != 0) {
		    g.fillRect(square_WIDTH * (x - 1), square_WIDTH * (y - 1), square_WIDTH, square_WIDTH);
		}
	    }
	}

	g.setColor(Color.ORANGE);
	if(color_flag){
	    g.fillRect(square_WIDTH * (pre_x - 1), square_WIDTH * (pre_y - 1), square_WIDTH, square_WIDTH);
	}
	for (int y = 1; y < square - 1; y++) {
	    for (int x = 1; x < square - 1; x++) {
		switch (board[x][y]) {
		case BLACK_PONE:
		    g.drawImage(bp,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case WHITE_PONE:
		    g.drawImage(wp,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case BLACK_ROOK:
		    g.drawImage(br,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case WHITE_ROOK:
		    g.drawImage(wr,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case BLACK_KNIGHT:
		    g.drawImage(bn,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case WHITE_KNIGHT:
		    g.drawImage(wn,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case BLACK_BISHOP:
		    g.drawImage(bb,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case WHITE_BISHOP:
		    g.drawImage(wb,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case BLACK_QUEEN:
		    g.drawImage(bq,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case WHITE_QUEEN:
		    g.drawImage(wq,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case BLACK_KING:
		    g.drawImage(bk,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		case WHITE_KING:
		    g.drawImage(wk,(x-1)*square_WIDTH,(y-1)*square_WIDTH,square_WIDTH,square_WIDTH,this);
		    break;
		default:
		    break;
		}
	    }
	}
    }
}
