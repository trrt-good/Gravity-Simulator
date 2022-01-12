public class Vector
{
    public double x;
    public double y;

    public Vector(double xPos, double yPos)
    {
        x = xPos;
        y = yPos;
    }

    public Vector(Vector vectorIn)
    {
        x = vectorIn.x;
        y = vectorIn.y;
    }

    public double getMagnitude() //returns the magnitude of the vector 
    {
        return Math.sqrt(getSqrMagnitude());
    }

    public double getSqrMagnitude() //returns the squared form of the magnitude for specific cases (much faster for computer)
    {
        return x*x+y*y;
    }

    public Vector getNormalized() //returns a vector scaled to the magnitude of 1 (doesn't change any values in the object)
    {
        return new Vector(x/getMagnitude(), y/getMagnitude());
    }

    public double getDirection() //returns the angle of the vector (1, 0) is 0 degrees 
    {
        if (x == 0 && y == 0)
            return 0.0;
        if (y == 0)
        {
            if (x > 0)
                return 0.0;
            if (x < 0)
                return 180.0;
        }
        else if (x == 0)
        {
            if (y > 0)
                return 90.0;
            if (y < 0)
                return 270;
        }
        return Math.toDegrees(Math.atan(y/x));
    }

    public String toString()
    {
        return String.format("(%.1f,%.1f)", x, y);
    }

    //static methods: 

    public static double distance(Vector pos1, Vector pos2) //returns the distance between two vectors 
    {
        return Math.sqrt((pos1.x-pos2.x)*(pos1.x-pos2.x) + (pos1.y-pos2.y)*(pos1.y-pos2.y));
    }

    public static Vector difference(Vector pos1, Vector pos2) //returns the difference between two vectors 
    {
        return new Vector(pos1.x-pos2.x, pos1.y-pos2.y);
    }

    public static Vector scaledDifference(Vector pos1, Vector pos2, double magnitude) //returns the difference between vectors but scaled to the specified magnitude
    {
        double mult = magnitude/Math.sqrt((pos1.x-pos2.x)*(pos1.x-pos2.x)+(pos1.y-pos2.y)*(pos1.y-pos2.y));
        return new Vector((pos1.x-pos2.x)*mult, (pos1.y-pos2.y)*mult);
    }

    public static Vector add(Vector pos1, Vector pos2) //adds two vectors together
    {
        return new Vector(pos1.x+pos2.x, pos1.y+pos2.y);
    }

    public static Vector setMagnitude(Vector pos1, double newMag)
    {
        return new Vector(pos1.x*(newMag/Math.sqrt(pos1.x*pos1.x+pos1.y*pos1.y)), pos1.y*(newMag/Math.sqrt(pos1.x*pos1.x+pos1.y*pos1.y)));
    }

    public static Vector multiply(Vector pos1, double multiplier)
    {
        return new Vector(pos1.x*multiplier, pos1.y*multiplier);
    }
}