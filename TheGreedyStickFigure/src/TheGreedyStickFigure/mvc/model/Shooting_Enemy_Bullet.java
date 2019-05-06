package TheGreedyStickFigure.mvc.model;

/**
 * Created by kevinhawryluk on 11/30/16. Extension of bullet that is fired by an enemy.
 */
public class Shooting_Enemy_Bullet extends Bullet {
    private Shooting_Enemy enemy;


    /**
     * Instantiates a new Shooting enemy bullet.
     *
     * @param person the person
     */
    public Shooting_Enemy_Bullet(Shooting_Enemy person) {
        super(person);
        enemy = person;
        setTeam(Team.FOE);
    }

    /**
     * Gets enemy.
     *
     * @return the enemy
     */
    public Shooting_Enemy getEnemy() {
        return enemy;
    }
}
