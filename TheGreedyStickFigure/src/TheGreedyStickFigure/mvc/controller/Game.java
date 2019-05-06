package TheGreedyStickFigure.mvc.controller;

import TheGreedyStickFigure.mvc.model.*;
import TheGreedyStickFigure.mvc.view.GamePanel;
import TheGreedyStickFigure.sounds.Sound;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

// ===============================================
// == This Game class is the CONTROLLER
// ===============================================

/**
 * The type Game.
 */
public class Game implements Runnable, KeyListener {

    // ===============================================
    // FIELDS
    // ===============================================

    /**
     * The constant DIM.
     */
    public static final Dimension DIM = new Dimension(1100, 700); //the dimension of the game.
    private GamePanel gmpPanel;
    /**
     * The constant R.
     */
    public static Random R = new Random();
    /**
     * The constant ANI_DELAY.
     */
    public final static int ANI_DELAY = 45; // milliseconds between screen
    // updates (animation)
    private Thread thrAnim;
    private int nLevel = 1;
    private int nTick = 0;

    private boolean bMuted = true;
    /**
     * The constant thisGame.
     */
    public static Game thisGame;

    private final int PAUSE = 80, // p key
            QUIT = 81, // q key
            LEFT = 37, // rotate left; left arrow
            RIGHT = 39, // rotate right; right arrow
            UP = 38, // thrust; up arrow
            START = 83, // s key
            FIRE = 32, // space key
            MUTE = 77, // m-key mute
            LEVEL1 = 49,
            LEVEL2 = 50,
            LEVEL3 = 51,
            LEVEL4 = 52,
            NUM_ENTER = 10; //Bumb

    private Clip clpThrust;
    private Clip clpMusicBackground;

    private static final int SPAWN_COIN = 35;
    private static final int SPAWN_FLOATER = 250;


    // ===============================================
    // ==CONSTRUCTOR
    // ===============================================

    /**
     * Instantiates a new Game.
     */
    public Game() {

        gmpPanel = new GamePanel(DIM);
        gmpPanel.addKeyListener(this);
        clpThrust = Sound.clipForLoopFactory("whitenoise.wav");
        clpMusicBackground = Sound.clipForLoopFactory("music-background.wav");
        thisGame = this;

    }

