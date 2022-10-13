package models;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * The Schedule class manages all the fitness classes at the gym. Schedule contains a list of
 * all the fitness classes and includes methods to detect time conflicts, check if a class
 * is offered at the gym, get FitnessClass objects, and print the schedule of classes.
 * @author Jackson Lee, Serena Zeng
 */
public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;
    private static final int NAME_INDEX = 0;
    private static final int INSTRUCTOR_INDEX = 1;
    private static final int TIME_INDEX = 2;
    private static final int CITY_INDEX = 3;
    public static final int NUM_ARGS = 4;

    /**
     * Create Schedule object that sets the number of classes initially to 0.
     */
    public ClassSchedule(){
        this.numClasses = 0;
    }

    /**
     * Print the list of fitness classes
     */
    public void printSchedule(){
        if(numClasses > 0 && classes != null){
            System.out.println("\n-Fitness classes-");
            for(int i = 0; i < classes.length; i++){
                System.out.println(classes[i]);
            }
            System.out.println();
        }
    }

    /**
     * Get the fitness class in the schedule given the name of the class
     * @param className     Name of the fitness class
     * @param instructor    Name of instructor teaching the fitness class
     * @param city          City of the fitness class
     * @return the fitness class with name className
     */
    public FitnessClass getClass(String className, String instructor, String city){
        for(FitnessClass fitClass : classes){
            if(fitClass.getName().equalsIgnoreCase(className) &&
                    fitClass.getInstructor().equalsIgnoreCase(instructor)){
                if((city == null && fitClass.getLocation() == null)
                        || Location.toLocation(city).equals(fitClass.getLocation())){
                    return fitClass;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the gym schedule includes a fitness class given the name of the class
     * @param className Name of the class
     * @return true if schedule contains class of className, false otherwise
     */
    public boolean hasClass(String className, String instructor, String city){
        return getClass(className, instructor, city) != null;
    }

    /**
     * Returns a fitness class that the member is registered for
     * that occurs the same time as the given class
     * @param member Member taking the class
     * @param fitClass FitnessClass to compare to other fitness classes taken by member
     * @return conflicting class that contains member, null if no class conflict
     */
    public FitnessClass sameTimeClass(Member member, FitnessClass fitClass){
        for(FitnessClass compClass : classes){
            if (!compClass.getName().equals(fitClass.getName())){
                if (compClass.contains(member) &&
                        fitClass.getTime() == compClass.getTime()) {
                    return compClass;
                }
            }
        }
        return null;
    }

    public void loadSchedule() throws FileNotFoundException {
        File file = new File("src/input/classSchedule.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()){
            String s = sc.nextLine();
            if(!s.isBlank()) {
                numClasses++;
            }
        }
        sc.close();
        sc = new Scanner(file);
        classes = new FitnessClass[numClasses];

        for (int i = 0; i < numClasses; i++){
            String classString = sc.nextLine();
            String[] classParts = classString.split("\\s+");
            if(classParts.length == NUM_ARGS){
                FitnessClass fitClass = new FitnessClass(classParts[NAME_INDEX],
                        classParts[INSTRUCTOR_INDEX], Time.toTime(classParts[TIME_INDEX]),
                        Location.toLocation(classParts[CITY_INDEX]));
                classes[i] = fitClass;
            }
        }
        sc.close();
    }
}
