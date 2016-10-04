package com.jeremy;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.*;
import com.sun.xml.internal.ws.api.message.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.EventListener;
import java.util.Scanner;

public class Main {
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {


        String key = null;
        //reading in the key
        try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))) {
            key = reader.readLine();
            System.out.println(key);


        } catch (Exception ioe) {
            System.out.println("No key file found, or could not read key. Please verify key.txt present");
            System.exit(-1);

        }
        boolean locationCheck = true;
        boolean correctInput = true;
        while (locationCheck) {

            try {
                //while loop to make sure its a correct location
                while (correctInput) {
                    System.out.println("Enter a location, example: Minneapolis");
                    String input = stringScanner.nextLine();

                    //setting the API key
                    GeoApiContext context = new GeoApiContext().setApiKey(key);
                    //getting the results for the user input
                    GeocodingResult[] geoResults = GeocodingApi.geocode(context, input).await();

                    //printing the results of the GEO results if there is more than 1 search result
                    if (geoResults.length > 1) {

                        for (int i = 0; i < geoResults.length; i++) {

                            System.out.println(geoResults[i].formattedAddress);
                        }

                        System.out.println("These are the top results for " + input + ". Please enter more details for your search. \n");
                    }
                    //if there is only 1 result it will print the result and show the elevation of the result
                    else {
                        ElevationResult[] elevationResults = ElevationApi.getByPoints(context, geoResults[0].geometry.location).await();
                        locationCheck = false;
                        ElevationResult userInputElevation = elevationResults[0];
                        System.out.println("The elevation of " + geoResults[0].formattedAddress + " above sea level is " + userInputElevation.elevation + " meters \n");
                        System.out.println(String.format("The elevation of " + geoResults[0].formattedAddress + " above sea level is %.2f meters.", userInputElevation.elevation));
                        correctInput = false;

                    }
                }

                //catch the exception if there is one
            } catch (Exception ioe) {
                System.out.println("No results for your location, enter a valid location \n");


            }
        }
    }

}
