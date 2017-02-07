package adamcooper.supertask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;

/**
 * Class to represent a generic task to be completed.
 */

public class Task implements Comparable<Task> {

    //fields
    private String name, color;
    private boolean isFavorite;
    private double percentDone;
    private GregorianCalendar dueDate, dateCompleted;
    private ArrayList<Long> reminders;
    private static final long MINUTE_MILLIS = 60000L;
    private static final long HOUR_MILLIS = 3600000L;
    private static final long DAY_MILLIS = 86400000L;




    /**
     * Constructor
     * @param name The name of this task
     * @param color The name of the color associated with this task
     * @param dueDate The due date of this task
     */
    public Task(String name, String color, GregorianCalendar dueDate) {
        setName(name);
        setColor(color);
        isFavorite = false;
        setPercentDone(0.0);
        addExtension(dueDate);
        reminders = new ArrayList<>();
    }


    /**
     * Constructor
     * @param j The JSONObject from which to read
     */
    public Task(JSONObject j) {
        try {
            this.name = j.getString("name");
            this.color = j.getString("color");
            this.isFavorite = j.getBoolean("isFavorite");
            this.percentDone = j.getDouble("percentDone");
            this.dueDate = (GregorianCalendar) j.get("dueDate");
            this.dateCompleted = (GregorianCalendar) j.get("dateCompleted");
            reminders = new ArrayList<>();
            JSONArray arrayCharles = j.getJSONArray("reminders");
            for (int i = 0; i < arrayCharles.length(); i++)
                this.addReminder(arrayCharles.getLong(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    /**
     * Finds the time from now until the deadline of this task
     * @return The remaining time left to complete this task
     */
    public long timeUntilDeadline(){
        return dueDate.getTimeInMillis() - System.currentTimeMillis();
    }




    /**
     * Sets the percentage done to a new value for generic tasks
     * @param newPercent The new percent to set
     */
    public void setPercentDone(double newPercent) {
        this.percentDone = newPercent;
    }



    public boolean setName(String newName) {
        this.name = newName;
        return true;
    }




    public boolean setColor(String newColor) {
        this.color = newColor;
        return true;
    }




    /**
     * Add an extension to this task, changing the due date to a new value
     * @return True if successfully extended, false otherwise
     */
    public boolean addExtension(GregorianCalendar newDate) {
        this.dueDate = newDate;
        return true;
    }




    /**
     * Sets the favorite status of this task
     * @param newStatus The status to which to update this Task's favorite status
     */
    public void setFavoriteStatus(boolean newStatus) {
        isFavorite = newStatus;
    }




    /**
     * Tells if this task is a favorite or not
     * @return This task's favorite status
     */
    public boolean isFavorite() {
        return this.isFavorite;
    }




    /**
     * The name property of this task
     * @return This task's name
     */
    public String getName() {
        return this.name;
    }




    /**
     * Returns the class associated with an assignment. Declared here
     * to avoid conflicts
     * @return The class associated with an assignment
     */
    public Class getAssociatedClass() {
        return new Class("", new Professor(""), this.color);
    }




    /**
     * Gets the due date of this task
     * @return This task's due date
     */
    public GregorianCalendar getDueDate() {
        return this.dueDate;
    }




    /**
     * Creates a string representation of the due date
     * in the form MM/dd HH:mm A/PM
     * @return A String representation of the due date
     */
    public static String getDateAsString(GregorianCalendar dueDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM 'at' HH:mm a");
        formatter.setCalendar(dueDate);
        return formatter.format(dueDate.getTime());
    }




    /**
     * Gets the date completed of this task
     * @return This task's due date
     */
    public GregorianCalendar getDateCompleted() {
        return this.dateCompleted;
    }




    /**
     * Gets the percentage of this task already completed
     * @return Percentage of this task already completed
     */
    public double getPercentDone() {
        return this.percentDone;
    }




    /**
     * Gives the priority of this task based on weighted
     * adjustments of percent done and time until deadline
     * @return
     */
    public double getPriority() {
        return (((double) timeUntilDeadline()) * -0.6) + (getPercentDone() * -0.4);
    }




    /**
     * Compare method done using getPriority
     * @param otherTask The task to which this task is compared
     * @return 1 if this has higher priority, -1 if this has lesser priority, 0 if the same
     */
    public int compareTo(Task otherTask) {
        if (this.getPriority() > otherTask.getPriority())
            return 1;
        else if (this.getPriority() < otherTask.getPriority())
            return -1;
        else
            return 0;
    }




    /**
     * Gets this task's list of reminders
     * @return This task's reminders
     */
    public ArrayList<Long> reminders() {
        return this.reminders;
    }




    /**
     * Adds a reminder to the reminders list, keeping it sorted properly
     * @param reminder The length of the reminder to add
     */
    public void addReminder(long reminder) {
        if (reminders.contains(reminder))
            return;
        //this is inefficient because list size will be small so who cares lmao
        reminders.add(reminder);
        Collections.sort(reminders);
        Collections.reverse(reminders);
    }


    /**
     * Removes a reminder from this task's list of reminders
     * @param reminder The reminder to remove
     */
    public void removeReminder(long reminder) {
        if (!reminders.contains(reminder))
            return;
        reminders.remove(reminder);
    }




    /**
     * Turns reminders into English strings
     * @param reminder The length of the reminder
     * @return An English string, null if invalid
     */
    public static String remindersToEnglish(long reminder) throws IllegalArgumentException {
        long num;
        if (DAY_MILLIS % reminder == 0) {
            num = reminder / DAY_MILLIS;
            return num + " day" + pluralityFixer(num);
        } else if (HOUR_MILLIS % reminder == 0) {
            num = reminder / HOUR_MILLIS;
            return num + " hour" + pluralityFixer(num);
        } else if (MINUTE_MILLIS % reminder == 0) {
            num = reminder / MINUTE_MILLIS;
            return num + " minute" + pluralityFixer(num);
        } else {
            throw new IllegalArgumentException("Negatives not allowed");
        }
    }




    /**
     * Helper method to make things plural or not
     * @param num The number to check
     * @return "s" if num != 1, "" otherwise
     */
    private static String pluralityFixer(long num) {
        if (num != 1)
            return "s";
        return "";
    }




    /**
     * Formats this task as a JSON Object
     * @return This task's JSON representation
     * @throws InputMismatchException If trouble creating object
     */
    public JSONObject getJSONObject() throws InputMismatchException {
        JSONObject j = new JSONObject();
        try {
            j.put("name", this.name);
            j.put("color", this.color);
            j.put("isFavorite", this.isFavorite);
            j.put("percentDone", this.getPercentDone());
            j.put("dueDate", this.getDueDate());
            j.put("reminders", new JSONArray(reminders));
            j.put("dateCompleted", this.getDateCompleted());
        } catch (JSONException e) {
            throw new InputMismatchException
                    ("Trouble writing file");
        }
        return j;
    }
}
