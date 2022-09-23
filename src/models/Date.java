package models;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    private int year;
    private int month;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    private int day;
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH);
        this.day = today.get(Calendar.DAY_OF_MONTH);
    } //create an object with today’s date (see Calendar class)

    /**
     *
     * @param date  take “mm/dd/yyyy” and create a Date object
     */
    public Date(String date) {
        String[] dateParts = date.split("/", 0);
        if(dateParts.length < 3){
            return;
        }
        this.year = Integer.parseInt(dateParts[2]);
        this.month = Integer.parseInt(dateParts[0]);
        this.day = Integer.parseInt(dateParts[1]);
    }

    /**
     *
     * @param date the object to be compared.
     * @return -1 if date is less
     */
    @Override
    public int compareTo(Date date) {
        if(year < date.getYear()) {
            return -1;
        }

        return 0;
    }
    public boolean isValid() {
        return false;
    } //check if a date is a valid calendar date
}