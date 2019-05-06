package TheGreedyStickFigure.mvc.model;

import TheGreedyStickFigure.mvc.controller.Game;
import TheGreedyStickFigure.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Command center.
 */
public class CommandCenter {

	private  int nNumPlayerLives;
	private  int nLevel = 1;
	private  long lScore;
	private Movable_Person person;
	private Floor floor;
	private Floor sky;
	private  boolean bPlaying;
	private  boolean bPaused;
	private boolean bMove;
	private int tickCount;
	private boolean bShift = false;
	// These ArrayLists with capacities set
	private List<Movable> movDebris = new ArrayList<>(300);
	private List<Movable> movFriends = new ArrayList<>(100);
	private List<RectangleSprite> movBlocks = new ArrayList<>(300);
	private List<Movable> movFoes = new ArrayList<>(200);
	private List<Movable> movFloaters = new ArrayList<>(50);
	private List<Movable_Person> movPeople = new ArrayList<>(100);
	private Floating_Tiles floating_tiles;
	private Level levelObj;

	private GameOpsList opsList = new GameOpsList();


	private static CommandCenter instance = null;

	// Constructor made private - static Utility class only
	private CommandCenter() {}


	/**
	 * Get instance command center.
	 *
	 * @return the command center
	 */
	public static CommandCenter getInstance(){
		if (instance == null){
			instance = new CommandCenter();
		}
		return instance;
	}


	/**
	 * Init game.
	 */
	public  void initGame(){
		createLevel();
		setScore(0);
		setNumPlayerLives(3);
		spawnPerson(true);
	}

	/**
	 * Create level.
	 */
	public void createLevel()
	{
		levelObj = new Level(nLevel);
		floor = levelObj.getFloor();
		createFloor(floor);
		movBlocks.addAll(levelObj.getLevelTiles());
		movBlocks.addAll(levelObj.getFinal_Blocks());
		movFoes.addAll(levelObj.getFinal_Blocks());
	}

	/**
	 * Create floor.
	 *
	 * @param floor the floor
	 */
	public void	createFloor(Floor floor)
	{
		for (Movable floorBlock : floor.getFloorBlocks()) {
			opsList.enqueue(floorBlock, CollisionOp.Operation.ADD);
		}
	}

	/**
	 * Spawn person.
	 *
	 * @param bFirst the b first
	 */
// The parameter is true if this is for the beginning of the game, otherwise false
	// When you spawn a new person, you need to decrement its number
	public  void spawnPerson(boolean bFirst) {
		if (getNumPlayerLives() != 0) {
			person = new PlayerCharacter();

 			opsList.enqueue(person, CollisionOp.Operation.ADD);
			if (!bFirst)
			    setNumPlayerLives(getNumPlayerLives() - 1);
		}

		Sound.playSound("shipspawn.wav");

	}

	/**
	 * Gets ops list.
	 *
	 * @return the ops list
	 */
	public GameOpsList getOpsList() {
		return opsList;
	}

	/**
	 * Sets ops list.
	 *
	 * @param opsList the ops list
	 */
	public void setOpsList(GameOpsList opsList) {
		this.opsList = opsList;
	}

	/**
	 * Clear all.
	 */
	public  void clearAll(){
		movDebris.clear();
		movFriends.clear();
		movFoes.clear();
		movFloaters.clear();
		movBlocks.clear();
	}

	/**
	 * Is playing boolean.
	 *
	 * @return the boolean
	 */
	public  boolean isPlaying() {
		return bPlaying;
	}

	/**
	 * Sets playing.
	 *
	 * @param bPlaying the b playing
	 */
	public  void setPlaying(boolean bPlaying) {
		this.bPlaying = bPlaying;
	}

	/**
	 * Is paused boolean.
	 *
	 * @return the boolean
	 */
	public  boolean isPaused() {
		return bPaused;
	}

	/**
	 * Sets paused.
	 *
	 * @param bPaused the b paused
	 */
	public  void setPaused(boolean bPaused) {
		this.bPaused = bPaused;
	}

