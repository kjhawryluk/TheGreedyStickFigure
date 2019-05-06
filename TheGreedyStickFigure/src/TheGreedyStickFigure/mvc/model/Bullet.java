package TheGreedyStickFigure.mvc.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class for bullet used to shoot foes.
 */
public class Bullet extends Sprite {

	  private final double FIRE_POWER = 35.0;


	/**
	 * Instantiates a new Bullet.
	 *
	 * @param person the person
	 */
	public Bullet(Movable_Person person){
		
		super();
	    setTeam(Team.FRIEND);
		
		//defined the points on a cartesean grid
		ArrayList<Point> pntCs = new ArrayList<Point>();
		
		pntCs.add(new Point(0,3)); //top point
		
		pntCs.add(new Point(1,-1));
		pntCs.add(new Point(0,-2));
		pntCs.add(new Point(-1,-1));

		assignPolarPoints(pntCs);

		//a bullet expires after 20 frames
	    setExpire( 20 );
	    setRadius(10);
	    

	    //everything is relative to the falcon ship that fired the bullet
	    setDeltaX( person.getDeltaX() +
	               Math.cos( Math.toRadians( person.getOrientation() ) ) * FIRE_POWER );
	   setDeltaY(0);
	    setCenter( person.getCenter() );

	    //set the bullet orientation to the falcon (ship) orientation
	    setOrientation(person.getOrientation());


	}

	@Override
	public void move(){

		super.move();

		if (getExpire() == 0 || getDeltaX() == 0)
		{CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
			CommandCenter.getInstance().getOpsList().enqueue(new Debris(25, this.getCenter()),CollisionOp.Operation.ADD);}
		else
			setExpire(getExpire() - 1);

	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		setColor(new Color(255,69,0));
		g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}

}
