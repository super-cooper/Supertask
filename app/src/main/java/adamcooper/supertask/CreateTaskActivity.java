package adamcooper.supertask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateTaskActivity extends AppCompatActivity {

    //fields
    java.lang.Class taskType;
    Task newTask;
    String[] tasks;
    private Integer[] colorIcons = {R.drawable.ic_stop_tomato_20dp, R.drawable.ic_stop_tangerine_20dp,
            R.drawable.ic_stop_banana_20dp, R.drawable.ic_stop_lime_20dp, R.drawable.ic_stop_basil_20dp,
            R.drawable.ic_stop_sage_20dp, R.drawable.ic_stop_peacock_20dp, R.drawable.ic_stop_blueberry_20dp,
            R.drawable.ic_stop_lavender_20dp, R.drawable.ic_stop_grape_20dp, R.drawable.ic_stop_flamingo_20dp,
            R.drawable.ic_stop_graphite_20dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        tasks = getResources().getStringArray(R.array.chooseTaskTypeOptions);
        Bundle bun = getIntent().getExtras(); //where we get our task type
        this.taskType = (java.lang.Class) bun.get(PickTaskDialogFragment.TASK_TYPE_KEY);

        String activityTitle = "New " + bun.get(PickTaskDialogFragment.TASK_NAME_KEY);

        setTitle(activityTitle);

        setUpViews();

        this.newTask = new Task();
    }




    /**
     * Sets up appropriate views based on the type of task
     */
    private void setUpViews() {
        this.setUpColorSpinner();
    }


    /**
     * Sets up the color selection spinner for this Activity
     */
    private void setUpColorSpinner() {
        Spinner colorSpinner = this.getColorSpinner();
        SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(this, colorIcons);
        colorSpinner.setAdapter(adapter);
        colorSpinner.setSelection(this.newTask.getColor()); // default selection is Peacock

        colorSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


    /**
     * Gets the color spinner used in this Activity
     * @return The color spinner used in this Activity
     */
    private Spinner getColorSpinner() {
        return (Spinner) findViewById(R.id.taskColorSpinner);
    }




    /**
     * Array adapter to populate color spinner
     */
    public class SimpleImageArrayAdapter extends ArrayAdapter<Integer> {

        //fields
        private String[] colors;

        public SimpleImageArrayAdapter(Context context, Integer[] colorIcons) {
            super(context, android.R.layout.simple_spinner_item, colorIcons);
            colors = getResources().getStringArray(R.array.colorList);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getViewForPosition(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getViewForPosition(position);
        }

        private View getViewForPosition(int position) {
            TextView textView = new TextView(getContext());
            textView.setText(colors[position]);
            //add drawable icon to the left
            textView.setCompoundDrawablesWithIntrinsicBounds(colorIcons[position], 0, 0, 0);
            textView.setLayoutParams(new AbsListView.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return textView;
        }
    }
}
