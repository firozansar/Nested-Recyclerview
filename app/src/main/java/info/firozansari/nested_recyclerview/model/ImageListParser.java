package info.firozansari.nested_recyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Firoz
 */
public class ImageListParser {

    @SerializedName("id")
    private long id;

    @SerializedName("backdrops")
    private List<Backdrop> backdrops;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

}
