package adamcooper.supertask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

/**
 * Class that represents a course being taken by a student
 */

public class Course {

    //fields
    private Professor professor;
    private String building, room;
    private String name, dept, number;
    private int color;
    private ArrayList<Long> reminders;
    private ArrayList<Integer> daysHeld;
    private static final long MINUTE_MILLIS = 60000L;
    private static final long HOUR_MILLIS = 3600000L;
    private static final long DAY_MILLIS = 86400000L;
    private static enum day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }



    public Course(String name, Professor prof, int color) {
        this.name = name;
        this.professor = prof;
        this.color = color;
        reminders = new ArrayList<>();
        daysHeld = new ArrayList<>();
    }

    public Course(JSONObject j) {
        try {
            this.professor = (Professor) j.get("professor");
            this.building = j.getString("building");
            this.room = j.getString("room");
            this.name = j.getString("name");
            this.dept = j.getString("dept");
            this.number = j.getString("number");
            this.color = j.getInt("color");
            reminders = new ArrayList<>();
            daysHeld = new ArrayList<>();
            JSONArray arrayCharles = j.getJSONArray("reminders");
            for (int i = 0; i < arrayCharles.length(); i++)
                this.addReminder(arrayCharles.getLong(i));
            arrayCharles = j.getJSONArray("daysHeld");
            for (int i = 0; i < arrayCharles.length(); i++)
                daysHeld.add(arrayCharles.getInt(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public String getBuilding() {
        return this.building;
    }

    public String getRoom() {
        return this.room;
    }

    public String getDept() {
        return this.dept;
    }

    public String getNumber() {
        return this.number;
    }

    public ArrayList<Long> getReminders() {
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
    public String remindersToEnglish(long reminder) {
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
            return null;
        }
    }




    /**
     * Helper method to make things plural or not
     * @param num The number to check
     * @return "s" if num != 1, "" otherwise
     */
    private static String pluralityFixer(long num) {
        if (num == 1)
            return "s";
        return "";
    }

    public JSONObject getJSONObject() throws InputMismatchException {
        JSONObject j = new JSONObject();

        try {
            j.put("name", this.name);
            j.put("dept", this.dept);
            j.put("number", this.number);
            j.put("professor", this.professor);
            j.put("building", this.building);
            j.put("room", this.room);
            j.put("color", this.color);
            j.put("reminders", new JSONArray(this.reminders));
            j.put("daysHeld", new JSONArray(this.daysHeld));
        } catch (JSONException e) {
            throw new InputMismatchException
                    ("Trouble writing file");
        }
        return j;
    }
}
