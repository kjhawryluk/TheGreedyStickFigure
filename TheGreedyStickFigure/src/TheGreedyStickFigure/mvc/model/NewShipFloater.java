package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * The type New ship floater.
 */
public class NewShipFloater extends Sprite {


	private int nSpin;

	/**
	 * Instantiates a new New ship floater.
	 */
	public NewShipFloater() {

		super();
		setTeam(Team.FLOATER);
		ArrayList<Point> pntCs = new ArrayList<Point>();
		// top of ship
		pntCs.add(new Point(5, 5));
		pntCs.add(new Point(4,0));
		pntCs.add(new Point(5, -5));
		pntCs.add(new Point(0,-4));
		pntCs.add(new Point(-5, -5));
		pntCs.add(new Point(-4,0));
		pntCs.add(new Point(-5, 5));
		pntCs.add(new Point(0,4));

		assignPolarPoints(pntCs);

		setExpire(250);
		setRadius(50);
		setColor(Color.BLUE);


		int nX = Game.R.nextInt(10);
		int nY = Game.R.nextInt(15);
		int nS = Game.R.nextInt(5);
		
		//set random DeltaX
		if (nX % 2 == 0)
			setDeltaX(nX);
		else
			setDeltaX(-nX);

		setDeltaY(nY);
		
		//set random spin
		if (nS % 2 == 0)
			setSpin(nS);
		else
			setSpin(-nS);

		//random point on the screen
		setCenter(new Point(Game.R.nextInt(Game.DIM.width), 0));

		//random orientation 
		 setOrientation(Game.R.nextInt(360));

	}

	@Override
	public void move() {
		super.move();
		setOrientation(getOrientation() + getSpin());

		//adding expire functionality
		if (getExpire() == 0)
			CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
		else
			setExpire(getExpire() - 1);


	}

	/**
	 * Gets spin.
	 *
	 * @return the spin
	 */
	public int getSpin() {
		return this.nSpin;
	}

	/**
	 * Sets spin.
	 *
	 * @param nSpin the n spin
	 */
	public void setSpin(int nSpin) {
		this.nSpin = nSpin;
	}




	@Override
	public void draw(Graphics g) {
		super.draw(g);
		//fill this polygon (with whatever color it has)
		g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
		//now draw a white border
		g.setColor(Color.WHITE);
		g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}

}
