package edu.bsu.cs222;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

//This class creates the UI

public class UI extends Application{

    ListView<String> listView = new ListView<>();
    private Button button = new Button("Click to search");
    private GridPane grid = new GridPane();
    private VBox layout = new VBox(20);
    private Scene scene = new Scene(layout, 600, 600);
    private TextField userInput = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Two Week Project");

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(listView, button, grid);

        primaryStage.setScene(scene);
        primaryStage.show();

        userInput.setPromptText("Search Wikipedia");
        userInput.setPadding(new Insets(10, 20, 10,20));

        grid.getChildren().addAll(userInput);
        grid.setAlignment(Pos.BASELINE_CENTER);

        button.setOnAction(e -> buttonClicked());
    }

    private void buttonClicked() {
       CheckConnection checkConnection = new CheckConnection();
       checkConnection.checkURL();
       String inputString = userInput.getText();

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

        listView.getItems().removeAll();
        for (Revision r: revisions) {
            System.out.println(r);
            listView.getItems().add(r.toString());
        }
    }
}
