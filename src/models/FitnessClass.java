package models;

import java.util.ArrayList;

/**
 * The FitnessClass class represents the fitness classes at the gym.
 * Each FitnessClass object includes the name of the class, the name
 * of the instructor, the time of the class, and a list of members
 * who are checked in at the current time. This class also includes methods
 * to add or drop a class, and to check if a member is checked into the class.
 * @author Jackson Lee, Serena Zeng
 */
public class FitnessClass {
    private String name;
    private String instructor;
    private Time time;
    private Location location;
    private ArrayList<Member> checkedInMembers;
    private ArrayList<Member> checkedInGuests;

    /**
     * Create a new Fitness Class object with no checked in members
     * @param name          Name of class
     * @param instructor    Name of the class instructor
     * @param time          Time of the class (morning or afternoon)
     * @param location      Location of class
     */
    public FitnessClass(String name, String instructor, Time time, Location location){
        this.name = name;
        this.location = location;
        this.instructor = instructor;
        this.time = time;
        checkedInMembers = new ArrayList<>();
        checkedInGuests = new ArrayList<>();
    }

    /**
     * Get name of fitness class
     * @return class name
     */
    public String getName(){ return name; }

    /**
     * Get time of fitness class
     * @return MORNING or AFTERNOON
     */
    public Time getTime(){ return time; }

    /**
     * Get location of fitness class
     * @return Location object
     */
    public Location getLocation(){ return location; }

    /**
     * Get fitness class instructor
     * @return Name of fitness class instructor
     */
    public String getInstructor(){ return instructor; }

    @Override
    public boolean equals(Object obj){
        FitnessClass otherClass = (FitnessClass) obj;
        if(!otherClass.getName().equalsIgnoreCase(name) ||
            !otherClass.getInstructor().equalsIgnoreCase(instructor) ||
            !otherClass.getLocation().equals(location)){
            return false;
        }
        return true;
    }


    /**
     * Converts object to a String containing name of the class,
     * instructor name, time of the class, and a list of participants
     * @return Fitness class formatted as a String
     */
    @Override
    public String toString() {
        String toReturn = name.toUpperCase() + " - " + instructor.toUpperCase() + ", " + time.getTime()
                + ", " + location.name().toUpperCase();
        if(checkedInGuests.size() > 0 || checkedInMembers.size() > 0){
            return toReturn + getClassMemberList() + getClassGuestList();
        }
        return toReturn + "\n";
    }

    /**
     * Gets a list of Members (as Strings) who are checked into the fitness class
     * @return  List of participants of the class, empty string if no participants
     */
    public String getClassMemberList(){
        String toReturn = "";
        if(checkedInMembers.size() > 0){
            toReturn = "\n- Participants -\n";
        }
        else{
            return "";
        }

        for(int i=0; i<checkedInMembers.size(); i++){
            if(checkedInMembers.get(i) != null){
                toReturn = toReturn + "\t" + checkedInMembers.get(i).toString();
            }
            if(i != checkedInMembers.size() - 1){
                toReturn = toReturn + "\n";
            }
            else if(checkedInGuests.size() < 1){
                toReturn = toReturn + "\n";
            }
        }
        return toReturn;
    }

    public String getClassGuestList(){
        String toReturn = "";
        if(checkedInGuests.size() > 0){
            toReturn = "\n- Guests -\n";
        }
        else{
            return "";
        }

        for(int i=0; i<checkedInGuests.size(); i++){
            if(checkedInGuests.get(i) != null){
                toReturn = toReturn + "\t" + checkedInGuests.get(i).toString();
            }
            toReturn = toReturn + "\n";
        }
        return toReturn;
    }

    /**
     * Check in a member to the fitness class and
     * increment the number of participants checked into the class
     * @param member Member to be checked into class
     * @return true if member successfully added, false otherwise
     */
    public boolean add(Member member) {
        if (checkedInMembers.contains(member)) {
            return false;
        }
        checkedInMembers.add(member);
        return true;
    }

    /**
     * Check in a guest to the fitness class and
     * increment the number of guests checked into the class
     * @param member Member who is giving the guest pass
     * @return true if member successfully added, false otherwise
     */
    public boolean addGuest(Member member) {
        if(!(member instanceof Family)){
            return false;
        }
        if(member.getLocation() != location){
            return false;
        }
        if (!((Family) member).hasGuestPass()){
            return false;
        }
        ((Family) member).useGuestPass();
        checkedInGuests.add(member);
        getClassMemberList();
        return true;
    }

    /**
     * Remove the given member from the list of participants in the class and
     * decrement the number of participants checked into the class
     * @param member Member to remove from participant list
     * @return false if member is not in the class, otherwise true
     */
    public boolean dropClass(Member member){
        return checkedInMembers.remove(member);
    }

    /**
     * Remove the given Member providing the pass to the guest and
     * @param member Member to remove from guest list
     * @return false if guest of member is not in the class, otherwise true
     */
    public boolean dropGuestClass(Member member){
        return checkedInGuests.remove(member);
    }

    public boolean contains(Member member){
        return checkedInMembers.contains(member);
    }

    public boolean containsGuest(Member member){
        return checkedInGuests.contains(member);
    }
}
