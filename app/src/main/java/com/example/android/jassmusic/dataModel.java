package com.example.android.jassmusic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class dataModel implements Serializable {
    int index;
    int image;
    String img;
    String text;
    int musicresource;
    String songurl,mkey;

    public dataModel() {
    }

    public dataModel(int index, int image, String text, int musicresource) {
        this.index=index;
        this.image = image;
        this.text = text;
        this.musicresource=musicresource;
    }
    @Exclude
    public String getMkey() {
        return mkey;
    }
    @Exclude
    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public dataModel(String text, String songurl , String img) {
//        this.index=index;
        this.img = img;
        this.text = text;
        this.songurl=songurl;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public String getSongurl() {
        return songurl;
    }

    public void setSongurl(String songurl) {
        this.songurl = songurl;
    }

    public int getIndex(){
        return index;
    }
    public int getImage() {
        return image;
    }
    public String getImg() {
        return img;
    }
    public String getText() {
        return text;
    }
    public int getMusicresource(){
        return musicresource;
    }

}
