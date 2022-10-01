package models;

import java.util.Scanner;

public class GymManager {
    public static final String RUNNING_MSG = "Gym Manager running...";
    public static final String DOB_ERROR = "DOB ";
    public static final String EXPIRATION_ERROR = "Expiration date ";
    public static final int A_COMMAND_LENGTH = 6;
    public static final int FNAME_INDEX = 1;
    public static final int LNAME_INDEX = 2;
    public static final int DOB_INDEX = 3;
    public static final int EXP_INDEX = 4;
    public static final int LOC_INDEX = 5;

    protected boolean run(){
        System.out.println(RUNNING_MSG);
        Scanner sc = new Scanner(System.in);
        String s = "";

        MemberDatabase database = new MemberDatabase();
        
        while(sc.hasNextLine()) {
            s = sc.nextLine();
            if(s.equals("Q")){
                break;
            }
            String command = s.split(" ")[0];
            runCommand(command, s, database);
        }

        return true;
    }

    private void runCommand(String command, String line, MemberDatabase database){
        switch (command) {
            case "A":
                handleAddMember(line, database);
                break;
            case "R":
                handleCancelMembership(line, database);
                break;
            case "P":
                // display list of members without sorting
                break;
            case "PC":
                // display the list of members ordered by location
                break;
            case "PN":
                // display the list of members ordered by name
                break;
            case "PD":
                // display the list of members ordered by expiration dates
                break;
            case "S":
                // display class schedule
                break;
            case "C":
                // check in
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
     * @param db
     * @return member added success
     */
    private boolean handleAddMember(String command, MemberDatabase db){
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
        if(db.contains(member)) {
            System.out.println(parts[FNAME_INDEX] + " " + parts[LNAME_INDEX] + " is already in the database.");
            return false;
        }

        if(db.add(member)){
            System.out.println(parts[FNAME_INDEX] + " " + parts[LNAME_INDEX] + " added.");
        }
        return true;
    }

    private boolean handleCancelMembership(String command, MemberDatabase db){
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
