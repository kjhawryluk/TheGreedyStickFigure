package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;

/**
 * Created by kevinhawryluk on 11/21/16. The basic enemy that walks around and kills you if you collide.
 */
public class WalkingEnemy extends Movable_Person implements Scorable {
    private int score = 50;
    private final static int WALKING_ENEMY_EXPIRE = 700;
    private static Color bodyColor = new Color(0,100,0);

    /**
     * Instantiates a new Walking enemy.
     *
     * @param startingX the starting x
     */
    public WalkingEnemy (int startingX) {
        super();

        if(getRandomBoolean())
        {
            switchDirection();
            setCurrentPosition(super.getLeftCharacter());
            assignPolarPoints(getCurrentPosition());
        }

        setDeltaY(10);
        setDeltaX(-10);
        setTeam(Team.FOE);
        setRadius(35);
        setColor(bodyColor);

        updateCenter(new Point(startingX, 200));
        setFrontOfCharacter(new Point(getCenter().x + getRadius(), getCenter().y));
        setRectangle();
        setExpire(WALKING_ENEMY_EXPIRE);
    }

    @Override
    public void move() {
        //Designed to pace back and forth.
        super.move();
        int speed = -5;
        if(getDeltaX() == 0)
            setDeltaX(speed);
        int randomTimeOffset = 25 + (int)Math.round(Math.random() * 10);
        if(Game.thisGame.getTick() % randomTimeOffset == 0)
        {
            setDeltaX(-speed);
            reorientCharacter();
        }
        switchDirection();

        if (getExpire() == 0)
        {
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        }
        else
            setExpire(getExpire() - 1);
    }

    /**
     * Gets random boolean.
     *
     * @return the random boolean
     */
    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        drawShipWithColor(g, bodyColor);
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public double handleOffScreenPerson(double dX) {
        if (dX > MAX_RIGHT || dX < MAX_LEFT)
        {
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        }
        return dX;
    }

    /**
     * Gets body color.
     *
     * @return the body color
     */
    public static Color getBodyColor() {
        return bodyColor;
    }

    /**
     * Sets body color.
     *
     * @param bodyColor the body color
     */
    public static void setBodyColor(Color bodyColor) {
        WalkingEnemy.bodyColor = bodyColor;
    }
}
