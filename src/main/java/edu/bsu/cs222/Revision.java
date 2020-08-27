package edu.bsu.cs222;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//This class gets the revision data from the api

public class Revision {

    @SerializedName("user")
    String name;

    @SerializedName("timestamp")
    Date timeStamp;

    public Revision(String name, String timeStamp) {
        this.name = name;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            this.timeStamp = formatter.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    Revision(String name, Date timeStamp) {
        this.name = name;
        this.timeStamp = timeStamp;
    }

    public boolean equals(Object o) {
        Revision rev = (Revision) o;
        return rev.name.equals(this.name) && rev.timeStamp.equals(this.timeStamp);
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\tTimeStamp: " + this.timeStamp;
    }

}
