package models;

import static models.Constants.NOT_FOUND;

/**
 * The MemberDatabase class contains a list of all the members of the gym,
 * stored in an array of Member objects. This class also includes methods
 * to print the members based on different sorted fields.
 * @author Jackson Lee, Serena Zeng
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;
    private static final int GROWTH_FACTOR = 4;

    /**
     * Create new MemberDatabase object with size 0
     */
    public MemberDatabase(){
        size = 0;
        mlist = new Member [GROWTH_FACTOR];
    }

    /**
     * Finds index of a member in the member database
     * @param member Target Member object
     * @return Index of the member in member list, or -1 if member doesn't exist.
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++){
            if (member.equals(mlist[i])) return i;
        }
        return NOT_FOUND;
    }

    /**
     * Grow the member list mlist by GROWTH_FACTOR
     */
    private void grow() {
        Member [] mcopy = new Member [mlist.length + GROWTH_FACTOR];
        for (int i = 0; i < mlist.length; i++) {
            mcopy[i] = mlist[i];
        }
        mlist = mcopy;
    }

    /**
     * Add a member to the database, and check if it's already in the database
     * @param member to be added
     * @return false if member already exists in database
     */
    public boolean add(Member member) {
        if (contains(member)) return false;
        mlist[size] = member;
        size++;
        if (size == mlist.length) grow();
        return true;
    }

    /**
     * Remove a member from the gym database
     * @param member Member to be removed
     * @return false if member does not exist, otherwise true
     */
    public boolean remove(Member member) {
        if (!contains(member)) return false;
        int mindex = find(member);

        for (int i = mindex; i < size; i++){
            mlist[i] = mlist[i + 1];
        }
        size--;
        return true;
    }

    /**
     * If there are no members, a message indicating database is empty is printed.
     * Otherwise, a list of members is printed without sorting.
     */
    public void print() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        System.out.println("\n-list of members-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-\n");
    }

    /**
     * If there are no members, a message indicating database is empty is printed.
     * Otherwise, print the list of members sorted in place by county, then zip code.
     */
    public void printByLocation() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member mem1 = mlist[j];
                Member mem2 = mlist[j + 1];
                if (mem1.compareCounty(mem2) > 0){
                    mlist[j] = mem2;
                    mlist[j + 1] = mem1;
                }
            }
        }

        System.out.println("\n-list of members sorted by county and zipcode-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-\n");
    }

    /**
     * If there are no members, a message indicating database is empty is printed.
     * Otherwise, print the list of members sorted in place by expiration date of membership.
     */
    public void printByExpirationDate() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member mem1 = mlist[j];
                Member mem2 = mlist[j + 1];
                if (mem1.compareExpiration(mem2) > 0){
                    mlist[j] = mem2;
                    mlist[j + 1] = mem1;
                }
            }
        }
        System.out.println("\n-list of members sorted by membership expiration date-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-\n");
    }

    /**
     * If there are no members, a message indicating database is empty is printed.
     * Otherwise, print the list of members sorted in place by last name, then first name.
     */
    public void printByName() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member mem1 = mlist[j];
                Member mem2 = mlist[j + 1];
                if (mem1.compareTo(mem2) > 0){
                    mlist[j] = mem2;
                    mlist[j + 1] = mem1;
                }
            }
        }
        System.out.println("\n-list of members sorted by last name, and first name-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-\n");
    }

    /**
     * Check whether the member database contains the given member
     * @param member Member object being checked for
     * @return true if member in database, otherwise false
     */
    public boolean contains(Member member){
        return find(member) != NOT_FOUND;
    }

    /**
     * Gets the Member object from the member database that is equivalent
     * to the given Member object (typically does not contain all fields)
     * @param member Target Member object
     * @return Member object from member database with no null fields
     */
    public Member getMember(Member member){
        int index = find(member);
        if(index == NOT_FOUND){
            return null;
        }
        return mlist[index];
    }
}
