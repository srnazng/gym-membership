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
    private static final int A_COMMAND_LENGTH = 6;
    private static final int R_COMMAND_LENGTH = 4;
    private static final int C_COMMAND_LENGTH = 7;
    private static final int D_COMMAND_LENGTH = 7;
    private static final int ARG_1 = 1;
    private static final int ARG_2 = 2;
    private static final int ARG_3 = 3;
    private static final int ARG_4 = 4;
    private static final int ARG_5 = 5;
    public static final int ARG_6 = 6;

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
        if(command.equals("LS")) { handleLoadSchedule(); }
        else if(command.equals("LM")) { handleLoadMemberList(); }
        // TODO: add a member with the standard membership to the member database
        else if(command.equals("A")) { handleAddMember(line); }
        else if(command.equals("AF")) { handleAddFamilyMember(line); }
        else if(command.equals("AP")) { handleAddPremiumMember(line); }
        else if(command.equals("PF")) { database.print(true); }
        // TODO: check in for in person class
        else if(command.equals("C")) { handleCheckIn(line, false); }
        else if(command.equals("CG")) { handleCheckIn(line, true); }
        // TODO: drop in person class
        else if(command.equals("D")) { handleDropClass(line, false); }
        else if(command.equals("DG")) { handleDropClass(line, true); }
        else if(command.equals("R")) { handleCancelMembership(line); }
        else if(command.equals("P")) { database.print(false); }
        else if(command.equals("PC")) {  database.printByLocation(); }
        else if(command.equals("PN")) { database.printByName(); }
        else if(command.equals("PD")) { database.printByExpirationDate(); }
        else if(command.equals("S")) { schedule.printSchedule(); }
        else { System.out.println(command + " is an invalid command!"); }
    }

    /**
     * Handles inputs with command type LS.
     * Load the fitness class schedule from the file classSchedule.txt
     * to the class schedule in the software system.
     * @return true if schedule successfully loaded, false otherwise
     */
    private boolean handleLoadSchedule() throws FileNotFoundException {
        schedule.loadSchedule();
        return true;
    }

    /**
     * Handles inputs with command type LM.
     * Load a list of members from the file memberList.txt to the member database.
     * @return true if schedule successfully loaded, false otherwise
     */
    private boolean handleLoadMemberList(){
        String path = "input/memberList.txt";
        return true;
    }

    /**
     * Handles inputs with command type A
     * Parses command for member information
     * Checks if member is valid and if so, adds member to member database
     * @param command   Entire line of instruction containing member information
     * @return true if member successfully added, false otherwise
     */
    private boolean handleAddMember(String command){
        String[] parts = command.split(" ");
        if(parts.length < A_COMMAND_LENGTH) return false;

        Date dob = new Date(parts[ARG_3]);
        Date expire = new Date(parts[ARG_4]);
        String error = checkDateErrors(dob, parts[ARG_3], expire, parts[ARG_4]);
        if(error != null){
            System.out.println(error);
            return false;
        }
        Location location = Location.toLocation(parts[ARG_5]);
        if(location == null) {
            System.out.println(parts[ARG_5] + ": invalid location!");
            return false;
        }
        Member member = new Member(parts[ARG_1], parts[ARG_2], dob, expire, location);
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
     * Handles inputs with command type AF
     * Parses command for member information.
     * Checks if member is valid and if so, adds member with Family Membership
     * to member database.
     * @param command   Entire line of instruction containing member information
     * @return true if member successfully added, false otherwise
     */
    private boolean handleAddFamilyMember(String command){
        return true;
    }

    /**
     * Handles inputs with command type AF
     * Parses command for member information.
     * Checks if member is valid and if so, adds member with Premium Membership
     * to member database.
     * @param command   Entire line of instruction containing member information
     * @return true if member successfully added, false otherwise
     */
    private boolean handleAddPremiumMember(String command){
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
        if (member.getExpire().isPast()) {
            System.out.println(fname + " " + lname + " " + dob + " membership expired.");
            return false;
        }
        if (!schedule.hasClass(className, instructor, location)) {
            System.out.println(className + " class does not exist.");
            return false;
        }
        FitnessClass fitClass = schedule.getClass(className, instructor, location);
        if (fitClass.contains(member)) {
            System.out.println(fname + " " + lname + " has already checked in " + fitClass.getName() + ".");
            return false;
        }
        FitnessClass otherClass = schedule.sameTimeClass(member, fitClass);
        if (otherClass != null) {
            System.out.println(fitClass.getName() + " time conflict -- " + fname + " " + lname
                    + " has already checked in " + otherClass.getName() + ".");
            return false;
        }
        if(isGuest){
            fitClass.addGuest(member);
            System.out.println(fname + " " + lname + " checked in " + fitClass.getName() + ".");
        }
        else{
            fitClass.add(member);
            System.out.println(fname + " " + lname + " checked in " + fitClass.getName() + ".");
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
            System.out.println(DOB_ERROR + parts[ARG_4] + ": invalid calendar date!"
            );
            return false;
        }
        // check if fitness class exists
        FitnessClass fitClass = schedule.getClass(className, instructor, location);
        if(fitClass == null){
            System.out.println(parts[ARG_1] + " class does not exist.");
            return false;
        }
        // check if user is registered
        Member member = new Member(fname, lname, dob);
        if(!fitClass.contains(member)){
            System.out.println(fname + " " + lname +
                    " is not a participant in " + className + ".");
            return false;
        }
        if((isGuest && !fitClass.dropGuestClass(member)) || !isGuest && !fitClass.dropClass(member)){
            return false;
        }
        System.out.println(fname + " " + lname + " dropped " + className + ".");
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

    /**
     * Return errors if DOB or expiration date cannot be used for member
     * @param dob           date of birth represented as Date object
     * @param dobText       date of birth represented as String
     * @param expiration    expiration date represented as Date object
     * @param expText       expiration date represented as String
     * @return  error message if there is an error, null otherwise
     */
    private String checkDateErrors(Date dob, String dobText, Date expiration, String expText){
        if(!dob.isValid()){
            return DOB_ERROR + dobText + ": invalid calendar date!";
        }
        if(!dob.isPast()){
            return DOB_ERROR + dobText + ": cannot be today or a future date!";
        }
        if(!dob.isEighteen()){
            return DOB_ERROR + dobText + ": must be 18 or older to join!";
        }
        if(!expiration.isValid()){
            return EXPIRATION_ERROR + expText + ": invalid calendar date!";
        }
        return null;
    }
}
