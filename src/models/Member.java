package models;

public class Member implements Comparable<Member>{
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;

    public Member(String fname, String lname, Date dob, Date expire, Location location){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * Represent current member as string
     * @return string representing member
     */
    @Override
    public String toString() {
        return fname + " " + lname + ", DOB: " + dob +
                ", Membership expires " + expire +
                ", Location: " + location;
    }

    /**
     * ereturns true if the two first names, last names and dates of birth are equal.
     * @param obj
     * @return boolean representing if this member is equal to other member
     */
    @Override
    public boolean equals(Object obj) {
        Member member = (Member) obj;

        return fname.toLowerCase().equals(member.fname.toLowerCase())
                && lname.toLowerCase().equals(member.lname.toLowerCase())
                && dob.getYear() == member.dob.getYear()
                && dob.getMonth() == member.dob.getMonth()
                && dob.getDay() == member.dob.getDay();
    }

    /**
     *
     * @param member the object to be compared.
     * @return negative if member is before, 0 if equal, positive if after
     */
    @Override
    public int compareTo(Member member) {
        int last_compare = lname.compareTo(member.lname);
        if(last_compare != 0){
            return last_compare;
        }
        return fname.compareTo(member.fname);
    }
    public static void main(String[] args){

    }
}
