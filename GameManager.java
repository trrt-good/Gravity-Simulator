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
    public static double timeScale = 200;

    public static PhysicsObject object1;
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
                physicsObjects.get(i).applyGravity();
                physicsObjects.get(i).updatePosition();
            }
        }
    });
    
    public static void main (String [] args)
    {
        createJFrame();

        //Create physics objects to simulate: 
        object1 = new PhysicsObject(new Vector2(0, 200), 400);
        object1.addForce(new Vector2(-500, 0), 1);
        object1.diameter = 8;
        PhysicsObject object2 = new PhysicsObject(new Vector2(0, -50), 2000);
        object2.diameter = 40;
        object2.addForce(new Vector2(6000, 400), 1);
        PhysicsObject object3 = new PhysicsObject(new Vector2(-300, -200), 1000);
        object3.diameter = 20;
        object3.addForce(new Vector2(1000, -100), 1);

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

