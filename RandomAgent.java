// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomAgent.java

import java.util.Random;

public class RandomAgent extends Agent
{

    public RandomAgent(Connect4Game connect4game, boolean flag)
    {
        super(connect4game, flag);
        r = new Random();
    }

    public void move()
    {
        if(!myGame.boardFull())
        {
            int i;
            for(i = r.nextInt(myGame.getColumnCount()); getTopEmptySlot(myGame.getColumn(i)) == null; i = r.nextInt(myGame.getColumnCount()));
            moveOnColumn(myGame, i);
        }
    }

    public void moveOnColumn(Connect4Game connect4game, int i)
    {
        Connect4Slot connect4slot = getTopEmptySlot(connect4game.getColumn(i));
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

    public String getName()
    {
        return "Random Agent";
    }

    Random r;
}
