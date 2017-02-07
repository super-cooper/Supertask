package adamcooper.supertask;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;

import java.lang.*;


/**
 * Fragment that creates the task-type chooser floating menu
 */
public class PickTaskDialogFragment extends DialogFragment {

    //fields
    public static final String TASK_TYPE_KEY = "taskType";
    int taskType = TaskManager.OTHER_ASSIGNMENT; //can't be primitive for some reason


    public PickTaskDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        String[] items = getResources().getStringArray(R.array.chooseTaskTypeOptions);
        dialogBuilder.setTitle(R.string.dialog_choose_task_title).
                setSingleChoiceItems(items, TaskManager.OTHER_ASSIGNMENT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        taskType = i;
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
                java.lang.Class task = TaskManager.taskTyper(taskType); //get the type of task
                intent.putExtra(TASK_TYPE_KEY, task);
                dismiss();
                startActivity(intent);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss(); //do nothing
            }
        });
        return dialogBuilder.create();
   }
}
