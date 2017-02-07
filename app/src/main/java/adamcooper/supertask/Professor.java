package adamcooper.supertask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.InputMismatchException;

/**
 * Class that represents a professor and any handy information
 */

public class Professor {

    //fields
    String name, email, phoneNumber, department;

    public Professor(String name) {
        this.name = name;
    }

    public Professor(JSONObject j) {
        try {
            this.name = j.getString("name");
            this.email = j.getString("email");
            this.phoneNumber = j.getString("phoneNumer");
            this.department = j.getString("department");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public JSONObject getJSONObject() throws InputMismatchException {
        JSONObject j = new JSONObject();

        try {
            j.put("name", this.name);
            j.put("email", this.email);
            j.put("phoneNumber", this.phoneNumber);
            j.put("department", this.department);
        } catch (JSONException e) {
            throw new InputMismatchException
                    ("Trouble writing file");
        }
        return j;
    }
}
