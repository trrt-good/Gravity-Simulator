import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
public class GameManager extends JFrame  //will manage the physics/gravity objects and preform physics updates 
{
    public static final int FPS = 500; // FPS for the game

    public static List<GravityObject> gravityObjects = new ArrayList<GravityObject>(); //list containing all gravity objects, new gravity objects are automatically added to the list.
    public static List<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();//list containing all physics objects, new physics objects are automatically added to the list. 

    private static GameManager gm;

    public static final double FIXED_TIME_STEP = 0.02; // used by time based calculations 
    public static double fixedDeltaTime = 0.02; //time between each physics update is called

    /* 
    DO NOT CHANGE FIXED_TIME_STEP ON IT'S OWN

    keep FIXED_TIME_STEP and fixedDeltaTime equal for real-time. 
    They can be increased for less physics accuracy but reduced load on the CPU, 
    or decreased for higher physics accuracy but increase CPU load
    
    changing fixedDeltaTime alone will increase/decrease the speed of time
    */

    private Rendering renderer; //the rendering class 

    public GameManager() //constructor which sets up JFrame
    {
        renderer = new Rendering(FPS, 1, new Vector(0, 0), null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(renderer);
		this.pack();
        this.setTitle("Game");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
    }

    public static void main (String [] args)
    {
        gm = new GameManager(); //constructs a game manager which creates the JFrame and starts the rendering process

        //#region DEBUG
        PhysicsObject pObject1 = new PhysicsObject(new Vector(0, 200), 10, 0.05, 0.01, false, false, true, new Vector(0,0));
        PhysicsObject pObject2 = new PhysicsObject(new Vector(0, -200), 10, 0.05, 0.01, false, false, true, new Vector(0,0));
        GravityObject gObject = new GravityObject(new Vector(0, 0), 10000.0, 300, true);
        gm.renderer.targetObject = pObject2;
        gm.renderer.scale = 4;
        gObject.mass = gObject.estimateMass();
        pObject1.addForce(new Vector(200, 200), 1);
        pObject2.addForce(new Vector(-200, -200), 1);
        //#endregion

        

        startPhysicsUpdates(); //starts the physics updates 
    }


    public static void startPhysicsUpdates()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() 
        { 
            public void run() 
            {
                int i = 0;
                for (i = 0; i < physicsObjects.size(); i++) //runs physics update methods for each physicsObject
                {
                    physicsObjects.get(i).applyDrag();
                    physicsObjects.get(i).applyAngularDrag();
                    physicsObjects.get(i).applyGravity();
                    physicsObjects.get(i).updatePosition();
                    physicsObjects.get(i).updateRotation();
                    physicsObjects.get(i).checkCollisions();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, (long)1, (long)(fixedDeltaTime*1000)); //runs the method above at a constant rate 
    }

}