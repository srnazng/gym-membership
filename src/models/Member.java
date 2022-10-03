package models;

/**
 * The Member class represents a member of the gym and is comparable based on name.
 * Each Member object contains member information including first name, last name, date of
 * birth, membership expiration date, and location. A member is uniquely identified by
 * first name, last name, and date of birth. This class also offers other methods of comparison
 * including by expiration date and by county/zipcode.
 * @author Jackson Lee, Serena Zeng
 */
public class Member implements Comparable<Member>{
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;

    /**
     * Creates a member object with all fields completed
     * @param fname     first name
     * @param lname     last name
     * @param dob       date of birth
     * @param expire    expiration date
     * @param location  location (county, city, zipcode)
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * Creates a member object with the minimally required fields
     * @param fname     first name
     * @param lname     last name
     * @param dob       date of birth
     */
    public Member(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = null;
        this.location = null;
    }

    /**
     * Get the expiration date of the Member
     * @return expiration date as a Date object
     */
    public Date getExpire(){
        return expire;
    }

    /**
     * Represent current member as string
     * @return String representing member
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
     * Checks if a given object is equal to the Member
     * Members are considered equal if the two first names, last names and DOBs are equal
     * @param obj   Member object being compared
     * @return true if obj is equal to member, false otherwise
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
     * Compares current member with another member based on first and last name
     * @param member Other Member object being compared to
     * @return negative if this member comes before, 0 if equal, positive otherwise
     */
    @Override
    public int compareTo(Member member) {
        int last_compare = lname.compareToIgnoreCase(member.lname);
        if(last_compare != 0){
            return last_compare;
        }
        return fname.compareToIgnoreCase(member.fname);
    }

    /**
     * Compares current member with another member based on location (sorted by county then zip code)
     * @param member    Other Member object being compared to
     * @return negative if this member comes before, 0 if same location, positive otherwise
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
     * Compare current member with another member based on the expiration date of their membership
     * @param member    Other Member object being compared to
     * @return negative if this member comes before, 0 if same expiration date, positive otherwise
     */
    public int compareExpiration(Member member){
        return expire.compareTo(member.expire);
    }

    public static void main(String[] args){
        Member member1 = new Member("A", "b", new Date("1/1/2022"));
        Member member2 = new Member("A", "A", new Date("1/1/2022"));
        System.out.println(member1.compareTo(member2));
    }
}
