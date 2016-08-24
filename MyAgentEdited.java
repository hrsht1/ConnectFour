/**
 * List of Methods available in this class
 * 
 * void move()
 * void moveOnColumn(int columnNumber)
 * int getLowestEmptyIndex(Connect4Column column)
 * int randomMove()
 * int iCanWin()
 * int theyCanWin()
 * Connect4Slot slotAtIJ(int i, int j)
 * boolean isValidMove(int row, int col)
 * boolean areSlotsEqual(Connect4Slot Slot1, Connect4Slot Slot2)
 * boolean isWin()
 * boolean isPerfectRandMove(int rmove)
 * String getName()
 * boolean isBoardEmpty()
 */
import java.util.Random;

public class MyAgentEdited extends Agent
{
    Random r;
    private final int COL_SIZE = myGame.getColumnCount();
    private final int ROW_SIZE = myGame.getRowCount();
    public int colNextMove;
    /**
     * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public MyAgentEdited(Connect4Game game, boolean iAmRed)
    {
        super(game, iAmRed);
        r = new Random();
        colNextMove = 0;
    }

    /**
     * The move method is run every time it is this agent's turn in the game. You may assume that
     * when move() is called, the game has at least one open slot for a token, and the game has not
     * already been won.
     * 
     * By the end of the move method, the agent should have placed one token into the game at some
     * point.
     * 
     * After the move() method is called, the game engine will check to make sure the move was
     * valid. A move might be invalid if:
     * - No token was place into the game.
     * - More than one token was placed into the game.
     * - A previous token was removed from the game.
     * - The color of a previous token was changed.
     * - There are empty spaces below where the token was placed.
     * 
     * If an invalid move is made, the game engine will announce it and the game will be ended.
     * 
     */
    public void move()
    {
        int middleCol = 0, count = 0, rmove = 0;
        boolean goodRandomMove = false;
        if(iAmRed)
        {
            if(iCanWin() != -1)
            {
                moveOnColumn(iCanWin());
            }
            else if(theyCanWin() != -1)
            {
                moveOnColumn(theyCanWin());
            }
            else
            {
                middleCol = r.nextInt(3)+2;
                int middleRow = getLowestEmptyIndex(myGame.getColumn(middleCol));
                int savedDoubleTrickMove = doubleTrickMove();
                if(isBoardEmpty())
                {
                    moveOnColumn(COL_SIZE/2);
                }
                
                
                else if(savedDoubleTrickMove != -1 )
                {
                    if(isPerfectRandMove(savedDoubleTrickMove))
                    {
                        moveOnColumn(savedDoubleTrickMove);
                    }
                }

                else if(middleRow != -1 && middleRow != 0 && isPerfectRandMove(middleCol))
                {
                    moveOnColumn(middleCol);
                }
                else
                {
                    count = 0;
                    goodRandomMove = false;
                    do
                    {
                        rmove = randomMove();
                        goodRandomMove = isPerfectRandMove(rmove);
                        count++;
                        
                    }while(!goodRandomMove && count<=14);
                    moveOnColumn(rmove);
                }
            }
        }
        else
        {
            if(theyCanWin() != -1)
            {
                moveOnColumn(theyCanWin());
            }
            else if(iCanWin() != -1)
            {
                moveOnColumn(iCanWin());
            }
            else
            {
                middleCol = r.nextInt(3)+2;
                int middleRow = getLowestEmptyIndex(myGame.getColumn(middleCol));
                if(isBoardEmpty())
                {
                    moveOnColumn(COL_SIZE/2);
                }
                else if(middleRow != -1 && middleRow != 0 && isPerfectRandMove(middleCol))
                {
                    moveOnColumn(middleCol);
                }
                else
                {
                    count = 0;
                    goodRandomMove = false;
                    do
                    {
                        rmove = randomMove();
                        goodRandomMove = isPerfectRandMove(rmove);
                        count++;
                        
                    }while(!goodRandomMove && count<=14);
                    moveOnColumn(rmove);
                }
            }
        }
    }
    

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     * 
     * @param columnNumber The column into which to drop the token.
     */
    public void moveOnColumn(int columnNumber)
    {
        int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));   // Find the top empty slot in the column
                                                                                                  // If the column is full, lowestEmptySlot will be -1
        if (lowestEmptySlotIndex > -1)  // if the column is not full
        {
            Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);  // get the slot in this column at this index
            if (iAmRed) // If the current agent is the Red player...
            {
                lowestEmptySlot.addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
            }
        }
    }

    /**
     * Returns the index of the top empty slot in a particular column.
     * 
     * @param column The column to check.
     * @return the index of the top empty slot in a particular column; -1 if the column is already full.
     */
    public int getLowestEmptyIndex(Connect4Column column) {
        int lowestEmptySlot = -1;
        for  (int i = 0; i < column.getRowCount(); i++)
        {
            if (!column.getSlot(i).getIsFilled())
            {
                lowestEmptySlot = i;
            }
        }
        return lowestEmptySlot;
    }

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     * 
     * @return a random valid move.
     */
    public int randomMove()
    {
        int i = r.nextInt(myGame.getColumnCount());
        while (getLowestEmptyIndex(myGame.getColumn(i)) == -1)
        {
            i = r.nextInt(myGame.getColumnCount());
        }
        return i;
    }

    /**
     * Returns the column that would allow the agent to win.
     * 
     * You might want your agent to check to see if it has a winning move available to it so that
     * it can go ahead and make that move. Implement this method to return what column would
     * allow the agent to win.
     *
     * @return the column that would allow the agent to win.
     */
    public int iCanWin()
    {
        colNextMove = -1;
        for (int i=0; i<ROW_SIZE; i++)
        {
            for (int j=0; j<COL_SIZE; j++)
            {
                if(isValidMove(i,j))
                {
                    slotAtIJ(i,j).addRed();
                    if(isWin())
                    {
                        colNextMove = j;
                        
                    }
                    slotAtIJ(i,j).addEmpty();
                }
            }
        }
        return colNextMove;
    }

    /**
     * Returns the column that would allow the opponent to win.
     * 
     * You might want your agent to check to see if the opponent would have any winning moves
     * available so your agent can block them. Implement this method to return what column should
     * be blocked to prevent the opponent from winning.
     *
     * @return the column that would allow the opponent to win.
     */
    public int theyCanWin()
    {
        colNextMove = -1;
        for (int i=0; i<ROW_SIZE; i++)
        {
            for (int j=0; j<COL_SIZE; j++)
            {
                if(isValidMove(i,j))
                {
                    slotAtIJ(i,j).addYellow();
                    if(isWin())
                    {
                        colNextMove = j;
                        
                    }
                    slotAtIJ(i,j).addEmpty();
                }
            }
        }
        return colNextMove;
    }
    /**
     * Takes the row and col, and return the slot correspoding to it
     * 
     * @param row The row of the demanded slot
     * 
     * @param col The column of the demanded slot
     * 
     * @return the Corespoding Slot
     */
    public Connect4Slot slotAtIJ(int row, int col)
    {
        return myGame.getColumn(col).getSlot(row);
    }
    /**
     * Checks wether the move at given row and col is valid or not
     * 
     * @param row The row of the slot
     * 
     * @param Col The column of the slot
     * 
     * @return Boolean true if the move is valid , else false
     */
    public boolean isValidMove(int row, int col)
    {
        if (row < ROW_SIZE && col < COL_SIZE)
        {
            if (row >= 0 && col >= 0)
            {
                if(slotAtIJ(row,col).getIsEmpty())
                {
                    if(getLowestEmptyIndex(myGame.getColumn(col)) == ROW_SIZE-1)
                    return true;
                    if(slotAtIJ(row+1,col).getIsFilled())
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Checks that the given slots are filled with same color
     * 
     * @param Slot1 First slot to be compared
     * 
     * @param Slot2 Second slot to be compared
     * 
     * @return Boolean , true if they are equal , else false
     */
    public boolean areSlotsEqual(Connect4Slot Slot1, Connect4Slot Slot2)
    {
        if((Slot1.getIsRed() && Slot2.getIsRed()) || (Slot1.getIsYellow() && Slot2.getIsYellow()) )
        {
            return true;
        }
        return false;
    }
    /**
     * Checks if there is a win in the current game state
     * 
     * @return Boolean true if there is a win , else false
     */
    public boolean isWin()
    {
        boolean win = false;
        // For Horizontal Win
        for (int row=0; row<ROW_SIZE; row++)
        {
            for (int col=0; col<COL_SIZE-3; col++)
            { //0 to 3
                if(areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row,col+1)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row,col+2)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row,col+3)))
                {
                    win = true;
                }
            }
        }
        
        // For Vertical Win
        for (int row=0; row<ROW_SIZE-3; row++)
        {
            for (int col=0; col<COL_SIZE; col++)
            { //0 to 3
                if(areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row+1,col)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row+2,col)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row+3,col)))
                {
                    win = true;
                }
            }
        }
        
        //check for win diagonally (upper left to lower right)
        for (int row=0; row<ROW_SIZE-3; row++)
        { //0 to 2
            for (int col=0; col<COL_SIZE-3; col++)
            { //0 to 3
                if(areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row+1,col+1)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row+2,col+2)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row+3,col+3)))
                {
                    win = true;
                }
            }
        }
        
        //check for win diagonally (lower left to upper right)
        for (int row=3; row<ROW_SIZE; row++)
        { //3 to 5
            for (int col=0; col<COL_SIZE-3; col++)
            { //0 to 3
                if(areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row-1,col+1)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row-2,col+2)) &&
                areSlotsEqual(slotAtIJ(row,col),slotAtIJ(row-3,col+3)))
                {
                    win = true;
                }
            }
        }
        
        
        return win;
    }
    /**
     * Checks whether the random move (random column) creates a situation where the opponent can win or not
     * 
     * @param rmove A random Move
     * 
     * @return Boolean true if there is no win for the opponent , else false
     */
    public boolean isPerfectRandMove(int rmove)
    {
        int savedTempRow = 0;
        boolean isPerfect = false;
        savedTempRow = getLowestEmptyIndex(myGame.getColumn(rmove));
        moveOnColumn(rmove);
        //whereTheyCanWin = theyCanWin();
        if(iAmRed)
        {
            if(theyCanWin() != -1) // if at that place opponent can win
            {
                isPerfect = false;
            }
            else // if at that place opponent CANNOT win
            {
                isPerfect = true;
            }
        }
        else
        {
            if(iCanWin() != -1) // if at that place opponent can win
            {
                isPerfect = false;
            }
            else // if at that place opponent CANNOT win
            {
                isPerfect = true;
            }
        }
        slotAtIJ(savedTempRow, rmove).addEmpty();
        return isPerfect;
    }
        
    public boolean isBoardEmpty()
    {
        int count = 0;
        for(int i = 0;i < COL_SIZE ;i++)
        {
            if(getLowestEmptyIndex(myGame.getColumn(i))==ROW_SIZE-1)
            {
                count++;
            }
        }
        if(count==COL_SIZE)
        return true;
        else
        return false;
    }
    /**
     * Checks if there exists a Double Win state
     * 
     * @return Int - if No Double Win state , else the column where the Doule win state exists
     */
    public int doubleTrickMove()
    {
        int playableMove = -1;
        if(iAmRed)
        {
            for (int row=0; row<ROW_SIZE; row++)
            { 
                for (int col=2; col<COL_SIZE-3; col++)
                {//2 to 3
                    if(slotAtIJ(row,col).getIsRed() && slotAtIJ(row,col+1).getIsRed() && 
                    isValidMove(row,col-1) && isValidMove(row,col+2))
                    {
                        if(isValidMove(row,col-2))
                        {
                            if(isValidMove(row,col-1) && isValidMove(row,col+2))
                            playableMove = col-1;
                        }
                        else if(isValidMove(row,col+3))
                        {
                            if(isValidMove(row,col + 2) && isValidMove(row,col-1))
                            playableMove = col + 2;
                        }
                    }
                }
            }
        }
        else
        {
            for (int row=0; row<ROW_SIZE; row++)
            { 
                for (int col=2; col<COL_SIZE-3; col++)
                {//2 to 3
                    if(slotAtIJ(row,col).getIsYellow() && slotAtIJ(row,col+1).getIsYellow() && 
                    isValidMove(row,col-1) && isValidMove(row,col+2))
                    {
                        if(isValidMove(row,col-2))
                        {
                            if(isValidMove(row,col-1) && isValidMove(row,col+2))
                            playableMove = col-1;
                        }
                        else if(isValidMove(row,col+3))
                        {
                            if(isValidMove(row,col + 2) && isValidMove(row,col-1))
                            playableMove = col + 2;
                        }
                    }
                }
            }
        }
        /*if(playableMove != -1)
        {
            moveOnColumn(playableMove);
            if(iCanWin() != -1)
            {
                int tRow = getLowestEmptyIndex(myGame.getColumn(playableMove));
                slotAtIJ(tRow,playableMove).addEmpty();
            }
            else
            playableMove = -1;
        }*/
        
        return playableMove;
    }
    
    /**
     * Returns the name of this agent.
     *
     * @return the agent's name
     */
    public String getName()
    {
        return "My Agent Edited";
    }
}
