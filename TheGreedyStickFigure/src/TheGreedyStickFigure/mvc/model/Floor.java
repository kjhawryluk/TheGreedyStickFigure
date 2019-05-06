package TheGreedyStickFigure.mvc.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinhawryluk on 11/17/16. This takes an array of numbers to determine the number of blocks in each floor
 * partition, creates that partition and adds the blocks to a big array to be used for the bulk of the setting.
 */
public class Floor {

    private int count_of_partitions;
    private List<BuildingBlocks> allFloorBlocks = new ArrayList<>();
    private List<Floor_Partition> floorPartitions = new ArrayList<>();

    /**
     * Instantiates a new Floor.
     *
     * @param floorPartitionsTileCount the floor partitions tile count
     */
    public Floor(int[] floorPartitionsTileCount) {
        double startingX;
        count_of_partitions = floorPartitionsTileCount.length;
        for (int partitionCount = 0; partitionCount < count_of_partitions; partitionCount++) {
            startingX = BuildingBlocks.BLOCK_HALF + partitionCount * BuildingBlocks.BLOCK_WIDTH;
            floorPartitions.add(new Floor_Partition(partitionCount, startingX, floorPartitionsTileCount[partitionCount]));
        }
        aggregateFloorBlocks();
    }


    /**
     * Aggregate floor blocks.
     */
    public void aggregateFloorBlocks() {
        allFloorBlocks.clear();
        for (Floor_Partition floorPartition : floorPartitions) {
            allFloorBlocks.addAll(floorPartition.getFloorBlocks());
        }
    }

    /**
     * Gets floor blocks.
     *
     * @return the floor blocks
     */
    public List<BuildingBlocks> getFloorBlocks() {
        return allFloorBlocks;
    }

    /**
     * Default get floor height int.
     *
     * @return the int
     */
    public int defaultGetFloorHeight() {
        return floorPartitions.get(0).getHeight();
    }

    /**
     * Gets floor height.
     *
     * @param sprite the sprite
     * @return the floor height
     */
    public int getFloorHeight(Sprite sprite) {
        int height = CommandCenter.getInstance().getPerson().getCurrentFloorHeight();
        for (Floor_Partition floorPartition : floorPartitions) {
            if (floorPartition.spriteInFloorPartition(sprite)) {
                height = floorPartition.getHeight();
                break;
            }
        }
        System.out.println("new height " + height);
        return height;
    }

    /**
     * Gets floor height.
     *
     * @param frontOfSprite the front of sprite
     * @return the floor height
     */
    public int getFloorHeight(Point frontOfSprite) {
        int height = CommandCenter.getInstance().getPerson().getCurrentFloorHeight();
        for (Floor_Partition floorPartition : floorPartitions) {
            if (floorPartition.spriteInFloorPartition(frontOfSprite)) {
                height = floorPartition.getHeight();
                break;
            }
        }
        return height;
    }

    /**
     * Gets floor partition.
     *
     * @param xPosition the x position
     * @return the floor partition
     */
    public Floor_Partition getFloorPartition(double xPosition) {
        int partitionNumber = (int) (Math.round(xPosition / (BuildingBlocks.BLOCK_WIDTH)));
        if (partitionNumber < 0) {
            partitionNumber = 0;
        } else if (partitionNumber > count_of_partitions - 1) {
            partitionNumber = count_of_partitions - 1;
        }
        return floorPartitions.get(partitionNumber);
    }

//    public void stopMove() {
//        bMove = false;
//        for (int partitionCount = 0; partitionCount < floorPartitions.size(); partitionCount++) {
//            floorPartitions.get(partitionCount).stopMove();
//        }
//    }
//    public void move() {
//        if (bMove == false) {
//            tickCount = Game.getThisGame().getTick();
//            bMove = true;
//            for (int partitionCount = 0; partitionCount < floorPartitions.size(); partitionCount++) {
//               // floorPartitions.get(partitionCount).setPartitionNumber(partitionCount - 1);
//                floorPartitions.get(partitionCount).move();
//            }
//            //aggregateFloorBlocks();
//        } else {
//            if (Game.getThisGame().getTick() > tickCount + 10) {
//                stopMove();
//            }
//        }
//    }
}
