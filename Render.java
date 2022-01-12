import java.awt.*;
import javax.swing.*;
public class Render extends JFrame 
{
    Vector pos1 = new Vector(0, 0);
    Vector pos2 = new Vector(0, 0);

    Render()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000); // 
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void paintLine(Graphics2D g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine((int)pos1.x + 500, (int)pos1.y + 500, (int)pos2.x + 500, (int)pos2.y + 500);
    }
}
