package com.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kuangcheng on 2014/9/30.
 */
public class ParcelUtils {

    public static <T extends Parcelable> Parcel writeToParcel(List<T> items) {
        Parcel p = Parcel.obtain();
        p.writeTypedList(items);
        return p;
    }

    public static <T extends Parcelable> Parcel writeToParcel(T item) {
        Parcel p = Parcel.obtain();
        item.writeToParcel(p, 0);
        return p;
    }

    public static <T extends Parcelable> List<T> readListFromTypes(byte[] bytes, Parcelable.Creator<T> creator) {
        if(bytes == null || bytes.length <= 0) {
            return null;
        }

        try {
            Parcel p = Parcel.obtain();
            p.unmarshall(bytes, 0, bytes.length);
            p.setDataPosition(0);
            List<T> models = p.createTypedArrayList(creator);
            p.recycle();
            return models;
        } catch (Exception e) {
        } catch (Error e){
        }
        return null;
    }

    public static <T extends Parcelable> T readItemFromTypes(byte[] bytes, Parcelable.Creator<T> creator) {
        if(bytes == null || bytes.length <= 0) {
            return null;
        }
        try {
            Parcel p = Parcel.obtain();
            p.unmarshall(bytes, 0, bytes.length);
            p.setDataPosition(0);
            T model = creator.createFromParcel(p);
            p.recycle();
            return model;
        } catch (Exception e) {
        } catch (Error e) {
        }
        return null;
    }
}
