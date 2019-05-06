package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;


/**
 * The type Coin. Falls randomly from the sky and scores players points if they collide with the coins or shoot them.
 */
public class Coin extends Movable_Person implements Scorable {

	/**
	 * Instantiates a new Coin.
	 */
	public Coin() {

		super();
		setTeam(Team.FOE);

		setExpire(50);
		setRadius(40);
		setColor(Color.BLUE);


		int nX = Game.R.nextInt(10);
		int nY = 10;

		//set random DeltaX
		if (nX % 2 == 0)
			setDeltaX(nX);
		else
			setDeltaX(-nX);

		//set DeltaY
		setDeltaY(nY);

		//random point at top of the screen
		updateCenter(new Point(Game.R.nextInt(Game.DIM.width), 0));

	}

	@Override
	public void move() {
		super.move();

		//adding expire functionality
		if (getExpire() == 0)
			CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
		else
			setExpire(getExpire() - 1);
		if(CommandCenter.getInstance().getPerson().getCenter().x > Game.DIM.width/2
				&& getCenter().x < Game.DIM.width/10)
		{
			CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
		}

	}


	@Override
	public void draw(Graphics g) {
		RadialGradientPaint gradientPaint = new RadialGradientPaint(getCenter().x, getCenter().y,
				getRadius(), new float[] {0, 1},
				new Color[] {new Color(162, 132, 7), new Color(255,215,0)});
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(gradientPaint);

		//fill this polygon (with whatever color it has)
		g2d.fillOval(getCenter().x, getCenter().y, getRadius(),getRadius());
		//now draw a white border
		g.setColor(Color.WHITE);
		g.drawOval(getCenter().x, getCenter().y, getRadius(),getRadius());
	}

	@Override
	public int getScore() {
		return 10;
	}
}
