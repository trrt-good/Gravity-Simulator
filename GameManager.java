import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class GameManager //will manage the physics/gravity objects and preform physics updates 
{
    public static List<GravityObject> gravityObjects = new ArrayList<GravityObject>(); //list containing all gravity objects, new gravity objects are automatically added to the list.
    public static List<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();//list containing all physics objects, new physics objects are automatically added to the list. 

    public static final double FIXED_TIME_STEP = 0.02; //used for physics updates 0.02 sec or 20 ms 
    public static double timeScale = 1.0; //time scale 
    public static double fixedDeltaTime = 0.02; //used by methods which are time-based because it changes with time scale

    public static void main (String [] args)
    {
        PhysicsObject pObject = new PhysicsObject(new Vector(0, 1000), 10, 0.05, 0, false, false, true, new Vector(0,0));
        GravityObject gObject = new GravityObject(new Vector(0, 0), 100000.0);
        pObject.addForce(new Vector(100, 0), 1);
        
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
                    System.out.print(physicsObjects.get(i).velocity.toString());
                    System.out.println(physicsObjects.get(i).position.toString());
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, (long)1, (long)(fixedDeltaTime*1000));
    }

    public static void setTimeScale(double timeScaleIn) //method for changing the time scale: 0 = time practically stops, 1 = normal speed, 0.5 = 2 times slower
    {
        timeScale = Math.abs(timeScaleIn);
        if (timeScale != 0)
            fixedDeltaTime = FIXED_TIME_STEP/timeScale;
        else 
        {
            timeScale = Double.MIN_VALUE;
            fixedDeltaTime = FIXED_TIME_STEP/timeScale;
        }
    }

    
}