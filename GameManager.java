import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
public class GameManager extends JFrame  //will manage the physics/gravity objects and preform physics updates 
{
    public static final int FPS = 500;

    public static List<GravityObject> gravityObjects = new ArrayList<GravityObject>(); //list containing all gravity objects, new gravity objects are automatically added to the list.
    public static List<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();//list containing all physics objects, new physics objects are automatically added to the list. 

    public static final double FIXED_TIME_STEP = 0.02; // used by time-based calculations
    public static double fixedDeltaTime = 0.02; //time between each physics update

    private Rendering renderer;

    public GameManager() 
    {
        renderer = new Rendering(FPS, 1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(renderer);
		this.pack();
        this.setTitle("Game");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
    }

    public static void main (String [] args)
    {
        new GameManager();
        PhysicsObject pObject1 = new PhysicsObject(new Vector(0, 300), 10, 0.05, 0, false, false, true, new Vector(0,0));
        PhysicsObject pObject2 = new PhysicsObject(new Vector(0, -300), 10, 0.05, 0, false, false, true, new Vector(0,0));
        GravityObject gObject = new GravityObject(new Vector(0, 0), 10000.0, 200);
        gObject.mass = gObject.estimateMass();
        pObject1.addForce(new Vector(1000, 0), 1);
        pObject2.addForce(new Vector(-1000, 0), 1);
        start();
    }


    public static void start()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() 
        { 
            public void run()
            {
                int i = 0;
                for (i = 0; i < physicsObjects.size(); i++)
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
        timer.scheduleAtFixedRate(timerTask, (long)1, (long)(fixedDeltaTime*1000));
    }

}