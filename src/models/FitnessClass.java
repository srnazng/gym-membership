/**
 * @author Jackson Lee, Serena Zeng
 */
package models;

public class FitnessClass {
    private String name;
    private String instructor;
    private Time time;
    private Member[] checkedInMembers;
    private int checkedInNum;

    public static final int GROWTH_FACTOR = 50;
    public static final int NOT_FOUND = -1;

    FitnessClass(String name, String instructor, Time time){
        this.name = name;
        this.instructor = instructor;
        this.time = time;
        checkedInMembers = new Member[GROWTH_FACTOR];
    }

    @Override
    public String toString() {
        return name + " - " + instructor.toUpperCase() + " " +
                time.getTime() + printMembers();
    }

    private String printMembers(){
        String toReturn = "";
        if(checkedInNum > 0){
            toReturn = "\n\t**participants**\n";
        }
        else{
            return "";
        }

        for(int i=0; i<checkedInNum; i++){
            if(checkedInMembers[i] != null){
                toReturn = toReturn + "\t" + checkedInMembers[i].toString() + "\n";
            }
        }
        return toReturn;
    }

    /**
     *
     * @param member to be added
     * @return true if member successfully added
     */
    public boolean add(Member member) {
        if (contains(member)) return false;
        checkedInMembers[checkedInNum] = member;
        checkedInNum++;
        if (checkedInNum == checkedInMembers.length) grow();
        return true;
    }

    /**
     *
     * @param member cancelling class
     * @return false if member is not in the class
     */
    public boolean dropClass(Member member){
        if (!contains(member)) return false;
        int mindex = find(member);

        for (int i = mindex; i < checkedInNum; i++){
            checkedInMembers[i] = checkedInMembers[i + 1];
        }
        return true;
    }

    /**
     * Grow checkedInMembers by GROWTH_FACTOR
     */
    private void grow() {
        Member[] mcopy = new Member [checkedInMembers.length + GROWTH_FACTOR];
        for (int i=0; i<checkedInMembers.length; i++) {
            mcopy[i] = checkedInMembers[i];
        }
        checkedInMembers = mcopy;
    }

    /**
     *
     * @param member to be found
     * @return
     */
    private boolean contains(Member member){ return find(member) != -1; }

    private int find(Member member){
        for (int i = 0; i < checkedInNum; i++){
            if (member.equals(checkedInMembers[i])) return i;
        }
        return NOT_FOUND;
    }


}
