package info.firozansari.nested_recyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by topcashback on 20/10/2017.
 */

public class TrailerObj {
    @SerializedName("youtube")
    private List<Trailer> trailerList;

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }
}
