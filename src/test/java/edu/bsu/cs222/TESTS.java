package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

//All the tests for the project

public class TESTS {

    //THis tests the revision parser
    @Test
    public void testRevisionParser() {
        JsonParser parser = new JsonParser();
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("sample.json");
        assert inputstream != null;
        Reader reader = new InputStreamReader(inputstream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        JsonArray array = null;
        for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
            Assert.assertNotNull(array);
        }
        System.out.println(array);

    }

    //This tests the revision class for the username and the timestamp
    @Test
    public void testRevision() {
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("sample.json");
        assert inputstream != null;
        Reader reader = new InputStreamReader(inputstream);
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(reader);

        RevisionParser revisionParser = new RevisionParser();
        List<Revision> revisionList = revisionParser.parse(rootElement);

        for (Revision r: revisionList) {
            System.out.println(r);
        }
        Assert.assertEquals(revisionList.get(0).toString(), "Name: Monkbot	TimeStamp: Fri Sep 13 11:10:41 EDT 2019");
    }

    //This tests to make sure we can parse the data correctly
    @Test
    public void testParseConnection() throws MalformedURLException {
        JsonArray array = null;
        JsonParser parser = new JsonParser();
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=\" + \"Ball State\" + \"&rvprop=timestamp|user&rvlimit=24&redirects");
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("sample.json");
        assert inputstream != null;
        Reader reader = new InputStreamReader(inputstream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
        }
        System.out.println(array);
        System.out.println(url);
    }

    //This tests our connection and redirects class
    @Test
    public void testAPIConnection() throws IOException {
        CheckConnection connection = new CheckConnection();
        connection.findRedirects("soccer");
    }

    //This tests if the user can connect to the internet
    @Test
    public void testInternetConnection(){
        CheckConnection checkConnection = new CheckConnection();
        checkConnection.checkURL();
    }

    //This tests to make sure everything works together
    @Test
    public void wholeThingTest() {
        String inputString = "Obama";
//        listView.getItems().add("some new element");
        URL url = null;
        try {
            url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + URLEncoder.encode(inputString,("utf-8")) + "&rvprop=timestamp|user&rvlimit=24&redirects");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        try{
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(in);
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(reader);

        RevisionParser parser = new RevisionParser();
        List<Revision> revisions = parser.parse(rootElement);

        for (Revision r: revisions) {
            System.out.println(r);
        }
    }

}

