import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Siddesh R Ohri
 */
public class Track 

{
  //Create an arraylist object to store the Point values
  private ArrayList<Point> points = new ArrayList<>();
  // TODO: Create a stub for the constructor
  //Declaring a constructor
  public Track()
  {
  }
  //Declaring a parameterized constructor
  public Track(String filename) throws IOException, GPSException
  {
    //Passing the filename to the readfile method
    readFile(filename);
  }

  // TODO: Create a stub for readFile()
  public void readFile(String filename) throws IOException, GPSException
  
  {
    //Clearing the old stored data for new data
    points.clear();
    //Declaring a scanner object for reading the file 
    Scanner scanner = new Scanner(new File(filename));
    //Read the file passing the header section of the file
    scanner.next();
    //Loop to read the csv file
    while(scanner.hasNext())
    
    {
      //Storing the value of the csv file line-wise

      String lines = scanner.next();
      //Splits the csv file line into parts with commas
      String[] part = lines.split(",");

      //Checks if the length is less than 4
      if(part.length != 4)
      {
        //Returns GPS Exception if the condition is true
        throw new GPSException("Invalid");
      }
      //Storing the value in time and parsing it into the data-type
      ZonedDateTime time = ZonedDateTime.parse(part[0]);
      //Storing the value in latitude and parsing it into the data-type
      double latitude = Double.parseDouble(part[2]);
      //Storing the value in elevation and parsing it into the data-type
      double elevation = Double.parseDouble(part[3]);
      //Storing the value in longitude and parsing it into the data-type
      double longitude = Double.parseDouble(part[1]);
      //Storing all the values in add() with object creation
      points.add(new Point(time, longitude, latitude, elevation));
    }

    //Closing the scanner class
    scanner.close();
  }


  // TODO: Create a stub for add()
  public void add(Point point)
  
  {
    //Storing the value of points in this function
    points.add(point);
  }


  // TODO: Create a stub for get()
  public Point get(int index) throws GPSException
  
  {
    //Checks if the conditon is in range
    if(index  <  0 || index  >=  points.size())
    
    {
      //If the condition is true, prints out an invalid message
      throw new GPSException("Invalid Position");
    }

    //Returns the value of the get() function
    return points.get(index);
  }


  // TODO: Create a stub for size()
  public int size()
  
  {
    //Returns the point size() function
    return points.size();
  }


  // TODO: Create a stub for lowestPoint()
  public Point lowestPoint() throws GPSException
  
  {
    //If the number of points is less than two
    if(points.size()  <  2)
    
    {
      //If the above condition is true,throws an exception
      throw new GPSException("Invalid Points");
    }

    //Stores the first point
    Point lowest = points.get(0);
    //Loop to check the lowest point
    for(int i =  0; i <  points.size() ;  i++)
    
    {
      //Stores the value of the point in test variable
      Point tmp = points.get(i);
      //Checks for the point variable 
      if((tmp.getElevation())  <  (lowest.getElevation()))
      
      {
        //If the above condition is true, stores the value in lowest
        lowest  =  tmp;
      }

    }

    //Returns the value of the lowest point
    return lowest;
  }


  // TODO: Create a stub for highestPoint()
  public Point highestPoint() throws GPSException
 
  {
    //If the number of points is less than two
    if(points.size()  <  2)
    
    {
      //If the above condition is true,throws an exception
      throw new GPSException("Invalid Points");
    }

    //Stores the first point
    Point highest = points.get(0);
    //Loop to check the highest point
    for(int i =  0; i <  points.size() ;  i++)
    
    {
      //Stores the value of the point in test1 variable
      Point tmp2 = points.get(i);
      //Checks for the point variable 
      if(tmp2.getElevation() > highest.getElevation())
      
      {
        //If the above condition is true, stores the value in lowest
        highest = tmp2;
      }

    }

    //Returns the value of the lowest point
    return highest;
  } 


  // TODO: Create a stub for totalDistance()
  public double totalDistance()throws GPSException
  
  {
    //If the number of points is less than two
    if(points.size()  <  2)
    
    {
      //If the above condition is true,throws an exception
      throw new GPSException("Invalid Points");
    }

    //Declaring the variable totaldistance
    double totalDistance =  0;
    //Loop to calculate the total distance between the points
    for (int i =  1; i <  points.size();  i++) 
    
    {
        //Stores the previous point in p1
        Point p1 =  points.get(i - 1);
        //Stores the current point in p2
        Point p2 =  points.get(i);
        //Calculate the distance between the points
        double distance = Point.greatCircleDistance(p1, p2);
        //Stores the value of the final totaldistance between two points
        totalDistance += distance;
    }

    //Returns the totaldisatnce between then points
    
    return totalDistance;
    
  }

  // TODO: Create a stub for averageSpeed()
  public double averageSpeed() throws GPSException
  
  {
    //If the number of points is less than two
    if(points.size() <  2)
    
    {
      //If the above condition is true,throws an exception
      throw new GPSException("Invalid Points");
    }

    //Stores the first point 
    Point fPoint =  points.get(0);
    //Stores the lastpoint 
    Point lPoint =  points.get(points.size() - 1);
    //Stores the starttime of the first point
    ZonedDateTime sTime =  fPoint.getTime();
    //Stores the starttime of the last point
    ZonedDateTime eTime =  lPoint.getTime();
    //Stores the seconds of the final time
    long secs =  ChronoUnit.SECONDS.between(sTime, eTime);
    //Stores the total distance
    double totalDistance =  totalDistance();
    //Calculates the average speede using the formula
    double averageSpeed = totalDistance / secs;
    //Returns the averagespeed 
    return averageSpeed;
  }


  public void writeKML(String filename) throws IOException 
  
  {
    BufferedWriter writer =  new BufferedWriter(new FileWriter(filename));

    writer.write( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" );

    writer.write( "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" ) ;

    writer.write( "<Document>\n" );

    writer.write( "<Style id=\"lineStyle\">\n" );

    writer.write( "<LineStyle>\n" );

    writer.write( "<color>7f0000ff</color>\n" );

    writer.write( "<width>4</width>\n" );

    writer.write( "</LineStyle>\n" );

    writer.write( "</Style>\n" ) ;

    writer.write( "<Placemark>\n" );

    writer.write( "<styleUrl>#lineStyle</styleUrl>\n" );

    writer.write( "<LineString>\n" );

    writer.write( "<coordinates>\n" );

    for (int i =  0; i <  points.size();  i++) 
    
    {
        Point gt =  points.get(i);
        
        writer.write(gt.getLongitude() + "," + gt.getLatitude() + "," + gt.getElevation() + "\n");
    }
    

    writer.write( "</coordinates>\n" );

    writer.write( "</LineString>\n" );

    writer.write( "</Placemark>\n" );

    writer.write( "</Document>\n" );

    writer.write( "</kml>\n" );

    writer.close();
}

}
