# Gravity-Simulator

About:
  - A simple program which can display and simulate the gravity and interactions between multiple objects.
  
![image](https://cdn.discordapp.com/attachments/998467921720385629/1008622378835787797/unknown.png?width=882&height=594)

Classes:
  - Vector2: stores all positions, directions and contains some basic 2d vector math methods. 
  - UI: manages all graphics and user input
  - PhysicsObject:  contains methods which calculate gravity and collisions, as well as containing methods for
    adding force to an object. 

Making changes:
  - If you wish to make changes, simply edit the code in the main method located in 
    GameManager.java. 
  - To add more physics objects, one can choose to call one or more of the following constructors:
       `PhysicsObject()
        PhysicsObject(Vector2 position, double mass)
        PhysicsObject(Vector2 position, double mass, boolean isKinematic, boolean freezeRot, boolean effectedByGrav, boolean graviational, boolean collisions)
  - To add forces to the desired physics objects, use the addForce() method:
       `[physicsObject].addForce(new Vector2(x, y), 1);
  - To change speed of time, edit the "timescale" variable in GameManager.java
  - To change the FPS, modify the "FPS" final int in UI.java
