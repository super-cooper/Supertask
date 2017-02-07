package adamcooper.supertask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.InputMismatchException;

/**
 * Class to represent a homework assignment
 */

public class Assignment extends Task {

    //fields
    Class associatedClass;


    /**
     * Constructor
     *
     * @param name            The name of this assignment
     * @param color           The color of this assignment
     * @param dueDate         The due date of this assignment
     * @param associatedClass The class associated with this assignment
     */
    public Assignment(String name, String color, GregorianCalendar dueDate, Class associatedClass) {
        super(name, color, dueDate);
        this.associatedClass = associatedClass;
    }


    /**
     * Constructor
     *
     * @param j The JSON Object from which to read
     */
    public Assignment(JSONObject j) {
        super(j);
        try {
            associatedClass = (Class) j.get("associatedClass");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Changes class, used for editing
     *
     * @param newClass The class to change to
     * @return True if class successfully changed
     */
    public boolean setClass(Class newClass) {
        this.associatedClass = newClass;
        setColor(newClass.getColor());
        return true;
    }


    @Override
    public Class getAssociatedClass() {
        return this.associatedClass;
    }


    @Override
    public JSONObject getJSONObject() throws InputMismatchException {
        JSONObject j = super.getJSONObject();

        try {
            j.put("associatedClass", associatedClass);
        } catch (JSONException e) {
            throw new InputMismatchException
                    ("Trouble writing file");
        }
        return j;
    }


    /**
     * Class to represent a reading assignment
     */
    class Reading extends Assignment {

        //fields
        Material material;
        float pagesToRead;
        float pagesRead;


        /**
         * Constructor
         *
         * @param name            The name of this assignment
         * @param color           The color of this assignment
         * @param dueDate         The due date of this assignment
         * @param associatedClass The class associated with this assignment
         * @param material        The material to read
         */
        public Reading(String name, String color, GregorianCalendar dueDate, Class associatedClass, Material material, int pagesToRead) {
            super(name, color, dueDate, associatedClass);
            this.material = material;
            this.pagesToRead = pagesToRead;
            pagesRead = 0;
        }


        /**
         * Constructor
         *
         * @param j The JSONObject from which to read
         */
        public Reading(JSONObject j) {
            super(j);
            try {
                material = (Material) j.get("material");
                pagesRead = (float) j.get("pagesRead");
                pagesToRead = (float) j.get("pagesToRead");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public Material getMaterial() {
            return this.material;
        }


        public float getPagesToRead() {
            return this.pagesToRead;
        }


        public float getPagesRead() {
            return this.pagesRead;
        }


        @Override
        public double getPercentDone() {
            return (double) this.pagesToRead / (double) pagesRead;
        }


        /**
         * Adds pages to the current amount read
         *
         * @param pages The amount of pages just read
         * @return True if added successfully
         */
        public boolean addPagesRead(float pages) {
            this.pagesRead += pages;
            material.pagesRead += pages;
            delimitPages();
            return true;
        }


        /**
         * Sets the pages read to a new value
         *
         * @param pages The new value of pages
         * @return True if set successfully
         */
        public boolean setPagesRead(float pages) {
            if (pages > this.pagesRead)
                material.pagesRead += pages;
            else if (pages < this.pagesRead)
                material.pagesRead -= pages;
            this.pagesRead = pages;
            delimitPages();
            return true;
        }


        /**
         * Makes sure the desired pages does not go over the maximum amount
         */
        private void delimitPages() {
            if (this.pagesRead > material.totalPages) {
                this.pagesRead = material.totalPages;
                material.pagesRead = material.totalPages;
            }
        }


        @Override
        public JSONObject getJSONObject() throws InputMismatchException {
            JSONObject j = super.getJSONObject();

            try {
                j.put("material", this.material);
                j.put("pagesRead", this.pagesRead);
                j.put("pagesToRead", this.pagesToRead);
            } catch (JSONException e) {
                throw new InputMismatchException
                        ("Trouble writing file");
            }
            return j;
        }


        /**
         * Class to represent reading material
         */
        class Material {

            //fields
            String name;
            int totalPages;
            int pagesRead;

            public Material(String name, int totalPages) {
                this.name = name;
                this.totalPages = totalPages;
                this.pagesRead = 0;
            }

            public Material(JSONObject j) {
                try {
                    this.name = j.getString("name");
                    this.totalPages = j.getInt("totalPages");
                    this.pagesRead = j.getInt("pagesRead");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public JSONObject getJSONObject() throws InputMismatchException {
                JSONObject j = new JSONObject();
                try {
                    j.put("name", this.name);
                    j.put("totalPages", this.totalPages);
                    j.put("pagesRead", this.pagesRead);
                } catch (JSONException e) {
                    throw new InputMismatchException
                            ("Trouble writing file");
                }
                return j;
            }
        }
    }


    /**
     * Class to represent a problem set
     */

    class ProblemSet extends Assignment {


        //fields
        int problemsToComplete;
        int problemsCompleted;

        public ProblemSet(String name, String color, GregorianCalendar dueDate, Class associatedClass, int problemsToComplete) {
            super(name, color, dueDate, associatedClass);
            this.problemsToComplete = problemsToComplete;
            this.problemsCompleted = 0;
        }


        public ProblemSet(JSONObject j) {
            super(j);
            try {
                problemsToComplete = j.getInt("problemsToComplete");
                problemsCompleted = j.getInt("problemsCompleted");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        /**
         * Adds more problems to the set of completed problems
         *
         * @param problemsDone The amount of problems to add to the amount done
         * @return True if successfully added
         */
        public boolean addProblemsDone(int problemsDone) {
            problemsCompleted += problemsDone;
            delimitProblems();
            return true;
        }


        /**
         * Sets the completed problems to a new value
         *
         * @param problemsDone The new amount of problems completed
         * @return True is successfully set
         */
        public boolean setProblemsDone(int problemsDone) {
            problemsCompleted = problemsDone;
            delimitProblems();
            return true;
        }


        /**
         * Pads problems completed down to a possible amount
         */
        private void delimitProblems() {
            if (problemsCompleted > problemsToComplete)
                problemsCompleted = problemsToComplete;
        }


        public int getProblemsToComplete() {
            return this.problemsToComplete;
        }


        public int getProblemsCompleted() {
            return this.problemsCompleted;
        }


        @Override
        public double getPercentDone() {
            return (double) problemsCompleted / (double) problemsToComplete;
        }


        @Override
        public JSONObject getJSONObject() throws InputMismatchException {
            JSONObject j = super.getJSONObject();
            try {
                j.put("problemsToComplete", problemsToComplete);
                j.put("problemsCompleted", problemsCompleted);
            } catch (JSONException e) {
                throw new InputMismatchException
                        ("Trouble writing file");
            }
            return j;
        }
    }


    /**
     * Class to represent a paper
     */
    class Paper extends Assignment {

        //fields
        int amtToWrite;
        float amtWritten;
        boolean assignedPages;


        /**
         * Constructor
         *
         * @param name            The name of this Paper
         * @param color           The color of this paper
         * @param dueDate         The due date of this paper
         * @param associatedClass The class associated with this paper
         * @param assignedPages   Switch for whether the required length of the paper is dependent on
         *                        pages or words
         * @param amtToWrite      The number of pages or words required to write
         */
        public Paper(String name, String color, GregorianCalendar dueDate, Class associatedClass, boolean assignedPages, int amtToWrite) {
            super(name, color, dueDate, associatedClass);
            this.assignedPages = assignedPages;
            this.amtToWrite = amtToWrite;
            amtWritten = 0;
        }


        /**
         * Constructor
         *
         * @param j The JSON Object from which to read
         */
        public Paper(JSONObject j) {
            super(j);
            try {
                amtToWrite = j.getInt("amtToWrite");
                amtWritten = (float) j.get("amtWritten");
                assignedPages = j.getBoolean("assignedPages");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        /**
         * Add pages to the amount written
         *
         * @param amtPages The pages to add
         * @return True if added successfully
         */
        public boolean addPages(float amtPages) {
            amtWritten += amtPages;
            return true;
        }


        /**
         * Set the amount of pages written to a number
         *
         * @param amtPages The amount of pages written
         * @return True if successfully set
         */
        public boolean setPagesWritten(float amtPages) {
            amtWritten = amtPages;
            return true;
        }


        public float getPagesWritten() {
            return this.amtWritten;
        }

        public int getAmtToWrite() {
            return this.amtToWrite;
        }

        public boolean isAssignedPages() {
            return this.assignedPages;
        }




        @Override
        public JSONObject getJSONObject() {
            JSONObject j = super.getJSONObject();
            try {
                j.put("amtWritten", amtWritten);
                j.put("amtToWrite", amtToWrite);
                j.put("assignedPages", assignedPages);
            } catch (JSONException e) {
                throw new InputMismatchException
                        ("Trouble writing file");
            }
            return j;
        }
    }


    /**
     * Class to represent a standard research assignment
     */
    class Presentation extends Assignment {
        public Presentation(String name, String color, GregorianCalendar dueDate, Class associatedClass) {
            super(name, color, dueDate, associatedClass);
        }

        public Presentation(JSONObject j) {
            super(j);
        }

        @Override
        public JSONObject getJSONObject() {
            return super.getJSONObject();
        }
    }


    /**
     * Class to represent a programming assignment
     */
    class Program extends Assignment {
        public Program(String name, String color, GregorianCalendar dueDate, Class associatedClass) {
            super(name, color, dueDate, associatedClass);
        }

        public Program(JSONObject j) {
            super(j);
        }

        @Override
        public JSONObject getJSONObject() {
            return super.getJSONObject();
        }
    }


    /**
     * Class to represent a composition for music or art
     */
    class Composition extends Assignment {
        public Composition(String name, String color, GregorianCalendar dueDate, Class associatedClass) {
            super(name, color, dueDate, associatedClass);
        }

        public Composition(JSONObject j) {
            super(j);
        }

        @Override
        public JSONObject getJSONObject() {
            return super.getJSONObject();
        }
    }


    /**
     * Class to represent the viewing of a video or a movie
     */
    class Viewing extends Assignment {

        //fields
        int minutesToWatch;
        int minutesWatched;

        public Viewing(String name, String color, GregorianCalendar dueDate, Class associatedClass, int minutesToWatch) {
            super(name, color, dueDate, associatedClass);
            setMinutesToWatch(minutesToWatch);
            minutesWatched = 0;
        }

        public Viewing(JSONObject j) {
            super(j);
            try {
                minutesToWatch = j.getInt("minutesToWatch");
                minutesWatched = j.getInt("minutesWatched");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void setMinutesWatched(int minutesWatched) {
            this.minutesWatched = minutesWatched;
            delimitMinutes();
        }

        public int getMinutesWatched() {
            return minutesWatched;
        }

        public void setMinutesToWatch(int minutesToWatch) {
            this.minutesToWatch = minutesToWatch;
        }

        public int getMinutesToWatch() {
            return getMinutesToWatch();
        }

        private void delimitMinutes() {
            if (this.minutesWatched > this.minutesToWatch)
                this.minutesWatched = this.minutesToWatch;
        }

        @Override
        public double getPercentDone() {
            return (double) minutesWatched / (double) minutesToWatch;
        }

        @Override
        public JSONObject getJSONObject() throws InputMismatchException {
            JSONObject j = super.getJSONObject();
            try {
                j.put("minutesWatched", minutesWatched);
                j.put("minutesToWatch", minutesToWatch);
            } catch (JSONException e) {
                throw new InputMismatchException
                        ("Trouble writing file");
            }
            return j;
        }
    }
}
