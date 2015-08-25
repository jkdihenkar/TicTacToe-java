import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class panel extends JPanel { 
	
	//dimensions to be applied....
	
	//panel dimensions... i.e. the entire boards'
	public static final int pwidth = 600;
	public static final int pheight = 600;
	
	//grids dimension i.e. each of the nine square of playing area
	public static final int gwidth = pwidth/3;
	public static final int gheight = pheight/3;
	
	//array as a 9x9 matrix ....
	int[][] board;
	
	int turn;
	//this indicates which players turn to play the move
	
	boolean finish;
	//this is to indicate if the game is finished or not
	
	Image ximg;
	Image yimg;
	//the symbols for the two players
	//chainging the image will also change the symbols instead of 
		//just 'x' and 'o'
	
	//public constructor for the panel i.e. board...
	
	public panel() { 
	
	setSize(pwidth,pheight);
	//the size to set...
	
	board = new int[3][3];
	//allocate memory to board
	
	//init the board to empty
	for(int i=0;i<board.length;i++) { 
	  for(int j=0;j<board[i].length;j++) { 
	    board[i][j]=0;
	  }
	}
	
	//status of game
	finish=false;
	
	//set the turn to first player
	turn = 1;
	
	ximg = Toolkit.getDefaultToolkit().createImage("x.png");
	yimg = Toolkit.getDefaultToolkit().createImage("o.png");
	//loads both the images..
	//Toolkit is the main class for swing elements handling,,,,
	
	//prepare the images so that they're loaded to memory and can be painted
	//note preparing actually occupy memory space
	//so load only those images that are to be loaded
	
	prepareImage(ximg,this);
	prepareImage(yimg,null);
	
	
	//setup the user input listener's event...
	addMouseListener(new MouseHandler(this));
	} //constructor ends i.e. all initials loaded and set
	
	//now we'll first create a utility function reset...
	  //this function will further clean up the board and 
	  //set the application ready for the next game 
	  
	public void reset() { 
	  finish = false; //game is not over
	  turn=1;  //first players turn
	 //clear board
	 for(int i=0;i<board.length;i++) { 
	  for(int j=0;j<board[i].length;j++) { 
	      board[i][j]=0;
	    }
	  }
	  repaint(); //instantly show the empty board...
	}
	
	//now what to show on the display...
	  //we'll paint the components...
	public void paintComponent(Graphics g) { 
	  //check if both the symbolic images have loaded...
	  if(ximg == null || yimg == null) { 
	    System.exit(1);
	    //exit with error code
	  }
	  //we'll draw the grid now....
	    //horizontal lines
	    g.drawLine(5,gheight,pwidth-5,gheight);
	    g.drawLine(5,2*gheight,pwidth-5,2*gheight);
	    //vertical lines
	    g.drawLine(gwidth,5,gwidth,pheight-5);
	    g.drawLine(2*gwidth,5,2*gwidth,pheight-5);
	  //so here the board's grid is drawn ...
	  //now we'll paint the elements in the board...
		g.setColor(Color.lightGray);
	  for(int i=0;i<board.length;i++) { 
	    for(int j=0;j<board[i].length;j++) { 
	      //if player 1's symbol there
	      if(board[i][j]==1) { 
	        //print player1's icon...
	        g.drawImage(ximg,i*gwidth,j*gheight,null);
	      }
	      else if(board[i][j]==2) { 
	        //print 2nd players icon...
	        g.drawImage(yimg,i*gwidth,j*gheight,null);
	      }
	      else { //means empty slot...
	        g.fillRect(i*gwidth+1,j*gheight+1,gwidth-1,gheight-1);
	      }
	    }  
	  }
	}//ended with painting... 
	
	//now we'll define the mouse handling actions and their response
	class MouseHandler implements MouseListener { 
	  //we can also use the MouseAdapter class...
	  panel pane;  //dummy variable for the class to indicate 
	  //actions on panel
	  
	  public MouseHandler(panel p){
	    pane=p;
	  } //state which panel's action to monitor 
	  
	  public void mouseClicked(MouseEvent e) { 
	  //when clicked...what?
	  
	  //if game has not ended only then....
	  if(!finish) { 
	    //first we'll get the box of where the user clicked
	    //which coloumn? : 
	    int col=(e.getX()/gwidth);
	    //which row? : 
	    int row=(e.getY()/gheight);
	    
	    //now action to be performed only if the box is empty!
	    //so check board array...
	    if(board[col][row]==0) { 
	      board[col][row]=turn;
	      //i.e. fill by whoever's turn is there...
	    //check if game is won?
	    //checking algo goes here...
	    checkWin();
	    //ends checking algo...
	    
	    //exchange turns if still in play...
	    //conditional : (turn==1)?(turn=2):(turn=1);
	      if(turn==1) { 
	        turn=2;
	      } else if(turn==2) { 
	        turn=1;
	      }
	    }//ends a single turn...
	    
	    //update the board
	    pane.repaint();
	  }
	}
	
	void checkWin() { 
	  //win conditions 3 types : Horiz Verti Diag
	  //draw conditn : boardfull & none of above
	  
	  //the three algos to check for win in horiz verti and diag streak
	  /*if(checkH()) { 
	    winDialog();
	    return;
	  }
	  if(checkV()) { 
	    winDialog();
	    return;
	  }
	  
	  //if won in any of the above three way then show message...
	  if(checkD()) { 
	    winDialog();
	    return;
	  }
	  */
	for (int i = 0; i < board.length; i++) {
	  boolean horiz = true;
	  for (int j = 0; j < board[i].length; j++) {
	  if (board[j][i] != turn)
	    horiz = false;
	  }
	  if (horiz == true) {
	    winDialog();
	    return;
	  }
	}
	
	for (int i = 0; i < board.length; i++) {
	  boolean verti = true;
	  for (int j = 0; j < board[i].length; j++) {
	    if (board[i][j] != turn)
	      verti = false;
	    }
	    if (verti == true) {
	      winDialog();
	      return;
	    }
	}
	
	boolean t = true;
	boolean b = true;
	for (int i = 0; i < board.length; i++) {
	if (board[i][i] != turn)
	t = false;
	if (board[(board.length-(i+1))][i] != turn)
	b = false;
	}
	if (t == true || b == true) {
	winDialog();
	return;
	}
	
	
	  if(checkF()) { 
	    pane.repaint();
	    JOptionPane.showMessageDialog(pane,"No results!","Draw Game",JOptionPane.INFORMATION_MESSAGE);
	    return;
	  }
	}
	/*
	boolean checkH() { 
	  for(int i=0;i<board.length;i++) {
	    for(int j=0;j<board[i].length;j++) { 
	      if(board[j][i]!=turn);
	      return false;
	    }
	  }
	  return true;
	}
	
	boolean checkV() { 
	  for(int i=0;i<board.length;i++) {
	    for(int j=0;j<board[i].length;j++) { 
	      if(board[i][j]!=turn); //notice change is jst among i - j
	      return false;
	    }
	  }
	  return true;
	}
	
	boolean checkD() { 
	  for(int i=0;i<board.length;i++) { 
	    if(board[i][i]!=turn) {
	      return false;
	    }
	    if(board[board.length-(i+1)][i]!=turn) { 
	      return false;
	    }
	  }
	  return true;
	}
	*/
	boolean checkF() { 
	  for(int i=0;i<board.length;i++) { 
	    for(int j=0;j<board[i].length;j++) { 
	      if(board[i][j]==0) { //any free slot
	        return false;
	      }
	    }
	  }
	  return true;
	}
	
	void winDialog() { 
	  finish=true;
	  pane.repaint();
	  String who;
	  who = "Player#"+turn+"WINS...!!!";
	  JOptionPane.showMessageDialog(pane,who,"Winner!",JOptionPane.INFORMATION_MESSAGE);
	  return;
	}
	
	//now overriding all the other methods of this interface as blanks...
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
} //MOUSE HANDLER ends here...

} //cLASS 'panel' ends here...
	
