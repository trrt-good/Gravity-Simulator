public class GravityObject 
{
    public Vector position; //position of the object
    public double mass; //mass of the object

    public boolean hasAtmosphere; //no implementation

    public GravityObject(Vector posIn, double massIn, boolean atmosphereIn) //constructor
    {
        PhysicsManager.gravityObjects.add(this); //adds this object to the list of GravityObjects in PhysicsManager when an object is constructed 
        position = posIn;
        mass = massIn;
        hasAtmosphere = atmosphereIn; 
    }
}
