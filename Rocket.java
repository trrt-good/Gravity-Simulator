import javax.swing.ImageIcon;

public class Rocket extends PhysicsObject
{
    public static final Engine ENGINE_1 = new Engine(100, 0.7, 100);
    public static final Engine ENGINE_2 = new Engine(100, 10, 100);

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
        public double thrust;
        public double efficiency; //measured as a decimal between 0 and 1, not using specific impulse 
        public double engineMass; 

        public Engine(double thrustIn, double efficiencyIn, double engineMassIn)
        {
            thrust = thrustIn;
            efficiency = efficiencyIn;
            engineMass = engineMassIn;
        }
    }

    public static class FuelTank
    {
        public double fuelMass;
        public double energyDensity;
    }
}
