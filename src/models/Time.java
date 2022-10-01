/**
 * @author Jackson Lee, Serena Zeng
 */
package models;

public enum Time {
    MORNING("9:30"),
    AFTERNOON("14:00");

    private final String time;

    Time(String time){
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
