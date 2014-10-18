package com.example.yinxiang;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kuangcheng on 2014/10/18.
 */
public class YinXiangFile implements Parcelable{

    private String url;
    private String name;

    public YinXiangFile(Parcel source) {
        url = source.readString();
        name = source.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
    }

    public  static Parcelable.Creator<YinXiangFile> CREATOR = new Parcelable.Creator<YinXiangFile>() {
        @Override
        public YinXiangFile createFromParcel(Parcel source) {
            return new YinXiangFile(source);
        }

        @Override
        public YinXiangFile[] newArray(int size) {
            return new YinXiangFile[size];
        }
    };
}
