package models;

public class PremiumMember extends Member{
    private int remainingGuestPasses;

    public PremiumMember(String fname, String lname, Date dob, Date expire, Location location) {
        super(fname, lname, dob, expire, location);
        remainingGuestPasses = 3;
    }

    public PremiumMember(String fname, String lname, Date dob) {
        super(fname, lname, dob);
    }

    @Override
    public double membershipFee(){
        return Constants.PREMIUM_FEE;
    }

    /**
     * Decrement the number of remaining guest passes
     * @return true if guest pass used, false if none available
     */
    @Override
    public boolean useGuestPass(){
        if(remainingGuestPasses < 1){
            return false;
        }
        remainingGuestPasses--;
        return true;
    }
}
