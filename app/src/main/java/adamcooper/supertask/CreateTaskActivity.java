package adamcooper.supertask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateTaskActivity extends AppCompatActivity {

    //fields
    java.lang.Class taskType;
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

        setUpViews();
    }




    /**
     * Sets up appropriate views based on the type of task
     */
    private void setUpViews() {
        Spinner colorSpinner = (Spinner) findViewById(R.id.taskColorSpinner);
        SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(this, colorIcons);
        colorSpinner.setAdapter(adapter);
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
