package models;

public class FamilyMember extends Member {
    private boolean hasGuestPass;

    public FamilyMember(String fname, String lname, Date dob, Date expire, Location location) {
        super(fname, lname, dob, expire, location);
        this.hasGuestPass = true;
    }

    public FamilyMember(String fname, String lname, Date dob) {
        super(fname, lname, dob);
        this.hasGuestPass = true;
    }

    @Override
    public double membershipFee(){
        return Constants.FAMILY_FEE;
    }

    @Override
    public boolean useGuestPass(){
        if(hasGuestPass){
            hasGuestPass = false;
            return true;
        }
        return false;
    }
}
