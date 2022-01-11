import java.util.ArrayList;

import java.util.List;
public class PhysicsManager //will manage the physics/gravity objects and preform physics updates 
{
    public static List<GravityObject> gravityObjects; //list containing all gravity objects, new gravity objects are automatically added to the list.
    public static List<PhysicsObject> physicsObjects; //list containing all physics objects, new physics objects are automatically added to the list. 

    public static final double FIXED_TIME_STEP = 0.02; //used for physics updates (not implemented yet) physics update every 0.02 sec or 20 ms 
    public static double timeScale = 1.0; //time scale 
    public static double fixedDeltaTime = 0.02; //used by methods which are time-based because it changes with time scale
    
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