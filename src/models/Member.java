package models;

public class Member implements Comparable<Member>{
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;

    public Member(){

    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
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
