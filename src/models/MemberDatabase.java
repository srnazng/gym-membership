/**
 * Database of all members at the gym
 * @author Jackson Lee, Serena Zeng
 */
package models;

import static models.Constants.NOT_FOUND;

public class MemberDatabase {
    private Member [] mlist;
    private int size;
    private static final int GROWTH_FACTOR = 4;

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
        size--;
        return true;
    }

    public void print() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        System.out.println("\n-list of members-");
        for (int i=0; i<size; i++){
            System.out.println(mlist[i]);
        }
        System.out.println("-end of list-\n");
    } //print the array contents as is

    public void printByCounty() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        Member [] copy = mlist;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member mem1 = copy[j];
                Member mem2 = copy[j + 1];
                if (mem1.compareCounty(mem2) > 0){
                    copy[j] = mem2;
                    copy[j + 1] = mem1;
                }
            }
        }

        System.out.println("\n-list of members sorted by county and zipcode-");
        for (int i = 0; i < size; i++){
            System.out.println(copy[i]);
        }
        System.out.println("-end of list-\n");
    } //sort by county and then zipcode

    public void printByExpirationDate() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        Member[] copy = mlist;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member mem1 = copy[j];
                Member mem2 = copy[j + 1];
                if (mem1.compareDate(mem2) > 0){
                    copy[j] = mem2;
                    copy[j + 1] = mem1;
                }
            }
        }
        System.out.println("\n-list of members sorted by membership expiration date-");
        for (int i = 0; i < size; i++){
            System.out.println(copy[i]);
        }
        System.out.println("-end of list-\n");
    } //sort by the expiration date

    public void printByName() {
        if(size < 1){
            System.out.println("Member database is empty!");
            return;
        }
        Member [] copy = mlist;
        for (int i = 0; i< size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                Member mem1 = copy[j];
                Member mem2 = copy[j + 1];
                if (mem1.compareTo(mem2) > 0){
                    copy[j] = mem2;
                    copy[j + 1] = mem1;
                }
            }
        }
        System.out.println("\n-list of members sorted by last name, and first name-");
        for (int i = 0; i < size; i++){
            System.out.println(copy[i]);
        }
        System.out.println("-end of list-\n");
    } //sort by last name and then first name


    public boolean contains(Member member){
        return find(member) != NOT_FOUND;
    }

    /**
     *
     * @param member Temporary member
     * @return Complete member object from member database
     */
    public Member getMember(Member member){
        int index = find(member);
        if(index == NOT_FOUND){
            return null;
        }
        return mlist[index];
    }
}
