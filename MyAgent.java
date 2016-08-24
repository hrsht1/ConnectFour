import java.util.Random;

public class MyAgent extends Agent
{

    public MyAgent(Connect4Game connect4game, boolean flag)
    {
        super(connect4game, flag);
        COL_SIZE = myGame.getColumnCount();
        ROW_SIZE = myGame.getRowCount();
        r = new Random();
    }

    public void move()
    {
        int i = 0;
        int middleCol = 0;
        int count = 0;
        int rmove = 0;
        boolean goodRandomMove = false;
        int j = canWin(iAmRed);
        int k = canWin(!iAmRed);
        if(j >= 0)
            i = j;
        else
        if(k >= 0)
        {
            i = k;
        } else
        {
            middleCol = r.nextInt(3) + 2;
            int middleRow = getLowestEmptyIndex(myGame.getColumn(middleCol));
            int savedDoubleTrickMove = doubleTrickMove();
            if(isBoardEmpty())
                i = COL_SIZE / 2;
            else
            if(savedDoubleTrickMove != -1)
            {
                if(isPerfectRandMove(savedDoubleTrickMove))
                    i = savedDoubleTrickMove;
            } else
            if(middleRow != -1 && middleRow != 0 && isPerfectRandMove(middleCol))
            {
                i = middleCol;
            } else
            {
                count = 0;
                goodRandomMove = false;
                do
                {
                    rmove = randomMove();
                    goodRandomMove = isPerfectRandMove(rmove);
                    count++;
                } while(!goodRandomMove && count <= 14);
                i = rmove;
            }
        }
        moveOnColumn(i);
    }

    public int randomMove()
    {
        int i;
        for(i = r.nextInt(myGame.getColumnCount()); getTopEmptySlot(myGame.getColumn(i)) == null; i = r.nextInt(myGame.getColumnCount()));
        return i;
    }

    public void moveOnColumn(int i)
    {
        Connect4Slot connect4slot = getTopEmptySlot(myGame.getColumn(i));
        if(connect4slot != null)
            if(iAmRed)
                connect4slot.addRed();
            else
                connect4slot.addYellow();
    }

    public Connect4Slot getTopEmptySlot(Connect4Column connect4column)
    {
        int i = -1;
        for(int j = 0; j < connect4column.getRowCount(); j++)
            if(!connect4column.getSlot(j).getIsFilled())
                i = j;

        if(i < 0)
            return null;
        else
            return connect4column.getSlot(i);
    }

