package com.example.yinxiang.audio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuangcheng on 2014/10/18.
 */
public class YinXiangAudioResult implements Parcelable{
    private List<YinXiangAudioNote> results;

    public YinXiangAudioResult(Parcel source) {
        results = source.createTypedArrayList(YinXiangAudioNote.CREATOR);
    }

    public List<YinXiangAudioNote> getResults() {
        return results;
    }

    public void setResults(List<YinXiangAudioNote> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    public  static Parcelable.Creator<YinXiangAudioResult> CREATOR = new Parcelable.Creator<YinXiangAudioResult>() {
        @Override
        public YinXiangAudioResult createFromParcel(Parcel source) {
            return new YinXiangAudioResult(source);
        }

        @Override
        public YinXiangAudioResult[] newArray(int size) {
            return new YinXiangAudioResult[size];
        }
    };
}
