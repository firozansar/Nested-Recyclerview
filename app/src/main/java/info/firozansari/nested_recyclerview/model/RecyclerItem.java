package info.firozansari.nested_recyclerview.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Firoz
 * Common interface for both type of items which are using in current project
 */
public interface RecyclerItem extends Serializable {
    List<Integer> getGenreIds();
}
