package info.firozansari.nested_recyclerview.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import info.firozansari.nested_recyclerview.R;
import info.firozansari.nested_recyclerview.adapter.EventListParentAdapter;
import info.firozansari.nested_recyclerview.helper.EventDatePredicate;
import info.firozansari.nested_recyclerview.model.EventDates;
import info.firozansari.nested_recyclerview.model.EventInformation;
import info.firozansari.nested_recyclerview.model.Events;

public class MainActivity extends AppCompatActivity {

    RecyclerView event_recycler_view_parent;
    EventListParentAdapter event_list_parent_adapter;
    ArrayList<EventDates> eventDatesArrayList;
    EventDatePredicate mPredicate = new EventDatePredicate();
    EventInformation eventInformation = new EventInformation();

    String jsonString = "{\n" +
            "    \"Id\" : \"1\",\n" +
            "    \"Name\" : \"Firoz Ansari\",\n" +
            "    \"Location\" : \"Manchester\",\n" +
            "    \"Event info\" : [ \n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Date\" : \"29-9-17\",\n" +
            "\t\t\t\t\t\t\t\"events\" : [ \n" +
            "\t\t\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventId\" : \"11\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventName\" : \"event one\"\n" +
            "\t\t\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventId\" : \"12\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventName\" : \"event two\"\n" +
            "\t\t\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"Date\" : \"30-9-17\",\n" +
            "\t\t\t\t\t\t\t\"events\" : [ \n" +
            "\t\t\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventId\" : \"3\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventName\" : \"event three\"\n" +
            "\t\t\t\t\t\t\t\t\t\t\t}, \n" +
            "\t\t\t\t\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventId\" : \"4\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\"eventName\" : \"event four\"\n" +
            "\t\t\t\t\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        try {
            //pasing jason data
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonDatesArray = jsonObject.getJSONArray("Event info");
            eventDatesArrayList = new ArrayList<>();
            for (int indexDates=0;indexDates<jsonDatesArray.length();indexDates++){
                EventDates eventDates = new EventDates();
                JSONObject jsonDateobject = jsonDatesArray.getJSONObject(indexDates);
                String date = jsonDateobject.getString("Date");
                eventDates.setDate(date);
                JSONArray jsonArrayevents = jsonDateobject.getJSONArray("events");
                ArrayList<Events> eventsArrayList = new ArrayList<>();
                for (int indexEvents=0;indexEvents<jsonArrayevents.length();indexEvents++){
                    Events events = new Events();
                    JSONObject eventObj = jsonArrayevents.getJSONObject(indexEvents);
                    events.setEventId(eventObj.getString("eventId"));
                    events.setEventName(eventObj.getString("eventName"));
                    eventsArrayList.add(events);
                }
                eventDates.setEventsArrayList(eventsArrayList);
                eventDatesArrayList.add(eventDates);
            }
            eventInformation.setEventsDatesList(eventDatesArrayList);
            Log.d("message",eventInformation.toString());
        }catch (Exception e){

        }
        //parent recyclerview
        event_recycler_view_parent = (RecyclerView) findViewById(R.id.event_recycler_view_parent);
        event_list_parent_adapter = new EventListParentAdapter(eventInformation,MainActivity.this);
        event_recycler_view_parent.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        event_recycler_view_parent.setLayoutManager(mLayoutManager);
        event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
        event_recycler_view_parent.setAdapter(event_list_parent_adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else  if (id == R.id.action_movie) {
            MoviesActivity.start(this);
            return true;
        } else if (id == R.id.action_filter){

            /*for ( int i = 0; i < eventDatesArrayList.size(); i++ ) {
                EventDates e = eventDatesArrayList.get(i);
                if ( mPredicate.apply(e) ) {
                    eventDatesArrayList.add(e);
                }
            }*/

            Consumer<EventDates> style = (EventDates e) -> Log.d("message",e.getDate());
            //eventDatesArrayList.forEach(style); calls require api level 24
            String date = "30";
            Predicate<EventDates> predicate = p -> p.getDate().contains(date) ;
            //eventDatesArrayList.removeIf(predicate); calls require api level 24

        }

        return super.onOptionsItemSelected(item);
    }



}
