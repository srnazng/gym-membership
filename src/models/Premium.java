package models;

import java.util.Calendar;

public class Premium extends Family {
    public Premium(String fname, String lname, Date dob, Location location) {
        super(fname, lname, dob, location);
        this.remainingGuestPasses = 3;
    }

    @Override
    public double membershipFee(){
        return Constants.PREMIUM_FEE;
    }

    @Override
    protected Date calculateExpirationDate(){
        Calendar threeMonthsLater = Calendar.getInstance();
        threeMonthsLater.add(Calendar.MONTH, Constants.PREMIUM_EXPIRATION);
        return new Date(threeMonthsLater);
    }
}
