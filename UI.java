import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
public class UI extends JPanel implements KeyListener, MouseListener, ActionListener, MouseMotionListener
{
	private final int FPS = 500;

	private Color backgroundColor = new Color(255, 255, 255);
	private int scale = 1;
	private Point offsetPoint = new Point(0, 0);

	private Timer uiTimer = new Timer(1000/FPS + 1, new ActionListener()
	{
		@Override
        public void actionPerformed(ActionEvent e) 
        {
            repaint();
        }
	});

	public UI()
	{
		setBackground(backgroundColor);
		uiTimer.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
        for (int i =0; i < GameManager.physicsObjects.size(); i++)
        {
            drawPhysicsObject(g, GameManager.physicsObjects.get(i));
        }
	}

	public void drawPhysicsObject(Graphics g, PhysicsObject physicsObject)
	{
		g.setColor(physicsObject.color);
        Point screenCoords = toScreenCoords(physicsObject.position);
		g.fillOval(screenCoords.x, screenCoords.y, (int)physicsObject.diameter, (int)physicsObject.diameter);
	}

	public Point toScreenCoords(Vector2 worldCoords)
	{
        Point screenPoint = new Point((int)(worldCoords.x*scale + getWidth()*2 - offsetPoint.x), (int)(getHeight()*2 - worldCoords.y*scale - offsetPoint.y));
        System.out.println("World: " + worldCoords.toString() + "\nScreen: " + screenPoint.toString());
		return screenPoint;
    }

    public Point toScreenCoords(Vector2 worldCoords, int diameter)
	{
		return new Point((int)(worldCoords.x*scale + getWidth()*2 - offsetPoint.x) - diameter/2, (int)(getHeight()*2 - worldCoords.y*scale - offsetPoint.y) - diameter/2);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
