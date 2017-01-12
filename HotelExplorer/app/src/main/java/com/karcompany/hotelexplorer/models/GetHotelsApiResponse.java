
package com.karcompany.hotelexplorer.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GetHotelsApiResponse implements Parcelable
{

    @SerializedName("hotel")
    @Expose
    private List<Hotel> hotel = null;
    public final static Creator<GetHotelsApiResponse> CREATOR = new Creator<GetHotelsApiResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetHotelsApiResponse createFromParcel(Parcel in) {
            GetHotelsApiResponse instance = new GetHotelsApiResponse();
            in.readList(instance.hotel, (com.karcompany.hotelexplorer.models.Hotel.class.getClassLoader()));
            return instance;
        }

        public GetHotelsApiResponse[] newArray(int size) {
            return (new GetHotelsApiResponse[size]);
        }

    }
    ;

    public List<Hotel> getHotel() {
        return hotel;
    }

    public void setHotel(List<Hotel> hotel) {
        this.hotel = hotel;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hotel).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetHotelsApiResponse) == false) {
            return false;
        }
        GetHotelsApiResponse rhs = ((GetHotelsApiResponse) other);
        return new EqualsBuilder().append(hotel, rhs.hotel).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(hotel);
    }

    public int describeContents() {
        return  0;
    }

}
