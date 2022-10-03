/**
 * Schedule of fitness classes at the gym
 * @author Jackson Lee, Serena Zeng
 */
package models;

public class Schedule {
    private FitnessClass[] classList;

    public Schedule(){
        this.classList = new FitnessClass[]{
                new FitnessClass("Pilates", "Jennifer", Time.MORNING),
                new FitnessClass("Spinning", "Denise", Time.AFTERNOON),
                new FitnessClass("Cardio", "Kim", Time.AFTERNOON)};
    }

    /**
     * print the class list
     */
    public void printSchedule(){
        System.out.println("\n-Fitness classes-");
        for(int i=0; i<classList.length; i++){
            System.out.println(classList[i]);
        }
        System.out.println();
    }

    public FitnessClass getClass(String className){
        for(FitnessClass fitClass : classList){
            if(fitClass.getName().equalsIgnoreCase(className)){
                return fitClass;
            }
        }
        return null;
    }

    public boolean hasClass(String className){
        return getClass(className) != null;
    }

    /**
     *
     * @param member to find
     * @param fitClass to compare to other fitness classes
     * @return class that contains member and that occurs at the same time.
     */
    public FitnessClass sameTimeClass(Member member, FitnessClass fitClass){
        for(FitnessClass compClass : classList){
            if (!compClass.getName().equals(fitClass.getName())){
                if (compClass.contains(member) && fitClass.getTime() == compClass.getTime()) return compClass;
            }
        }
        return null;
    }
}
