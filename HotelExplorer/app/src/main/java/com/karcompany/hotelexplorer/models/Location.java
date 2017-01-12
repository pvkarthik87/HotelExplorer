
package com.karcompany.hotelexplorer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Location implements Parcelable
{

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    public final static Creator<Location> CREATOR = new Creator<Location>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Location createFromParcel(Parcel in) {
            Location instance = new Location();
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.latitude = ((double) in.readValue((double.class.getClassLoader())));
            instance.longitude = ((double) in.readValue((double.class.getClassLoader())));
            return instance;
        }

        public Location[] newArray(int size) {
            return (new Location[size]);
        }

    }
    ;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(address).append(latitude).append(longitude).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Location) == false) {
            return false;
        }
        Location rhs = ((Location) other);
        return new EqualsBuilder().append(address, rhs.address).append(latitude, rhs.latitude).append(longitude, rhs.longitude).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(address);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
    }

    public int describeContents() {
        return  0;
    }

}
