package models;

public class MemberDatabase {
    private Member [] mlist;
    private int size;
    public static final int GROWTH_FACTOR = 4;
    public static final int NOT_FOUND = -1;
    public MemberDatabase(){
        size = 0;
        mlist = new Member [4];
    }

    /**
     * Finds index of a member in the member database
     * @param member to be found
     * @return index of the member in member list, or -1 if member doesn't exist.
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++){
            if (member.equals(mlist[i])){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grow the member list by 4
     */
    private void grow() {
        Member [] mcopy = new Member [mlist.length + GROWTH_FACTOR];
        for (int i = 0; i < mlist.length; i++) {
            mcopy[i] = mlist[i];
        }
        mlist = mcopy;
    }

    /**
     * Add a member to the database, and check if it's a valid member
     * @param member to be added
     * @return boolean representing if member is valid
     */
    public boolean add(Member member) {

        //Check if member is valid here
        

        mlist[size] = member;
        size++;

        if (size == mlist.length){
            grow();
        }

        return true;
    }
    public boolean remove(Member member) {
        return true;
    }
    public void print () { } //print the array contents as is
    public void printByCounty() { } //sort by county and then zipcode
    public void printByExpirationDate() { } //sort by the expiration date
    public void printByName() { } //sort by last name and then first name
    public boolean contains(Member member){
        if(find(member) == -1){
            return false;
        }
        return true;
    }
}
