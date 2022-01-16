import javax.swing.ImageIcon;

public class Rocket extends PhysicsObject
{
    public static final Engine ENGINE_RAPTOR = new Engine(100, 10, 100);

    public ImageIcon rocketImage;

    public Rocket()
    {

    }

    public Rocket(double massIn, Vector startPos, double thrustIn)
    {
        super();
        super.mass = massIn;
        super.position = new Vector(startPos);
    }

    public static class Engine
    {
        public double thrust; //measured in kilonewtons 
        public double specificImpulse; 
        public double engineMass; 

        public Engine(double thrustIn, double specificImpulseIn, double engineMassIn)
        {
            thrust = thrustIn;
            specificImpulse = specificImpulseIn;
            engineMass = engineMassIn;
        }
    }
}
