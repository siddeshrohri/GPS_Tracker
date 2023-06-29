import java.io.IOException;
  /**
 * Program to general a KML file from GPS track data stored in a file,
 * for the Advanced task of COMP1721 Coursework 1.
 *
 * @author Siddesh R Ohri
 */
  public class ConvertToKML

  {
    public static void main(String[] args) 
    
    {  
        //Checks if the number of arguments is in range
        if (args.length < 2) 
        
        {   
            //If the above condition is true, prints out the statement
            System.out.println(" Usage:ConvertToKML input.csv output.kml ");
            //Exits the program
            System.exit(0);
        }
        //Stores the output file as the second argument
        String outputFile = args[1];
        //Stores the input file as the first argument
        String inputFile = args[0];
        
        try 
        
        {
            //Creates a track object with the file as inputfile
            Track track = new Track(inputFile);
            //Writes the content into the output file
            track.writeKML(outputFile);
        
        } catch (IOException e) 
        
        {
            //Prints an error message if there is an exception
            System.err.println("Error: " + e.getMessage());
            //Exits the program
            System.exit(1);
        }

    }

}





