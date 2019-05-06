package TheGreedyStickFigure.mvc.model;

import java.awt.*;

/**
 * Created by kevinhawryluk on 11/25/16. These are the blocks for the floating tiles that move back and forth.
 */
public class Floating_Tile_Blocks extends BuildingBlocks {
    private int movementDistance;
    private final Point initialStartingLocation;
    private Point startingLocation;
    private int movementSpeed;

    /**
     * Instantiates a new Floating tile blocks.
     *
     * @param x                the x
     * @param y                the y
     * @param movementDistance the movement distance
     * @param movementSpeed    the movement speed
     */
    public Floating_Tile_Blocks(int x, int y, int movementDistance, int movementSpeed) {
        super(x, y);
        startingLocation = new Point(x, y);
        initialStartingLocation = new Point(x, y);
        this.movementDistance = movementDistance;
        this.movementSpeed = movementSpeed;
    }

    public void move()
    {
        int currentCenterOfFirstBlock = getCenter().x;

        //Move back and forth relative the the starting location.
        if(currentCenterOfFirstBlock > startingLocation.x + movementDistance * BuildingBlocks.BLOCK_WIDTH
                || currentCenterOfFirstBlock < startingLocation.x - movementDistance * BuildingBlocks.BLOCK_WIDTH)
        {
            movementSpeed = -movementSpeed;
        }

        Point pnt = getCenter();
        double dX = pnt.x + movementSpeed + getDeltaX();
        updateStartingLocation();
        updateCenter(new Point((int) dX, pnt.y));
    }

    /**
     * Update starting location. This is for when the blocks shift.
     */
    public void updateStartingLocation()
    {
        startingLocation.setLocation(startingLocation.x + getDeltaX(), startingLocation.y);
    }

    /**
     * Remove.
     */
    public void remove()
    {
        CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
    }
}
