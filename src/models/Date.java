package models;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    private int year;
    private int month; // 1 to 12

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;

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
     * Compare current date object with another date
     * @param date the object to be compared.
     * @return -1 if date is less
     */
    @Override
    public int compareTo(Date date) {
        if(year < date.getYear()) { return -1;}
        if(year > date.getYear()){ return 1; }

        // same year
        if(month < date.getMonth()){ return -1; }
        if(month > date.getMonth()){ return 1; }

        // same month
        if(day < date.getDay()){ return -1; }
        if(day > date.getDay()){ return 1; }

        // same day
        return 0;
    }

    /** check if a date is a valid calendar date
     * Check if the current date object is valid
     * @return  Whether the date is valid
     */
    public boolean isValid() {
        // check valid year
        if(year < 0 || month < 1 || month > 12 || day < 1){
            return false;
        }

        if((month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12)
                && day > 30){
            return false;
        }

        if((month == 4 || month == 6 || month == 9 || month == 11)
                && day > 31){
            return false;
        }

        // check leap years
        if(month == 2){
            if(day == 29 && !isLeapYear()
                || day > 29){
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether current date is in a leap year
     * @return  Whether current year is a leap year
     */
    private boolean isLeapYear(){
        if(year % QUADRENNIAL == 0){
            if(year % CENTENNIAL == 0){
                if(year % QUARTERCENTENNIAL == 0){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    // Testbed main
    public static void main(String[] args){
        Date day = new Date();
        System.out.println(day.getMonth() + "/" + day.getDay() + "/" + day.getYear());
    }
}