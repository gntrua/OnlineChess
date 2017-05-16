class Controller{

    public static final int BLACK = 2;
    public static final int WHITE = 3;

    public static final int WALL = -1;
    public static final int WHITE_PAWN = 31;
    public static final int WHITE_BISHOP = 32;
    public static final int WHITE_KNIGHT = 33;
    public static final int WHITE_ROOK = 34;
    public static final int WHITE_QUEEN = 35;
    public static final int WHITE_KING = 36;
    public static final int BLACK_PAWN = 21;
    public static final int BLACK_BISHOP = 22;
    public static final int BLACK_KNIGHT = 23;
    public static final int BLACK_ROOK = 24;
    public static final int BLACK_QUEEN = 25;
    public static final int BLACK_KING = 26;

	
    public int piece = 0;
    public int turn = WHITE;
    public int promotion;

    public boolean BRR_flag = true;//black,Right,Rook//dont move = true
    public boolean BLR_flag = true;//black,Left,Rook
    public boolean BK_flag = true;//black king 
    public boolean WRR_flag = true;//White,Right,Rook
    public boolean WLR_flag = true;//White,Left,Rook
    public boolean WK_flag = true;//White,KING
    public boolean Castling;//do Castling? 
    public boolean enpassant = false;
    public boolean p_flag = false;//send promotion?
    public boolean bdraw = false;
    public boolean wdraw = false;
    public boolean move_flag = true;


	
    public int[][] board = new int [10][10]; //board_size = 8*8
    public int[][] pre_board = new int [10][10];
    int[] pre_move = new int [4];
    public final int dir[][] = {{-1,-1},{0,-1},{1,-1},//dir[][] = {{0,1}} => dir[0][0] = 0,dir[0][1] = 1
				{-1, 0},		  {1,0},
				{-1, 1},{0, 1},{1,1}
    };

    public Controller(){
	init();
    }

    public String restart(){
	init();
        BRR_flag = true;//black,Right,Rook//dont move = true
	BLR_flag = true;//black,Left,Rook
	BK_flag = true;//black king 
	WRR_flag = true;//White,Right,Rook
	WLR_flag = true;//White,Left,Rook
	WK_flag = true;//White,KING
	p_flag = false;//send promotion?
	wdraw = false;
	bdraw = false;
	turn = WHITE;
	return StringBoard();
    }



    public void init(){
	boolean board_flag = true; 
	for(int x= 0;x<10;x++){
	    for(int y = 0;y < 10;y++ ){
		if(x==0||y==0||x==9||y==9){
		    board[x][y] = -1;
		}else{
		    board[x][y] = 0;
		}
	    }
	}
	for(int i = 1;i<9;i++){
	    board[i][2] = BLACK_PAWN;
	    board[i][7] = WHITE_PAWN;
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

    }

    public boolean out(int x,int y){
	if( x <= 0 || y <= 0 || x >= 9 || y >= 9){
	    return true;
	} else{return false;}
    }

    public boolean Check(int x,int y){
	boolean check = true;
	//pawn
	if(turn == BLACK){
	    if(board[x+1][y+1] == WHITE_PAWN || board[x-1][y+1] == WHITE_PAWN ){
		check = false;
	    }
	}else{
	    if(board[x+1][y-1] == BLACK_PAWN || board[x-1][y-1] == BLACK_PAWN ){
		check = false;
	    }
	}

	//BISHOP//QUEEN
	for(int i = 0;i < 8;i++){
	    if(out(x+i,y+i))break;
	    if(board[x+i][y+i] != 0 && (board[x+i][y+i] != (5-turn)*10+2 || board[x+i][y+i] != (5-turn)*10+5))break;
	    if(board[x+i][y+i] == (5-turn)*10+2 || board[x+i][y+i] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x+i][y+i] != 0){
		break;
	    }
	}

	for(int i = 0;i < 8;i++){
	    if(out(x-i,y-i))break;
	    if(board[x-i][y-i] != 0 && (board[x-i][y-i] != (5-turn)*10+2 || board[x-i][y-i] != (5-turn)*10+5))break;
	    if(board[x-i][y-i] == (5-turn)*10+2 || board[x-i][y-i] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x-i][y-i] != 0){
		break;
	    }
	}

	for(int i = 0;i < 8;i++){
	    if(out(x+i,y-i))break;
	    if(board[x+i][y-i] != 0 && (board[x+i][y-i] != (5-turn)*10+2 || board[x+i][y-i] != (5-turn)*10+5))break;		
	    if(board[x+i][y-i] == (5-turn)*10+2 || board[x+i][y-i] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x+i][y-i] != 0){
		break;
	    }
	}

	for(int i = 0;i < 8;i++){
	    if(out(x-i,y+i))break;
	    if(board[x-i][y+i] != 0 && (board[x-i][y+i] != (5-turn)*10+2 || board[x-i][y+i] != (5-turn)*10+5))break;
		
	    if(board[x-i][y+i] == (5-turn)*10+2 || board[x-i][y+i] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x-i][y+i] != 0){
		break;
	    }
	}

	//ROOK//QUEEN
	for(int i = 0;i < 8;i++){
	    if(out(x,y-i))break;
	    if(board[x][y-i] != 0 && (board[x][y-i] != (5-turn)*10+4 || board[x][y-i] != (5-turn)*10+5))break;
		
	    if(board[x][y-i] == (5-turn)*10+4 || board[x][y-i] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x][y-i] != 0){
		break;
	    }
	}

	for(int i = 0;i < 8;i++){
	    if(out(x,y+i))break;
	    if(board[x][y+i] != 0 && (board[x][y+i] != (5-turn)*10+4 || board[x][y+i] != (5-turn)*10+5))break;
		
	    if(board[x][y+i] == (5-turn)*10+4 || board[x][y+i] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x][y+i] != 0){
		break;
	    }
	}

	for(int i = 0;i < 8;i++){
	    if(out(x-i,y))break;
	    if(board[x-i][y] != 0 && (board[x-i][y] != (5-turn)*10+4 || board[x-i][y] != (5-turn)*10+5))break;
		
	    if(board[x-i][y] == (5-turn)*10+4 || board[x-i][y] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x-i][y] != 0){
		break;
	    }
	}

	for(int i = 0;i < 8;i++){
	    if(out(x+i,y))break;
	    if(board[x+i][y] != 0 && (board[x+i][y] != (5-turn)*10+4 || board[x+i][y] != (5-turn)*10+5))break;
		
	    if(board[x+i][y] == (5-turn)*10+4 || board[x+i][y] == (5-turn)*10+5){	
		check = false;
	    }else if(board[x+i][y] != 0){
		break;
	    }
	}

	//KNIGHT
	if(!out(x-2,y)){
	    if(board[x-2][y+1] == (5-turn)*10+3 ||board[x-2][y-1] == (5-turn)*10+3){
		check = false;
	    }
	}
	if(!out(x+2,y)){
	    if(board[x+2][y+1] == (5-turn)*10+3 || board[x+2][y-1] == (5-turn)*10+3 ){
		check = false;
	    }	
	}
	if(!out(x,y-2)){
	    if(board[x+1][y-2] == (5-turn)*10+3 ||board[x-1][y-2] == (5-turn)*10+3){
		check = false;
	    }
	} 
	if(!out(x,y+2)){
	    if(board[x+1][y+2] == (5-turn)*10+3 || board[x-1][y+2] == (5-turn)*10+3){
		check = false;
	    }
	}

	return check;
    }

    public boolean Pawn_flag(int x1,int y1){
	boolean pawn_flag = false;
	if((board[x1][y1] == BLACK_PAWN && y1 == 2) || (board[x1][y1] == WHITE_PAWN && y1 == 7))
	    {
		pawn_flag = true;		
	    }
	return pawn_flag;
    }


    public boolean canMove(int x1,int y1,int x2,int y2){
	boolean canmove = false;
	enpassant = false;//need to change
	Castling = false;
	if(turn == BLACK){
	    switch(board[x1][y1]){
	    case BLACK_PAWN:
		if(out(x2,y2)){break;}
		if(Pawn_flag(x1,y1) && y2-y1 == 2 && x2-x1 == 0){
		    canmove = true;
		}else{
		    if(board[x1][y1+1] == 0 && x2-x1 == 0 && y2-y1 == 1){
			canmove = true;
		    }
		    if(board[x1+1][y1+1] / 10 == WHITE && x2-x1 == 1 && y2-y1 == 1){
			canmove = true;
		    } 
		    if(board[x1-1][y1+1]/10 == WHITE && x2-x1 == -1 && y2-y1 == 1){
			canmove = true;
		    }
		}
		if(board[pre_move[2]][pre_move[3]] == WHITE_PAWN && pre_move[3]-pre_move[1] == -2 && pre_move[2] == x2){
		    if(y1 == pre_move[3] && Math.abs(pre_move[2] - x1) == 1){
			if(y2-y1 == 1 && Math.abs(x2 - x1) == 1){
			    
			    enpassant = true;
			    canmove = true;
			}
		    }
		}
		break;
	    case BLACK_BISHOP:
		if(out(x2,y2)){break;}
		if(!(Math.abs(x2-x1) == Math.abs(y2-y1))){
		    break;
		}

		boolean dirx = false,diry = false;
		if(x2-x1 >= 0){dirx = true;}
		if(y2-y1 >= 0){diry = true;}

		for(int k = 1;k<Math.abs(x1-x2)+1;k++){
		    if(dirx&&diry){
			if(board[x1+k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }else if(dirx&&!diry){
			if(board[x1+k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }else if(!dirx&&diry){
			if(board[x1-k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }else if(!dirx&&!diry){
			if(board[x1-k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }
		}
		break;

	    case BLACK_KNIGHT:
		if(out(x2,y2)){break;}
		if((Math.abs(y2 -y1) == 2 && Math.abs(x2-x1) == 1) ||(Math.abs(y2 -y1) == 1 && Math.abs(x2-x1) == 2)){
		    if(board[x2][y2] != BLACK){
			canmove = true;
		    }
		}else{}

		break;

	    case BLACK_ROOK:
		if(out(x2,y2)){break;}
		if(y1 == y2){
		    if(x2>x1){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1+i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(x1>x2){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1-i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else if(x1 == x2){
		    if(y2>y1){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1+i] == 0||(i == Math.abs(y2-y1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(y1>y2){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1-i] == 0||(i == Math.abs(y2-y1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else{break;}
		if(canmove){
		    if(x1 == 1){
			BLR_flag = false;
		    }else if(x1 == 8){
			BRR_flag = false;
		    } 
		}
		break;		

	    case BLACK_QUEEN:
		if(out(x2,y2)){break;}
		if(y1 == y2){
		    if(x2>x1){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1+i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(x1>x2){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1-i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else if(x1 == x2){
		    if(y2>y1){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1+i] == 0||(i == Math.abs(y2-y1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(y1>y2){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1-i] == 0||(i == Math.abs(y2-y1)&&board[x2][y2]/10 == WHITE)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else{
		    if(!(Math.abs(x2-x1) == Math.abs(y2-y1))){
			break;
		    }

		    boolean dirx2 = false,diry2 = false;
		    if(x2-x1 >= 0){dirx2 = true;}
		    if(y2-y1 >= 0){diry2 = true;}

		    for(int k = 1;k<Math.abs(x1-x2)+1;k++){
			if(dirx2&&diry2){
			    if(board[x1+k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}else if(dirx2&&!diry2){
			    if(board[x1+k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}else if(!dirx2&&diry2){
			    if(board[x1-k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}else if(!dirx2&&!diry2){
			    if(board[x1-k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == WHITE)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}
		break;
	    case BLACK_KING:
		if(out(x2,y2)){break;}
		if((Math.abs(x2-x1) == 1&&Math.abs(y2-y1) == 0)||(Math.abs(x2-x1) == 0&&Math.abs(y2-y1) == 1)||(Math.abs(x2-x1) == 1&&Math.abs(y2-y1) == 1)){
		    if(Check(x2,y2)&&board[x2][y2] != BLACK){	
			BK_flag = false;
			canmove  = true;
		    }
		}
		if(Math.abs(x2-x1) == 2){
		    if(BK_flag && BLR_flag){
			if(board[2][1] == 0 && board[3][1] == 0 && board[4][1] == 0){
			    if(Check(2,1) && Check(3,1) && Check(4,1)){
				Castling = true;
				canmove = true;
			    }
			}
		    }
		    if(BK_flag && BRR_flag){
			if(board[6][1] == 0 && board[7][1] == 0){
			    if(Check(6,1) && Check(7,1)){
				Castling = true;
				canmove = true;
			    }
			}
		    }
		}
		break;
	    default:
		break;
	    }
	}else if(turn == WHITE){
	    switch(board[x1][y1]){
	    case WHITE_PAWN:
		if(out(x2,y2)){break;}
		if(Pawn_flag(x1,y1) && x2-x1 == 0 && y2-y1 == -2){
		    canmove = true;
		}else{
		    if(board[x1][y1-1] == 0 && x2-x1 == 0 && y2-y1 == -1){
			canmove = true;
		    }
		    if(board[x1+1][y1-1] / 10 == BLACK && x2-x1 == 1 && y2-y1 == -1){
			canmove = true;
		    }

		    if(board[x1-1][y1-1]/10 == BLACK && x2-x1 == -1 && y2-y1 == -1){
			canmove = true;
		    }
		}
		if(board[pre_move[2]][pre_move[3]] == BLACK_PAWN && pre_move[3]-pre_move[1] == 2 && pre_move[2] == x2){
		    if(y1 == pre_move[3] && Math.abs(pre_move[2] - x1) == 1){
			if(y2-y1 == -1 && Math.abs(x2 - x1) == 1){
			    enpassant = true;
			    canmove = true;
			}
		    }
		}
		break;
	    case WHITE_BISHOP:
		if(out(x2,y2)){break;}
		if(!(Math.abs(x2-x1) == Math.abs(y2-y1))){
		    break;
		}

		boolean dirx1 = false,diry1 = false;
		if(x2-x1 >= 0){dirx1 = true;}
		if(y2-y1 >= 0){diry1 = true;}

		for(int k = 1;k<Math.abs(x1-x2)+1;k++){
		    if(dirx1&&diry1){
			if(board[x1+k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }else if(dirx1&&!diry1){
			if(board[x1+k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }else if(!dirx1&&diry1){
			if(board[x1-k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }else if(!dirx1&&!diry1){
			if(board[x1-k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
			    if(k == Math.abs(x1-x2)){
				canmove = true;
			    }
			}else{break;}
		    }
		}
		break;

	    case WHITE_KNIGHT:
		if(out(x2,y2)){break;}
		if((Math.abs(y2 - y1) == 2 && Math.abs(x2-x1) == 1) ||(Math.abs(y2 -y1) == 1 && Math.abs(x2-x1) == 2)){
		    if(board[x2][y2] != WHITE){
			canmove = true;
		    }
		}else{}

		break;
	    case WHITE_ROOK:
		if(out(x2,y2)){break;}
		if(y1 == y2){
		    if(x2>x1){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1+i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(x1>x2){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1-i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else if(x1 == x2){
		    if(y2>y1){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1+i] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(y1>y2){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1-i] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else{
		    break;
		}
		if(canmove){
		    if(x1 == 1){
			WLR_flag = false;
		    }else if(x1 == 8){
			WRR_flag = false;
		    } 
		}
		break;
	    case WHITE_QUEEN:
		if(out(x2,y2)){break;}
		if(y1 == y2){
		    if(x2>x1){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1+i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(x1>x2){
			for(int i = 1;i<Math.abs(x2-x1)+1;i++){
			    if(board[x1-i][y1] == 0||(i == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(x2-x1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else if(x1 == x2){
		    if(y2>y1){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1+i] == 0||(i == Math.abs(y2-y1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		    if(y1>y2){
			for(int i = 1;i<Math.abs(y2-y1)+1;i++){
			    if(board[x1][y1-i] == 0||(i == Math.abs(y2-y1)&&board[x2][y2]/10 == BLACK)){
				if(i == Math.abs(y2-y1)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }
		}else{
		    if(!(Math.abs(x2-x1) == Math.abs(y2-y1))){
			break;
		    }

		    boolean dirx3 = false,diry3 = false;
		    if(x2-x1 >= 0){dirx3 = true;}
		    if(y2-y1 >= 0){diry3 = true;}

		    for(int k = 1;k<Math.abs(x1-x2)+1;k++){
			if(dirx3&&diry3){
			    if(board[x1+k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}else if(dirx3&&!diry3){
			    if(board[x1+k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}else if(!dirx3&&diry3){
			    if(board[x1-k][y1+k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}else if(!dirx3&&!diry3){
			    if(board[x1-k][y1-k] == 0||(k == Math.abs(x2-x1)&&board[x2][y2]/10 == BLACK)){
				if(k == Math.abs(x1-x2)){
				    canmove = true;
				}
			    }else{break;}
			}
		    }

		}
		break;
	    case WHITE_KING:
		if(out(x2,y2)){break;}
		if((Math.abs(x2-x1) == 1&&Math.abs(y2-y1) == 0)||(Math.abs(x2-x1) == 0&&Math.abs(y2-y1) == 1)||(Math.abs(x2-x1) == 1&&Math.abs(y2-y1) == 1)){
		    if(Check(x2,y2)&&board[x2][y2] != WHITE){	
			WK_flag = false;
			canmove  = true;
		    }
		}
		if(Math.abs(x2-x1) == 2){
		    if(WK_flag && WLR_flag){
			if(board[2][8] == 0 && board[3][8] == 0 && board[4][8] == 0){
			    if(Check(5,8) && Check(4,8) && Check(3,8)){
				Castling = true;
				canmove = true;
			    }
			}
		    }
		    if(WK_flag && WRR_flag){
			if(board[6][8] == 0 && board[7][8] == 0){
			    if(Check(6,8) && Check(7,8)){
				Castling = true;
				canmove = true;
			    }
			}
		    }
		}
		break;
	    default:
		break;
	    }
			
	}else{
	    System.err.println();
	}
	
	if(canmove){
	    int temp = board[x2][y2];
	    board[x2][y2] = board[x1][y1];
	    board[x1][y1] = 0;
	    for(int y = 1;y<9;y++){
		for(int x = 1;x<9;x++){
		    if(board[x][y] == turn*10+6 && !Check(x,y)){
			canmove = false;
			break;
		    }
		}
		if(!canmove)break;
	    }
	    board[x1][y1] = board[x2][y2];
	    board[x2][y2] = temp;
	}
	if(!move_flag){
	    canmove = false;
	}
	return canmove;
    }

    public boolean stalemate(){
	boolean check_flag = false;
	boolean next_piece = true;
	boolean stalemate = false;
	for(int i = 1;i<9;i++){
	    for(int j = 1;j<9; j++){
		if(board[i][j] == turn*10+6 && !Check(i,j)){
		    check_flag = true;
		}
		TurnChange();
		if(Check(i,j)){
		    next_piece = false;
		}
		TurnChange();
	    }
	}
	if(check_flag && next_piece){
	    stalemate = true;
	}
	return stalemate;
    }

    public String win(int x1,int y1,int x2,int y2){
	boolean BLACKWIN = false,WHITEWIN = false,DRAW = false;
	int checkx = x2,checky = x2;
	String str = StringBoard();

	if(board[x2][y2] % 10 == 6 && turn == WHITE){
	    WHITEWIN = true;
	}else if(board[x2][y2] % 10 == 6 && turn == BLACK){
	    BLACKWIN = true;
	}

	//stalemate
	boolean flag = true;
	if(stalemate()){
	    DRAW = true;
	}

	//kurikaesi	//lack of material
	int draw_count = 0;
	boolean Bdraw = false,Wdraw = false;
	int piece_count[] = new int [10];
	for(int i = 0;i<10;i++){
	    for(int j = 0;j<10;j++){
		if(pre_board[i][j] != board[i][j]){
		    draw_count = 0;
		}
		switch(board[i][j]){
		case BLACK_PAWN:
		    piece_count[0]++;break;
		case WHITE_PAWN:
		    piece_count[1]++;break;
		case BLACK_KNIGHT:
		    piece_count[2]++;break;
		case WHITE_KNIGHT:
		    piece_count[3]++;break;
		case BLACK_BISHOP:
		    piece_count[4]++;break;
		case WHITE_BISHOP:
		    piece_count[5]++;break;
		case BLACK_ROOK:
		    piece_count[6]++;break;
		case WHITE_ROOK:
		    piece_count[7]++;break;
		case BLACK_QUEEN:
		    piece_count[8]++;break;
		case WHITE_QUEEN:
		    piece_count[9]++;break;
		}
	    }
	    if(i == 9){
		draw_count += 1;
	    }
	}
	if(draw_count == 3){
	    DRAW = true;
	}

	if(piece_count[0] == 0 && piece_count[6] == 0 && piece_count[8] == 0){
	    if(piece_count[2] <= 2 && piece_count[4] <= 1){
		Bdraw = true;
	    }	    
	}

	if(piece_count[1] == 0 && piece_count[7] == 0 && piece_count[9] == 0){
	    if(piece_count[3] <= 2 && piece_count[5] <= 1){
		Wdraw = true;
	    }	    
	}
	if(Bdraw && Wdraw) DRAW = true;

	if(BLACKWIN || WHITEWIN)str = "win,"+turn;
	if(DRAW)str = "draw";
	return str;
    }

    public void TurnChange(){
	turn = 5 - turn;
    }

    public String StringBoard(){
	String board_data = "";
	for(int y = 1;y <= 8;y++){
	    for(int x = 1;x <= 8;x++){
		board_data +=  String.valueOf(board[x][y]) + ",";
	    }
	}
	return board_data;
    }
	
    public void Castling(int x1,int y1,int x2,int y2){
	if(x2-x1 == 2 && y1 == 1){
	    board[8][1] = 0;
	    board[6][1] = BLACK_ROOK;
	}else if(x2-x1 == -2 && y1 == 1){
	    board[1][1] = 0;
	    board[4][1] = BLACK_ROOK;
	}else if(x2-x1 == 2 && y1 == 8){
	    board[8][8] = 0;
	    board[6][8] = WHITE_ROOK;
	}else if(x2-x1 == -2 && y1 == 8){
	    board[1][8] = 0;
	    board[4][8] = WHITE_ROOK;
	}
    }
	
    public String move(String string){
	boolean Promotion_flag = false;
	String str = StringBoard();
	System.out.println(string+","+string.length());
	if(string.equals("resign," + BLACK))return "win,"+WHITE;
	if(string.equals("resign," + WHITE))return "win,"+BLACK;
	if(string.equals("draw," + BLACK)){ 
	    bdraw = true;
	    move_flag = false;
	    if(bdraw && wdraw){
		bdraw = false;
		wdraw = false;
		move_flag = true;
		return "draw";
	    }
	    return "draw?";
	}else if(string.equals("draw," + WHITE)){
	    wdraw = true;
	    move_flag = false;
	    if(bdraw && wdraw){
		bdraw = false;
		wdraw = false;
		move_flag = true;
		return "draw";
	    }
	    return "draw?";
	}
	if(string.equals("notdraw")){
	    move_flag = true;
	    System.out.println("do");
	    return StringBoard();
	}
	if(string.equals("restart"))return restart();
	    
	
	if(!p_flag){
	    String[] piece_move = string.split(",",-1);
			
	    int x1 = Integer.parseInt(piece_move[0]);
	    int y1 = Integer.parseInt(piece_move[1]);
	    int x2 = Integer.parseInt(piece_move[2]);
	    int y2 = Integer.parseInt(piece_move[3]);
	    
	    if(canMove(x1,y1,x2,y2) == true){
		getMove(x1,y1,x2,y2);
		switch(board[x1][y1]){
		case BLACK_PAWN:
		    if(y2 == 8){
			Promotion_flag =true;
		    }
		    break;
		case BLACK_ROOK:
		    if(board[1][1] != BLACK_ROOK ){
			BLR_flag = false;
		    }else if(board[8][1] != BLACK_ROOK){
			BRR_flag = false;
		    }
		    break;
		case BLACK_KING:
		    BK_flag = false;
		    break;
		case WHITE_PAWN:
		    if(y2 == 1){
			Promotion_flag = true;
		    }
		    break;
		case WHITE_ROOK:
		    if(board[1][8] != WHITE_ROOK){
			WLR_flag = false;
		    }else if(board[8][8] != WHITE_ROOK){
			WRR_flag = false;
		    }
		    break;
		case WHITE_KING:
		    WK_flag = false;
		    break;
		default:
		    break;
		}
		if(Castling){
		    Castling(x1,y1,x2,y2);//need to change
		}
		if(!win(x1,y1,x2,y2).equals(StringBoard())){
		    str = win(x1,y1,x2,y2);
		}else{		
		    board[x2][y2] = board[x1][y1];
		    board[x1][y1] = 0;	
		    if(Promotion_flag){
			p_flag = true;
			str = "promotion";
		    }else{
			if(enpassant){
			    System.out.println("turn = " + turn);
			    if(turn == BLACK){
				board[x2][y2-1] = 0;
			    }else if(turn == WHITE){
				board[x2][y2+1] = 0;
			    }
			}
			str = StringBoard();
			TurnChange();
		    }
		}

	    
	    }else if(p_flag){
		board[pre_move[2]][pre_move[3]] = Integer.parseInt(string) + turn * 10;
		p_flag = false;
		str = StringBoard();
		TurnChange();
	    }else{}
	    
	}					
	return str;
    }

    public int[] getMove(int x1,int y1,int x2,int y2){
	pre_move[0] = x1;pre_move[1] = y1;pre_move[2] = x2;pre_move[3] = y2;
	return pre_move;
    }
	
    public int Promotion(){
	return promotion;
    }
}
