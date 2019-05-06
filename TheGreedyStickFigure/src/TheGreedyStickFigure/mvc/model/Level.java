package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kevinhawryluk on 11/25/16. This is a class for creating the levels.
 */
public class Level {
    private ArrayList<Floating_Tiles> tilesArrayList;
    private ArrayList<Final_Blocks> final_Blocks;
    private Floor floor;

    /**
     * Instantiates a new Level.
     *
     * @param levelNumber the level number
     */
    public Level(int levelNumber) {
        tilesArrayList = new ArrayList<>();
        final_Blocks = new ArrayList<>();
        switch (levelNumber) {

            //First level. Mostly running and dodging enemies. One moving platform.
            case 1:
                tilesArrayList.add(new Floating_Tiles(3, new Point(800, 450), 1, 5));
                tilesArrayList.add(new Floating_Tiles(3, new Point(2800, 450), 1, 5));
                int[] tileCountArr = {1, 1, 1, 2, 3, 4, 0, 0, 0, 0, 2, 3, 1, 1, 1, 1, 2, 1, 1, 2,
                                      3, 2, 1, 2, 3, 4, 0, 0, 0, 0, 2, 3, 1, 1, 1, 1, 2, 1, 1, 1};
                final_Blocks.add(new Final_Blocks(tileCountArr.length * BuildingBlocks.BLOCK_WIDTH
                        - BuildingBlocks.BLOCK_HALF,
                        Game.DIM.height - BuildingBlocks.BLOCK_WIDTH * 2.5));
                final_Blocks.add(new Final_Blocks(tileCountArr.length * BuildingBlocks.BLOCK_WIDTH
                        - BuildingBlocks.BLOCK_HALF,
                        Game.DIM.height - BuildingBlocks.BLOCK_WIDTH * 2.5 - BuildingBlocks.BLOCK_WIDTH));
                final_Blocks.add(new Final_Blocks(tileCountArr.length * BuildingBlocks.BLOCK_WIDTH
                        - BuildingBlocks.BLOCK_HALF,
                        Game.DIM.height - BuildingBlocks.BLOCK_WIDTH * 2.5 - 2 * BuildingBlocks.BLOCK_WIDTH));
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(300), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(900), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(1000), CollisionOp.Operation.ADD);

                floor = new Floor(tileCountArr);

                break;

            //Second Level. All moving platforms.
            case 2:
                int[] tileCountArr2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                tilesArrayList.add(new Floating_Tiles(2, new Point(0, 200), 1, 5));
                tilesArrayList.add(new Floating_Tiles(3, new Point(200, 450), 1, 5));
                tilesArrayList.add(new Floating_Tiles(2, new Point(300, 550), 1, 3));
                tilesArrayList.add(new Floating_Tiles(2, new Point(600, 650), 2, 10));
                tilesArrayList.add(new Floating_Tiles(3, new Point(800, 550), 1, 5));
                tilesArrayList.add(new Floating_Tiles(2, new Point(1000, 350), 1, 5));
                tilesArrayList.add(new Floating_Tiles(4, new Point(1100, 450), 2, 15));
                tilesArrayList.add(new Floating_Tiles(3, new Point(1300, 450), 1, 5));
                tilesArrayList.add(new Floating_Tiles(2, new Point(1400, 550), 1, 3));
                tilesArrayList.add(new Floating_Tiles(2, new Point(1700, 650), 2, 10));
                tilesArrayList.add(new Floating_Tiles(3, new Point(1900, 550), 1, 5));
                tilesArrayList.add(new Floating_Tiles(2, new Point(2100, 350), 1, 5));
                tilesArrayList.add(new Floating_Tiles(4, new Point(2200, 450), 2, 15));

                floor = new Floor(tileCountArr2);
                final_Blocks.add(new Final_Blocks(tileCountArr2.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 2));
                final_Blocks.add(new Final_Blocks(tileCountArr2.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 2 + BuildingBlocks.BLOCK_WIDTH));
                final_Blocks.add(new Final_Blocks(tileCountArr2.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                       BuildingBlocks.BLOCK_WIDTH * 4));
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(300), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(600), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(1100), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(800), CollisionOp.Operation.ADD);
                break;

            //Third level. Very hilly, lots of enemies, as well as some asteroids.
            case 3:
                int[] tileCountArr3 = {4, 3, 2, 1, 1, 1, 2, 3, 4, 4, 3, 2, 1, 1, 1, 0, 0, 0, 0, 0, 1, 2, 1, 1,
                        4, 3, 2, 1, 1, 1, 2, 3, 4, 4, 3, 2, 1, 1, 1, 0, 0, 0, 0, 0, 1, 2, 1, 1};
                final_Blocks.add(new Final_Blocks(tileCountArr3.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 2));
                final_Blocks.add(new Final_Blocks(tileCountArr3.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 2 + BuildingBlocks.BLOCK_WIDTH));
                final_Blocks.add(new Final_Blocks(tileCountArr3.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 4));
                tilesArrayList.add(new Floating_Tiles(3, new Point(1700, 550), 2, 5));
                tilesArrayList.add(new Floating_Tiles(3, new Point(3800, 550), 2, 5));
                floor = new Floor(tileCountArr3);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(300), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(600), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(1100), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(800), CollisionOp.Operation.ADD);
                break;

            //Fourth level. Very flat. Lots of enemies and asteroids.
            case 4:
                int[] tileCountArr4 = {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1,
                        1, 1, 2, 1, 1, 1, 1, 2, 3, 4, 4, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1};
                floor = new Floor(tileCountArr4);
                final_Blocks.add(new Final_Blocks(tileCountArr4.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 2));
                final_Blocks.add(new Final_Blocks(tileCountArr4.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 2 + BuildingBlocks.BLOCK_WIDTH));
                final_Blocks.add(new Final_Blocks(tileCountArr4.length * BuildingBlocks.BLOCK_WIDTH - BuildingBlocks.BLOCK_HALF,
                        BuildingBlocks.BLOCK_WIDTH * 4));
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(300), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(600), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(1100), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new WalkingEnemy(800), CollisionOp.Operation.ADD);
                break;

        }
    }

    /**
     * Level tile size int.
     *
     * @return the int
     */
    public int levelTileSize()
    {
        return tilesArrayList.size();
    }

    /**
     * Gets level tiles.
     *
     * @return the level tiles
     */
    public ArrayList<Floating_Tile_Blocks> getLevelTiles() {
        ArrayList<Floating_Tile_Blocks> listOfFloatingTiles = new ArrayList<>();
        for (Floating_Tiles floating_tiles : tilesArrayList) {
            listOfFloatingTiles.addAll(floating_tiles.getTiles());
        }
        return listOfFloatingTiles;
    }

    /**
     * Gets final blocks.
     *
     * @return the final blocks
     */
    public ArrayList<Final_Blocks> getFinal_Blocks() {
        return final_Blocks;
    }

    /**
     * Gets floor.
     *
     * @return the floor
     */
    public Floor getFloor() {
        return floor;
    }
}
