package TheGreedyStickFigure.mvc.model;

import java.awt.*;
import java.util.Random;

/**
 * Created by kevinhawryluk on 11/14/16. This is the class for debris that is created when something dies or a bullet
 * hits a block.
 */
public class Debris extends Sprite {
    private int mExpiry;
    private Point mPoint;
    private Random mRandom;


    /**
     * Instantiates a new Debris.
     *
     * @param mExpiry the m expiry
     * @param mPoint  the m point
     */
    public Debris(int mExpiry, Point mPoint) {
        this.mExpiry = mExpiry;
        this.mPoint = mPoint;
        mRandom = new Random();
        setCenter(mPoint);
        setRadius(mExpiry);
        setTeam(Team.DEBRIS);

    }

    @Override
    public void move() {
        if(mExpiry == 0)
        {
            CommandCenter.getInstance().getOpsList()
                    .enqueue(this, CollisionOp.Operation.REMOVE);
        }
        else
        {
            mExpiry--;
            setRadius(mExpiry);
        }

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(mRandom.nextInt(256),mRandom.nextInt(256),mRandom.nextInt(256)));
        g.fillOval(mPoint.x, mPoint.y, mExpiry, mExpiry);
    }


    //Collision Detection


    public void shift()
    {
    }

    public void stopShift()
    {
    }

    /**
     * Gets expiry.
     *
     * @return the expiry
     */
    public int getmExpiry() {
        return mExpiry;
    }

    /**
     * Sets expiry.
     *
     * @param mExpiry the m expiry
     */
    public void setmExpiry(int mExpiry) {
        this.mExpiry = mExpiry;
    }
}
