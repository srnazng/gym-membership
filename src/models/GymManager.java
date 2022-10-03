package models;

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
    private static final int C_COMMAND_LENGTH = 5;
    private static final int D_COMMAND_LENGTH = 5;
    private static final int AR_FNAME_INDEX = 1;
    private static final int AR_LNAME_INDEX = 2;
    private static final int AR_DOB_INDEX = 3;
    private static final int AR_EXP_INDEX = 4;
    private static final int AR_LOC_INDEX = 5;
    private static final int CD_CLASS_INDEX = 1;
    private static final int CD_FNAME_INDEX = 2;
    private static final int CD_LNAME_INDEX = 3;
    private static final int CD_DOB_INDEX = 4;

    MemberDatabase database;
    Schedule schedule;

    /**
     * Run program
     * Reads lines from terminal and performs commands based off inputs
     * @return true
     */
    protected boolean run(){
        System.out.println("Gym Manager running...");
        Scanner sc = new Scanner(System.in);
        String s = "";

        database = new MemberDatabase();
        schedule = new Schedule();
        
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
     * @param line  entire instruction
     */
    private void runCommand(String command, String line){
        if(command.trim().length() == 0){
            return;
        }
        switch (command) {
            case "A":
                handleAddMember(line);
                break;
            case "R":
                handleCancelMembership(line);
                break;
            case "P":
                database.print();
                break;
            case "PC":
                database.printByLocation();
                break;
            case "PN":
                database.printByName();
                break;
            case "PD":
                database.printByExpirationDate();
                break;
            case "S":
                schedule.printSchedule();
                break;
            case "C":
                handleCheckIn(line);
                break;
            case "D":
                handleDropClass(line);
                break;
            default:
                System.out.println(command + " is an invalid command!");
        }
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

        Date dob = new Date(parts[AR_DOB_INDEX]);
        Date expire = new Date(parts[AR_EXP_INDEX]);
        String error = checkDateErrors(dob, parts[AR_DOB_INDEX], expire, parts[AR_EXP_INDEX]);
        if(error != null){
            System.out.println(error);
            return false;
        }
        Location location = Location.toLocation(parts[AR_LOC_INDEX]);
        if(location == null) {
            System.out.println(parts[AR_LOC_INDEX] + ": invalid location!");
            return false;
        }
        Member member = new Member(parts[AR_FNAME_INDEX], parts[AR_LNAME_INDEX], dob, expire, location);
        if(database.contains(member)) {
            System.out.println(parts[AR_FNAME_INDEX] + " " + parts[AR_LNAME_INDEX] + " is already in the database.");
            return false;
        }
        if(database.add(member)){
            System.out.println(parts[AR_FNAME_INDEX] + " " + parts[AR_LNAME_INDEX] + " added.");
        }
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
        if(database.remove(new Member(parts[AR_FNAME_INDEX], parts[AR_LNAME_INDEX], new Date(parts[AR_DOB_INDEX])))){
            System.out.println(parts[AR_FNAME_INDEX] + " " + parts[AR_LNAME_INDEX] + " removed.");
        }
        else{
            System.out.println(parts[AR_FNAME_INDEX] + " " + parts[AR_LNAME_INDEX] + " is not in the database.");
        }
        return true;
    }

    /**
     * Handles inputs with command C
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
    private boolean handleCheckIn(String command){
        String[] parts = command.split(" ");
        if(parts.length < C_COMMAND_LENGTH) return false;
        Date dob = new Date(parts[CD_DOB_INDEX]);
        String fname = parts[CD_FNAME_INDEX];
        String lname = parts[CD_LNAME_INDEX];
        String className = parts[CD_CLASS_INDEX];

        if (!dob.isValid()){
            System.out.println(DOB_ERROR + dob + ": invalid calendar date!");
            return false;
        }
        Member member = database.getMember(new Member(fname, lname, dob));
        if (member == null){
            System.out.println(fname + " " + lname + " " + dob + " is not in the database.");
            return false;
        }
        if (member.getExpire().isPast()){
            System.out.println(fname + " " + lname + " " + dob + " membership expired.");
            return false;
        }
        if (!schedule.hasClass(className)){
            System.out.println(className + " class does not exist.");
            return false;
        }
        FitnessClass fitClass = schedule.getClass(className);
        if (fitClass.contains(member)){
            System.out.println(fname + " " + lname + " has already checked in " + fitClass.getName() + ".");
            return false;
        }
        FitnessClass otherClass = schedule.sameTimeClass(member, fitClass);
        if (otherClass != null){
            System.out.println(fitClass.getName() + " time conflict -- " + fname + " " + lname + " has already checked in " + otherClass.getName() + ".");
            return false;
        }
        System.out.println(fname + " " + lname + " checked in " + fitClass.getName() + ".");
        fitClass.add(member);
        return true;
    }

    /**
     * Handles inputs with command D
     * Parses command for fitness class and member information
     * Drops the member from the class if
     * 1. The member is initially checked into the class
     * 2. Their date of birth is valid
     * 3. The specified class exists
     * Displays appropriate error messages if unable to drop member from class
     * @param command Entire line of instruction containing member and class information
     * @return true if member dropped from class successfully, false otherwise
     */
    private boolean handleDropClass(String command){
        String[] parts = command.split(" ");
        if(parts.length < D_COMMAND_LENGTH) return false;

        // check if DOB is valid
        Date dob = new Date(parts[CD_DOB_INDEX]);
        if(!dob.isValid()) {
            System.out.println(DOB_ERROR + parts[CD_DOB_INDEX] + ": invalid calendar date!"
            );
            return false;
        }
        // check if fitness class exists
        FitnessClass fitClass = schedule.getClass(parts[CD_CLASS_INDEX]);
        if(fitClass == null){
            System.out.println(parts[CD_CLASS_INDEX] + " class does not exist.");
            return false;
        }
        // check if user is registered
        Member member = new Member(parts[CD_FNAME_INDEX], parts[CD_LNAME_INDEX], dob);
        if(!fitClass.contains(member)){
            System.out.println(parts[CD_FNAME_INDEX] + " " + parts[CD_LNAME_INDEX] +
                    " is not a participant in " + parts[CD_CLASS_INDEX] + ".");
            return false;
        }
        if(!fitClass.dropClass(member)){ return false; }
        System.out.println(parts[CD_FNAME_INDEX] + " " + parts[CD_LNAME_INDEX]
                + " dropped " + parts[CD_CLASS_INDEX] + ".");
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