	/**
	 * Is game over boolean.
	 *
	 * @return the boolean
	 */
	public  boolean isGameOver() {		//if the number of falcons is zero, then game over
		if (getNumPlayerLives() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Gets level.
	 *
	 * @return the level
	 */
	public  int getLevel() {
		return nLevel;
	}

	/**
	 * Gets score.
	 *
	 * @return the score
	 */
	public   long getScore() {
		return lScore;
	}

	/**
	 * Sets score.
	 *
	 * @param lParam the l param
	 */
	public  void setScore(long lParam) {
		lScore = lParam;
	}

	/**
	 * Sets level.
	 *
	 * @param n the n
	 */
	public  void setLevel(int n) {
		nLevel = n;
	}

	/**
	 * Gets num player lives.
	 *
	 * @return the num player lives
	 */
	public  int getNumPlayerLives() {
		return nNumPlayerLives;
	}

	/**
	 * Sets num player lives.
	 *
	 * @param nParam the n param
	 */
	public  void setNumPlayerLives(int nParam) {
		nNumPlayerLives = nParam;
	}

	/**
	 * Get person movable person.
	 *
	 * @return the movable person
	 */
	public Movable_Person getPerson(){
		return person;
	}

	/**
	 * Set person.
	 *
	 * @param movablePerson the movable person
	 */
	public  void setPerson(Movable_Person movablePerson){
		person = movablePerson;
	}

	/**
	 * Gets mov debris.
	 *
	 * @return the mov debris
	 */
	public  List<Movable> getMovDebris() {
		return movDebris;
	}

	/**
	 * Gets mov friends.
	 *
	 * @return the mov friends
	 */
	public  List<Movable> getMovFriends() {
		return movFriends;
	}

	/**
	 * Gets mov blocks.
	 *
	 * @return the mov blocks
	 */
	public List<RectangleSprite> getMovBlocks() {
		return movBlocks;
	}

	/**
	 * Gets mov foes.
	 *
	 * @return the mov foes
	 */
	public  List<Movable> getMovFoes() {
		return movFoes;
	}


	/**
	 * Gets mov floaters.
	 *
	 * @return the mov floaters
	 */
	public  List<Movable> getMovFloaters() {
		return movFloaters;
	}

	/**
	 * Check floor collisions with rectangles array list.
	 *
	 * @param playerRectangle the player rectangle
	 * @return the array list
	 */
	public static ArrayList<RectangleSprite> checkFloorCollisionsWithRectangles(Rectangle playerRectangle)
	{
		ArrayList<RectangleSprite> listOfCollisionRectangles = new ArrayList<>();
		Rectangle blockRectangle;
		for (RectangleSprite movBlock : CommandCenter.getInstance().getMovBlocks()) {
			blockRectangle = movBlock.getRectangle();
			if(playerRectangle.intersects(blockRectangle)) {
				listOfCollisionRectangles.add(movBlock);
			}
		}
		return listOfCollisionRectangles;
	}

	/**
	 * Convert rectangle list array list.
	 *
	 * @param listOfRectangles the list of rectangles
	 * @return the array list
	 */
	public static ArrayList<Movable> convertRectangleList(List<RectangleSprite> listOfRectangles)
	{
		ArrayList<Movable> movableArrayList = new ArrayList<>();
		for(int spriteCounter = 0; spriteCounter < listOfRectangles.size(); spriteCounter++)
		{
			movableArrayList.add(listOfRectangles.get(spriteCounter));
		}
		return movableArrayList;
	}

	/**
	 * Stop shifting surroundings.
	 */
	public void stopShiftingSurroundings() {
		if(bMove) {
			bMove = false;
			for (RectangleSprite movBlock : movBlocks) {
				movBlock.stopShift();
			}

			for (Movable movFoe : movFoes) {
				if(!(movFoe instanceof Final_Blocks))
					movFoe.stopShift();
			}
			for (Movable movFloater : movFloaters) {
				movFloater.stopShift();
			}
		}
	}

	/**
	 * Shift surroundings.
	 */
	public void shiftSurroundings() {
		if (!bMove) {
			tickCount = Game.getThisGame().getTick();
			bMove = true;
			for (RectangleSprite movBlock : movBlocks) {
				movBlock.shift();
			}

			for (Movable movFoe : movFoes) {
				if(!(movFoe instanceof Final_Blocks))
					movFoe.shift();
			}
			for (Movable movFloater : movFloaters) {
				movFloater.shift();
			}


		} else {
			if (Game.getThisGame().getTick() > tickCount + 10) {
				stopShiftingSurroundings();
			}
		}
	}

	/**
	 * Gets level object.
	 *
	 * @return the level object
	 */
	public Level getLevelObject()
{
	return levelObj;
}


	/**
	 * Isb shift boolean.
	 *
	 * @return the boolean
	 */
	public boolean isbShift() {
		return bShift;
	}

	/**
	 * Sets shift.
	 *
	 * @param bShift the b shift
	 */
	public void setbShift(boolean bShift) {
		this.bShift = bShift;
	}

	/**
	 * Gets mov people.
	 *
	 * @return the mov people
	 */
	public List<Movable_Person> getMovPeople() {
		return movPeople;
	}

	/**
	 * Sets mov people.
	 *
	 * @param movPeople the mov people
	 */
	public void setMovPeople(List<Movable_Person> movPeople) {
		this.movPeople = movPeople;
	}
}
