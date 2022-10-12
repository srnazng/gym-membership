package models;

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

    /**
     * Create Schedule object that includes a list of predetermined Fitness classes
     */
    public ClassSchedule(){
        this.numClasses = 0;
    }

    /**
     * Print the list of fitness classes
     */
    public void printSchedule(){
        System.out.println("\n-Fitness classes-");
        for(int i = 0; i < classList.length; i++){
            System.out.println(classList[i]);
        }
        System.out.println();
    }

    /**
     * Get the fitness class in the schedule given the name of the class
     * @param className Name of the fitness class
     * @return the fitness class with name className
     */
    public FitnessClass getClass(String className){
        for(FitnessClass fitClass : classList){
            if(fitClass.getName().equalsIgnoreCase(className)){
                return fitClass;
            }
        }
        return null;
    }

    /**
     * Checks if the gym schedule includes a fitness class given the name of the class
     * @param className Name of the class
     * @return true if schedule contains class of className, false otherwise
     */
    public boolean hasClass(String className){
        return getClass(className) != null;
    }

    /**
     * Returns a fitness class that the member is registered for
     * that occurs the same time as the given class
     * @param member Member taking the class
     * @param fitClass FitnessClass to compare to other fitness classes taken by member
     * @return conflicting class that contains member, null if no class conflict
     */
    public FitnessClass sameTimeClass(Member member, FitnessClass fitClass){
        for(FitnessClass compClass : classList){
            if (!compClass.getName().equals(fitClass.getName())){
                if (compClass.contains(member) &&
                        fitClass.getTime() == compClass.getTime()) {
                    return compClass;
                }
            }
        }
        return null;
    }
}
