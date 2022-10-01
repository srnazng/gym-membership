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
        System.out.println("-Fitness classes-");
        for(int i=0; i<classList.length; i++){
            System.out.println(classList[i]);
        }
    }
}
