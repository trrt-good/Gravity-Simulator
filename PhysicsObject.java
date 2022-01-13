public class PhysicsObject
{
    private double surfaceAirDensity = 1.225;

    public Vector position; //global position of the PhysicsObject as a vector 
    public Vector prevPosition; //position before the last physics update
    //local position is always 0,0
    public double rotation = 0.0; //rotation of the PhysicsObject in degrees

    public double mass; //Mass of the PhysicsObject (in metric tons)
    public Vector centerOfMass; //The center of mass of the PhysicsObject in local space

    public double drag; //Coefficient of drag
    public double angularDrag = 0.05; //Coefficient of angular drag

    public double angularVelocity = 0; //angular velocity in degrees per second 
    public Vector velocity = new Vector(0, 0); //Linear velocity of the PhysicsObject in units/sec

    public boolean isKinematic; //should this PhysicsObject be taken out of physics control?
    public boolean freezeRotation; //freezes rotation if true 
    public boolean effectedByGravity; //should this PhysicsObject be effected by Gravity?

    public PhysicsObject(Vector posIn, double massIn, double angDragIn, double dragIn, boolean isKnemtcIn, boolean frzRotationIn, boolean effbyGravIn, Vector centOfMassIn)
    {
        GameManager.physicsObjects.add(this);
        position = new Vector(posIn);
        mass = massIn;
        drag = dragIn;
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
        if (isKinematic == true) return;
        if (forceType == 0)
        {
            velocity.x += (forceIn.x*GameManager.FIXED_TIME_STEP)/mass;
            velocity.y += (forceIn.y*GameManager.FIXED_TIME_STEP)/mass;
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
            angularVelocity = angularVelocity + (torqueIn * GameManager.FIXED_TIME_STEP)/mass;
        else if (torqueType == 1)
            angularVelocity = angularVelocity + (torqueIn)/mass;
    }

    public void setRotation(double angleIn) //sets rotation to specified value
    {
        rotation = angleIn;
    }

    //methods used in physics updates:

    public void applyDrag() //applies drag by decreasing velocity. Called each physics update 
    {
        int i = 0;
        for (i = 0; i < GameManager.gravityObjects.size(); i++)
        {
            double surfaceGForce = (GameManager.gravityObjects.get(i).mass*mass)/Math.pow(GameManager.gravityObjects.get(i).diameter/2, 2);
            if (velocity.getMagnitude() != 0 && ((GameManager.gravityObjects.get(i).mass*mass)/Math.pow(Vector.distance(getWorldCenterOfMass(), GameManager.gravityObjects.get(i).position), 2)/surfaceGForce) > 0.2)
            {
                double multiplier = (velocity.getMagnitude()-(0.5*(surfaceAirDensity*((GameManager.gravityObjects.get(i).mass*mass)/Math.pow(Vector.distance(getWorldCenterOfMass(), GameManager.gravityObjects.get(i).position), 2)/(surfaceGForce*3)))*velocity.getSqrMagnitude())*drag*GameManager.FIXED_TIME_STEP)/velocity.getMagnitude();
                velocity.x *= multiplier;
                velocity.y *= multiplier;
            }
        } 
    }

    public void applyAngularDrag()
    {
        if (angularVelocity !=0 )
        {
            angularVelocity = angularVelocity-0.5*surfaceAirDensity*angularVelocity*angularVelocity*angularDrag;
        }
    }

    public void applyGravity() //applies gravity by adding a force twards each gravity object 
    {
        if (!effectedByGravity) return;
        int i = 0;
        for (i = 0; i < GameManager.gravityObjects.size(); i++)
        {
            addForce(Vector.scaledDifference(getWorldCenterOfMass(), GameManager.gravityObjects.get(i).position, -(GameManager.gravityObjects.get(i).mass*mass)/Math.pow(Vector.distance(getWorldCenterOfMass(), GameManager.gravityObjects.get(i).position), 2)), 0);
        }
        
    }

    public void updatePosition() //changes the position of the opject based off velocity 
    {
        prevPosition = position;
        position = Vector.add(position, Vector.multiply(velocity, GameManager.FIXED_TIME_STEP));
    }

    public void updateRotation() //changes the rotation based off velocity 
    {
        if (freezeRotation == true) return;
        rotation = rotation + angularVelocity*GameManager.FIXED_TIME_STEP;
    }

    public void checkCollisions()
    {
        int i = 0;
        for (i = 0; i < GameManager.gravityObjects.size(); i++)
        {
            if (Vector.distance(position, GameManager.gravityObjects.get(i).position) <= GameManager.gravityObjects.get(i).diameter/2)
            {
                velocity.x = 0;
                velocity.y = 0;
                effectedByGravity = false;
                freezeRotation = true;
            } 
            else
            {
                effectedByGravity = true;
                freezeRotation = false;
            }
        }
    }
}