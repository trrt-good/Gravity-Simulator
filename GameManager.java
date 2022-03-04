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
    public static final int TIME_SCALE = 1;

    public static PhysicsObject mainObject;
    public static JFrame simulationFrame;
    public static JPanel uiPanel = new UI();
    public static UI inputManager;


    public static Timer physicsTickTimer = new Timer((int)(1000.0/PHYSICS_TICK_SPEED/(double)TIME_SCALE), new ActionListener()
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
        mainObject = new PhysicsObject(new Vector2(0, 0), 10);
        mainObject.addForce(new Vector2(10, 0), 1);
        //#endregion

        physicsTickTimer.start(); //starts the physics updates 
    }

    public static void createJFrame()
    {
        simulationFrame = new JFrame();
        simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulationFrame.add(uiPanel);
        simulationFrame.setTitle("Physics Simulation");
        simulationFrame.setSize(1920, 1080);
        simulationFrame.addKeyListener(inputManager);
        simulationFrame.addMouseListener(inputManager);
		simulationFrame.setLocationRelativeTo(null);
		simulationFrame.setVisible(true);
    }
    
    public static double getDeltaTime()
    {
        return 1/(double)PHYSICS_TICK_SPEED;
    }
}

