package models;

import java.util.Scanner;

public class GymManager {
    protected boolean run(){
        System.out.println("Gym Manager running...");
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
                // add member
                handleAddMember(line, database);
                break;
            case "R":
                // cancel membership
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

    private boolean handleAddMember(String command, MemberDatabase db){
        String[] parts = command.split(" ");

        if(parts.length < 6){
            return false;
        }

        String fname = parts[1];
        String lname = parts[2];

        Date dob = new Date(parts[3]);
        // TODO: make error messages constants
        if(!dob.isValid()){
            System.out.println("DOB: " + parts[3] + ": invalid calendar date!");
            return false;
        }
        if(!dob.isPast()){
            System.out.println("DOB: " + parts[3] + ": cannot be today or a future date!");
            return false;
        }
        if(!dob.isEighteen()){
            System.out.println("DOB: " + parts[3] + ": must be 18 or older to join!");
            return false;
        }

        Date expire = new Date(parts[4]);
        if(!expire.isValid()){
            System.out.println("Expiration date " + parts[4] + ": invalid calendar date!");
        }

        Location location = Location.toLocation(parts[5]);
        if(location == null) {
            System.out.println(parts[4] + ": invalid location!");
            return false;
        }

        Member member = new Member(fname, lname, dob, expire, location);

        if(db.contains(member)) {
            System.out.println(fname + " " + lname + " is already in the database.");
            return false;
        }

        return true;
    }

    private String isValidDob(){
        return null;
    }

    private String isValidExpiration(){
        return null;
    }
}
