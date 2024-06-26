import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener{
	//Constants for game 
	// number of ROWS by COLS cell constants 
	public static final int ROWS = 3;     
	public static final int COLS = 3;  
	public static final String TITLE = "Tic Tac Toe";

	//constants for dimensions used for drawing
	//cell width and height
	public static final int CELL_SIZE = 100;
	//drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	//Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;    
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	/*declare game object variables*/
	// the game board 
	Board board;
	
	// the game board cells
	Cell cell;
	 	 
	//Declares the enum "GameState" for various states of the game
	private GameState currentState;
	
	// the current player
	private Player currentPlayer; 
	
	// for displaying game status message
	private JLabel statusBar;       
	

	//Constructor to setup the UI and game components on the panel
	public GameMain() {   
				     
		// Setup the status bar (JLabel) to display status message       
		statusBar = new JLabel("         ");       
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
		statusBar.setOpaque(true);       
		statusBar.setBackground(Color.LIGHT_GRAY);  
		
		//layout of the panel is in border layout
		setLayout(new BorderLayout());       
		add(statusBar, BorderLayout.SOUTH);
		// account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
		
		// Create a new instance of the game "Board" class
		board = new Board();

		// Call the method to initialise the game board
		initGame();
		
		// Register the panel for mouse events
        addMouseListener(this);

	}
	
	public static void main(String[] args) {
		    // Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
				//create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);
				
				// Create the new GameMain panel and add it to the frame
	            GameMain gameMainPanel = new GameMain();
	            frame.add(gameMainPanel);

	            // Set the default close operation of the frame to EXIT_ON_CLOSE
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
				
				frame.pack();             
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
	         }
		 });
	}
	//Custom painting codes on this JPanel
	public void paintComponent(Graphics g) {
		//fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		//ask the game board to paint itself
		board.paint(g);
		
		//set status bar message
		if (currentState == GameState.Playing) {          
			statusBar.setForeground(Color.BLACK);          
			if (currentPlayer == Player.Cross) {   
				// Use the status bar to display the message "X"'s Turn
	            statusBar.setText("X's Turn");	
			} else {    
				// Use the status bar to display the message "O"'s Turn
	            statusBar.setText("O's Turn");
			}    
				//Use the status bar to display it's a draw
			} else if (currentState == GameState.Draw) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("It's a Draw! Click to play again.");   
				//Use the status bar to display Cross won
			} else if (currentState == GameState.Cross_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'X' Won! Click to play again.");   
				//Use the status bar to display Nought won
			} else if (currentState == GameState.Nought_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'O' Won! Click to play again.");       
			}
		}
		
	
	  //Initialise the game-board contents and the current status of GameState and Player)
		public void initGame() {
			for (int row = 0; row < ROWS; ++row) {          
				for (int col = 0; col < COLS; ++col) {  
					// all cells empty
					board.cells[row][col].content = Player.Empty;           
				}
			}
			 currentState = GameState.Playing;
			 currentPlayer = Player.Cross;
		}
		
		
		//Check which player has won and update the current state to the appropriate game state for the winner
		public void updateGame(Player thePlayer, int row, int col) {
			// Check for win after play
		    if (board.hasWon(thePlayer, row, col)) {
		        //If Cross won, change the game state to Cross_won
		        if (thePlayer == Player.Cross) {
		            currentState = GameState.Cross_won;
		         //If Nought won, change the game state to Nought_won
		        } else {
		            currentState = GameState.Nought_won;
		        }
		    //If the game was a draw, change the game state to Draw
		    } else if (board.isDraw()) {
		        // Set the current state to the draw game state
		        currentState = GameState.Draw;
		    }
		    // Otherwise, no change to current state of playing
		}
		
			
	//Checks where the mouse clicked, if the cell is available, updates the UI with the new "move" from player
	public void mouseClicked(MouseEvent e) {  
		// get the coordinates of where the click event happened            
		int mouseX = e.getX();             
	    int mouseY = e.getY();             
	    int rowSelected = mouseY / CELL_SIZE;             
	    int colSelected = mouseX / CELL_SIZE; 

	    if (currentState == GameState.Playing) {                
	        if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS) {
	            if (board.cells[rowSelected][colSelected].content == Player.Empty) {
	                // Move  
	                board.cells[rowSelected][colSelected].content = currentPlayer; 
	                updateGame(currentPlayer, rowSelected, colSelected); 
	                if (currentPlayer == Player.Cross) {
	                    currentPlayer =  Player.Nought;
	                } else {
	                    currentPlayer = Player.Cross;
	                }
	            } 
	        }             
	    } else {        
	        // Game over and restart              
	        initGame();            
	    }   

	    repaint();           
	}
		
	
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used
		
	}
}
