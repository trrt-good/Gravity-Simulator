import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager  //will manage the physics/gravity objects and preform physics updates 
{
    public static final int FPS = 500; // FPS for the game

    public static List<GravityObject> gravityObjects = new ArrayList<GravityObject>(); //list containing all gravity objects, new gravity objects are automatically added to the list.
    public static List<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();//list containing all physics objects, new physics objects are automatically added to the list. 

    public static final double FIXED_TIME_STEP = 0.005; // used by time based calculations 
    public static double fixedDeltaTime = 0.005; //time between each physics update is called

    public static PhysicsObject mainObj;
    public static Rendering renderer;
    private static GameFrame gf;

    /* 
    DO NOT CHANGE FIXED_TIME_STEP ON IT'S OWN

    keep FIXED_TIME_STEP and fixedDeltaTime equal for real-time. 
    They can be increased for less physics accuracy but reduced load on the CPU, 
    or decreased for higher physics accuracy but increase CPU load
    
    changing fixedDeltaTime alone will increase/decrease the speed of time
    */
    

    public static void main (String [] args)
    {
        gf = new GameFrame(); //constructs a game manager which creates the JFrame and starts the rendering process

        //#region DEBUG
        mainObj = new PhysicsObject(new Vector(0, 400), 10, 0.05, 0.01, false, false, true, new Vector(0,0));
        GravityObject gObject = new GravityObject(new Vector(0, 0), 10000.0, 300, false);
        
        gObject.mass = gObject.estimateMass();
        mainObj.addForce(new Vector(100, 0), 1);
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
                    dragMap();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, (long)1, (long)(fixedDeltaTime*1000)); //runs the method above at a constant rate 
    }

    public static void toggleMap()
    {
        if (renderer.targetObject == null)
            renderer.targetObject = mainObj;
        else if (renderer.targetObject == mainObj)
            renderer.targetObject = null;
    }

    public static void zoom(int inOut) 
    {
        switch (inOut)
        {
            case 1: if (renderer.targetObject == null && renderer.scaleMap < 3) {renderer.scaleMap += renderer.scaleMap/10;} else if (renderer.scaleWhileTargeting < 50) {renderer.scaleWhileTargeting += renderer.scaleMap;}
                break;
            case -1: if (renderer.targetObject == null && renderer.scaleMap > 0.05) {renderer.scaleMap -= renderer.scaleMap/10;} else if (renderer.scaleWhileTargeting > 3) {renderer.scaleWhileTargeting -= renderer.scaleMap;} 
                break;
        }
    }

    public static void dragMap()
    {
        if (renderer.targetObject == null)
        {
            if (gf.mouseDown)
            { //changes the render offset based off mouse position
                renderer.fixedOffset = Vector.add(new Vector(-(gf.getMousePos().x/renderer.scaleMap - gf.getClickPos().x/renderer.scaleMap), -(gf.getMousePos().y/renderer.scaleMap - gf.getClickPos().y/renderer.scaleMap)), gf.getClickOffset());
            }
        }
    }
}