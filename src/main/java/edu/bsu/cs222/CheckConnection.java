package edu.bsu.cs222;

import java.net.URLEncoder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

//This class checks the connection to the Wikipedia page and redirects it if it can be redirected

class CheckConnection {
    void checkURL(){
        try {
            URL url = new URL("https://www.wikipedia.org");
            URLConnection connection = url.openConnection();
            connection.connect();

            System.out.println("Connection Successful");
        }
        catch (Exception e) {
            System.out.println("Internet Not Connected");
        }
    }
    void findRedirects(String search) throws IOException {
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + URLEncoder.encode(search,("utf-8")) + "&rvprop=timestamp|user&rvlimit=24&redirects");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        System.out.println(reader);
        System.out.println(url);
        reader.close();
    }
}