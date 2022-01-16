import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.MouseInfo;
public class GameFrame extends JFrame implements KeyListener, MouseListener
{
    public Rendering renderer;
    public boolean mouseDown; //true while mouse button is down
    private Vector clickPos; //the position of the mouse on the window when clicked
    private Vector clickOffset; //the render offset when clicked

    public GameFrame() //constructor which sets up JFrame
    {
        renderer = new Rendering(0.5, new Vector(0, 0), null, 4);
        GameManager.renderer = renderer;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(renderer);
		this.pack();
        this.setTitle("Game");
        this.addKeyListener(this);
        this.addMouseListener(this);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

    }

    public Vector getMousePos()
    {
        return new Vector(MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().x -7, MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().y -30);
    }

    public Vector getClickPos()
    {
        return clickPos;
    }

    public Vector getClickOffset()
    {
        return clickOffset;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyChar())
        {
            case KeyBinds.TOGGLE_MAP: GameManager.toggleMap();
                break;
            case KeyBinds.ZOOM_IN: GameManager.zoom(1);
                break;
            case KeyBinds.ZOOM_OUT: GameManager.zoom(-1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }

    //mouse methods

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        mouseDown = true;
        clickPos = getMousePos();
        clickOffset = new Vector(renderer.fixedOffset);
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        mouseDown = false;
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

}
