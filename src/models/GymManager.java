package models;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The GymManager class is the user interface class that processes the
 * command line inputs and prints the corresponding outputs.
 * @author Jackson Lee, Serena Zeng
 */
public class GymManager {
    private static final String DOB_ERROR = "DOB ";
    private static final String EXPIRATION_ERROR = "Expiration date ";
    private static final int A_COMMAND_LENGTH = 5;
    private static final int R_COMMAND_LENGTH = 4;
    private static final int C_COMMAND_LENGTH = 7;
    private static final int D_COMMAND_LENGTH = 7;
    private static final int ARG_1 = 1;
    private static final int ARG_2 = 2;
    private static final int ARG_3 = 3;
    private static final int ARG_4 = 4;
    private static final int ARG_5 = 5;
    public static final int ARG_6 = 6;
    enum Plan { STANDARD, FAMILY, PREMIUM };

    MemberDatabase database;
    ClassSchedule schedule;

    /**
     * Run program
     * Reads lines from terminal and performs commands based off inputs
     * @return true
     */
    protected boolean run() throws FileNotFoundException {
        System.out.println("Gym Manager running...");
        Scanner sc = new Scanner(System.in);
        String s = "";

        database = new MemberDatabase();
        schedule = new ClassSchedule();
        
        while(sc.hasNextLine()) {
            s = sc.nextLine();
            if(s.equals("Q")){
                System.out.println("Gym Manager terminated.");
                break;
            }
            runCommand(s.split(" ")[0], s);
        }

        return true;
    }

    /**
     * Processes terminal commands
     * @param command   command indicating type of task
     * @param line      entire instruction
     */
    private void runCommand(String command, String line) throws FileNotFoundException {
        if(command.trim().length() == 0) { return; }
        if(command.equals("LS")) { schedule.loadSchedule(); }
        else if(command.equals("LM")) { database.loadMembers(); }
        else if(command.equals("A")) { handleAddMember(line, Plan.STANDARD); }
        else if(command.equals("AF")) { handleAddMember(line, Plan.FAMILY); }
        else if(command.equals("AP")) { handleAddMember(line, Plan.PREMIUM); }
        else if(command.equals("PF")) { database.printDefault(true); }
        else if(command.equals("C")) { handleCheckIn(line, false); }
        else if(command.equals("CG")) { handleCheckIn(line, true); }
        else if(command.equals("D")) { handleDropClass(line, false); }
        else if(command.equals("DG")) { handleDropClass(line, true); }
        else if(command.equals("R")) { handleCancelMembership(line); }
        else if(command.equals("P")) { database.printDefault(false); }
        else if(command.equals("PC")) {  database.printByLocation(); }
        else if(command.equals("PN")) { database.printByName(); }
        else if(command.equals("PD")) { database.printByExpirationDate(); }
        else if(command.equals("S")) { schedule.printSchedule(); }
        else { System.out.println(command + " is an invalid command!"); }
    }

    /**
     * Handles inputs with command type A, AF, AP
     * Parses command for member information
     * Checks if member is valid and if so, adds member to member database
     * @param command   Entire line of instruction containing member information
     * @return true if member successfully added, false otherwise
     */
    private boolean handleAddMember(String command, Plan membershipType){
        String[] parts = command.split(" ");
        if(parts.length < A_COMMAND_LENGTH) return false;

        Date dob = new Date(parts[ARG_3]);
        String error = checkBirthdayErrors(dob);
        if(error != null){
            System.out.println(error);
            return false;
        }
        Location location = Location.toLocation(parts[ARG_4]);
        if(location == null) {
            System.out.println(parts[ARG_4] + ": invalid location!");
            return false;
        }
        Member member;
        if(membershipType == Plan.PREMIUM){
            member = new Premium(parts[ARG_1], parts[ARG_2], dob, location);
        }
        else if(membershipType == Plan.FAMILY){
            member = new Family(parts[ARG_1], parts[ARG_2], dob, location);
        }
        else{
            member = new Member(parts[ARG_1], parts[ARG_2], dob, location);
        }

        if(database.contains(member)) {
            System.out.println(parts[ARG_1] + " " + parts[ARG_2] + " is already in the database.");
            return false;
        }
        if(database.add(member)){
            System.out.println(parts[ARG_1] + " " + parts[ARG_2] + " added.");
        }
        return true;
    }

