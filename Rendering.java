import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Rendering extends JPanel implements ActionListener{

	final int PANEL_WIDTH = 1000;
	final int PANEL_HEIGHT = 1000;

    public double scale; //scales the objects displayed bigger or smaller 1 is real size

	private Timer timer;

	public Rendering(int fpsIn, double scaleIn) //instantiate after other classes
    {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.setBackground(Color.white);
        scale = scaleIn;
		timer = new Timer(1000/fpsIn + 1, this);
		timer.start();
	}

	public void paint(Graphics g) 
    {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
        for (int i = 0; i < GameManager.physicsObjects.size(); i++)
        {
            g2D.drawOval((int)(GameManager.physicsObjects.get(i).position.x*scale + PANEL_WIDTH/2), (int)(GameManager.physicsObjects.get(i).position.y*scale + PANEL_HEIGHT/2), (int)(6*scale), (int)(6*scale));
        }

        for (int i = 0; i < GameManager.gravityObjects.size(); i++)
        {
            g2D.drawOval((int)(GameManager.gravityObjects.get(i).position.x*scale + PANEL_WIDTH/2 - GameManager.gravityObjects.get(i).diameter*scale/2), (int)(GameManager.gravityObjects.get(i).position.y*scale + PANEL_HEIGHT/2 - GameManager.gravityObjects.get(i).diameter*scale/2), (int)(GameManager.gravityObjects.get(i).diameter*scale), (int)(GameManager.gravityObjects.get(i).diameter*scale));
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
    {
		repaint();
	}
}
