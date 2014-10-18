package com.example.yinxiang.audio;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.yinxiang.YinXiangFile;

public class YinXiangAudioNote implements Parcelable{
    private String title;
    private String description;
    private YinXiangFile uri;
    private String objectId;

    public YinXiangAudioNote(Parcel note) {
        title = note.readString();
        description = note.readString();
        uri = YinXiangFile.CREATOR.createFromParcel(note);
        objectId = note.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public YinXiangFile getUri() {
        return uri;
    }

    public void setUri(YinXiangFile uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        uri.writeToParcel(dest, flags);
        dest.writeString(objectId);
    }

    public  static Parcelable.Creator<YinXiangAudioNote> CREATOR = new Parcelable.Creator<YinXiangAudioNote>() {
        @Override
        public YinXiangAudioNote createFromParcel(Parcel source) {
            return new YinXiangAudioNote(source);
        }

        @Override
        public YinXiangAudioNote[] newArray(int size) {
            return new YinXiangAudioNote[size];
        }
    };




}
