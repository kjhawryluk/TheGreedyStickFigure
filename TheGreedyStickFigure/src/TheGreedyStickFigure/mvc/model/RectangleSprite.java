package TheGreedyStickFigure.mvc.model;

import java.awt.*;

/**
 * Created by kevinhawryluk on 11/22/16. This was created to facilitate collision detection, by keeping track of corners.
 */
public abstract class RectangleSprite extends Sprite {
    private Rectangle rectangle;
    private double rectangleWidth;
    private double rectangleHalfWidth;
    private Point tLCorner;
    private Point tRCorner;
    private Point bLCorner;
    private Point bRCorner;

    /**
     * Gets rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void setCenter(Point pntParam) {
        super.setCenter(pntParam);
        setTLCorner(pntParam);
    }

    /**
     * Update center.
     *
     * @param pntParam the pnt param
     */
    public void updateCenter(Point pntParam)
    {
        super.setCenter(pntParam);
        setCorners(pntParam);
        setRectangle();
    }

    @Override
    public void setRadius(int n) {
        super.setRadius(n);
        rectangleWidth = n * 2;
        rectangleHalfWidth  = rectangleWidth/2;
    }

    /**
     * Sets rectangle.
     */
    public void setRectangle()
    {
        rectangle = new Rectangle(tLCorner.x, tLCorner.y,
                (int)rectangleWidth, (int)(rectangleWidth));
    }

    /**
     * Gets l corner.
     *
     * @return the l corner
     */
    public Point gettLCorner() {
        return tLCorner;
    }

    /**
     * Gets rectangle width.
     *
     * @return the rectangle width
     */
    public double getRectangleWidth() {
        return rectangleWidth;
    }

    /**
     * Sets rectangle width.
     *
     * @param rectangleWidth the rectangle width
     */
    public void setRectangleWidth(double rectangleWidth) {
        this.rectangleWidth = rectangleWidth;
    }

    /**
     * Gets rectangle half width.
     *
     * @return the rectangle half width
     */
    public double getRectangleHalfWidth() {
        return rectangleHalfWidth;
    }

    /**
     * Sets rectangle half width.
     *
     * @param rectangleHalfWidth the rectangle half width
     */
    public void setRectangleHalfWidth(double rectangleHalfWidth) {
        this.rectangleHalfWidth = rectangleHalfWidth;
    }

    /**
     * Is vertical intersection boolean.
     *
     * @param collisionRectangle the collision rectangle
     * @return the boolean
     */
    public boolean isVerticalIntersection(RectangleSprite collisionRectangle)
    {
        boolean verticalIntersection = false;
        Point collisionTlCorner = collisionRectangle.gettLCorner();
        Point collisionBlCorner = collisionRectangle.getbLCorner();

        if(bLCorner.y > collisionTlCorner.y || tLCorner.y < collisionBlCorner.y)
            verticalIntersection = true;
        return verticalIntersection;
    }

    /**
     * Is horizontal intersection boolean.
     *
     * @param collisionRectangle the collision rectangle
     * @return the boolean
     */
    public boolean isHorizontalIntersection(RectangleSprite collisionRectangle)
    {
        boolean horizontalIntersection = false;
        Point collisionTrCorner = collisionRectangle.gettRCorner();
        Point collisionTlCorner = collisionRectangle.gettLCorner();

        if(tRCorner.x > collisionTlCorner.x && tLCorner.x < collisionTrCorner.x
                || tLCorner.x < collisionTlCorner.x && tRCorner.x > collisionTrCorner.x)
            horizontalIntersection = true;
        return horizontalIntersection;
    }

    /**
     * Set corners.
     *
     * @param center the center
     */
    public void setCorners(Point center){
        setTLCorner(center);
        setTRCorner(center);
        setBLCorner(center);
        setBRCorner(center);
    }

    /**
     * Sets tl corner.
     *
     * @param center the center
     */
    public void setTLCorner(Point center)
    {
        tLCorner = new Point((int)(center.x - rectangleHalfWidth),(int) (center.y - rectangleHalfWidth));
    }

    /**
     * Sets tr corner.
     *
     * @param center the center
     */
    public void setTRCorner(Point center)
    {
        tRCorner = new Point((int)(center.x + rectangleHalfWidth),(int) (center.y - rectangleHalfWidth));
    }

    /**
     * Sets bl corner.
     *
     * @param center the center
     */
    public void setBLCorner(Point center)
    {
        bLCorner = new Point((int)(center.x - rectangleHalfWidth),(int) (center.y + rectangleHalfWidth));
    }

    /**
     * Sets br corner.
     *
     * @param center the center
     */
    public void setBRCorner(Point center)
    {
        bRCorner = new Point((int)(center.x - rectangleHalfWidth),(int) (center.y - rectangleHalfWidth));
    }

    /**
     * Gets r corner.
     *
     * @return the r corner
     */
    public Point gettRCorner() {
        return tRCorner;
    }

    /**
     * Gets l corner.
     *
     * @return the l corner
     */
    public Point getbLCorner() {
        return bLCorner;
    }

    /**
     * Gets r corner.
     *
     * @return the r corner
     */
    public Point getbRCorner() {
        return bRCorner;
    }




}
