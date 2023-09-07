package com.example.audioplayer;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private int id;
    private int idImage;
    private String audioName;
    private String artistName;
    private Boolean isPlaying;

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", idImage=" + idImage +
                ", audioName='" + audioName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", isPlaying=" + isPlaying +
                '}';
    }

    public Model(int id, int idImage, String audioName, String artistName, Boolean isPlaying) {
        this.id = id;
        this.idImage = idImage;
        this.audioName = audioName;
        this.artistName = artistName;
        this.isPlaying = isPlaying;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }


}
