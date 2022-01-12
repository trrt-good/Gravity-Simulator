public class GravityObject 
{
    public Vector position; //position of the object
    public double mass; //mass of the object (in metric tons)

    public boolean hasAtmosphere; //no implementation

    public GravityObject(Vector posIn, double massIn) //constructor
    {
        GameManager.gravityObjects.add(this);
        position = posIn;
        mass = massIn;
    }
}
