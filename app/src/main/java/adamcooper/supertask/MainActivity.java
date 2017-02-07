package adamcooper.supertask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //fields
    CardAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("To-Do List");
        if (savedInstanceState == null)
            getFragmentManager().beginTransaction().
                    add(R.id.container, new PickTaskDialogFragment()).commit();
        setUpRecyclerView();
    }




    /**
     * Sets up recycler view
     */
    public void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //avoids calculating layout dimensions
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CardAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
    }




    /**
     * Tells what to do when the FAB is clicked
     * Opens dialog to create a new task
     */
    public void fabClick(View view) {
        FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");

        if (prev != null) {
            fragTrans.remove(prev);
        }
        fragTrans.addToBackStack(null);

        PickTaskDialogFragment df = new PickTaskDialogFragment();
        df.show(fragTrans, "dialog");
    }
}