    // ===============================================
    // ==METHODS
    // ===============================================

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {
                try {
                    Game game = new Game(); // construct itself
                    game.fireUpAnimThread();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fireUpAnimThread() { // called initially
        if (thrAnim == null) {
            thrAnim = new Thread(this); // pass the thread a runnable object (this)
            thrAnim.start();
        }
    }

    // implements runnable - must have run method
    public void run() {

        // lower this thread's priority; let the "main" aka 'Event Dispatch'
        // thread do what it needs to do first
        thrAnim.setPriority(Thread.MIN_PRIORITY);

        // and get the current time
        long lStartTime = System.currentTimeMillis();

        // this thread animates the scene
        while (Thread.currentThread() == thrAnim) {
            tick();
            spawnCoin();
            gmpPanel.update(gmpPanel.getGraphics()); // update takes the graphics context we must 
            // surround the sleep() in a try/catch block
            // this simply controls delay time between 
            // the frames of the animation

            //this might be a good place to check for collisions
            checkCollisions();
            //this might be a god place to check if the level is clear (no more foes)
            //if the level is clear then spawn some big asteroids -- the number of asteroids 
            //should increase with the level. 
            checkNewLevel();

            try {
                // The total amount of time is guaranteed to be at least ANI_DELAY long.  If processing (update) 
                // between frames takes longer than ANI_DELAY, then the difference between lStartTime - 
                // System.currentTimeMillis() will be negative, then zero will be the sleep time
                lStartTime += ANI_DELAY;
                Thread.sleep(Math.max(0,
                        lStartTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                // just skip this frame -- no big deal
                continue;
            }
        } // end while
    } // end run

    private void checkCollisions() {

        Point pntFriendCenter, pntFoeCenter, pntBlockCenter;
        int nFriendRadiux, nFoeRadiux;
        double nBlockRadiux;

        for (Movable movFriend : CommandCenter.getInstance().getMovFriends()) {
            for (Movable movFoe : CommandCenter.getInstance().getMovFoes()) {
                pntFriendCenter = movFriend.getCenter();
                pntFoeCenter = movFoe.getCenter();
                nFriendRadiux = movFriend.getRadius();
                nFoeRadiux = movFoe.getRadius();

                //detect collision
                if (pntFriendCenter.distance(pntFoeCenter) < (nFriendRadiux + nFoeRadiux)) {

                    //Player
                    if ((movFriend instanceof Movable_Person && !(movFoe instanceof Final_Blocks ||
                            movFoe instanceof Coin))) {
                        if (!CommandCenter.getInstance().getPerson().getProtected()) {
                            CommandCenter.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                            CommandCenter.getInstance().spawnPerson(false);
                        }
                    }
                    else if(movFriend instanceof Movable_Person && (movFoe instanceof Final_Blocks ||
                            movFoe instanceof Coin))
                    {
                        CommandCenter.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);
                    }
                    //not the Player
                    else {
                        CommandCenter.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                    }//end else

                    //kill the foe and if asteroid, then spawn new asteroids
                    killFoe(movFoe);
                    if(movFoe instanceof Coin)
                    {
                        Sound.playSound("pacman_eatghost.wav");
                    }
                    else
                    {
                        Sound.playSound("kapow.wav");
                    }


                    if (movFoe instanceof Final_Blocks && movFriend instanceof Bullet)
                        CommandCenter.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);


                }//end if


            }//end inner for

            if (!(movFriend instanceof PlayerCharacter)) {
                for (Movable movBlock : CommandCenter.getInstance().getMovBlocks()) {
                    pntFriendCenter = movFriend.getCenter();
                    pntBlockCenter = movBlock.getCenter();
                    nFoeRadiux = movFriend.getRadius();
                    nBlockRadiux = BuildingBlocks.BLOCK_HALF;
                    if (pntBlockCenter.distance(pntFriendCenter) < (nBlockRadiux + nFoeRadiux)) {
                        CommandCenter.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                        CommandCenter.getInstance().getOpsList().enqueue(new Debris(10, movFriend.getCenter()),
                                CollisionOp.Operation.ADD);

                    }
                }
            }

        }//end outer for


        //check for collisions between Player and floaters
        if (CommandCenter.getInstance().getPerson() != null) {
            Point pntFalCenter = CommandCenter.getInstance().getPerson().getCenter();
            int nFalRadiux = CommandCenter.getInstance().getPerson().getRadius();
            Point pntFloaterCenter;
            int nFloaterRadiux;

            for (Movable movFloater : CommandCenter.getInstance().getMovFloaters()) {
                pntFloaterCenter = movFloater.getCenter();
                nFloaterRadiux = movFloater.getRadius();

                //detect collision
                if (pntFalCenter.distance(pntFloaterCenter) < (nFalRadiux + nFloaterRadiux)) {
                    CommandCenter.getInstance().setNumPlayerLives(CommandCenter.getInstance().getNumPlayerLives() + 1);
                    CommandCenter.getInstance().getOpsList().enqueue(movFloater, CollisionOp.Operation.REMOVE);
                    Sound.playSound("pacman_eatghost.wav");
                }//end if 
            }//end inner for
        }//end if not null


        //Check for collisions between blocks and enemies, the play, and floats.
        for (RectangleSprite block : CommandCenter.getInstance().getMovBlocks()) {
            for (Movable enemy : CommandCenter.getInstance().getMovFoes()) {
                if (enemy instanceof Movable_Person && !(enemy instanceof Coin)) {
                    if (block.getRectangle().intersects(((Movable_Person) enemy).getRectangle())) {
                        ((Movable_Person) enemy).handleCollisions(block.getDeltaX());
                    }
                }
            }
            if (block.getRectangle().intersects(CommandCenter.getInstance().getPerson().getRectangle())) {
                CommandCenter.getInstance().getPerson().handleCollisions(block.getDeltaX());
            }

            for (Movable movFloater : CommandCenter.getInstance().getMovFloaters()) {
                Point pntFloaterCenter = movFloater.getCenter();
                int nFloaterRadiux = movFloater.getRadius();

                //detect collision
                if (block.getCenter().distance(pntFloaterCenter) < (block.getRadius() + nFloaterRadiux)) {
                    if(movFloater instanceof NewShipFloater)
                    {
                        ((NewShipFloater) movFloater).setDeltaY(0);
                        ((NewShipFloater) movFloater).setDeltaX(block.getDeltaX());

                    }
                }//end if
            }//end inner for

        }

        for (Movable potentialBullet : CommandCenter.getInstance().getMovFoes()) {
            for (Movable movFoe : CommandCenter.getInstance().getMovFoes()) {
                if (potentialBullet instanceof Shooting_Enemy_Bullet && movFoe instanceof Shooting_Enemy &&
                        movFoe != ((Shooting_Enemy_Bullet) (potentialBullet)).getEnemy()) {
                    Point pntBulletCenter = potentialBullet.getCenter();
                    pntFoeCenter = movFoe.getCenter();
                    int nBulletRadiux = potentialBullet.getRadius();
                    nFoeRadiux = movFoe.getRadius();

                    //detect collision
                    if (pntBulletCenter.distance(pntFoeCenter) < (nBulletRadiux + nFoeRadiux)) {
                        CommandCenter.getInstance().getOpsList().enqueue(potentialBullet, CollisionOp.Operation.REMOVE);
                    }
                }
            }
        }
        for (Movable movFoe : CommandCenter.getInstance().getMovFoes()) {
            for (Movable movBlock : CommandCenter.getInstance().getMovBlocks()) {
                pntFoeCenter = movFoe.getCenter();
                pntBlockCenter = movBlock.getCenter();
                nFoeRadiux = movFoe.getRadius();
                nBlockRadiux = BuildingBlocks.BLOCK_HALF;
                //detect collision
                if (movFoe instanceof Asteroid || movFoe instanceof Shooting_Enemy_Bullet) {
                    if (pntBlockCenter.distance(pntFoeCenter) < (nBlockRadiux + nFoeRadiux)) {
                        CommandCenter.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);
                        //end else
                        //kill the foe and if asteroid, then spawn new asteroids
                        killFoe(movFoe);
                    }
                }
            }//end inner for
        }//end outer for


        //we are dequeuing the opsList and performing operations in serial to avoid mutating the movable arraylists while iterating them above
        while (!CommandCenter.getInstance().getOpsList().isEmpty()) {
            CollisionOp cop = CommandCenter.getInstance().getOpsList().dequeue();
            Movable mov = cop.getMovable();
            CollisionOp.Operation operation = cop.getOperation();

            switch (mov.getTeam()) {
                case FOE:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovFoes().add(mov);
                    } else {
                        if (mov instanceof Final_Blocks)
                            CommandCenter.getInstance().getMovBlocks().remove(mov);
                        CommandCenter.getInstance().getMovFoes().remove(mov);
                        CommandCenter.getInstance().getLevelObject().getFinal_Blocks().remove(mov);
                    }

                    break;
                case FRIEND:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovFriends().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovFriends().remove(mov);
                    }
                    break;

                case FLOATER:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovFloaters().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovFloaters().remove(mov);
                    }
                    break;

                case DEBRIS:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovDebris().add(mov);
                    } else {
                        CommandCenter.getInstance().getMovDebris().remove(mov);
                    }
                    break;
                case BLOCK:
                    if (operation == CollisionOp.Operation.ADD) {
                        CommandCenter.getInstance().getMovBlocks().add((RectangleSprite) mov);
                    } else {
                        CommandCenter.getInstance().getMovBlocks().remove(mov);
                        CommandCenter.getInstance().getLevelObject().getFinal_Blocks().remove(mov);
                    }
                    break;

            }

            if (mov instanceof Movable_Person) {
                if (operation == CollisionOp.Operation.ADD) {
                    CommandCenter.getInstance().getMovPeople().add((Movable_Person) mov);
                } else {
                    CommandCenter.getInstance().getMovPeople().remove(mov);
                }
                break;
            }

        }
        //a request to the JVM is made every frame to garbage collect, however, the JVM will choose when and how to do this
        System.gc();

    }//end meth

    private void killFoe(Movable movFoe) {
        if (movFoe instanceof Scorable)
            CommandCenter.getInstance().setScore(CommandCenter.getInstance().getScore() + ((Scorable) movFoe).getScore());

        if (movFoe instanceof Asteroid) {
            //we know this is an Asteroid, so we can cast without threat of ClassCastException
            Asteroid astExploded = (Asteroid) movFoe;
            //big asteroid 
            if (astExploded.getSize() == 0) {
                //spawn two medium Asteroids
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);
                //CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);

            }
            //medium size aseroid exploded
            else if (astExploded.getSize() == 1) {
                //spawn three small Asteroids
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);
                CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);
                //CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(astExploded), CollisionOp.Operation.ADD);

            }
        }

        //remove the original Foe
        CommandCenter.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);

    }

    /**
     * Tick.
     */
