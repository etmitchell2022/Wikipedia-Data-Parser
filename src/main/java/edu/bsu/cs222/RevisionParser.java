package edu.bsu.cs222;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//This class parses the data received from the revision class to be used by the UI

public class RevisionParser {

    public List<Revision> parse (InputStream inputStream) {
        JsonParser parser = new JsonParser();
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        JsonArray array = null;
        List<Revision> revisionList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
        }

        assert array != null;
        for (JsonElement element : array) {
            String username = element.getAsJsonObject().get("username").getAsString();
            Integer timestamp = element.getAsJsonObject().get("timestamp").getAsInt();
            System.out.println("Username: " + username);
            System.out.println("timestamp: " + timestamp);
        }
        return revisionList;
    }

        ArrayList<Revision> parse(JsonElement rootElement){
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
            JsonArray revisions = null;
            for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
                JsonObject entryObject = entry.getValue().getAsJsonObject();
                revisions = entryObject.getAsJsonArray("revisions");
            }
            Type revisionListType = new TypeToken<ArrayList<Revision>>() {
            }.getType();
            return gson.fromJson(revisions, revisionListType);
        }
}


