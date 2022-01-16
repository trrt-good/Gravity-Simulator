public class GravityObject 
{
    public Vector position; //position of the object
    public double mass; //mass of the object (in metric tons)

    public boolean hasAtmosphere; //false if this object has no atmosphere

    public int diameter; 
    public boolean collisions;

    public GravityObject(Vector posIn, double massIn, int diameterIn, boolean hasAtmosphereIn, boolean collisionsIn) //constructor
    {
        GameManager.gravityObjects.add(this);
        position = posIn;
        mass = massIn;
        diameter = diameterIn;
        hasAtmosphere = hasAtmosphereIn;
        collisions = collisionsIn;
    }

    public double estimateMass() //gives an approximate mass based off diameter using the density of earth
    {
        return Math.PI*Math.pow(diameter/2, 2);
    }
}
