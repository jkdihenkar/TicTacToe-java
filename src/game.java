import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class game extends JFrame { 
	
	panel gameboard;
	
	public game() { 
	  super("TicTacToe--byJD");
	  setMinimumSize(new Dimension(610,610+70));//for provision of reset button...
	  setSize(600,670);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  //now set the board...
	  gameboard=new panel();
	  add(gameboard,BorderLayout.CENTER);
	  
	  //now we'll setup the reset button....
	  JPanel bpan = new JPanel();
	  JButton rset = new JButton("Reset Board!");//create button
	  bpan.add(rset);//add to the panel
	  
	  add(bpan,BorderLayout.SOUTH);
	  
	  rset.addActionListener(new ActionListener() { 
	    public void actionPerformed(ActionEvent ae) { 
	      gameboard.reset();
	      gameboard.requestFocus();
	    }
	  });
	  
	  gameboard.requestFocus();
	  setVisible(true);
	}
	
	public static void main(String args[]) { 
	  new game();
	}
}

	  
	  
