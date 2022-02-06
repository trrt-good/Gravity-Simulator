import javax.swing.*;
import java.awt.event.*;
import java.awt.MouseInfo;
public class GameFrame extends JFrame implements KeyListener, MouseListener
{
    public Rendering renderer;
    public boolean mouseDown; //true while mouse button is down
    private Vector2 clickPos; //the position of the mouse on the window when clicked
    private Vector2 clickOffset; //the render offset when clicked

    public GameFrame() //constructor which sets up JFrame
    {
        renderer = new Rendering(0.5, new Vector2(0, 0), null, 4);
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


    public Vector2 getMousePos()
    {
        return new Vector2(MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().x -7, MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().y -30);
    }

    public Vector2 getClickPos()
    {
        return clickPos;
    }

    public Vector2 getClickOffset()
    {
        return clickOffset;
    }

//==================================== KEY INPUT =========================================

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

//#region ===================== MOUSE INPUT ============================ 

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        mouseDown = true;
        clickPos = getMousePos();
        clickOffset = new Vector2(renderer.fixedOffset);
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        
    }

    //#endregion 

}
