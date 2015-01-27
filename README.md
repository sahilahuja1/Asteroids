# Asteroids

The game starts in the main method of the Game class. The Game class displays the menu screen and the instructions.

From there, the JFrame Asteroids is created, which is the ViewController for the game. It is an ActionListener, KeyListener and MouseListener and as such deals with all of the user interaction with the game. It is responsible for painting everything to the screen and updating the game as well. It owns the GameController, which takes care of the logic of the game.

The GameController object stores all of the data for the game, including score, lives, and many other objects. It has the logic for performing all of the actions and controlling the interactions among objects on screen. One “cool feature” is that GameController reads the level data from a file

Every object visible on screen is a subclass of the abstract class “MoveableObject”, which has logic to store position, angle, speed, rotation/movement data, and physical shape of the object. This is important for an object to be able to understand itself, no matter what type of object. It has methods like updatePosition, handling a collision, facing the object towards a point, etc.

The Spacecraft is a subclass of MoveableObject that also stores its bulletList, exhaustList, and missileList. 

Rock is another MoveableObject. It has a subclass called BigRock with all the same functionality except it must be hit multiple times before being destroyed. This is a feature of later levels in the game.

Bullet is also a MoveableObject that has its own features. The subclass of Bullet, “Missile”, has the added feature of updating itself to automatically aim for a rock.

Exhaust is released from the back of the Spacecraft every time it accelerates, and Debris is released momentarily after every collision, adding animation, another “cool feature”.

Power-ups are randomly left after some rock destructions, and have different functionality, including adding lives, score, missiles, and bullets.
