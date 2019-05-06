package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;

/**
 * Created by kevinhawryluk on 11/21/16. This is the character the player controls.
 */
public class PlayerCharacter extends Movable_Person {
    /**
     * Instantiates a new Player character.
     */
    public PlayerCharacter() {
        super();
        setTeam(Team.FRIEND);
        setColor(Color.white);

        //this is the size of the falcon
        setRadius(Movable_Person.START_RADIUS);

        updateCenter(new Point(60, 0));

        setFrontOfCharacter(new Point(getCenter().x + getRadius(), getCenter().y));

        setProtected(true);
        setFadeValue(0);
        setRectangle();
    }

    @Override
    public void move() {

        super.move();
        //implementing the fadeInOut functionality - added by Dmitriy
        if (getProtected()) {
            setFadeValue(getFadeValue() + 3);
        }
        if (getFadeValue() == 150) {
            setProtected(false);
        }

        stopRunning();

        launchEnemies();

        shiftStage();
    }

    private void shiftStage() {
        if (getCenter().x > Game.DIM.width * 1 / 3) {
            CommandCenter.getInstance().shiftSurroundings();
            CommandCenter.getInstance().setbShift(true);
        } else {
            CommandCenter.getInstance().stopShiftingSurroundings();
            CommandCenter.getInstance().setbShift(false);
        }
    }

    private void launchEnemies() {
        if (getCenter().x > Game.DIM.width * 1 / 3 &&
                    Game.getThisGame().getTick() % 150 / CommandCenter.getInstance().getLevel() == 0)
        {
            CommandCenter.getInstance().getOpsList().enqueue(new Shooting_Enemy(
                    (int) (Game.DIM.width - BuildingBlocks.BLOCK_HALF)), CollisionOp.Operation.ADD);
            if(CommandCenter.getInstance().getLevel() > 2)
                Game.getThisGame().spawnAsteroids(CommandCenter.getInstance().getLevel() - 2);
        }
    }
}
