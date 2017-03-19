package deputy.ttb.com.deputy.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by naveenu on 14/03/2017.
 */

public class Shifts implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("startLatitude")
    @Expose
    private String startLatitude;
    @SerializedName("startLongitude")
    @Expose
    private String startLongitude;
    @SerializedName("endLatitude")
    @Expose
    private String endLatitude;
    @SerializedName("endLongitude")
    @Expose
    private String endLongitude;
    @SerializedName("image")
    @Expose
    private String image;

    protected Shifts(Parcel in) {
        id = in.readInt();
        start = in.readString();
        end = in.readString();
        startLatitude = in.readString();
        startLongitude = in.readString();
        endLatitude = in.readString();
        endLongitude = in.readString();
        image = in.readString();
    }

    public static final Creator<Shifts> CREATOR = new Creator<Shifts>() {
        @Override
        public Shifts createFromParcel(Parcel in) {
            return new Shifts(in);
        }

        @Override
        public Shifts[] newArray(int size) {
            return new Shifts[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(startLatitude);
        dest.writeString(startLongitude);
        dest.writeString(endLatitude);
        dest.writeString(endLongitude);
        dest.writeString(image);
    }
}
