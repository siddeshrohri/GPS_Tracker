import java.io.IOException;
/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Siddesh R Ohri
 */
public class TrackInfo 

{
  public static void main(String[] args) throws IOException{
    // TODO: Implement TrackInfo application here
    //Checks if it is valid or not
    if(args.length  ==  0)
    
    {
      //If its not valid, it exits the program
      System.out.println("Invalid");
      System.exit(0);
    }

      //Stores the filename
      String filename = args[0];
      //Create a new object for Track class
      Track obj = new Track(filename);
      //Prints out the points in the track file
      System.out.println(obj.size()+ " points in track");
      //Prints out the LowestPoint of the file
      System.out.println("Lowest point is "+obj.lowestPoint());
      //Prints out the HighestPoint of the file
      System.out.println("Highest point is "+obj.highestPoint());
      //Prints out the TotalDistance between the points
      System.out.printf("Total Distance = %.3f km", obj.totalDistance()/1000);
      //Prints out the Average speed
      System.out.printf("\nAverage speed = %.3f m/s", obj.averageSpeed());
      //Next Line
      System.out.println();
  }

}
