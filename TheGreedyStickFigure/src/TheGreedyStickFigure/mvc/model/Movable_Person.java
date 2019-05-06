package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;


/**
 * The type Movable person. This is the abstract class for the people.
 */
public abstract class Movable_Person extends RectangleSprite {

    // ==============================================================
    // FIELDS
    // ==============================================================

    /**
     * The constant MAX_SPEED.
     */
    public static final int MAX_SPEED = 15;
    /**
     * The constant START_RADIUS.
     */
    public static final int START_RADIUS = 35;
    /**
     * The constant GRAVITY.
     */
    final static int GRAVITY = 25;
    /**
     * The Degree step.
     */
    final int DEGREE_STEP = 7;
    private final double THRUST = 1.25;
    private final double[] FLAME = {23 * Math.PI / 24 + Math.PI / 2,
            Math.PI + Math.PI / 2, 25 * Math.PI / 24 + Math.PI / 2};
    private int radius = START_RADIUS;
    private int currentFloorHeight;
    private boolean bShield = false;
    private boolean bFlame = false;
    private boolean bProtected; //for fade in and out
    private boolean bThrusting = false;
    private boolean bTurningRight = false;
    private boolean bTurningLeft = false;
    private int nShield;
    private int[] nXFlames = new int[FLAME.length];
    private int[] nYFlames = new int[FLAME.length];
    private Point[] pntFlames = new Point[FLAME.length];
    private boolean collision = false;
    private boolean jumping = false;
    private boolean running = false;
    private Point frontOfCharacter;
    private static PointsArrayList rightCharacter;
    private static PointsArrayList leftCharacter;
    private static PointsArrayList leftStanding;
    private static PointsArrayList rightStanding;
    private PointsArrayList currentPosition;
    private double gapCoefficient = 20.0;
    /**
     * The constant MAX_LEFT.
     */
    public static double MAX_LEFT = 0;
    /**
     * The Max right.
     */
    public static double  MAX_RIGHT = Game.DIM.width;

    // ==============================================================
    // CONSTRUCTOR
    // ==============================================================

    /**
     * Instantiates a new Movable person.
     */
    public Movable_Person() {
        super();
        characterPointArrays();
        currentPosition = rightCharacter;
        assignPolarPoints(getCurrentPosition());
        setOrientation(0);
    }

