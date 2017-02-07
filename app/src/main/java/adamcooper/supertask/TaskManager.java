package adamcooper.supertask;

import java.lang.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Class to consolidate and manage all tasks
 */

public class TaskManager {

    //fields
    PriorityQueue<Task> taskList,
                        favoriteList;
    public static final int READING = 0;
    public static final int PROBLEM_SET = 1;
    public static final int PAPER = 2;
    public static final int PRESENTATION = 3;
    public static final int PROGRAM = 4;
    public static final int COMPOSITION = 5;
    public static final int MEDIA_VIEWING = 6;
    public static final int OTHER_ASSIGNMENT = 7;
    public static final int GENERIC_TASK = 8;


    /**
     * Constructor
     */
    public TaskManager() {
        taskList = new PriorityQueue<>();
        favoriteList = new PriorityQueue<>();
    }




    /**
     * Adds a new task to the task list using its time until deadline
     * @param task
     */
    public void add(Task task) {
        taskList.offer(task);
    }




    /**
     * Remove an item from the task manager
     * @param task The task to remove
     * @return True if successfully removed, false otherwise
     */
    public boolean remove(Task task) {
        if (favoriteList.contains(task))
            return favoriteList.remove(task);
        else if (taskList.contains(task))
            return taskList.remove(task);
        return false;
    }




    /**
     * Moves a task into the favorites list
     * @param task The task to move
     */
    public void addToFavorites(Task task) {
        if (taskList.contains(task))
            taskList.remove(task);
        favoriteList.offer(task);
    }



    public ArrayList<Task> orderedItems() {
        ArrayList<Task> list = new ArrayList<>(favoriteList);
        list.addAll(taskList);
        return list;
    }




    /**
     * Defines class objects based on the static fields
     * of this class. Useful for specifying types of tasks
     * @param index The value equal to the task desired
     * @return The class associated with the value given
     */
    public static java.lang.Class taskTyper(int index) {
        switch (index) { //which radio button was selected
            case READING:
                return Assignment.Reading.class;
            case PROBLEM_SET:
                return Assignment.ProblemSet.class;
            case PAPER:
                return Assignment.Paper.class;
            case PRESENTATION:
                return Assignment.Presentation.class;
            case PROGRAM:
                return Assignment.Program.class;
            case COMPOSITION:
                return Assignment.Composition.class;
            case MEDIA_VIEWING:
                return Assignment.Viewing.class;
            case OTHER_ASSIGNMENT:
                return Assignment.class;
            case GENERIC_TASK:
                return Task.class;
            default:
                throw new IllegalArgumentException("See static fields of TaskManager");
        }
    }
}
