public class PhysicsObject
{
    private double surfaceAirDensity = 1.225; //air density at the surface of a gravity object

    public Vector position = new Vector(0, 0); //global position of the PhysicsObject as a vector 
    public Vector prevPosition = new Vector(0, 0); //position before the last physics update
    //local position is always 0,0
    public double rotation = 0.0; //rotation of the PhysicsObject in degrees

    public double mass = 10; //Mass of the PhysicsObject 
    public Vector centerOfMass = new Vector(0, 0); //The center of mass of the PhysicsObject in local space

    public double drag = 0.01; //Coefficient of drag
    public double angularDrag = 0.05; //Coefficient of angular drag

    public double angularVelocity = 0; //angular velocity in degrees per second 
    public Vector velocity = new Vector(0, 0); //Linear velocity of the PhysicsObject in units/sec

    public boolean isKinematic = false; //should this PhysicsObject be taken out of physics control?
    public boolean freezeRotation = false; //freezes rotation if true 
    public boolean effectedByGravity = true; //should this PhysicsObject be effected by Gravity?

    //no arg constructor
    public PhysicsObject(){}

    //constructor 
    public PhysicsObject(Vector posIn, double massIn, double angDragIn, double dragIn, boolean isKnemtcIn, boolean frzRotationIn, boolean effbyGravIn, Vector centOfMassIn)
    {
        GameManager.physicsObjects.add(this); //adds this object to the list of physics objects in the game manager class when it is constructed
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



//========================== METHODS USED IN PHYSICS UPDATES ============================



    public void applyDrag() //applies drag by decreasing velocity. Called each physics update 
    {
        if (drag == 0) return;
        int i = 0;
        for (i = 0; i < GameManager.gravityObjects.size(); i++)
        {
            if (GameManager.gravityObjects.get(i).hasAtmosphere == true)
            {
                double surfaceGForce = (GameManager.gravityObjects.get(i).mass*mass)/Math.pow(GameManager.gravityObjects.get(i).diameter/2, 2);
                if (velocity.getMagnitude() != 0 && ((GameManager.gravityObjects.get(i).mass*mass)/Math.pow(Vector.distance(getWorldCenterOfMass(), GameManager.gravityObjects.get(i).position), 2)/surfaceGForce) > 0.2)
                {
                    double multiplier = (velocity.getMagnitude()-(0.5*(surfaceAirDensity*((GameManager.gravityObjects.get(i).mass*mass)/Math.pow(Vector.distance(getWorldCenterOfMass(),GameManager.gravityObjects.get(i).position), 2)/(surfaceGForce)))*velocity.getSqrMagnitude())*drag*GameManager.FIXED_TIME_STEP)/velocity.getMagnitude();
                    velocity.x *= multiplier;
                    velocity.y *= multiplier;
                }
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
        for (i = 0; i < GameManager.gravityObjects.size(); i++) //applies gravity for each gravity object
        {
            addForce( //adds force to this object based off the vector 
                Vector.scaledDifference( //returns a vector that's x and y fields are the difference between two specified positions, then scaled proportionally to the specified magnitude 
                    getWorldCenterOfMass(), //position of this object's center of mass
                    GameManager.gravityObjects.get(i).position, //position of the gravity object
                    -(GameManager.gravityObjects.get(i).mass*mass)/Math.pow(Vector.distance(getWorldCenterOfMass(), GameManager.gravityObjects.get(i).position), 2)), 0); 
                    //^ the magnitude of the force of gravity, calculated by the formula (m1*m2)/d^2   
        }
        
    }

    public void updatePosition() //changes the position of the opject based off velocity 
    {
        prevPosition = position; //sets the prevposition field before it does calculations 
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
        for (i = 0; i < GameManager.gravityObjects.size(); i++) //checks collisions against each gravity object
        {
            if (GameManager.gravityObjects.get(i).collisions)
            {
                //true if the distance between this object and the gravity object is less than the radius of gravity object
                if (Vector.distance(position, GameManager.gravityObjects.get(i).position) <= GameManager.gravityObjects.get(i).diameter/2)
                {
                    //freezes the object
                    velocity.x = 0;
                    velocity.y = 0;
                    effectedByGravity = false; 
                    freezeRotation = true;
                } 
                else
                {
                    //un freezes
                    effectedByGravity = true;
                    freezeRotation = false;
                }
            }
        }
    }
}