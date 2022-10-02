/**
 * @author Jackson Lee, Serena Zeng
 */
package models;

import java.util.Scanner;

public class GymManager {
    public static final String DOB_ERROR = "DOB ";
    public static final String EXPIRATION_ERROR = "Expiration date ";
    public static final int A_COMMAND_LENGTH = 6;
    public static final int R_COMMAND_LENGTH = 4;
    public static final int C_COMMAND_LENGTH = 5;
    public static final int FNAME_INDEX = 1;
    public static final int LNAME_INDEX = 2;
    public static final int DOB_INDEX = 3;
    public static final int EXP_INDEX = 4;
    public static final int LOC_INDEX = 5;
    public static final int C_CLASS_INDEX = 1;
    public static final int C_FNAME_INDEX = 2;
    public static final int C_LNAME_INDEX = 3;
    public static final int C_DOB_INDEX = 4;

    MemberDatabase database;
    Schedule schedule;

    protected boolean run(){
        System.out.println("Gym Manager running...");
        Scanner sc = new Scanner(System.in);
        String s = "";

        database = new MemberDatabase();
        schedule = new Schedule();
        
        while(sc.hasNextLine()) {
            s = sc.nextLine();
            if(s.equals("Q")){
                break;
            }
            String command = s.split(" ")[0];
            runCommand(command, s);
        }

        return true;
    }

    private void runCommand(String command, String line){
        switch (command) {
            case "A":
                handleAddMember(line);
                break;
            case "R":
                handleCancelMembership(line);
                break;
            case "P":
                database.print(); // display members without sorting
                break;
            case "PC":
                database.printByCounty(); // display members ordered by location
                break;
            case "PN":
                database.printByName(); // display members ordered by name
                break;
            case "PD":
                database.printByExpirationDate(); // display members ordered by expiration
                break;
            case "S":
                schedule.printSchedule();
                break;
            case "C":
                handleCheckIn(line);
                break;
            case "D":
                // drop class
                break;
            default:
                System.out.println(command + " is an invalid command!");
        }
    }

    /**
     * Command A
     * @param command
     * @return member added success
     */
    private boolean handleAddMember(String command){
        String[] parts = command.split(" ");
        if(parts.length < A_COMMAND_LENGTH) return false;

        Date dob = new Date(parts[DOB_INDEX]);
        Date expire = new Date(parts[EXP_INDEX]);
        String error = checkDateErrors(dob, parts[DOB_INDEX], expire, parts[EXP_INDEX]);
        if(error != null){
            System.out.println(error);
            return false;
        }

        Location location = Location.toLocation(parts[LOC_INDEX]);
        if(location == null) {
            System.out.println(parts[LOC_INDEX] + ": invalid location!");
            return false;
        }

        Member member = new Member(parts[FNAME_INDEX], parts[LNAME_INDEX], dob, expire, location);
        if(database.contains(member)) {
            System.out.println(parts[FNAME_INDEX] + " " + parts[LNAME_INDEX] + " is already in the database.");
            return false;
        }

        if(database.add(member)){
            System.out.println(parts[FNAME_INDEX] + " " + parts[LNAME_INDEX] + " added.");
        }
        return true;
    }

    /**
     * Cancel membership when command R
     * @param command
     * @return  cancel success
     */
    private boolean handleCancelMembership(String command){
        String[] parts = command.split(" ");
        if(parts.length < R_COMMAND_LENGTH) return false;

        if(database.remove(new Member(parts[FNAME_INDEX], parts[LNAME_INDEX], new Date(parts[DOB_INDEX])))){
            System.out.println(parts[FNAME_INDEX] + " " + parts[LNAME_INDEX] + " removed.");
        }
        else{
            System.out.println(parts[FNAME_INDEX] + " " + parts[LNAME_INDEX] + " is not in the database.");
        }

        return true;
    }

    /**
     *
     * @param command
     * @return member checked in for class successfully
     */
    private boolean handleCheckIn(String command){
        String[] parts = command.split(" ");
        if(parts.length < C_COMMAND_LENGTH) return false;
        Date dob = new Date(parts[C_DOB_INDEX]);
        if (!dob.isValid()){
            System.out.println(DOB_ERROR + parts[C_DOB_INDEX] + ": invalid calendar date!");
            return false;
        }



        return true;
    }

    /**
     * Return errors if DOB or expiration date cannot be used for member
     * @param dob
     * @param dobText
     * @param expiration
     * @param expText
     * @return  error String
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
