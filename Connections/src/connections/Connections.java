package connections;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Team 6
 */
public class Connections {

    public static void main(String[] args) throws URISyntaxException, UnknownHostException, IOException  {
        
        //Try statement to catch any exceptions
        try 
        {
            /*
            This section of code is just used to display
            the host name and IP address
            */
            URI uri = new URI("https://www.ncei.noaa.gov/access/services/search/v1/data?dataset=global-hourly");

            String host = uri.getHost();
            System.out.println("Hostname: " + host);

            InetAddress address = InetAddress.getByName(host);
            System.out.println("Host IP Address: " + address.getHostAddress());
            
            //This is the URL we are connecting to in order to receive JSON data
            URL url = new URL("https://www.ncei.noaa.gov/access/services/search/v1/data?dataset=global-hourly");
                        
            //Creating a HTTP URL Connection so we can close it when finished
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            
            //Our buffered reader object which reads in the output stream from the server
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));     

            //Our string which reads in the buffered reader
            String jsontext = br.readLine();
            
            //This is our GSON object that will read in/parse JSON information
            Gson gson = new Gson();
            
            //This try statement is just to test if the data received is JSON format or not
            try 
            {
                //Test if the string data we pull from the buffered reader can be converted to JSON or not
                gson.fromJson(jsontext, Object.class);
                
                //If it can be converted, we print out the data
                System.out.println(gson.toJson(jsontext));
            }
            catch(com.google.gson.JsonSyntaxException ex) 
            {
                //If it can't be converted, print an error message stating so.
                System.out.println("ERROR! Not valid JSON data!");
                
            }
            
            /*
            This block of code closes the connection.
            We're making sure that the httpConnection is connected first.
            If it's connected, we disconnect.
            */
            if(httpConnection != null) 
            {
                httpConnection.disconnect();
            }

            }
        
        //Catch statements for various exceptions, with accompanying error messages
        catch(MalformedURLException ex)
        {
            System.out.println("ERROR! Malformed URL!");
        }
        catch(IOException ex)
        {
            System.out.println("ERROR! IO Exception Error!");
        }
        catch(Exception ex)
        {
            System.out.println("ERROR! Exception Error!");
        }
        
    }
    
}
