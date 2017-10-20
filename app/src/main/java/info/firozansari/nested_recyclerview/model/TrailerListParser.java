package info.firozansari.nested_recyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firoz on 20/10/2017.
 */

public class TrailerListParser {

    @SerializedName("trailers")
    private TrailerObj trailerObj;

    public TrailerObj getTrailers() {
        return trailerObj;
    }

    public void setTrailerObj(TrailerObj trailerObj) {
        this.trailerObj = trailerObj;
    }

    public List<Trailer> getTrailerList() {
        return this.trailerObj.getTrailerList();
    }
}
