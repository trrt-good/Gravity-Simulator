import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Rendering extends JPanel implements ActionListener{

	final int PANEL_WIDTH = 1000; //width of the pannel 
	final int PANEL_HEIGHT = 1000; //height of the pannel 

	public Vector defaultOffset; //offsets the position of objects that are rendered. (0,0) is centered

	public PhysicsObject targetObject; //the renderer will focus on this object 

	public double targetScale; //the scale when the renderer is targeting an object
    public double defaultScale; //scales the objects displayed bigger or smaller 1 is real size

	private double a_scale = defaultScale; //the actual offset that effects the renderer 
	private Vector a_offset = defaultOffset; //the actual scale that effects the renderer 
	private Timer timer; //timer class for timing fps 

	public Rendering(int fpsIn, double scaleIn, Vector offsetIn, PhysicsObject focusObjectIn, double targetScaleIn) //constructor which sets up the timer 
    {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setBackground(Color.white);
        defaultScale = scaleIn;
		a_scale = defaultScale;
		targetScale = targetScaleIn;
		timer = new Timer(1000/fpsIn + 1, this);
		defaultOffset = new Vector(offsetIn);
		a_offset = defaultOffset;
		targetObject = focusObjectIn;
		timer.start(); //starts the timer to repaint the panel every n milliseconds
	}

	public void paint(Graphics g) //paint method method is automatically called when this class is instantiated 
    {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //antialiasing (not sure if it works)
		g2D.setRenderingHints(rh);

        for (int i = 0; i < GameManager.physicsObjects.size(); i++) //draws all the physics objects
        {
            g2D.drawOval(
				(int)(GameManager.physicsObjects.get(i).position.x*a_scale + PANEL_WIDTH/2 + a_offset.x*a_scale), //x position of oval
				(int)(GameManager.physicsObjects.get(i).position.y*a_scale + PANEL_HEIGHT/2 + a_offset.y*a_scale), //y position of oval
				(int)(6*a_scale), 	//width
				(int)(6*a_scale) 	);	//height
        }

        for (int i = 0; i < GameManager.gravityObjects.size(); i++) //draws all the gravity objects
        {
            g2D.drawOval(
				(int)(GameManager.gravityObjects.get(i).position.x*a_scale + PANEL_WIDTH/2 - GameManager.gravityObjects.get(i).diameter*a_scale/2 + a_offset.x*a_scale), //x position of oval
				(int)(GameManager.gravityObjects.get(i).position.y*a_scale + PANEL_HEIGHT/2 - GameManager.gravityObjects.get(i).diameter*a_scale/2 + a_offset.y*a_scale),  //y position of oval
				(int)(GameManager.gravityObjects.get(i).diameter*a_scale), //width of oval
				(int)(GameManager.gravityObjects.get(i).diameter*a_scale) 	); //height of oval 
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) //this action is reapeated repeatidly based off fps value 
    {
		if (targetObject != null)
		{
			a_offset = targetObject.position;
			a_scale = targetScale;
		}
		else
		{
			a_offset = defaultOffset;
			a_scale = defaultScale;
		}
		repaint(); //repaints the pannel 
	}
}
