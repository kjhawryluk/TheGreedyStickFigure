package TheGreedyStickFigure.mvc.model;


import java.awt.*;
import java.util.Arrays;

import TheGreedyStickFigure.mvc.controller.Game;

/**
 * The type Asteroid.
 */
public class Asteroid extends Sprite {

	
	private int nSpin;
	
	//radius of a large asteroid
	private final int RAD = 100;
	private Color randomColor = new Color((int)Math.round(Math.random()*255),(int)Math.round(Math.random()*255)
			,(int)Math.round(Math.random()*255));

    /**
     * Instantiates a new Asteroid.
     *
     * @param nSize the n size
     */
//nSize determines if the Asteroid is Large (0), Medium (1), or Small (2)
	//when you explode a Large asteroid, you should spawn 2 or 3 medium asteroids
	//same for medium asteroid, you should spawn small asteroids
	//small asteroids get blasted into debris
	public Asteroid(int nSize){
		
		//call Sprite constructor
		super();
		setCenter(new Point(Game.R.nextInt(Game.DIM.width), 0));  //Comes from the top.
		setTeam(Team.FOE);
		
		//the spin will be either plus or minus 0-9
		int nSpin = Game.R.nextInt(10);
		if(nSpin %2 ==0)
			nSpin = -nSpin;
		setSpin(nSpin);
			
		//random delta-x
		int nDX = Game.R.nextInt(10) + 20;
		if(nDX %2 ==0)
			nDX = -nDX;
		setDeltaX(nDX);
		
		//random delta-y
		int nDY = 20;

		setDeltaY(nDY);
			
		assignRandomShape();
		
		//an nSize of zero is a big asteroid
		//a nSize of 1 or 2 is med or small asteroid respectively
		if (nSize == 0)
			setRadius(RAD);
		else
			setRadius(RAD/(nSize * 2));
		

	}


    /**
     * Instantiates a new Asteroid.
     *
     * @param astExploded the ast exploded
     */
    public Asteroid(Asteroid astExploded){
	

		//call Sprite constructor
		super();
		setTeam(Team.FOE);
		int  nSizeNew =	astExploded.getSize() + 1;
		
		
		//the spin will be either plus or minus 0-9
		int nSpin = Game.R.nextInt(10);
		if(nSpin %2 ==0)
			nSpin = -nSpin;
		setSpin(nSpin);
			
		//random delta-x
		int nDX = Game.R.nextInt(10 + nSizeNew*2) + 10;
		if(nDX %2 ==0)
			nDX = -nDX;
		setDeltaX(nDX);
		
		//random delta-y
		int nDY = Game.R.nextInt(10+ nSizeNew*2) + 10;
		if(nDY %2 ==0)
			nDY = -nDY;
		setDeltaY(nDY);
			
		assignRandomShape();
		
		//an nSize of zero is a big asteroid
		//a nSize of 1 or 2 is med or small asteroid respectively

		setRadius(RAD/(nSizeNew * 2));
		setCenter(astExploded.getCenter());
		
		
		

	}

    /**
     * Get size int.
     *
     * @return the int
     */
    public int getSize(){
		
		int nReturn = 0;
		
		switch (getRadius()) {
			case 100:
				nReturn= 0;
				break;
			case 50:
				nReturn= 1;
				break;
			case 25:
				nReturn= 2;
				break;
		}
		return nReturn;
		
	}


	@Override
	public void move(){
		super.move();
		
		//an asteroid spins, so you need to adjust the orientation at each move()
		setOrientation(getOrientation() + getSpin());
		
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

    /**
     * Assign random shape.
     */
//this is for an asteroid only
	  public void assignRandomShape ()
	  {
	    int nSide = Game.R.nextInt( 7 ) + 7;
	    int nSidesTemp = nSide;

	    int[] nSides = new int[nSide];
	    for ( int nC = 0; nC < nSides.length; nC++ )
	    {
	      int n = nC * 48 / nSides.length - 4 + Game.R.nextInt( 8 );
	      if ( n >= 48 || n < 0 )
	      {
	        n = 0;
	        nSidesTemp--;
	      }
	      nSides[nC] = n;
	    }

	    Arrays.sort( nSides );

	    double[]  dDegrees = new double[nSidesTemp];
	    for ( int nC = 0; nC <dDegrees.length; nC++ )
	    {
	    	dDegrees[nC] = nSides[nC] * Math.PI / 24 + Math.PI / 2;
	    }
	   setDegrees( dDegrees);
	   
		double[] dLengths = new double[dDegrees.length];
			for (int nC = 0; nC < dDegrees.length; nC++) {
				if(nC %3 == 0)
				    dLengths[nC] = 1 - Game.R.nextInt(40)/100.0;
				else
					dLengths[nC] = 1;
			}
		setLengths(dLengths);

	  }

	@Override
	public void draw(Graphics g) {
		draw(g, new Color(24, 24, 28), new Color(74, 72, 75));
		setColor(Color.black);
	}

    /**
     * Draw.
     *
     * @param g          the g
     * @param outerColor the outer color
     * @param innerColor the inner color
     */
    public void draw(Graphics g, Color outerColor, Color innerColor) {
		super.draw(g);
		RadialGradientPaint gradientPaint = new RadialGradientPaint(getCenter().x, getCenter().y,
				getRadius(), new float[]{0, 1},
				new Color[]{innerColor, outerColor});
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(gradientPaint);

		g2d.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}

}
