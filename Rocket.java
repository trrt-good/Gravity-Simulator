import javax.swing.ImageIcon;

public class Rocket extends PhysicsObject
{
    public double thrust;
    public ImageIcon rocketImage;

    public Rocket(double massIn, Vector startPos, double thrustIn)
    {
        super();
        super.mass = massIn;
        super.position = new Vector(startPos);
        thrust = thrustIn;
    }
}
