import javax.naming.spi.DirectoryManager;

public class GravityObject 
{
    public Vector position; //position of the object
    public double mass; //mass of the object (in metric tons)

    public boolean hasAtmosphere; //no implementation

    public int diameter; 

    public GravityObject(Vector posIn, double massIn, int diameterIn) //constructor
    {
        GameManager.gravityObjects.add(this);
        position = posIn;
        mass = massIn;
        diameter = diameterIn;
    }

    public double estimateMass() //gives an approximate mass based off diameter using the density of earth
    {
        return Math.PI*Math.pow(diameter/2, 3);
    }
}
