package info.firozansari.nested_recyclerview.helper;

import com.android.internal.util.Predicate;

import info.firozansari.nested_recyclerview.model.EventDates;

/**
 * Created by firoz on 29/10/2017.
 */

public class EventDatePredicate implements Predicate<EventDates> {
    @Override
    public boolean apply(EventDates entry) {
        return !entry.getDate().isEmpty();
    }
}
