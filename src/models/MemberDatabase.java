package models;

public class MemberDatabase {
    private Member [] mlist;
    private int size;
    public static final int GROWTH_FACTOR = 4;
    public MemberDatabase(){
        size = 0;
    }
    private int find(Member member) {
        final int NOT_FOUND = -1;

        for (int i = 0; i < size; i++){

        }
        return 0;
    }
    private void grow() { }
    public boolean add(Member member) {
        return true;
    }
    public boolean remove(Member member) {
        return true;
    }
    public void print () { } //print the array contents as is
    public void printByCounty() { } //sort by county and then zipcode
    public void printByExpirationDate() { } //sort by the expiration date
    public void printByName() { } //sort by last name and then first name
}
