/**
 * Created by pvkarthik on 2017-01-12.
 *
 * This is POJO class corresponding to server response (JSON).
 */
package com.karcompany.hotelexplorer.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Hotel implements Parcelable
{

    @SerializedName("hotelId")
    @Expose
    private long hotelId;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("summary")
    @Expose
    private Summary summary;
    public final static Creator<Hotel> CREATOR = new Creator<Hotel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Hotel createFromParcel(Parcel in) {
            Hotel instance = new Hotel();
            instance.hotelId = ((long) in.readValue((long.class.getClassLoader())));
            in.readList(instance.image, (com.karcompany.hotelexplorer.models.Image.class.getClassLoader()));
            instance.location = ((Location) in.readValue((Location.class.getClassLoader())));
            instance.summary = ((Summary) in.readValue((Summary.class.getClassLoader())));
            return instance;
        }

        public Hotel[] newArray(int size) {
            return (new Hotel[size]);
        }

    }
    ;

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hotelId).append(image).append(location).append(summary).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Hotel) == false) {
            return false;
        }
        Hotel rhs = ((Hotel) other);
        return new EqualsBuilder().append(hotelId, rhs.hotelId).append(image, rhs.image).append(location, rhs.location).append(summary, rhs.summary).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(hotelId);
        dest.writeList(image);
        dest.writeValue(location);
        dest.writeValue(summary);
    }

    public int describeContents() {
        return  0;
    }

}
