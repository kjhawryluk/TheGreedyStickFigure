package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinhawryluk on 11/19/16.
 */
public class Floor_Partition {
    private List<BuildingBlocks> floorBlocks = new ArrayList<>();
    private int partitionNumber;
    /**
     * The constant STARTING_Y_POSITION.
     */
    public final static int STARTING_Y_POSITION = (int)(Game.DIM.height  - BuildingBlocks.BLOCK_HALF);
    private double height = STARTING_Y_POSITION - BuildingBlocks.BLOCK_HALF;
    private double leftBoundary;
    private double rightBoundary;
    private boolean bMove = false;


    /**
     * Instantiates a new Floor partition.
     *
     * @param partitionNumber the partition number
     * @param startingX       the starting x
     * @param numOfTiles      the num of tiles
     */
    public Floor_Partition(int partitionNumber, double startingX, int numOfTiles) {
        this.partitionNumber = partitionNumber;
        buildFloor(startingX, numOfTiles);
    }

    /**
     * Build floor.
     *
     * @param startingX  the starting x
     * @param numOfTiles the num of tiles
     */
    public void buildFloor(double startingX, int numOfTiles)
    {
        //floorBlocks.add(new BuildingBlocks(startingX, STARTING_Y_POSITION));
        buildExtraFloorTiles(startingX, numOfTiles);
    }


    /**
     * Build extra floor tiles.
     *
     * @param xPosition  the x position
     * @param numOfTiles the num of tiles
     */
    public void buildExtraFloorTiles(double xPosition, int numOfTiles){

        double newYPosition = 0;

        //If the randomStackOfBlocks is greater than 0, add the blocks and update the height.
        if(numOfTiles > 0) {
            for (int randomBlockCount = 1; randomBlockCount <= numOfTiles; randomBlockCount++) {
                newYPosition = STARTING_Y_POSITION - randomBlockCount * BuildingBlocks.BLOCK_WIDTH;
                floorBlocks.add(new BuildingBlocks(xPosition, newYPosition));
            }
            height = newYPosition - BuildingBlocks.BLOCK_WIDTH;
        }
    }

    /**
     * Gets floor blocks.
     *
     * @return the floor blocks
     */
    public List<BuildingBlocks> getFloorBlocks() {
        return floorBlocks;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return (int)Math.ceil(height);
    }


    /**
     * Sprite in floor partition boolean.
     *
     * @param frontOfSprite the front of sprite
     * @return the boolean
     */
    public boolean spriteInFloorPartition(Point frontOfSprite)
    {
        if(frontOfSprite.x < leftBoundary || frontOfSprite.x > rightBoundary)
        {
            return false;
        }
        else
        {
            return true;
        }

    }


    /**
     * Sprite in floor partition boolean.
     *
     * @param sprite the sprite
     * @return the boolean
     */
    public boolean spriteInFloorPartition(Sprite sprite)
    {
        double radius = sprite.getRadius();
        if(sprite.getCenter().x - radius/2.0 < leftBoundary || sprite.getCenter().x + radius/2.0 > rightBoundary)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    /**
     * Move.
     */
    public void move()
    {
        System.out.println("start moving floor");
            for (BuildingBlocks floorBlock : floorBlocks) {
                floorBlock.setDeltaX(-BuildingBlocks.BLOCK_WIDTH/10);
            }
        }

    /**
     * Stop move.
     */
    public void stopMove()
    {
        for (BuildingBlocks floorBlock : floorBlocks) {
            floorBlock.setDeltaX(0);
        }
    }

    /**
     * Gets left boundary.
     *
     * @return the left boundary
     */
    public double getLeftBoundary() {
        return leftBoundary;
    }

    /**
     * Gets right boundary.
     *
     * @return the right boundary
     */
    public double getRightBoundary() {
        return rightBoundary;
    }

    /**
     * Sets floor blocks.
     *
     * @param floorBlocks the floor blocks
     */
    public void setFloorBlocks(List<BuildingBlocks> floorBlocks) {
        this.floorBlocks = floorBlocks;
    }

    /**
     * Gets partition number.
     *
     * @return the partition number
     */
    public int getPartitionNumber() {
        return partitionNumber;
    }

    /**
     * Sets partition number.
     *
     * @param partitionNumber the partition number
     */
    public void setPartitionNumber(int partitionNumber) {
        this.partitionNumber = partitionNumber;
    }
}
