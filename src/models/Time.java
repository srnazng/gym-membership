package models;

/**
 * The Time enum represents the two times at which
 * fitness classes are offered.
 * @author Jackson Lee, Serena Zeng
 */
public enum Time {
    MORNING("9:30"),
    AFTERNOON("14:00");

    private final String time;

    /**
     * Create a Time object given the time of day
     * @param time  Time of day as HH:MM
     */
    Time(String time){
        this.time = time;
    }

    /**
     * Formats the time as HH:MM
     * @return  the time value as a String
     */
    public String getTime() { return time; }
}
