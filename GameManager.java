import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameManager  //will manage the physics/gravity objects and preform physics updates 
{
    public static List<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();//list containing all physics objects, new physics objects are automatically added to the list. 

    public static final int PHYSICS_TICK_SPEED = 200;
    public static double timeScale = 10;

    public static PhysicsObject mainObject;
    public static JFrame simulationFrame;
    public static UI mainUI = new UI();
    public static JPanel uiPanel = mainUI;

    public static Timer physicsTickTimer = new Timer((int)(1000.0/PHYSICS_TICK_SPEED), new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            for (int i = 0; i < physicsObjects.size(); i++)
            {
                physicsObjects.get(i).applyDrag();
                physicsObjects.get(i).applyGravity();
                physicsObjects.get(i).updatePosition();
            }
        }
    });
    
    public static void main (String [] args)
    {
        createJFrame();
        //#region DEBUG
        mainObject = new PhysicsObject(new Vector2(0, 200), 100000);
        mainObject.addForce(new Vector2(5000000, 0), 1);
        mainObject.diameter = 20;
        PhysicsObject otherObject = new PhysicsObject(new Vector2(0, -50), 500000);
        otherObject.diameter = 100;
        PhysicsObject otherotherObj = new PhysicsObject(new Vector2(-300, -200), 200000);
        otherotherObj.diameter = 50;
        otherotherObj.addForce(new Vector2(-1000000, 7000000), 1);

        //#endregion

        physicsTickTimer.start(); //starts the physics updates 
    }

    public static void createJFrame()
    {
        simulationFrame = new JFrame();
        simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simulationFrame.setSize(1920, 1080);
		simulationFrame.getContentPane().add(uiPanel);
        simulationFrame.setTitle("Physics Simulation");
		simulationFrame.setVisible(true);
    }
    
    public static double getDeltaTime()
    {
        return 1/(double)PHYSICS_TICK_SPEED*timeScale;
    }
}

