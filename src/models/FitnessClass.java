/**
 * The FitnessClass class represents the fitness classes at the gym.
 * Each FitnessClass object includes the name of the class, the name
 * of the instructor, the time of the class, and a list of members
 * who are checked in at the current time. This class also includes methods
 * to add or drop a class, and to check if a member is checked into the class.
 * @author Jackson Lee, Serena Zeng
 */
package models;

public class FitnessClass {
    private String name;
    private String instructor;
    private Time time;
    private Member[] checkedInMembers;
    private int checkedInNum;

    private static final int GROWTH_FACTOR = 50;

    /**
     * Create a new Fitness Class object with no checked in members
     * @param name  Name of class
     * @param instructor    Name of the class instructor
     * @param time  Time of the class (morning or afternoon)
     */
    FitnessClass(String name, String instructor, Time time){
        this.name = name;
        this.instructor = instructor;
        this.time = time;
        checkedInMembers = new Member[GROWTH_FACTOR];
    }

    /**
     * Get name of fitness class
     * @return class name
     */
    public String getName(){ return name; }

    /**
     * Time of fitness class
     * @return MORNING or AFTERNOON
     */
    public Time getTime(){ return time; }


    /**
     * Converts object to a String containing name of the class,
     * instructor name, time of the class, and a list of participants
     * @return Fitness class formatted as a String
     */
    @Override
    public String toString() {
        return name + " - " + instructor.toUpperCase() + " " +
                time.getTime() + getClassMemberList();
    }

    /**
     * Returns the members who are checked into the fitness class
     * @return  List of participants of the class, empty string if no participants
     */
    private String getClassMemberList(){
        String toReturn = "";
        if(checkedInNum > 0){
            toReturn = "\n\t** participants **\n";
        }
        else{
            return "";
        }

        for(int i=0; i<checkedInNum; i++){
            if(checkedInMembers[i] != null){
                toReturn = toReturn + "\t\t" + checkedInMembers[i].toString();
            }
            if(i != checkedInNum - 1){
                toReturn = toReturn + "\n";
            }
        }
        return toReturn;
    }

    /**
     * Check in a member to the fitness class
     * Increment the number of participants checked into the class
     * @param member Member to be checked into class
     * @return true if member successfully added, false otherwise
     */
    public boolean add(Member member) {
        if (contains(member)) return false;
        checkedInMembers[checkedInNum] = member;
        checkedInNum++;
        if (checkedInNum == checkedInMembers.length) grow();
        return true;
    }

    /**
     * Remove the given member from the list of participants in the class
     * Decrement the number of participants checked into the class
     * @param member Member to remove from participant list
     * @return false if member is not in the class, otherwise true
     */
    public boolean dropClass(Member member){
        if (!contains(member)) return false;
        int mindex = find(member);

        for (int i = mindex; i < checkedInNum; i++){
            checkedInMembers[i] = checkedInMembers[i + 1];
        }
        checkedInNum--;
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
     * Check whether a member is checked into the fitness class
     * @param member Member to be found
     * @return true if member is a participant, false otherwise
     */
    public boolean contains(Member member){ return find(member) != -1; }

    /**
     * Find the index of the given Member in the list of participants
     * @param member Member to be found
     * @return  index of member in list of participants
     */
    private int find(Member member){
        for (int i = 0; i < checkedInNum; i++){
            if (member.equals(checkedInMembers[i])) return i;
        }
        return Constants.NOT_FOUND;
    }
}
