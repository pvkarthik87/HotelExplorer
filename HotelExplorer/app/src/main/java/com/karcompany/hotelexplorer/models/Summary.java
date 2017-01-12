
package com.karcompany.hotelexplorer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Summary implements Parcelable
{

    @SerializedName("highRate")
    @Expose
    private double highRate;
    @SerializedName("hotelName")
    @Expose
    private String hotelName;
    @SerializedName("lowRate")
    @Expose
    private double lowRate;
    public final static Creator<Summary> CREATOR = new Creator<Summary>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Summary createFromParcel(Parcel in) {
            Summary instance = new Summary();
            instance.highRate = ((double) in.readValue((double.class.getClassLoader())));
            instance.hotelName = ((String) in.readValue((String.class.getClassLoader())));
            instance.lowRate = ((double) in.readValue((double.class.getClassLoader())));
            return instance;
        }

        public Summary[] newArray(int size) {
            return (new Summary[size]);
        }

    }
    ;

    public double getHighRate() {
        return highRate;
    }

    public void setHighRate(double highRate) {
        this.highRate = highRate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public double getLowRate() {
        return lowRate;
    }

    public void setLowRate(double lowRate) {
        this.lowRate = lowRate;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(highRate).append(hotelName).append(lowRate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Summary) == false) {
            return false;
        }
        Summary rhs = ((Summary) other);
        return new EqualsBuilder().append(highRate, rhs.highRate).append(hotelName, rhs.hotelName).append(lowRate, rhs.lowRate).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(highRate);
        dest.writeValue(hotelName);
        dest.writeValue(lowRate);
    }

    public int describeContents() {
        return  0;
    }

}
