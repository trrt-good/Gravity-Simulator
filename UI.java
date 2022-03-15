import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeSupport;
import java.awt.Color;
public class UI extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private final int FPS = 500;

	private Color backgroundColor = new Color(0, 0, 0);
	private double scale = 1;
	private Point offsetPoint = new Point(0, 0);

    private Point clickPoint;
    private Point clickOffsetPoint;
    
    private Color debugLineColor = Color.RED;
    private Color debugStringColor = new Color(100, 100, 255);

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
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
        for (int i =0; i < GameManager.physicsObjects.size(); i++)
        {
            drawPhysicsObject(g, GameManager.physicsObjects.get(i), true, false);
        }
	}

	public void drawPhysicsObject(Graphics g, PhysicsObject physicsObject, boolean drawVelocity, boolean drawVelocityString)
	{
		g.setColor(physicsObject.color);
        Point screenCoords = toScreenCoords(physicsObject.position, (int)physicsObject.diameter);
		g.fillOval(screenCoords.x, screenCoords.y, (int)(physicsObject.diameter*scale), (int)(physicsObject.diameter*scale));

        if (drawVelocity)
        {
            g.setColor(debugLineColor);
            g.drawLine(toScreenCoords(physicsObject.position).x, toScreenCoords(physicsObject.position).y, toScreenCoords(Vector2.add(physicsObject.position, physicsObject.velocity)).x, toScreenCoords(Vector2.add(physicsObject.position, physicsObject.velocity)).y);
        }

        if (drawVelocityString)
        {
            g.setColor(debugStringColor);
            g.drawString(physicsObject.velocity.toString(), toScreenCoords(physicsObject.position).x, toScreenCoords(physicsObject.position).y);
        }
	}

	public Point toScreenCoords(Vector2 worldCoords)
	{
        Point screenPoint = new Point((int)(worldCoords.x*scale + getWidth()/2 - offsetPoint.x), (int)(getHeight()/2 - worldCoords.y*scale - offsetPoint.y));
        //System.out.println("World: " + worldCoords.toString() + "\nScreen: " + screenPoint.toString());
		return screenPoint;
    }

    public Point toScreenCoords(Vector2 worldCoords, int diameter)
    {
		Point screenPoint = new Point((int)(worldCoords.x*scale + getWidth()/2 - offsetPoint.x) - (int)(diameter/2*scale), (int)(getHeight()/2 - worldCoords.y*scale - offsetPoint.y) - (int)(diameter/2*scale));
        //System.out.println("World: " + worldCoords.toString() + "\nScreen: " + screenPoint.toString());
        return screenPoint;
    }

    public double getScale()
    {
        return scale;
    }

    public Point getOffsePoint()
    {
        return offsetPoint;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        requestFocusInWindow();
        clickPoint = e.getPoint();
        clickOffsetPoint = offsetPoint;
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
        GameManager.timeScale += 1;
        System.out.println(GameManager.timeScale);
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
    public void mouseDragged(MouseEvent e) 
    {
        offsetPoint = new Point(clickPoint.x - e.getPoint().x + clickOffsetPoint.x, clickPoint.y - e.getPoint().y + clickOffsetPoint.y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scale -= 0.1*(e.getUnitsToScroll()/Math.abs(e.getUnitsToScroll()));
        
        System.out.println(0.1*(e.getUnitsToScroll()/Math.abs(e.getUnitsToScroll())));
    }
}
