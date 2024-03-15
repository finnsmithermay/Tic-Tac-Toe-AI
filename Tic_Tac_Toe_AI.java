
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class tttGame extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	char playerMark = 'x';
	JButton[] buttons = new JButton[9];
	
	public tttGame() {
		setLayout(new GridLayout(3,3));
		initializeButtons();
		
	}
	
	// a method used to create 9 buttons
	// set the text, add action listeners
	// and add them to the screen
	public void initializeButtons()
    {
        for(int i = 0; i <= 8; i++)
        {
            buttons[i] = new JButton();
            buttons[i].setText("-");
            buttons[i].setBackground(Color.white);
            buttons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton buttonClicked = (JButton) e.getSource(); //get the particular button that was clicked
					if(buttonClicked.getText().charAt(0)== '-') {
						buttonClicked.setText(String.valueOf(playerMark));

				        	buttonClicked.setBackground(Color.CYAN);
					        displayVictor();
					        playerMark = 'o';
	
				        	int cT = bestMove(buttons);
				        	JButton ComputerButton = buttons[cT];
				        	ComputerButton.setBackground(Color.ORANGE);
				        	ComputerButton.setText(String.valueOf(playerMark));
					        displayVictor();
					        playerMark ='x';
			        
						displayVictor();
					}
				}
				
			});
            
            add(buttons[i]); //adds this button to JPanel        
        }
    }
	
	private int bestMove(JButton[] buttons) {
		int bestMove=0;
		
		double bestScore = -1000;
		
		for(int i = 0; i < 9; i++){
				if(buttons[i].getText().charAt(0) == '-') {
					//square is available
		        	
					buttons[i].setText(String.valueOf('o'));

					double score = miniMax(buttons, false, 0);
					buttons[i].setText(String.valueOf('-'));
					//save best move
					if(score > bestScore) {
						bestScore = score;
						bestMove = i;
					}
				}
		}
		return bestMove;
	}
	
	
	
	public double miniMax(JButton[] buttons, boolean isMaxing, int depth) {
		// TODO Auto-generated method stub
		if(checkForWinner() == true) {
			double winScore = checkScore(buttons)-depth;
			//System.out.println(winScore);
			return winScore;
		}
		
		if(isMaxing) {
			Double bestScore = Double.NEGATIVE_INFINITY;
			for(int i=0; i<9;i++) {
				if(buttons[i].getText().charAt(0) == '-') {
					buttons[i].setText(String.valueOf('o'));
					double score = miniMax(buttons, false, depth + 1);
					bestScore = Math.max(bestScore, score);
					buttons[i].setText(String.valueOf('-'));
				}
			}
			return bestScore;
		
		}else {
			Double bestScore = Double.POSITIVE_INFINITY;
			for(int i=0; i<9;i++) {
				if(buttons[i].getText().charAt(0) == '-') {
					buttons[i].setText(String.valueOf('x'));
					double score = miniMax(buttons, true, depth + 1);
					bestScore = Math.min(bestScore, score);
					buttons[i].setText(String.valueOf('-'));

				}
			}
			return bestScore;
		}
	}

	public void displayVictor() {
		
		if(checkForWinner() == true) {
			
			// reverse the player marks
			// because after we put x and we win, the game changes it to o
			// but x is the winner
			char winner = ' ';
			if(checkScore(buttons) > 0) {
				winner = 'o';
			}else {
				winner = 'x';
			}
			
			JOptionPane pane = new JOptionPane();
			int dialogResult = JOptionPane.showConfirmDialog(pane, "Game Over. "+ winner + " wins. Would you like to play again?","Game over.",
					JOptionPane.YES_NO_OPTION);
			
			if(dialogResult == JOptionPane.YES_OPTION) resetTheButtons();
			else System.exit(0);
		}
		
		else if(checkDraw()) {
			JOptionPane pane = new JOptionPane();
			int dialogResult = JOptionPane.showConfirmDialog(pane, "Draw. Play again?","Game over.", JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION)  resetTheButtons();
			else System.exit(0);
		}
	}
	

	// method used to reset the buttons
	// so you can play again
	private void resetTheButtons() {
		playerMark = 'x';
		for(int i =0;i<9;i++) {
			  
			  buttons[i].setText("-");
			  buttons[i].setBackground(Color.white);
			  
		  }	
	}

	// checks for draw
	public boolean checkDraw() {
		boolean full = true;
		for(int i = 0 ; i<9;i++) {
			if(buttons[i].getText().charAt(0) == '-') {
				full = false;
			}
		}
		return full;
	}
	
	// checks for a winner
		public boolean checkForWinner() {
			if(checkRows() == true || checkColumns() == true || checkDiagonals() == true) return true;
			else return false;
		}
		
		// checks rows for a win
		public boolean checkRows() {
			int i = 0;
			for(int j = 0;j<3;j++) {
			if( buttons[i].getText().equals(buttons[i+1].getText()) && buttons[i].getText().equals(buttons[i+2].getText()) 
					&& buttons[i].getText().charAt(0) != '-') {
				return true;
			}
			i = i+3;
			
		}
			return false;
	}
		
		// checks columns for a win
		public boolean checkColumns() {
			
			int i = 0;
			for(int j = 0;j<3;j++) {
			if( buttons[i].getText().equals(buttons[i+3].getText()) && buttons[i].getText().equals(buttons[i+6].getText())
					&& buttons[i].getText().charAt(0) != '-') 
			{
				return true;
			}
			i++;
			}
			return false;	
		}
		// checks diagonals for a win
		public boolean checkDiagonals() {
			if(buttons[0].getText().equals(buttons[4].getText()) && buttons[0].getText().equals(buttons[8].getText())
					&& buttons[0].getText().charAt(0) !='-')
				return true;
			else if(buttons[2].getText().equals(buttons[4].getText()) && buttons[2].getText().equals(buttons[6].getText())
					&& buttons[2].getText().charAt(0) !='-') return true;
				
			else return false;
		}
		
		// checks diagonals for a win
		public int checkScore(JButton[] buttons2) {
			char winner = '-';
			
			
			//checks rows
			int i = 0;
			for(int j = 0;j<3;j++) {
			if( buttons2[i].getText().equals(buttons2[i+1].getText()) && buttons2[i].getText().equals(buttons2[i+2].getText()) 
					&& buttons2[i].getText().charAt(0) != '-') {
				     
					winner = buttons2[i].getText().charAt(0);
			}
			i = i+3;
			
		}
			
			// checks columns
			int a = 0;
			for(int j = 0;j<3;j++) {
			if( buttons2[a].getText().equals(buttons2[a+3].getText()) && buttons2[a].getText().equals(buttons2[a+6].getText()) && buttons2[a].getText().charAt(0) != '-'){
				winner = buttons2[a].getText().charAt(0);

				}
					a++;
			}
			// checks diagonals
			if(buttons2[0].getText().equals(buttons2[4].getText()) && buttons2[0].getText().equals(buttons2[8].getText())
					&& buttons2[0].getText().charAt(0) !='-') {
						winner = buttons2[0].getText().charAt(0);
			}

			else if(buttons2[2].getText().equals(buttons2[4].getText()) && buttons2[2].getText().equals(buttons2[6].getText())
					&& buttons2[2].getText().charAt(0) !='-') {
						winner = buttons2[2].getText().charAt(0);
			}
				
			//Set Score
			if(winner == 'x') {
				return -10;
			}
			if(winner == 'o') {
				return 10;
			}
			
				
			else return 0;
		}
	

		
	public static void main(String[] args) {
		JFrame window = new JFrame("Tic Tac Toe");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new tttGame()); // adds the data
        window.setBounds(500,500,500,500); // area
        window.setVisible(true); // show the window
        window.setLocationRelativeTo(null); // center the window
	}

}
