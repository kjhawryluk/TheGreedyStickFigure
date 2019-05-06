package TheGreedyStickFigure.mvc.model;

import java.awt.*;

/**
 * Created by kevinhawryluk on 11/30/16. Extension of walking enemy that shoots the player.
 */
public class Shooting_Enemy extends WalkingEnemy {
    /**
     * Instantiates a new Shooting enemy.
     *
     * @param startingX the starting x
     */
    public Shooting_Enemy(int startingX) {
        super(startingX);
        setBodyColor(new Color(83, 3, 85));
        setColor(getBodyColor());
    }


    @Override
    public void move() {
        super.move();

        //Shoot the player.
        if(getExpire() % 30 == 0)
        {
            CommandCenter.getInstance().getOpsList().enqueue(new Shooting_Enemy_Bullet(this), CollisionOp.Operation.ADD);
        }


    }
}
