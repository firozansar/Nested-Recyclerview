package info.firozansari.nested_recyclerview.helper;

import com.android.internal.util.Predicate;
import com.datonicgroup.narrate.app.models.Entry;

import info.firozansari.nested_recyclerview.model.Events;

/**
 * Created by timothymiko on 12/28/14.
 */
public class EntryDateRangePredicate implements Predicate<Events> {

    private long start;
    private long end;


    public EntryDateRangePredicate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean apply(Events entry) {
        return (entry.creationDate.getTimeInMillis() >= start) && (entry.creationDate.getTimeInMillis() <= end);
    }
}
