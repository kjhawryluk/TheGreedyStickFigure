package TheGreedyStickFigure.mvc.model;

import java.awt.*;

/**
 * Created by kevinhawryluk on 11/27/16. These are the blocks necessary to blow up in order to clear the level.
 */
public class Final_Blocks extends BuildingBlocks{
    /**
     * The B moved.
     */
    boolean bMoved = false;

    /**
     * Instantiates a new Final blocks.
     *
     * @param x the x
     * @param y the y
     */
    public Final_Blocks(double x, double y) {
        super(x, y);
        setTeam(Team.FOE);
    }

    @Override
    public void shift() {
        //This is half as slow because it moves twice because it's both a block and an enemy.
        setDeltaX(-BuildingBlocks.BLOCK_WIDTH/(SHIFT_SPEED));
    }

    @Override
    public void move() {
        if(!bMoved) {
            super.move();
            bMoved = true;
        }
        else
        {
            bMoved = false;
        }
    }


    @Override
    public void draw(Graphics g) {
        super.draw(g, new Color(218,165,32), new Color(255,215,0));
    }

}