    /**
     * Handles inputs with command C and CG
     * Parses command for fitness class and member information
     * Checks in the member if
     * 1. The member is in the database
     * 2. Their membership is not expired, their
     * 3. Their date of birth is valid
     * 4. The specified class exists
     * 5. The class has no time conflict with the member's other fitness clases
     * 6. The member is not already checked in
     * Displays appropriate error messages if unable to check in member to class
     * @param command Entire line of instruction containing member and class information
     * @return true if member checked in for class successfully, false otherwise
     */
    private boolean handleCheckIn(String command, boolean isGuest) {
        String[] parts = command.split(" ");
        if (parts.length != C_COMMAND_LENGTH) return false;
        Date dob = new Date(parts[ARG_6]);
        String fname = parts[ARG_4];
        String lname = parts[ARG_5];
        String className = parts[ARG_1];
        String instructor = parts[ARG_2];
        String location = parts[ARG_3];
        if(!dob.isValid()) {
            System.out.println(DOB_ERROR + dob + ": invalid calendar date!");
            return false;
        }
        Member member = database.getMember(new Member(fname, lname, dob));
        if (member == null) {
            System.out.println(fname + " " + lname + " " + dob + " is not in the database.");
            return false;
        }
        if(isGuest && !(member instanceof Family)){
            System.out.println("Standard membership - guest check-in is not allowed.");
            return false;
        }
        if(isGuest && !((Family) member).hasGuestPass()){
            System.out.println(fname + " " + lname + " ran out of guest pass.");
            return false;
        }
        if (!schedule.hasClass(className, instructor, location)) {
            handleClassNotExist(className, instructor, location);
            return false;
        }
        if (member.getExpire().isPast()) {
            System.out.println(fname + " " + lname + " " + dob + " membership expired.");
            return false;
        }
        FitnessClass fitClass = schedule.getClass(className, instructor, location);
        if ((!isGuest && fitClass.contains(member))) {
            System.out.println(fname + " " + lname + " already checked in.");
            return false;
        }
        if( !(member instanceof Family) && fitClass.getLocation() != member.getLocation()){
            System.out.println(fname + " " + lname + " checking in " + fitClass.getLocation().toString().toUpperCase()
                    + " - standard membership location restriction.");
            return false;
        }
        if( isGuest && fitClass.getLocation() != member.getLocation()){
            System.out.println(fname + " " + lname + " Guest checking in " + fitClass.getLocation().toString().toUpperCase()
                    + " - guest location restriction.");
            return false;
        }
        FitnessClass otherClass;
        if(isGuest) { otherClass = schedule.sameTimeGuestClass(member, fitClass); }
        else { otherClass = schedule.sameTimeClass(member, fitClass); }
        if (otherClass != null) {
            System.out.println("Time conflict - " + fitClass.getName().toUpperCase() + " - " + fitClass.getInstructor().toUpperCase()
                    + ", "  +  fitClass.getTime().getTime() + ", " + fitClass.getLocation().toString().toUpperCase());
            return false;
        }
        if(isGuest){
            fitClass.addGuest(member);
            System.out.print(fname + " " + lname + " (guest) checked in " + fitClass.getName().toUpperCase() +
                    fitClass.getInstructor().toUpperCase() + ", " + fitClass.getTime().getTime() + ", " + fitClass.getLocation().name());
            System.out.print(fitClass.getClassMemberList());
            System.out.print(fitClass.getClassGuestList());
        }
        else{
            fitClass.add(member);
            System.out.print(fname + " " + lname + " checked in " + fitClass.getName().toUpperCase() + " - " +
                    fitClass.getInstructor().toUpperCase() + ", " + fitClass.getTime().getTime() + ", " + fitClass.getLocation().name());
            System.out.print(fitClass.getClassMemberList());
            System.out.print(fitClass.getClassGuestList());
        }
        return true;
    }


    /**
     * Handles inputs with command D and DG
     * Parses command for fitness class and member information
     * Drops the member from the class if
     * 1. The member is initially checked into the class
     * 2. Their date of birth is valid
     * 3. The specified class exists
     * Displays appropriate error messages if unable to drop member from class
     * @param command Entire line of instruction containing member and class information
     * @return true if member dropped from class successfully, false otherwise
     */
    private boolean handleDropClass(String command, boolean isGuest){
        String[] parts = command.split(" ");
        if(parts.length < D_COMMAND_LENGTH) return false;

        Date dob = new Date(parts[ARG_6]);
        String fname = parts[ARG_4];
        String lname = parts[ARG_5];
        String className = parts[ARG_1];
        String instructor = parts[ARG_2];
        String location = parts[ARG_3];

        // check if DOB is valid
        if(!dob.isValid()) {
            System.out.println(DOB_ERROR + parts[ARG_6] + ": invalid calendar date!"
            );
            return false;
        }
        if (!schedule.hasClass(className, instructor, location)) {
            handleClassNotExist(className, instructor, location);
            return false;
        }
        // check if fitness class exists
        FitnessClass fitClass = schedule.getClass(className, instructor, location);
        // check if user is registered
        Member member = new Member(fname, lname, dob);
        if(!database.contains(member)){
            System.out.println(fname + " " + lname + " " + parts[ARG_6] + " is not in the database.");
            return false;
        }
        if(!fitClass.contains(member)){
            System.out.println(fname + " " + lname + " did not check in.");
            return false;
        }
        if((isGuest && !fitClass.dropGuestClass(member)) || !isGuest && !fitClass.dropClass(member)){
            return false;
        }
        System.out.println(fname + " " + lname + " done with this class.");
        return true;
    }

    /**
     * Handles inputs with command R
     * Parses command for member information
     * Checks if member is in the member database and if so, removes from the member from the database
     * @param command   Entire line of instruction containing member information
     * @return true if membership cancel success, false otherwise
     */
    private boolean handleCancelMembership(String command){
        String[] parts = command.split(" ");
        if(parts.length < R_COMMAND_LENGTH) return false;
        if(database.remove(new Member(parts[ARG_1], parts[ARG_2], new Date(parts[ARG_3])))){
            System.out.println(parts[ARG_1] + " " + parts[ARG_2] + " removed.");
        }
        else{
            System.out.println(parts[ARG_1] + " " + parts[ARG_2] + " is not in the database.");
        }
        return true;
    }

    private String checkBirthdayErrors(Date dob) {
        if (!dob.isValid()) {
            return DOB_ERROR + dob + ": invalid calendar date!";
        }
        if (!dob.isPast()) {
            return DOB_ERROR + dob + ": cannot be today or a future date!";
        }
        if (!dob.isEighteen()) {
            return DOB_ERROR + dob + ": must be 18 or older to join!";
        }
        return null;
    }

    private void handleClassNotExist(String className, String instructor, String location){
        if (!schedule.hasClassName(className)){
            System.out.println(className + " - class does not exist.");
        }
        else if (Location.toLocation(location) == null){
            System.out.println(location + " - invalid location.");
        }
        else if (!schedule.hasInstructor(instructor)){
            System.out.println(instructor + " - instructor does not exist.");
        }
        else{
            System.out.println(className + " by " + instructor + " does not exist at " + location);
        }

    }
}
