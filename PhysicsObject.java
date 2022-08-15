import java.awt.Color;

public class PhysicsObject
{
    public double diameter = 10;

    public final Color defaultColor = Color.LIGHT_GRAY;
    public Color color = defaultColor;

    public Vector2 position = new Vector2(0, 0); //global position of the PhysicsObject as a vector 
    public Vector2 originalPosition = new Vector2(0, 0); //Position that was originally set for the PhysicsObject
    //local position is always 0,0

    public double mass = 10; //Mass of the PhysicsObject
    public double originalMass = 10; //The mass that was originally set for the PhysicsObject

    public double drag = 0.0001; //Coefficient of drag

    public Vector2 velocity = new Vector2(0, 0); //Linear velocity of the PhysicsObject 

    public boolean isKinematic = false; //should this PhysicsObject be taken out of physics control?
    public boolean kinOG = false;
    public boolean freezeRotation = false; //freezes rotation if true 
    public boolean frzRotOG = false;
    public boolean effectedByGravity = true; //should this PhysicsObject be effected by Gravity?
    public boolean effctGvtyOG = true;

    public boolean collisions = true;
    public boolean collsnsOG = true;
    public boolean gravitational = false; //should this PhysicsObject have its own graviational pull? 
    public boolean drawVelocity = true;

    //#region constructors 

    public PhysicsObject()
    {
        GameManager.physicsObjects.add(this);
    }

    public PhysicsObject(Vector2 posIn, double massIn, boolean isKnemtcIn, boolean frzRotationIn, boolean effbyGravIn, boolean graviationalIn, boolean collisionsIn)
    {
        //adds this object to the list of physics objects in the game manager class when it is constructed
        GameManager.physicsObjects.add(this);
        position = new Vector2(posIn);
        originalPosition = new Vector2(posIn);
        mass = massIn;
        originalMass = massIn;
        isKinematic = kinOG = isKnemtcIn;
        freezeRotation = frzRotOG = frzRotationIn;
        effectedByGravity = effctGvtyOG = effbyGravIn;
        gravitational = graviationalIn;
        collisions = collsnsOG = collisionsIn;
    }

    public PhysicsObject(Vector2 posIn, double massIn)
    {
        GameManager.physicsObjects.add(this);
        position = new Vector2(posIn);
        originalPosition = new Vector2(posIn);
        mass = massIn;
        originalMass = massIn;
    }

    //#endregion 

    public void addForce(Vector2 forceIn, int forceType) //forceType 0 is force over time, forceType 1 is instant force applied
    {
        if (isKinematic) return;
        if (forceType == 0)
        {
            velocity.x += (forceIn.x*GameManager.getDeltaTime())/mass;
            velocity.y += (forceIn.y*GameManager.getDeltaTime())/mass;
        }
        else if (forceType == 1)
        {
            velocity.x += (forceIn.x)/mass;
            velocity.y += (forceIn.y)/mass;
        }

        velocity.x *= (1 - drag);
        velocity.y *= (1 - drag);
    }

    public Vector2[] getGravityVectors()
    {
        Vector2[] gravityVectors;
        int counter = 0;

        for (int i = 0; i < GameManager.physicsObjects.size(); i++) //applies gravity for each gravity object
        {
            if (Vector2.difference(position, GameManager.physicsObjects.get(i).position).getMagnitude() > 1)
            {
                counter ++;
            }
        }
        gravityVectors = new Vector2[counter];
        counter = 0;
        for (int i = 0; i < GameManager.physicsObjects.size(); i++) //applies gravity for each gravity object
        {
            if (Vector2.difference(position, GameManager.physicsObjects.get(i).position).getMagnitude() > 1)
            {
                gravityVectors[counter] = Vector2.scaledDifference(position, GameManager.physicsObjects.get(i).position, -(GameManager.physicsObjects.get(i).mass*mass)/Math.pow(Vector2.distance(position, GameManager.physicsObjects.get(i).position), 2));
                counter ++;
            }
        } 
        return gravityVectors;
    } 

//========================== METHODS USED IN PHYSICS UPDATES ============================

    public void applyGravity() //applies gravity by adding a force twards each gravity object 
    {
        if (!effectedByGravity) return;
        int i = 0;
        for (i = 0; i < GameManager.physicsObjects.size(); i++) //applies gravity for each gravity object
        {
            if (Vector2.difference(position, GameManager.physicsObjects.get(i).position).getMagnitude() > 1)
            {
                //System.out.print(Vector2.scaledDifference( position, GameManager.physicsObjects.get(i).position, -(GameManager.physicsObjects.get(i).mass*mass)/Math.pow(Vector2.distance(position, GameManager.physicsObjects.get(i).position), 2)).toString());
                addForce( //adds force to this object based off the vector 
                Vector2.scaledDifference( //returns a vector that's angle is equal to the one inputed, then scaled proportionally to the specified magnitude 
                position, //position of this object's center of mass
                GameManager.physicsObjects.get(i).position, //position of the gravity object
                -(GameManager.physicsObjects.get(i).mass*mass)/Math.pow(Vector2.distance(position, GameManager.physicsObjects.get(i).position), 2)), 0); 
                //^ the magnitude of the force of gravity, calculated by the formula (m1*m2)/d^2   

            }
        }
    }

    public void updatePosition() //changes the position of the opject based off velocity 
    {
        position = Vector2.add(position, Vector2.multiply(velocity, GameManager.getDeltaTime())); 
    }

    public void checkCollisions()
    {
        int i = 0;
        for (i = 0; i < GameManager.physicsObjects.size(); i++) //checks collisions against each gravity object
        {
            if (GameManager.physicsObjects.get(i).collisions && GameManager.physicsObjects.get(i) != this)
            {
                //true if the distance between this object and the gravity object is less than the radius of gravity object
                if (Vector2.distance(position, GameManager.physicsObjects.get(i).position) <= GameManager.physicsObjects.get(i).diameter/2)
                {
                    //freezes the object
                    velocity.x = 0;
                    velocity.y = 0;
                    isKinematic = true;
                } 
                else
                {
                    //un freezes
                    isKinematic = false;
                }
            }
        }
    }
}