//some methods for timing events in the game,
    //such as the appearance of UFOs, floaters (power-ups), etc. 
    public void tick() {
        if (nTick == Integer.MAX_VALUE)
            nTick = 0;
        else
            nTick++;
    }

    /**
     * Gets tick.
     *
     * @return the tick
     */
    public int getTick() {
        return nTick;
    }

    private void spawnCoin() {
        //make the appearance of coins dependent upon ticks and levels
        //the higher the level the more frequent the appearance
        if (nTick % (SPAWN_COIN / nLevel ) == 0) {
            CommandCenter.getInstance().getOpsList().enqueue(new Coin(), CollisionOp.Operation.ADD);
        }
        if (nTick % (SPAWN_FLOATER / nLevel ) == 0) {
            CommandCenter.getInstance().getOpsList().enqueue(new NewShipFloater(), CollisionOp.Operation.ADD);
        }
    }

    // Called when user presses 's' or 1,2,3 or 4 to choose the level.
    private void startGame(int levelNum) {
        CommandCenter.getInstance().clearAll();
        CommandCenter.getInstance().setLevel(levelNum);
        CommandCenter.getInstance().initGame();
        CommandCenter.getInstance().setPlaying(true);
        CommandCenter.getInstance().setPaused(false);
        if (!bMuted)
            clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Spawn asteroids.
     *
     * @param nNum the n num
     */
//this method spawns new asteroids
    public void spawnAsteroids(int nNum) {
        for (int nC = 0; nC < nNum; nC++) {
            //Asteroids with size of zero are big
            CommandCenter.getInstance().getOpsList().enqueue(new Asteroid(1), CollisionOp.Operation.ADD);
        }
    }


    private boolean isLevelClear() {
        //if there are no more Asteroids on the screen
        boolean bBlocksClear = false;
        if (CommandCenter.getInstance().getLevelObject() != null) {
            if (CommandCenter.getInstance().getLevelObject().getFinal_Blocks().size() == 0)
                bBlocksClear = true;
        }
        return bBlocksClear;


    }
    private void checkNewLevel() {
        if (isLevelClear()) {
            launchNewLevel(CommandCenter.getInstance().getLevel() + 1);
        }
    }

    private void launchNewLevel (int levelNumber) {
            CommandCenter.getInstance().clearAll();
            CommandCenter.getInstance().setLevel(levelNumber);
            CommandCenter.getInstance().initGame();
    }


    /**
     * Gets this game.
     *
     * @return the this game
     */
    public static Game getThisGame() {
        return thisGame;
    }

    // Varargs for stopping looping-music-clips
    private static void stopLoopingSounds(Clip... clpClips) {
        for (Clip clp : clpClips) {
            clp.stop();
        }
    }


    // ===============================================
    // KEYLISTENER METHODS
    // ===============================================

    @Override
    public void keyPressed(KeyEvent e) {
        Movable_Person player = CommandCenter.getInstance().getPerson();
        int nKey = e.getKeyCode();
        // System.out.println(nKey);

        if (!CommandCenter.getInstance().isPlaying()) {
                chooseLevel(nKey);
            }

        if (player != null) {

            switch (nKey) {
                case PAUSE:
                    CommandCenter.getInstance().setPaused(!CommandCenter.getInstance().isPaused());
                    if (CommandCenter.getInstance().isPaused())
                        stopLoopingSounds(clpMusicBackground, clpThrust);
                    else
                        clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
                    break;
                case QUIT:
                    System.exit(0);
                    break;
                case UP:
                    player.setCollision(false);
                    if (!player.isJumping()) {
                        System.out.println("Jump again");
                        player.setDeltaY(-130);
                        player.setJumping(true);
                    }

                    break;
                case LEFT:
                    player.thrustOn();
                    if (player.getDeltaX() > -10) {
                        player.setDeltaX(-10);
                    }
                    player.switchDirection();
                    player.setRunning(true);
                    break;
                case RIGHT:
                    player.thrustOn();
                    if (player.getDeltaX() < 10) {
                        player.setDeltaX(10);
                    }
                    player.switchDirection();
                    player.setRunning(true);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Movable_Person player = CommandCenter.getInstance().getPerson();
        int nKey = e.getKeyCode();
        System.out.println(nKey);
        if (player != null) {
            switch (nKey) {
                case FIRE:
                    CommandCenter.getInstance().getOpsList().enqueue(new Bullet(player), CollisionOp.Operation.ADD);
                    Sound.playSound("laser.wav");
                    break;

                //Explode a bomb
                case NUM_ENTER:
                    CommandCenter.getInstance().getOpsList().enqueue(new Bomb(player.getCenter()), CollisionOp.Operation.ADD);
                    Sound.playSound("shipspawn.wav");
                    break;

                case LEFT:
//				player.stopRotating();
                    player.setRunning(false);
                    break;
                case RIGHT:
//				player.stopRotating();
                    player.setRunning(false);
                    break;
                case UP:
                    player.thrustOff();
                    clpThrust.stop();
                    break;

                case MUTE:
                    if (!bMuted) {
                        stopLoopingSounds(clpMusicBackground);
                        bMuted = !bMuted;
                    } else {
                        clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
                        bMuted = !bMuted;
                    }
                    break;


                default:
                    break;
            }
        }
    }

    private void chooseLevel(int nKey) {
        int level = 0;

        switch (nKey)
        {
            case START:
            case LEVEL1:
                level = 1;
                break;
            case LEVEL2:
                level = 2;
                break;
             case LEVEL3:
                level = 3;
                break;
            case LEVEL4:
                level = 4;
                break;
        }
        if(level > 0)
            startGame(level);
    }

    @Override
    // Just need it b/c of KeyListener implementation
    public void keyTyped(KeyEvent e) {
    }


}


