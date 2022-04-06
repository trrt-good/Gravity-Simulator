# Gravity-Simulator

About:
  - before making changes, running the code should display three objects that move around due to simulated gravity.
  - Vector2 class: stores all positions, directions and contains some basic 2d vector math methods. 
  - UI class: manages all graphics and user input
  - PhysicsObject class:  contains methods which calculate gravity and collisions, as well as containing methods for
    adding force to an object. 

Making changes:
  - If you wish to make changes, simply edit the code in the main method located in 
    GameManager.java. 
  - To add more physics objects, one can choose to call one or more of the following constructors:
       `PhysicsObject()
        PhysicsObject(Vector2 position, double mass)
        PhysicsObject(Vector2 position, double mass, double drag, boolean isKinematic, boolean freezeRotation, boolean effectedByGrav, boolean graviational, boolean collisions)
  - To add forces to the desired physics objects, use the addForce() method:
       `[physicsObject].addForce(new Vector2(x, y), 1);
  - To change speed of time, edit the "timescale" variable in GameManager.java
  - To change the FPS, modify the "FPS" final int in UI.java