    /**
     * Character point arrays.
     */
    public void characterPointArrays() {
        rightCharacter = new PointsArrayList("rightCharacter");
        leftCharacter =new PointsArrayList("leftCharacter");
        leftStanding = new PointsArrayList("leftStanding");
        rightStanding = new PointsArrayList("rightStanding");

        rightCharacter.add(new Point(335, 223));
        rightCharacter.add(new Point(377, 214));
        rightCharacter.add(new Point(420, 228));
        rightCharacter.add(new Point(452, 261));
        rightCharacter.add(new Point(467, 293));
        rightCharacter.add(new Point(474, 315));
        rightCharacter.add(new Point(462, 348));
        rightCharacter.add(new Point(449, 377));
        rightCharacter.add(new Point(424, 399));
        rightCharacter.add(new Point(402, 407));
        rightCharacter.add(new Point(358, 404));
        rightCharacter.add(new Point(330, 392));
        rightCharacter.add(new Point(303, 369));
        rightCharacter.add(new Point(294, 342));
        rightCharacter.add(new Point(291, 323));
        rightCharacter.add(new Point(291, 290));
        rightCharacter.add(new Point(269, 285));
        rightCharacter.add(new Point(220, 281));
        rightCharacter.add(new Point(158, 273));
        rightCharacter.add(new Point(116, 424));
        rightCharacter.add(new Point(106, 441));
        rightCharacter.add(new Point(69, 441));
        rightCharacter.add(new Point(101, 246));
        rightCharacter.add(new Point(123, 214));
        rightCharacter.add(new Point(217, 207));
        rightCharacter.add(new Point(95, 110));
        rightCharacter.add(new Point(-11, 75));
        rightCharacter.add(new Point(-110, 218));
        rightCharacter.add(new Point(-330, 325));
        rightCharacter.add(new Point(-357, 397));
        rightCharacter.add(new Point(-391, 427));
        rightCharacter.add(new Point(-422, 404));
        rightCharacter.add(new Point(-394, 307));
        rightCharacter.add(new Point(-162, 176));
        rightCharacter.add(new Point(-80, 43));
        rightCharacter.add(new Point(-233, -82));
        rightCharacter.add(new Point(-115, -314));
        rightCharacter.add(new Point(-77, -326));
        rightCharacter.add(new Point(-58, -307));
        rightCharacter.add(new Point(-62, -287));
        rightCharacter.add(new Point(-162, -102));
        rightCharacter.add(new Point(-20, 3));
        rightCharacter.add(new Point(108, 24));
        rightCharacter.add(new Point(158, 65));
        rightCharacter.add(new Point(236, 129));
        rightCharacter.add(new Point(271, 169));
        rightCharacter.add(new Point(259, 14));
        rightCharacter.add(new Point(152, -119));
        rightCharacter.add(new Point(153, -148));
        rightCharacter.add(new Point(175, -165));
        rightCharacter.add(new Point(194, -158));
        rightCharacter.add(new Point(301, -29));
        rightCharacter.add(new Point(308, -10));
        rightCharacter.add(new Point(311, 14));
        rightCharacter.add(new Point(330, 213));

        leftCharacter.add(new Point(-335, 223));
        leftCharacter.add(new Point(-377, 214));
        leftCharacter.add(new Point(-420, 228));
        leftCharacter.add(new Point(-452, 261));
        leftCharacter.add(new Point(-467, 293));
        leftCharacter.add(new Point(-474, 315));
        leftCharacter.add(new Point(-462, 348));
        leftCharacter.add(new Point(-449, 377));
        leftCharacter.add(new Point(-424, 399));
        leftCharacter.add(new Point(-402, 407));
        leftCharacter.add(new Point(-358, 404));
        leftCharacter.add(new Point(-330, 392));
        leftCharacter.add(new Point(-303, 369));
        leftCharacter.add(new Point(-294, 342));
        leftCharacter.add(new Point(-291, 323));
        leftCharacter.add(new Point(-291, 290));
        leftCharacter.add(new Point(-269, 285));
        leftCharacter.add(new Point(-220, 281));
        leftCharacter.add(new Point(-158, 273));
        leftCharacter.add(new Point(-116, 424));
        leftCharacter.add(new Point(-106, 441));
        leftCharacter.add(new Point(-69, 441));
        leftCharacter.add(new Point(-101, 246));
        leftCharacter.add(new Point(-123, 214));
        leftCharacter.add(new Point(-217, 207));
        leftCharacter.add(new Point(-95, 110));
        leftCharacter.add(new Point(11, 75));
        leftCharacter.add(new Point(110, 218));
        leftCharacter.add(new Point(330, 325));
        leftCharacter.add(new Point(357, 397));
        leftCharacter.add(new Point(391, 427));
        leftCharacter.add(new Point(422, 404));
        leftCharacter.add(new Point(394, 307));
        leftCharacter.add(new Point(162, 176));
        leftCharacter.add(new Point(80, 43));
        leftCharacter.add(new Point(233, -82));
        leftCharacter.add(new Point(115, -314));
        leftCharacter.add(new Point(77, -326));
        leftCharacter.add(new Point(58, -307));
        leftCharacter.add(new Point(62, -287));
        leftCharacter.add(new Point(162, -102));
        leftCharacter.add(new Point(20, 3));
        leftCharacter.add(new Point(-108, 24));
        leftCharacter.add(new Point(-158, 65));
        leftCharacter.add(new Point(-236, 129));
        leftCharacter.add(new Point(-271, 169));
        leftCharacter.add(new Point(-259, 14));
        leftCharacter.add(new Point(-152, -119));
        leftCharacter.add(new Point(-153, -148));
        leftCharacter.add(new Point(-175, -165));
        leftCharacter.add(new Point(-194, -158));
        leftCharacter.add(new Point(-301, -29));
        leftCharacter.add(new Point(-308, -10));
        leftCharacter.add(new Point(-311, 14));
        leftCharacter.add(new Point(-330, 213));

        leftStanding.add(new Point(-269, 84));
        leftStanding.add(new Point(-281, 79));
        leftStanding.add(new Point(-294, 74));
        leftStanding.add(new Point(-310, 74));
        leftStanding.add(new Point(-331, 76));
        leftStanding.add(new Point(-352, 84));
        leftStanding.add(new Point(-363, 97));
        leftStanding.add(new Point(-378, 112));
        leftStanding.add(new Point(-397, 138));
        leftStanding.add(new Point(-407, 166));
        leftStanding.add(new Point(-407, 190));
        leftStanding.add(new Point(-402, 217));
        leftStanding.add(new Point(-385, 237));
        leftStanding.add(new Point(-360, 258));
        leftStanding.add(new Point(-331, 275));
        leftStanding.add(new Point(-291, 274));
        leftStanding.add(new Point(-263, 257));
        leftStanding.add(new Point(-241, 235));
        leftStanding.add(new Point(-226, 206));
        leftStanding.add(new Point(-226, 176));
        leftStanding.add(new Point(-221, 146));
        leftStanding.add(new Point(-150, 141));
        leftStanding.add(new Point(-95, 131));
        leftStanding.add(new Point(28, 245));
        leftStanding.add(new Point(41, 253));
        leftStanding.add(new Point(60, 250));
        leftStanding.add(new Point(73, 235));
        leftStanding.add(new Point(-46, 91));
        leftStanding.add(new Point(-59, 74));
        leftStanding.add(new Point(-75, 67));
        leftStanding.add(new Point(-145, 77));
        leftStanding.add(new Point(-16, -30));
        leftStanding.add(new Point(82, -59));
        leftStanding.add(new Point(160, 49));
        leftStanding.add(new Point(177, 47));
        leftStanding.add(new Point(209, 42));
        leftStanding.add(new Point(402, -47));
        leftStanding.add(new Point(476, -22));
        leftStanding.add(new Point(488, -22));
        leftStanding.add(new Point(500, -27));
        leftStanding.add(new Point(521, -39));
        leftStanding.add(new Point(526, -49));
        leftStanding.add(new Point(521, -62));
        leftStanding.add(new Point(513, -77));
        leftStanding.add(new Point(422, -106));
        leftStanding.add(new Point(186, -20));
        leftStanding.add(new Point(149, -91));
        leftStanding.add(new Point(447, -324));
        leftStanding.add(new Point(456, -342));
        leftStanding.add(new Point(461, -354));
        leftStanding.add(new Point(466, -366));
        leftStanding.add(new Point(463, -379));
        leftStanding.add(new Point(451, -388));
        leftStanding.add(new Point(439, -388));
        leftStanding.add(new Point(233, -232));
        leftStanding.add(new Point(92, -134));
        leftStanding.add(new Point(-12, -123));
        leftStanding.add(new Point(-38, -109));
        leftStanding.add(new Point(-113, -52));
        leftStanding.add(new Point(-202, 34));
        leftStanding.add(new Point(-197, -92));
        leftStanding.add(new Point(-41, -134));
        leftStanding.add(new Point(-33, -143));
        leftStanding.add(new Point(-26, -154));
        leftStanding.add(new Point(-26, -171));
        leftStanding.add(new Point(-38, -180));
        leftStanding.add(new Point(-48, -181));
        leftStanding.add(new Point(-239, -143));
        leftStanding.add(new Point(-263, 89));
        leftStanding.add(new Point(-216, -149));
        leftStanding.add(new Point(-246, -114));

        rightStanding.add(new Point(384, 235));
        rightStanding.add(new Point(396, 230));
        rightStanding.add(new Point(409, 225));
        rightStanding.add(new Point(425, 225));
        rightStanding.add(new Point(446, 227));
        rightStanding.add(new Point(467, 235));
        rightStanding.add(new Point(478, 248));
        rightStanding.add(new Point(493, 263));
        rightStanding.add(new Point(512, 289));
        rightStanding.add(new Point(522, 317));
        rightStanding.add(new Point(522, 341));
        rightStanding.add(new Point(517, 368));
        rightStanding.add(new Point(500, 388));
        rightStanding.add(new Point(475, 409));
        rightStanding.add(new Point(446, 426));
        rightStanding.add(new Point(406, 425));
        rightStanding.add(new Point(378, 408));
        rightStanding.add(new Point(356, 386));
        rightStanding.add(new Point(341, 357));
        rightStanding.add(new Point(341, 327));
        rightStanding.add(new Point(336, 297));
        rightStanding.add(new Point(265, 292));
        rightStanding.add(new Point(210, 282));
        rightStanding.add(new Point(87, 396));
        rightStanding.add(new Point(74, 404));
        rightStanding.add(new Point(55, 401));
        rightStanding.add(new Point(42, 386));
        rightStanding.add(new Point(161, 242));
        rightStanding.add(new Point(174, 225));
        rightStanding.add(new Point(190, 218));
        rightStanding.add(new Point(260, 228));
        rightStanding.add(new Point(131, 121));
        rightStanding.add(new Point(33, 92));
        rightStanding.add(new Point(-45, 200));
        rightStanding.add(new Point(-62, 198));
        rightStanding.add(new Point(-94, 193));
        rightStanding.add(new Point(-287, 104));
        rightStanding.add(new Point(-361, 129));
        rightStanding.add(new Point(-373, 129));
        rightStanding.add(new Point(-385, 124));
        rightStanding.add(new Point(-406, 112));
        rightStanding.add(new Point(-411, 102));
        rightStanding.add(new Point(-406, 89));
        rightStanding.add(new Point(-398, 74));
        rightStanding.add(new Point(-307, 45));
        rightStanding.add(new Point(-71, 131));
        rightStanding.add(new Point(-34, 60));
        rightStanding.add(new Point(-332, -173));
        rightStanding.add(new Point(-341, -191));
        rightStanding.add(new Point(-346, -203));
        rightStanding.add(new Point(-351, -215));
        rightStanding.add(new Point(-348, -228));
        rightStanding.add(new Point(-336, -237));
        rightStanding.add(new Point(-324, -237));
        rightStanding.add(new Point(-118, -81));
        rightStanding.add(new Point(23, 17));
        rightStanding.add(new Point(127, 28));
        rightStanding.add(new Point(153, 42));
        rightStanding.add(new Point(228, 99));
        rightStanding.add(new Point(317, 185));
        rightStanding.add(new Point(312, 59));
        rightStanding.add(new Point(156, 17));
        rightStanding.add(new Point(148, 8));
        rightStanding.add(new Point(141, -3));
        rightStanding.add(new Point(141, -20));
        rightStanding.add(new Point(153, -29));
        rightStanding.add(new Point(163, -30));
        rightStanding.add(new Point(354, 8));
        rightStanding.add(new Point(378, 240));
        rightStanding.add(new Point(331, 2));
        rightStanding.add(new Point(361, 37));
    }
    // ==============================================================
    // METHODS
    // ==============================================================

