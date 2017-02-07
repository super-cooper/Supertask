package adamcooper.supertask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.InputMismatchException;

/**
 * Class to represent an exam
 */

public class Exam extends Assignment {

    //fields
    boolean isFinal;
    long amtTime;
    String building, room;




    /**
     * Constructor
     * @param associatedClass The class giving this exam
     * @param isFinal Is this exam a final?
     * @param date The date of this exam
     */
    public Exam(Class associatedClass, boolean isFinal, GregorianCalendar date) {
        super(associatedClass.getName() + (isFinal ? " Final" : " Exam"),
                associatedClass.getColor(), date, associatedClass);
        this.isFinal = isFinal;
        amtTime = 0L;
        building = "";
        room = "";
    }




    /**
     * Constructor
     * @param j The JSONObject to read from
     */
    public Exam(JSONObject j) {
        super(j);
        try {
            this.isFinal = j.getBoolean("isFinal");
            amtTime = j.getLong("amtTime");
            building = j.getString("building");
            room = j.getString("room");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isFinalExam() {
        return this.isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public long getAmtTime() {
        return this.amtTime;
    }

    public void setAmtTime(long time) {
        this.amtTime = time;
    }

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }




    @Override
    public JSONObject getJSONObject() throws InputMismatchException {
        JSONObject j = super.getJSONObject();
        try {
            j.put("isFinal", isFinal);
            j.put("amtTime", amtTime);
            j.put("building", building);
            j.put("room", room);
        } catch (JSONException e) {
            throw new InputMismatchException
                    ("Trouble writing file");
        }
        return j;
    }
}
