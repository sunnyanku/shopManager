package com.bebo.shopmanager.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Service;


public class GoogleGeoCode 
{
    private static final String GEO_CODE_SERVER = "http://maps.googleapis.com/maps/api/geocode/json?";

    public static void main(String[] args)
    {
        String code = args[0];

        String response = getLocation(code);

        String[] result = parseLocation(response);

        System.out.println("Latitude: " + result[0]);
        System.out.println("Longitude: " + result[1]);
    }

    public static String getLocation(String code)
    {
        String address = buildUrl(code);

        String content = null;

        try
        {
            URL url = new URL(address);

            InputStream stream = url.openStream();

            try
            {
                int available = stream.available();

                byte[] bytes = new byte[available];

                stream.read(bytes);

                content = new String(bytes);
            }
            finally
            {
                stream.close();
            }

            return (String) content.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String buildUrl(String code)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(GEO_CODE_SERVER);

        builder.append("address=");
        builder.append(code.replaceAll(" ", "+"));
        builder.append("&sensor=false");

        return builder.toString();
    }

    public static String[] parseLocation(String response)
    {
        // Look for location using brute force.
        // There are much nicer ways to do this, e.g. with Google's JSON library: Gson
        //     https://sites.google.com/site/gson/gson-user-guide

        String[] lines = response.split("\n");

        String lat = null;
        String lng = null;

        for (int i = 0; i < lines.length; i++)
        {
            if ("\"location\" : {".equals(lines[i].trim()))
            {
                lat = getOrdinate(lines[i+1]);
                lng = getOrdinate(lines[i+2]);
                break;
            }
        }

        return new String[] {lat, lng};
    }

    private static String getOrdinate(String s)
    {
        String[] split = s.trim().split(" ");

        if (split.length < 1)
        {
            return null;
        }

        String ord = split[split.length - 1];

        if (ord.endsWith(","))
        {
            ord = ord.substring(0, ord.length() - 1);
        }

        // Check that the result is a valid double
        Double.parseDouble(ord);

        return ord;
    }
    
    public static Double calculateDistanceBetweenPoints(Double offerLat,Double offerLng,Double userLat,Double userLng){
		Double earthRadius = 3958.75;
		Double distanceLat = Math.toRadians((Double)offerLat-userLat);
		Double distanceLng = Math.toRadians((Double)offerLng-userLng);
 
		Double sindLat = Math.sin(distanceLat / 2);
		Double sindLng = Math.sin(distanceLng / 2);
 
		Double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(Math.toRadians(userLat.doubleValue())) * Math.cos(Math.toRadians(offerLat.doubleValue()));
 
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
 
		double distance = earthRadius * c;
		return distance;
	}

}