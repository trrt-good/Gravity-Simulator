import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
public class UI extends JPanel implements KeyListener, MouseListener, ActionListener, MouseMotionListener
{
	private final int FPS = 500;

	private Color backgroundColor = new Color(0, 0, 0);
	private int scale = 1;
	private Point offsetPoint;

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

	}

	public void drawPhysicsObject(Graphics g, PhysicsObject physicsObject)
	{
		g.setColor(physicsObject.color);
		
	}

	public Point toScreenCoords(Vector2 worldCoords)
	{
		return new Point((int)(worldCoords.x*scale + getWidth()*2 - offsetPoint.x), (int)(getHeight()*2 - worldCoords.y*scale - offsetPoint.y));
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
