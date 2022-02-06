public class Rocket extends PhysicsObject
{
    public Engine engine;
    public Body body;

    public Rocket(Vector2 position)
    {
        super();
        
        GameManager.rockets.add(this); 
        engine = Engine.ENGINE_LARGE;
        body = new Body(100, Body.ENERGY_DENSITY_HIGH);
        super.mass = body.fuelMass*1.1 + engine.engineMass;
        super.position = position;
    }

    public Rocket(Vector2 position, Body bodyIn, Engine engineIn)
    {
        super();
        body = bodyIn;
        engine = engineIn;
        super.mass = body.fuelMass*1.1 + engine.engineMass;
        super.position = position;
    }

    public static class Engine
    {
        public static final Engine ENGINE_LARGE = new Engine(100, 0.65, 100);
        public static final Engine ENGINE_MEDIUM = new Engine(60, 0.7, 60);
        public static final Engine ENGINE_SMALL = new Engine(30, 0.75, 30);

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

    public static class Body
    {
        public double fuelMass;
        private double energyDensity;
        public double energy;

        public static final int ENERGY_DENSITY_LOW = 55;
        public static final int ENERGY_DENSITY_MEDIUM = 78;
        public static final int ENERGY_DENSITY_HIGH = 120;

        public Body(double fuelMassIn, int energyDensityIn)
        {
            fuelMass = fuelMassIn;
            energyDensity = energyDensityIn;
            energy = energyDensity*fuelMass;
        }
    }
}