    /**
     * Stop running.
     */
    public void stopRunning() {
        if (!isRunning()) {
            setDeltaX(0);
        }
    }

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets running.
     *
     * @param running the running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Is jumping boolean.
     *
     * @return the boolean
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * Sets jumping.
     *
     * @param jumping the jumping
     */
    public void setJumping(boolean jumping) {

        this.jumping = jumping;
    }

    /**
     * Rotate left.
     */
    public void rotateLeft() {
        bTurningLeft = true;
    }

    /**
     * Rotate right.
     */
    public void rotateRight() {
        bTurningRight = true;
    }

    /**
     * Stop rotating.
     */
    public void stopRotating() {
        bTurningRight = false;
        bTurningLeft = false;
    }

    /**
     * Thrust on.
     */
    public void thrustOn() {
        bThrusting = true;
    }

    /**
     * Thrust off.
     */
    public void thrustOff() {
        bThrusting = false;
        bFlame = false;
    }

    private int adjustColor(int nCol, int nAdj) {
        if (nCol - nAdj <= 0) {
            return 0;
        } else {
            return nCol - nAdj;
        }
    }

    @Override
    public void draw(Graphics g) {
        Color colShip;
        if (getFadeValue() == 150) {
            colShip = Color.white;
        } else {
            colShip = new Color(adjustColor(getFadeValue(), 200),
                    adjustColor(getFadeValue(), 175), getFadeValue());
        }

        drawShipWithColor(g, colShip);

    }  //end draw()

