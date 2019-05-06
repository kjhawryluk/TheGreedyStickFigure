package TheGreedyStickFigure.mvc.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kevinhawryluk on 11/25/16. This creates an object for floating tiles that move back and forth.
 */
public class Floating_Tiles {
    private ArrayList<Floating_Tile_Blocks> tiles;
    private Point startingLocation;
    private int movementSpeed;
    private int movementDistance;

    /**
     * Instantiates a new Floating tiles.
     *
     * @param numOfTiles          the num of tiles
     * @param startingBlockCenter the starting block center
     * @param movementDistance    the movement distance
     * @param movementSpeed       the movement speed
     */
    public Floating_Tiles(int numOfTiles, Point startingBlockCenter, int movementDistance, int movementSpeed){
        this.movementDistance = movementDistance;
        this.movementSpeed = movementSpeed;
        createTiles(numOfTiles, startingBlockCenter);
    }

    /**
     * Create tiles.
     *
     * @param numOfTiles          the num of tiles
     * @param startingBlockCenter the starting block center
     */
    public void createTiles(int numOfTiles, Point startingBlockCenter){
        tiles = new ArrayList<>();
        startingLocation = startingBlockCenter;
        for(int tileCount = 0; tileCount < numOfTiles; tileCount++)
        {
            tiles.add(new Floating_Tile_Blocks((int)(startingBlockCenter.x + tileCount*BuildingBlocks.BLOCK_WIDTH),
                    startingBlockCenter.y, movementDistance, movementSpeed ));
        }
    }

    /**
     * Gets tiles.
     *
     * @return the tiles
     */
    public ArrayList<Floating_Tile_Blocks> getTiles() {
        return tiles;
    }


    /**
     * Gets starting location.
     *
     * @return the starting location
     */
    public Point getStartingLocation() {
        return startingLocation;
    }

    /**
     * Sets starting location.
     *
     * @param startingLocation the starting location
     */
    public void setStartingLocation(Point startingLocation) {
        this.startingLocation = startingLocation;
    }


    /**
     * Gets movement distance.
     *
     * @return the movement distance
     */
    public int getMovementDistance() {
        return movementDistance;
    }

    /**
     * Sets movement distance.
     *
     * @param movementDistance the movement distance
     */
    public void setMovementDistance(int movementDistance) {
        this.movementDistance = movementDistance;
    }

}
