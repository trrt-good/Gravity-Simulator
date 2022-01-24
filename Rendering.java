import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.*;
import java.awt.Color;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;
public class Rendering extends JPanel implements ActionListener{

	final int PANEL_WIDTH = 1000; //width of the pannel 
	final int PANEL_HEIGHT = 1000; //height of the pannel 

	public int FPS = 200;

	public Vector fixedOffset; //offsets the position of objects that are rendered. (0,0) is centered

	public PhysicsObject targetObject; //the renderer will focus on this object 

	public double scaleWhileTargeting; //the scale when the renderer is targeting an object
    public double scaleMap; //the scale when in map mode

	private double a_scale = scaleMap; //the actual offset that effects the renderer 
	private Vector a_offset = fixedOffset; //the actual scale that effects the renderer 
	private Timer timer; //timer class for timing fps 

	public Rendering(double scaleIn, Vector offsetIn, PhysicsObject focusObjectIn, double targetScaleIn) //constructor which sets up the timer 
    {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setBackground(Color.white);
        scaleMap = scaleIn;
		a_scale = scaleMap;
		scaleWhileTargeting = targetScaleIn;
		timer = new Timer(1000/FPS + 1, this);
		fixedOffset = new Vector(offsetIn);
		a_offset = fixedOffset;
		targetObject = focusObjectIn;
		timer.start(); //starts the timer to repaint the panel every n milliseconds
	}

	public void paint(Graphics g) //paint method method is automatically called when this class is instantiated 
    {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;

		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //antialiasing (not sure if it works)
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);

        drawPhysicsObjects(g2D);
		drawGravityObjects(g2D);
    
		drawStats(g2D);
	}

	public void drawPhysicsObjects(Graphics2D g2D)
	{
		if (targetObject== null)
		{
			for (int i = 0; i < GameManager.rockets.size(); i++) //draws all the physics objects
			{
				g2D.setColor(new ColorUIResource(40, 40, 40));
				try 
				{
					g2D.drawImage(ImageIO.read(new File("Images", "Arrow.png")), 
					(int)(GameManager.rockets.get(i).position.x*a_scale + PANEL_WIDTH/2 - a_offset.x*a_scale -9), 
					(int)(GameManager.rockets.get(i).position.y*a_scale + PANEL_HEIGHT/2 - a_offset.y*a_scale -12), 18, 24, null);
				} 
				catch (IOException e) 
				{
					System.out.println("exception");
				}
				// g2D.fillOval(
				// 	(int)(GameManager.rockets.get(i).position.x*a_scale + PANEL_WIDTH/2 - a_offset.x*a_scale -3), //x position of oval
				// 	(int)(GameManager.rockets.get(i).position.y*a_scale + PANEL_HEIGHT/2 - a_offset.y*a_scale -6), //y position of oval
				// 	(int)(6), 	//width
				// 	(int)(12) 	);	//height
			}
		}
		else
		{
			for (int i = 0; i < GameManager.rockets.size(); i++) //draws all the physics objects
			{
				g2D.setColor(new ColorUIResource(40, 40, 40));
				try 
				{
					g2D.drawImage(ImageIO.read(new File("Images", "Rocket.png")), 
					(int)(GameManager.rockets.get(i).position.x*a_scale + PANEL_WIDTH/2 - a_offset.x*a_scale -3*a_scale), 
					(int)(GameManager.rockets.get(i).position.y*a_scale + PANEL_HEIGHT/2 - a_offset.y*a_scale -6*a_scale), (int)(6*a_scale), (int)(12*a_scale), null);
				} 
				catch (IOException e) 
				{
					System.out.println("error with file \"Rocket.png\"");
				}
			}
		}
	}

	public void drawGravityObjects(Graphics2D g2D)
	{
		for (int i = 0; i < GameManager.gravityObjects.size(); i++) //draws all the gravity objects
        {
			g2D.setColor(new ColorUIResource(64, 156, 255));
            g2D.fillOval(
				(int)(GameManager.gravityObjects.get(i).position.x*a_scale + PANEL_WIDTH/2 - GameManager.gravityObjects.get(i).diameter*a_scale/2 - a_offset.x*a_scale), //x position of oval
				(int)(GameManager.gravityObjects.get(i).position.y*a_scale + PANEL_HEIGHT/2 - GameManager.gravityObjects.get(i).diameter*a_scale/2 - a_offset.y*a_scale),  //y position of oval
				(int)(GameManager.gravityObjects.get(i).diameter*a_scale), //width of oval
				(int)(GameManager.gravityObjects.get(i).diameter*a_scale) 	); //height of oval 
        }
	}

	public void drawStats(Graphics2D g2D)
	{
		g2D.setColor(new ColorUIResource(0, 0, 0));

		g2D.drawString(String.format("zoom: %.2fx", a_scale), 20, 30);
		g2D.drawString("time scale: " + GameManager.FIXED_TIME_STEP/GameManager.fixedDeltaTime + "x", 20, 50);

		g2D.drawString(String.format("speed: %.2f", GameManager.mainObject.velocity.getMagnitude()) , 20, 90);
		g2D.drawString("position: " + GameManager.mainObject.velocity.toString(), 20, 110);
		
		g2D.drawString(String.format("rotational velocity: %.2f", GameManager.mainObject.angularVelocity), 20, 150);
		g2D.drawString(String.format("rotation: %.2f", GameManager.mainObject.rotation), 20, 170);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) //this action is reapeated repeatidly based off fps value 
    {
		if (targetObject != null)
		{
			a_offset = targetObject.position;
			a_scale = scaleWhileTargeting;
		}
		else
		{
			a_offset = fixedOffset;
			a_scale = scaleMap; 
		}
		repaint(); //repaints the pannel 
	}
}