    /**
     * Draw ship with color.
     *
     * @param g   the g
     * @param col the col
     */
    public void drawShipWithColor(Graphics g, Color col) {
        super.draw(g, col);
        g.setColor(col);
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);

    }

    /**
     * Gets current floor height.
     *
     * @return the current floor height
     */
    public int getCurrentFloorHeight() {
        return currentFloorHeight;
    }

    /**
     * Gets protected.
     *
     * @return the protected
     */
    public boolean getProtected() {
        return bProtected;
    }

    /**
     * Sets protected.
     *
     * @param bParam the b param
     */
    public void setProtected(boolean bParam) {
        if (bParam) {
            setFadeValue(0);
        }
        bProtected = bParam;
    }

    /**
     * Is collision boolean.
     *
     * @return the boolean
     */
    public boolean isCollision() {
        return collision;
    }

    /**
     * Sets collision.
     *
     * @param collision the collision
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    /**
     * Gets front of character.
     *
     * @return the front of character
     */
    public Point getFrontOfCharacter() {
        return frontOfCharacter;
    }

    /**
     * Sets front of character.
     *
     * @param frontOfCharacter the front of character
     */
    public void setFrontOfCharacter(Point frontOfCharacter) {
        this.frontOfCharacter = frontOfCharacter;
    }

    /**
     * Gets right character.
     *
     * @return the right character
     */
    public ArrayList<Point> getRightCharacter() {
        return rightCharacter;
    }

    /**
     * Gets left character.
     *
     * @return the left character
     */
    public PointsArrayList getLeftCharacter() {
        return leftCharacter;
    }

    /**
     * Sets current to right character.
     */
    public void setCurrentToRightCharacter() {

        if(this.currentPosition.equals(leftCharacter) || this.currentPosition.equals(leftStanding)) {
            this.currentPosition = rightCharacter;
        }
        else if(this.currentPosition.equals(rightCharacter))
        {
            this.currentPosition = rightStanding;
        }
        else
        {
            this.currentPosition = rightCharacter;
        }
    }


    /**
     * Sets current to left character.
     */
    public void setCurrentToLeftCharacter() {
        if(this.currentPosition.equals(rightCharacter)  || this.currentPosition.equals(rightStanding)) {
            this.currentPosition = leftCharacter;
        }
        else if(this.currentPosition.equals(leftCharacter))
        {
            this.currentPosition = leftStanding;
        }
        else
        {
            this.currentPosition = leftCharacter;
        }
    }

    /**
     * Switch direction.
     */
    public void switchDirection() {
        if (getDeltaX() > 0) {
            bTurningLeft = false;
            bTurningRight = true;
        } else if (getDeltaX() < 0) {
            bTurningLeft = true;
            bTurningRight = false;
        }
    }

    /**
     * Reorient character.
     */
    public void reorientCharacter() {
        if (bTurningLeft) {
            setOrientation(180);
            setCurrentToLeftCharacter();
        }
        if (bTurningRight) {
            setOrientation(0);
            setCurrentToRightCharacter();
        }
        assignPolarPoints(getCurrentPosition());
    }


    @Override
    public void move() {
        if (Game.getThisGame().getTick() % 2 == 0) {
           reorientCharacter();
        }
        if (bThrusting) {
            if (getDeltaX() < MAX_SPEED && getDeltaX() > -MAX_SPEED)
                setDeltaX(getDeltaX() * THRUST);
        }

        Point pnt = getCenter();
        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();
        double[] newXY = checkForCollisions(dX, dY);
        dX = newXY[0];
        dY = newXY[1];

        updateCenter(new Point((int) dX, (int) dY));

        if (getCenter().y >= Game.DIM.getHeight()) {
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
            if (this instanceof PlayerCharacter)
                CommandCenter.getInstance().spawnPerson(false);
        }

    } //end move

    /**
     * Handle collisions.
     *
     * @param deltaX the delta x
     */
    public void handleCollisions(double deltaX)
    {
     if(CommandCenter.getInstance().isbShift())
         setDeltaX(deltaX);

        double[] newXY = checkForCollisions(getCenter().x, getCenter().y);
        double dX = newXY[0];
        double dY = newXY[1];

        updateCenter(new Point((int) dX, (int) dY));

        if (getCenter().y >= Game.DIM.getHeight()) {
            CommandCenter.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
            if (this instanceof PlayerCharacter)
                CommandCenter.getInstance().spawnPerson(false);
        }
    }

    /**
     * Check for collisions double [ ].
     *
     * @param dX the d x
     * @param dY the d y
     * @return the double [ ]
     */
    public double[] checkForCollisions(double dX, double dY) {
        boolean verticalIntersectionFixed = false;
        boolean horizontalIntersectionFixed = false;
        double deltaY = getDeltaY();

        double[] newXY = new double[2];

        //updateCenter(destination);
        ArrayList<RectangleSprite> collisionRectangles =
                CommandCenter.checkFloorCollisionsWithRectangles
                        (new Rectangle((int) dX - this.getRadius(), (int) dY - this.getRadius(),
                                (int) this.getRectangleWidth(), (int) this.getRectangleWidth()));

        if (collisionRectangles.size() > 0) {
            for (RectangleSprite collisionRectangle : collisionRectangles) {
                Point thisCorner = this.gettLCorner();
                double thisWidth = this.getRectangleWidth();
                Point collisionCorner = collisionRectangle.gettLCorner();
                double collisionWidth = collisionRectangle.getRectangleWidth();

                if (this.isVerticalIntersection(collisionRectangle) && !verticalIntersectionFixed) {

                    if (deltaY > 0)
                    //it's falling
                    {
                        if ((thisCorner.x + thisWidth * (gapCoefficient - 1) / gapCoefficient >= collisionCorner.x &&
                                thisCorner.x <= collisionCorner.x) ||
                                (thisCorner.x + thisWidth * 1 / gapCoefficient <= collisionCorner.x + collisionWidth &&
                                        thisCorner.x >= collisionCorner.x)) {
                            setDeltaY(0);
                            jumping = false;
                            dY = collisionCorner.y - this.getRectangleHalfWidth();
                            verticalIntersectionFixed = true;

                        }

                    } else if (deltaY < 0) //It's jumping
                    {
                        //Jumping into bottom of box
                        if (thisCorner.x + thisWidth * (gapCoefficient - 1) / gapCoefficient >= collisionCorner.x &&
                                thisCorner.x - thisWidth * 1 / gapCoefficient <= collisionCorner.x + collisionWidth) {
                            dY = collisionCorner.y + collisionWidth + this.getRectangleHalfWidth();
                            jumping = false;
                            setDeltaY(GRAVITY);
                            verticalIntersectionFixed = true;
                        }

                    }


                }
            }

            Rectangle horRect = (new Rectangle((int) dX - this.getRadius(), (int) dY - this.getRadius(),
                    (int) this.getRectangleWidth(), (int) this.getRectangleWidth()));
            collisionRectangles =
                    CommandCenter.checkFloorCollisionsWithRectangles(horRect);
            if (collisionRectangles.size() > 0) {
                for (RectangleSprite collisionRectangle : collisionRectangles) {
                    if (this.isHorizontalIntersection(collisionRectangle)
                            && !horizontalIntersectionFixed) //it's just running.
                    {
                        dX = rectangularCollisionAdjustX(this.gettLCorner().x, collisionRectangle);
                        horizontalIntersectionFixed = true;
                    }
                }
            }
        } else {
            setDeltaY(GRAVITY);
        }

        dX = handleOffScreenPerson(dX);

        newXY[0] = dX;
        newXY[1] = dY;

        return newXY;
    }

    /**
     * Handle off screen person double.
     *
     * @param dX the d x
     * @return the double
     */
    public double handleOffScreenPerson(double dX) {
        if (dX > MAX_RIGHT) {
            dX = MAX_RIGHT - this.getRectangleHalfWidth();
            if (!(this instanceof PlayerCharacter))
                setDeltaX(-getDeltaX());
        } else if (dX < MAX_LEFT) {
            dX = MAX_LEFT + this.getRectangleHalfWidth();
            if (!(this instanceof PlayerCharacter))
                setDeltaX(-getDeltaX());
        }
        return dX;
    }

    private int rectangularCollisionAdjustX(int thisCornerX, RectangleSprite collisionRectangle) {
        int collisionTlCornerX = collisionRectangle.gettLCorner().x;
        int collisionTrCornerX = collisionRectangle.gettRCorner().x;
        int dX = thisCornerX;
        if (getDeltaX() > 0) {
            if (bTurningRight && (thisCornerX + this.getRectangleWidth() > collisionTlCornerX
                    && thisCornerX <= collisionTlCornerX)) {
                dX = (int) (collisionTlCornerX - this.getRectangleHalfWidth());
            }
        } else if (getDeltaX() < 0) {
            if (bTurningLeft && (thisCornerX < collisionTrCornerX)) {
                dX = (int) (collisionTrCornerX + this.getRectangleHalfWidth());
            }
        } else {
            if (bTurningRight)
            {
                if(collisionRectangle.getDeltaX() > 0)
                {
                    dX = (int) (collisionTlCornerX + this.getRectangleHalfWidth());
                }
                else {
                    dX = (int) (collisionTlCornerX - this.getRectangleHalfWidth());
                }
            } else if (bTurningLeft)
            {
                if(collisionRectangle.getDeltaX() < 0)
                {
                    dX = (int) (collisionTlCornerX - this.getRectangleHalfWidth());
                }
                else {
                    dX = (int) (collisionTrCornerX + this.getRectangleHalfWidth());
                }
            } else {
                System.out.println("not moving nor turning");
            }
        }
        setDeltaX(0);
        return dX;
    }


    /**
     * Sets gap coefficient.
     *
     * @param gapCoefficient the gap coefficient
     */
    public void setGapCoefficient(double gapCoefficient) {
        this.gapCoefficient = gapCoefficient;
    }

    /**
     * Gets left standing.
     *
     * @return the left standing
     */
    public ArrayList<Point> getLeftStanding() {
        return leftStanding;
    }

    /**
     * Sets left standing.
     *
     * @param leftStanding the left standing
     */
    public void setLeftStanding(PointsArrayList leftStanding) {
        this.leftStanding = leftStanding;
    }

    /**
     * Gets right standing.
     *
     * @return the right standing
     */
    public ArrayList<Point> getRightStanding() {
        return rightStanding;
    }

    /**
     * Sets right standing.
     *
     * @param rightStanding the right standing
     */
    public void setRightStanding(PointsArrayList rightStanding) {
        this.rightStanding = rightStanding;
    }

    /**
     * Gets current position.
     *
     * @return the current position
     */
    public ArrayList<Point> getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets current position.
     *
     * @param currentPosition the current position
     */
    public void setCurrentPosition(PointsArrayList currentPosition) {
        this.currentPosition = currentPosition;
    }


} //end class
