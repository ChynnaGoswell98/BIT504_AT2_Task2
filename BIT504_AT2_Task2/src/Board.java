import java.awt.*;

public class Board {
	// Grid line width
    public static final int GRID_WIDTH = 8;
    // Grid line half width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    
    // 2D array of ROWS-by-COLS Cell instances
    Cell [][] cells;
    
    // Constructor to create the game board
    public Board() {
        
        // Initialize the cells array using ROWS and COLS constants 
        cells = new Cell[GameMain.ROWS][GameMain.COLS];
        
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                // Corrected the row and col indices here
                cells[row][col] = new Cell(row, col);
            }
        }
    }

	//Checking if the game is a draw
	public boolean isDraw() {
		 
		// Iterate through all cells on the board
	    for (int row = 0; row < GameMain.ROWS; ++row) {
	        for (int col = 0; col < GameMain.COLS; ++col) {
	            // If any cell is empty, return false
	            if (cells[row][col].content == Player.Empty) {
	                return false;
	            }
	        }
	    }
	    // If no empty cells are found, return true indicating a draw
	    return true;
	}
	
	//Return true if the current player "thePlayer" has won after making their move
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
	    // Check if player has 3-in-that-row
	    if (cells[playerRow][0].content == thePlayer && 
	        cells[playerRow][1].content == thePlayer && 
	        cells[playerRow][2].content == thePlayer) {
	        return true; // Player has won in the row
	    }
	    
	    // Check if player has 3-in-that-column
	    if (cells[0][playerCol].content == thePlayer && 
	        cells[1][playerCol].content == thePlayer && 
	        cells[2][playerCol].content == thePlayer) {
	        return true; // Player has won in the column
	    }
	    
	    // Check 3-in-the-diagonal
	    if (playerRow == playerCol && 
	        cells[0][0].content == thePlayer &&
	        cells[1][1].content == thePlayer &&
	        cells[2][2].content == thePlayer) {
	        return true; // Player has won in the main diagonal
	    }
	    
	    // Check 3-in-the-opposite-diagonal
	    if (playerRow + playerCol == 2 && 
	        cells[0][2].content == thePlayer &&
	        cells[1][1].content == thePlayer &&
	        cells[2][0].content == thePlayer) {
	        return true; // Player has won in the opposite diagonal
	    }
	    
	    // No winner, keep playing
	    return false;
	}
	
	//Draws the grid (rows then columns) using constant sizes, then call on the cells to paint themselves into the grid
	public void paint(Graphics g) {
		//draw the grid
		g.setColor(Color.gray);
		for (int row = 1; row < GameMain.ROWS; ++row) {          
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF,                
					GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,                
					GRID_WIDTH, GRID_WIDTH);       
			}
		for (int col = 1; col < GameMain.COLS; ++col) {          
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0,                
					GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,                
					GRID_WIDTH, GRID_WIDTH);
		}
		
		//Draw the cells
		for (int row = 0; row < GameMain.ROWS; ++row) {          
			for (int col = 0; col < GameMain.COLS; ++col) {  
				cells[row][col].paint(g);
			}
		}
	}
	

}
