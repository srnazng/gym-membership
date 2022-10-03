/**
 * The Member class represents a member of the gym and is comparable based on name.
 * Each Member object contains member information including first name, last name, date of
 * birth, membership expiration date, and location. A member is uniquely identified by
 * first name, last name, and date of birth. This class also offers other methods of comparison
 * including by expiration date and by county/zipcode.
 * @author Jackson Lee, Serena Zeng
 */
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

    public Member(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = null;
        this.location = null;
    }

    /**
     * Represent current member as string
     * @return string representing member
     */
    @Override
    public String toString() {
        if(expire.isPast()){
            return fname + " " + lname + ", DOB: " + dob.toString() +
                    ", Membership expired " + expire.toString() +
                    ", Location: " + location;
        }
        return fname + " " + lname + ", DOB: " + dob.toString() +
                ", Membership expires " + expire.toString() +
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
        return fname.equalsIgnoreCase(member.fname)
                && lname.equalsIgnoreCase(member.lname)
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
        int last_compare = lname.compareToIgnoreCase(member.lname);
        if(last_compare != 0){
            return last_compare;
        }
        return fname.compareTo(member.fname);
    }

    /**
     *
     * @param member
     * @return positive if member comes after other member when sorted by county and zip code
     */
    public int compareCounty(Member member){
        // compare county
        String county1 = location.getCounty();
        String county2 = member.location.getCounty();
        int countComp = county1.compareToIgnoreCase(county2);
        if (countComp != 0) {
            return countComp;
        }
        // compare zip code
        String zip1 = location.getZipCode();
        String zip2 = member.location.getZipCode();
        return zip1.compareToIgnoreCase(zip2);
    }

    /**
     *
     * @param member
     * @return 1 if member comes after other member when sorted by date
     */
    public int compareDate(Member member){
        return expire.compareTo(member.expire);
    }

    public static void main(String[] args){

    }
}
