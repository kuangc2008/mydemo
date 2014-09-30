package com.example.yinxiang;

import android.os.Parcel;
import android.os.Parcelable;

public class YinXiangNote implements Parcelable {
    public String title;
    public String url;

    public YinXiangNote() {}

    public YinXiangNote(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public YinXiangNote(Parcel note) {
        title = note.readString();
        url = note.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
    }

    public  static Parcelable.Creator<YinXiangNote> CREATOR = new Parcelable.Creator<YinXiangNote>() {
        @Override
        public YinXiangNote createFromParcel(Parcel source) {
            return new YinXiangNote(source);
        }

        @Override
        public YinXiangNote[] newArray(int size) {
            return new YinXiangNote[size];
        }
    };
}
