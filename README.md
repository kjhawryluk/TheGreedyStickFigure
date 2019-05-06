# ABOUT THE GREEDY STICK FIGURE

In this game, the player navigates the levels by running and jumping across platforms, while trying to avoid falling
to his her death, or getting hit by an asteroid, an enemy, or an enemy's bullet. To defend his/herself, the player can
shoot the foes (by pressing space) or detonate a local bomb (by pressing enter) that kills all nearby enemies and
asteroids.

The player and enemy characters are all extensions of an abstract class "Movable_Person". This class has the bulk of the
methods used for telling the characters how to move and handle collisions when moving as seen at Movable_Person: Line 651.
The Movable_Person class also stores the point arrays for the people, as shown at Movable_Person: Line 86. Also, as the
player moves, the arrays alternate to make it look like the person is walking (Movable_Person: Line 606). As
the computer must control how the enemies walk, WalkingEnemy: Line 44 shows how their move function has them pace back
and forth by switching the delta x.

Specific to the PlayerCharacter, PlayerCharacter: Line 50 shows how when the player gets to a certain part of the screen, he/she triggers
the setting to shift and more enemies to spawn. For more details on how the the setting is shifted, you can see the
Shift method at CommandCenter: Line 367.

Throughout the level, coins fall randomly, and when a player collides with them or shoots them, the player gets points.
The player also gets points by killing enemies. Coins, Asteroids, and WalkingEnemies all implement the Scorable
interface, which enables the program to keep track of score. Also "NewShipFloater" objects fall from the sky, and the
player must touch these to get an extra life.

The levels and people objects all are extensions of the RectangleSprite class. This class extended the sprite class
but then stored a rectangle, with it's vertices that could be used for handling collision detection, as circular sprites
were not practical for the platform level design. In CommandCenter: Line 315, you can see how rectangle collision detection
was implemented.

There are four levels (Level: Line 19), each with different traits. Ranging from being very hilly, to very flat
with lots of enemies, to one level that is exclusively comprised of moving platforms. At the end of each level are
three Final Blocks, which are used as the goal for clearing the level. Once these are destroyed, the player moves on
to the next level.

