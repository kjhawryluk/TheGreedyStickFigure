package TheGreedyStickFigure.mvc.model;


import java.awt.*;

/**
 * Class for building the blocks that create the levels.
 */
public class BuildingBlocks extends RectangleSprite {

    /**
     * The constant RADIUS.
     */
    public static final int RADIUS = 50;
    /**
     * The constant BLOCK_WIDTH.
     */
    public static final double BLOCK_WIDTH =  RADIUS * 2;// * Math.sqrt(2);
    /**
     * The constant BLOCK_HALF.
     */
    public static final double BLOCK_HALF =  RADIUS;

    /**
     * Instantiates a new Building blocks.
     *
     * @param x the x
     * @param y the y
     */
    public BuildingBlocks(double x, double y) {
        super();
        setTeam(Team.BLOCK);

        setColor(Color.ORANGE);

        //this is the size of the block
        setRadius(RADIUS);

        updateCenter(new Point((int)(x), (int)(y)));
        setRectangle();
        setColor(Color.GRAY);
    }

    @Override
    public void move() {
        Point pnt = getCenter();
        double dX = pnt.x + getDeltaX();
        updateCenter(new Point((int) dX, pnt.y));
    }

    @Override
    public String toString() {
        return "BuildingBlocks: X: " + getCenter().x + " y: " + getCenter().y ;
    }


    @Override
    public void draw(Graphics g) {
        draw(g, new Color(85, 2, 15), new Color(104, 4, 23));
    }

    /**
     * Draw.
     *
     * @param g          the g
     * @param outerColor the outer color
     * @param innerColor the inner color
     */
    public void draw(Graphics g, Color outerColor, Color innerColor) {
        RadialGradientPaint gradientPaint = new RadialGradientPaint(getCenter().x, getCenter().y,
                getRadius(), new float[] {0, 1},
                new Color[] {innerColor, outerColor});
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(gradientPaint);

        g2d.fillRoundRect(gettLCorner().x, gettLCorner().y,
                (int)Math.ceil(BLOCK_WIDTH), (int)Math.ceil(BLOCK_WIDTH),10,10);
        g.setColor(getColor());
        g.drawRoundRect(gettLCorner().x, gettLCorner().y,
                (int)Math.ceil(BLOCK_WIDTH), (int)Math.ceil(BLOCK_WIDTH),10,10);
    }

}
