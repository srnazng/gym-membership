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
     * Print schedule and format with header and footer
     */
    public void printSchedule(){
        if(numClasses > 0 && classes != null){
            System.out.println("\n-Fitness classes-");
            printClasses();
            System.out.println("-end of class list.\n");
        }
        else{
            System.out.println("Fitness class schedule is empty.");
        }
    }

    /**
     * Print all the classes in the schedule
     */
    public void printClasses(){
        for(int i = 0; i < classes.length; i++){
            System.out.print(classes[i]);
        }
    }

    /**
     * Get the fitness class in the schedule given the name of the class, instructor, and city
     * @param targetClass   Temporary Fitness Class object to find
     * @return the fitness class with name className
     */
    public FitnessClass getClass(FitnessClass targetClass){
        for(int i=0; i<numClasses; i++){
            FitnessClass fitClass = classes[i];
            if(fitClass.getName().equalsIgnoreCase(targetClass.getName()) &&
                    fitClass.getInstructor().equalsIgnoreCase(targetClass.getInstructor()) &&
                    fitClass.getLocation().name().equalsIgnoreCase(targetClass.getLocation().name())){
                    return fitClass;
            }
        }
        return null;
    }

    /**
     * Check if a fitness class in the schedule exists given the name of the class instructor, and city
     * @param fitClass  temporary FitnessClass object to find in schedule
     * @return True if there exists fitness class with that name, instructor, and city, false otherwise.
     */
    public boolean hasClass(FitnessClass fitClass){
        return getClass(fitClass) != null;
    }

    /**
     * Return error message for a Fitness Class that is known to not be in the schedule
     * @param fitClass  Fitness class that user is trying to check into
     * @return  Error message
     */
    public String handleClassNotExist(FitnessClass fitClass){
        if (Location.toLocation(fitClass.getLocation().name()) == null){
            return fitClass.getLocation() + " - invalid location.";
        }
        boolean foundClass = false;
        // check has class name
        for (int i = 0; i < numClasses; i++){
            if (classes[i].getName().equalsIgnoreCase(fitClass.getName())){
                foundClass = true;
            }
        }
        if(!foundClass) { return fitClass.getName() + " - class does not exist."; }
        boolean foundInstructor = false;
        for (int i = 0; i < numClasses; i++){
            if (classes[i].getInstructor().equalsIgnoreCase(fitClass.getInstructor())) {
                foundInstructor = true;
            }
        }
        if(!foundInstructor) { return fitClass.getInstructor() + " - instructor does not exist."; }
        return fitClass.getName() + " by " + fitClass.getInstructor() + " does not exist at " + fitClass.getLocation().name();
    }

    /**
     * Returns a list of fitness classes at the same time as fitClass
     * @param fitClass FitnessClass to compare to other fitness classes
     * @return array of conflicting classes taking place at the same time
     */
    public FitnessClass[] sameTimeClasses(FitnessClass fitClass){
        int num = numClassesAtTime(fitClass.getTime());
        FitnessClass[] classList = new FitnessClass[num - 1];

        int ptr = 0;
        for(int i=0; i<numClasses; i++){
            FitnessClass compClass = classes[i];
            if (compClass.getTime().equals(fitClass.getTime()) && !compClass.equals(fitClass)){
                classList[ptr] = compClass;
                ptr++;
            }
        }
        return classList;
    }

    /**
     * Get the number of fitness classes in schedule at specified time
     * @param time  time of fitness classes
     * @return  Number of FitnessClass objects
     */
    private int numClassesAtTime(Time time){
        int num = 0;
        for(FitnessClass compClass : classes){
            if (time == compClass.getTime()) {
                    num++;
            }
        }
        return num;
    }

    /**
     * Reads the predefined file classSchedule.txt
     * And loads it into the class schedule.
     */
    public void loadSchedule(){
        try{
            File file  = new File("src/input/classSchedule.txt");
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

            for (int i = 0; i < numClasses && sc.hasNextLine(); i++){
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

            System.out.println("\n-Fitness classes loaded-");
            printClasses();
            System.out.println("-end of class list.\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a fitness class to the class schedule
     * @param fitClass fitness class to the added
     */
    public void addClass(FitnessClass fitClass){
        numClasses++;
        FitnessClass[] classCopy = new FitnessClass[numClasses];
        for (int i = 0; i < numClasses - 1; i++){
            classCopy[i] = classes[i];
        }
        classCopy[numClasses - 1] = fitClass;
        classes = classCopy;
    }
}
