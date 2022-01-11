public class PhysicsObject
{
    private double airDensity = 1.225; //temporary variable, will be moved to a different class later. 

    public Vector position; //global position of the PhysicsObject as a vector 
    //local position is always 0,0
    public double rotation = 0.0; //rotation of the PhysicsObject in degrees

    public double mass = 1; //Mass of the PhysicsObject
    public Vector centerOfMass = new Vector(0.0, 0.0); //The center of mass of the PhysicsObject in local space

    public double drag = 0; //Coefficient of drag
    public double angularDrag = 0.05; //Coefficient of angular drag

    public double angularVelocity = 0; //angular velocity in degrees per second 
    public Vector velocity = new Vector(0, 0); //Linear velocity of the PhysicsObject in units/sec

    public boolean isKinematic = false; //should this PhysicsObject be taken out of physics control?
    public boolean freezeRotation = false; //freezes rotation if true 
    public boolean effectedByGravity = true; //should this PhysicsObject be effected by Gravity?

    public PhysicsObject(Vector posIn, double massIn, double angDragIn, boolean isKnemtcIn, boolean frzRotationIn, boolean effbyGravIn, Vector centOfMassIn)
    {
        PhysicsManager.physicsObjects.add(this); //adds this object to the list of PhysicsObjects in PhysicsManager when an object is constructed 
        position = new Vector(posIn);
        mass = massIn;
        angularDrag = angDragIn;
        isKinematic = isKnemtcIn;
        freezeRotation = frzRotationIn;
        effectedByGravity = effbyGravIn;
        centerOfMass = new Vector(centOfMassIn);
    }

    public Vector getWorldCenterOfMass() //The center of mass of the PhysicsObject in global space
    {
        return new Vector(position.x + centerOfMass.x, position.y + centerOfMass.y);
    }

    public void addForce(Vector forceIn, int forceType) //forceType 0 is force over time, forceType 1 is instant force applied
    {
        if (forceType == 0)
        {
            velocity.x += (forceIn.x*PhysicsManager.FIXED_TIME_STEP)/mass;
            velocity.y += (forceIn.y*PhysicsManager.FIXED_TIME_STEP)/mass;
        }
        else if (forceType == 1)
        {
            velocity.x += (forceIn.x)/mass;
            velocity.y += (forceIn.y)/mass;
        }
    }

    public void addTorque(double torqueIn, int torqueType) //forceType 0 is force over time, forceType 1 is instant force applied
    {
        if (freezeRotation == true) return; 
        if (torqueType == 0)
            angularVelocity = angularVelocity + (torqueIn * PhysicsManager.FIXED_TIME_STEP)/mass;
        else if (torqueType == 1)
            angularVelocity = angularVelocity + (torqueIn)/mass;
    }

    public void setRotation(double angleIn) //sets rotation to specified value
    {
        rotation = angleIn;
    }

    //private methods:

    private void applyDrag() //applies drag by decreasing velocity. Called each physics update 
    {
        if (velocity.getMagnitude() != 0)
        {
            double multiplier = (velocity.getMagnitude()-0.5*airDensity*velocity.getSqrMagnitude()*drag*PhysicsManager.FIXED_TIME_STEP)/velocity.getMagnitude();
            velocity.x *= multiplier;
            velocity.y *= multiplier;
        }
    }

    private void applyGravity() //applies gravity by adding a force twards each gravity object 
    {
        if (!effectedByGravity) return;
        int i = 0;
        for (i = 0; i < PhysicsManager.gravityObjects.size(); i++)
        {
            addForce(Vector.scaledDifference(position, PhysicsManager.gravityObjects.get(i).position, (PhysicsManager.gravityObjects.get(i).mass*mass)/Vector.distance(position, PhysicsManager.gravityObjects.get(i).position)), 0);
        }
    }

    private void updatePosition() //changes the position of the opject based off velocity 
    {
        position = Vector.add(position, velocity);
    }

    private void updateRotation() //changes the rotation based off velocity
    {
        
    }
}