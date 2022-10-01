/**
 * @author Jackson Lee, Serena Zeng
 */
package models;

public class MemberDatabase {
    private Member [] mlist;
    private int size;
    public static final int GROWTH_FACTOR = 4;
    public static final int NOT_FOUND = -1;
    public MemberDatabase(){
        size = 0;
        mlist = new Member [GROWTH_FACTOR];
    }

    /**
     * Finds index of a member in the member database
     * @param member to be found
     * @return index of the member in member list, or -1 if member doesn't exist.
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++){
            if (member.equals(mlist[i])) return i;
        }
        return NOT_FOUND;
    }

    /**
     * Grow the member list by GROWTH_FACTOR
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
     *
     * @param member to be removed
     * @return false if member does not exist
     */
    public boolean remove(Member member) {
        if (!contains(member)) return false;
        int mindex = find(member);

        for (int i = mindex; i < size; i++){
            mlist[i] = mlist[i + 1];
        }
        return true;
    }

    public void print () {
        System.out.println("-list of members-");
        for (Member member : mlist){
            System.out.println(member);
        }
        System.out.println("-end of list-");
    } //print the array contents as is

    public void printByCounty() {
        Member [] copy = mlist;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member memi = copy[i];
                Member memj = copy[j];
                if (memi.compareCounty(memj) > 0){
                    copy[i] = memj;
                    copy[j] = memi;
                }
            }
        }

        System.out.println("-list of members sorted by county and zipcode-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-");
    } //sort by county and then zipcode

    public void printByExpirationDate() {
        Member[] copy = mlist;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member memi = copy[i];
                Member memj = copy[j];
                if (memi.compareDate(memj) > 0){
                    copy[i] = memj;
                    copy[j] = memi;
                }
            }
        }

        System.out.println("-list of members sorted by membership expiration date-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-");
    } //sort by the expiration date

    public void printByName() {
        Member [] copy = mlist;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member memi = copy[i];
                Member memj = copy[j];
                if (memi.compareTo(memj) > 0){
                    copy[i] = memj;
                    copy[j] = memi;
                }
            }
        }

        System.out.println("-list of members sorted by last name, and first name-");
        for (int i = 0; i < size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-");
    } //sort by last name and then first name


    public boolean contains(Member member){
        return find(member) != NOT_FOUND;
    }
}
