package models;

import java.util.Calendar;

public class Premium extends Family {
    /**
     * Create a new instance of a Premium plan member
     * @param fname     first name
     * @param lname     last name
     * @param dob       date of birth
     * @param location  location of gym
     */
    public Premium(String fname, String lname, Date dob, Location location) {
        super(fname, lname, dob, location);
        this.remainingGuestPasses = 3;
    }

    @Override
    public double membershipFee(){
        return Constants.PREMIUM_FEE;
    }

    /**
     * Determine the expiration date based on today's date
     * @return  Date object of expiration date
     */
    @Override
    protected Date calculateExpirationDate(){
        Calendar threeMonthsLater = Calendar.getInstance();
        threeMonthsLater.add(Calendar.MONTH, Constants.PREMIUM_EXPIRATION);
        return new Date(threeMonthsLater);
    }
}
