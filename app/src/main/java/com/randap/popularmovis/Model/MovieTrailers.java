package com.randap.popularmovis.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shenanigan on 16/9/16.
 */
public class MovieTrailers {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Video> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }


    private class Video {
        @SerializedName("key")
        private String key;
        @SerializedName("name")
        private String name;
        @SerializedName("type")
        private String type;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


}
