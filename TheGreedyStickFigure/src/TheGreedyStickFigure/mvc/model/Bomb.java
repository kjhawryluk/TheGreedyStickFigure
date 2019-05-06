package TheGreedyStickFigure.mvc.model;

import java.awt.*;

/**
 * Bomb weapon for killing all enemies in nearby radius.
 */
public class Bomb extends Debris {
    /**
     * The constant BOMB_EXPIRY.
     */
    public static final int BOMB_EXPIRY = 300;

    /**
     * Instantiates a new Bomb.
     *
     * @param mPoint the m point
     */
    public Bomb(Point mPoint)
    {
        this(BOMB_EXPIRY, new Point(mPoint.x - BOMB_EXPIRY/4, mPoint.y - BOMB_EXPIRY/4));
    }


    /**
     * Instantiates a new Bomb.
     *
     * @param mExpiry the m expiry
     * @param mPoint  the m point
     */
    public Bomb(int mExpiry, Point mPoint) {
        super(mExpiry, mPoint);
        setTeam(Team.FRIEND);
    }

    @Override
    public void move() {
        if(getmExpiry() == 0)
        {
            CommandCenter.getInstance().getOpsList()
                    .enqueue(this, CollisionOp.Operation.REMOVE);
        }
        else
        {
          setmExpiry(getmExpiry() - 50);
        }

    }
}
