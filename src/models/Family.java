package models;

public class Family extends Member {
    protected int remainingGuestPasses;

    public Family(String fname, String lname, Date dob, Location location) {
        super(fname, lname, dob, location);
        this.remainingGuestPasses = 1;
    }

    @Override
    public double membershipFee(){
        return Constants.FAMILY_FEE;
    }

    public boolean useGuestPass(){
        if(remainingGuestPasses < 1){
            return false;
        }
        remainingGuestPasses--;
        return true;
    }

    public void incrementGuestPass(){
        remainingGuestPasses++;
    }

    public boolean hasGuestPass(){
        return remainingGuestPasses > 0;
    }

    @Override
    public String toString(){
        return super.toString() + remainingGuestPasses;
    }
}