    public int canWin(boolean flag)
    {
        for(int i = 0; i < myGame.getColumnCount(); i++)
        {
            int j = getLowestEmptyIndex(myGame.getColumn(i));
            if(j > -1)
            {
                if(j < myGame.getRowCount() - 3 && myGame.getColumn(i).getSlot(j + 1).getIsRed() == flag && myGame.getColumn(i).getSlot(j + 2).getIsRed() == flag && myGame.getColumn(i).getSlot(j + 3).getIsRed() == flag)
                    return i;
                if(i < myGame.getColumnCount() - 3 && checkIfEqual(flag, myGame.getColumn(i + 1).getSlot(j), myGame.getColumn(i + 2).getSlot(j), myGame.getColumn(i + 3).getSlot(j)))
                    return i;
                if(i < myGame.getColumnCount() - 2 && i > 0 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j), myGame.getColumn(i + 1).getSlot(j), myGame.getColumn(i + 2).getSlot(j)))
                    return i;
                if(i < myGame.getColumnCount() - 1 && i > 1 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j), myGame.getColumn(i + 1).getSlot(j), myGame.getColumn(i - 2).getSlot(j)))
                    return i;
                if(i > 2 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j), myGame.getColumn(i - 3).getSlot(j), myGame.getColumn(i - 2).getSlot(j)))
                    return i;
                if(i < myGame.getColumnCount() - 3 && j < myGame.getRowCount() - 3 && checkIfEqual(flag, myGame.getColumn(i + 1).getSlot(j + 1), myGame.getColumn(i + 3).getSlot(j + 3), myGame.getColumn(i + 2).getSlot(j + 2)))
                    return i;
                if(i < myGame.getColumnCount() - 2 && i > 0 && j < myGame.getRowCount() - 2 && j > 0 && checkIfEqual(flag, myGame.getColumn(i + 1).getSlot(j + 1), myGame.getColumn(i - 1).getSlot(j - 1), myGame.getColumn(i + 2).getSlot(j + 2)))
                    return i;
                if(i < myGame.getColumnCount() - 1 && i > 1 && j < myGame.getRowCount() - 1 && j > 1 && checkIfEqual(flag, myGame.getColumn(i + 1).getSlot(j + 1), myGame.getColumn(i - 1).getSlot(j - 1), myGame.getColumn(i - 2).getSlot(j - 2)))
                    return i;
                if(i < myGame.getColumnCount() && i > 2 && j < myGame.getRowCount() && j > 2 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j - 1), myGame.getColumn(i - 2).getSlot(j - 2), myGame.getColumn(i - 3).getSlot(j - 3)))
                    return i;
                if(i > 2 && i < myGame.getColumnCount() && j < myGame.getRowCount() - 3 && j >= 0 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j + 1), myGame.getColumn(i - 2).getSlot(j + 2), myGame.getColumn(i - 3).getSlot(j + 3)))
                    return i;
                if(i > 1 && i < myGame.getColumnCount() - 1 && j < myGame.getRowCount() - 2 && j > 0 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j + 1), myGame.getColumn(i - 2).getSlot(j + 2), myGame.getColumn(i + 1).getSlot(j - 1)))
                    return i;
                if(i > 0 && i < myGame.getColumnCount() - 2 && j < myGame.getRowCount() - 1 && j > 1 && checkIfEqual(flag, myGame.getColumn(i - 1).getSlot(j + 1), myGame.getColumn(i + 2).getSlot(j - 2), myGame.getColumn(i + 1).getSlot(j - 1)))
                    return i;
                if(i >= 0 && i < myGame.getColumnCount() - 3 && j < myGame.getRowCount() && j > 2 && checkIfEqual(flag, myGame.getColumn(i + 3).getSlot(j - 3), myGame.getColumn(i + 2).getSlot(j - 2), myGame.getColumn(i + 1).getSlot(j - 1)))
                    return i;
            }
        }

        return -1;
    }

    public boolean checkIfEqual(boolean flag, Connect4Slot connect4slot, Connect4Slot connect4slot1, Connect4Slot connect4slot2)
    {
        return connect4slot.getIsFilled() && connect4slot1.getIsFilled() && connect4slot2.getIsFilled() && connect4slot.getIsRed() == flag && connect4slot1.getIsRed() == flag && connect4slot2.getIsRed() == flag;
    }

    public int getLowestEmptyIndex(Connect4Column connect4column)
    {
        int i = -1;
        for(int j = 0; j < connect4column.getRowCount(); j++)
            if(!connect4column.getSlot(j).getIsFilled())
                i = j;

        return i;
    }

    public String getName()
    {
        return "Computer Agent";
    }

    public boolean isBoardEmpty()
    {
        int count = 0;
        for(int i = 0; i < COL_SIZE; i++)
            if(getLowestEmptyIndex(myGame.getColumn(i)) == ROW_SIZE - 1)
                count++;

        return count == COL_SIZE;
    }

    public boolean isPerfectRandMove(int rmove)
    {
        int savedTempRow = 0, i;
        boolean isPerfect = false;
        savedTempRow = getLowestEmptyIndex(myGame.getColumn(rmove));
        moveOnColumn(rmove);
        /*if(iAmRed)
        {*/i = canWin(!iAmRed);
            if(i != -1)
                isPerfect = false;
            /*else if(canWin(iAmRed) == rmove)
                isPerfect = false;*/
            else
                isPerfect = true;
        /*} else
        if(canWin(iAmRed) != -1)
            isPerfect = false;
        else
        if(canWin(!iAmRed) == rmove)
            isPerfect = false;
        else
            isPerfect = true; */
        slotAtIJ(savedTempRow, rmove).addEmpty();
        return isPerfect;
    }

    public int doubleTrickMove()
    {
        int playableMove = -1;
        if(iAmRed)
        {
            for(int row = 0; row < ROW_SIZE; row++)
            {
                for(int col = 2; col < COL_SIZE - 3; col++)
                    if(slotAtIJ(row, col).getIsRed() && slotAtIJ(row, col + 1).getIsRed() && isValidMove(row, col - 1) && isValidMove(row, col + 2))
                        if(isValidMove(row, col - 2))
                        {
                            if(isValidMove(row, col - 1) && isValidMove(row, col + 2))
                                playableMove = col - 1;
                        } else
                        if(isValidMove(row, col + 3) && isValidMove(row, col + 2) && isValidMove(row, col - 1))
                            playableMove = col + 2;

            }

        } else
        {
            for(int row = 0; row < ROW_SIZE; row++)
            {
                for(int col = 2; col < COL_SIZE - 3; col++)
                    if(slotAtIJ(row, col).getIsYellow() && slotAtIJ(row, col + 1).getIsYellow() && isValidMove(row, col - 1) && isValidMove(row, col + 2))
                        if(isValidMove(row, col - 2))
                        {
                            if(isValidMove(row, col - 1) && isValidMove(row, col + 2))
                                playableMove = col - 1;
                        } else
                        if(isValidMove(row, col + 3) && isValidMove(row, col + 2) && isValidMove(row, col - 1))
                            playableMove = col + 2;

            }

        }
        return playableMove;
    }

    public Connect4Slot slotAtIJ(int row, int col)
    {
        return myGame.getColumn(col).getSlot(row);
    }

    public boolean isValidMove(int row, int col)
    {
        if(row < ROW_SIZE && col < COL_SIZE && row >= 0 && col >= 0 && slotAtIJ(row, col).getIsEmpty())
        {
            if(getLowestEmptyIndex(myGame.getColumn(col)) == ROW_SIZE - 1)
                return true;
            if(slotAtIJ(row + 1, col).getIsFilled())
                return true;
        }
        return false;
    }

    public boolean areSlotsEqual(Connect4Slot Slot1, Connect4Slot Slot2)
    {
        return Slot1.getIsRed() && Slot2.getIsRed() || Slot1.getIsYellow() && Slot2.getIsYellow();
    }

    private final int COL_SIZE;
    private final int ROW_SIZE;
    Random r;
}