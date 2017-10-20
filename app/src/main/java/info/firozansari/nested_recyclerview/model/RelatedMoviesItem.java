package info.firozansari.nested_recyclerview.model;

import java.util.List;

/**
 * @author Firoz
 */
public class RelatedMoviesItem implements RecyclerItem {

    private List<Movie> relatedMovieList;

    public List<Movie> getRelatedMovieList() {
        return relatedMovieList;
    }

    public void setRelatedMovieList(List<Movie> relatedMovieList) {
        this.relatedMovieList = relatedMovieList;
    }

    @Override
    public List<Integer> getGenreIds() {
        return null;
    }
}
