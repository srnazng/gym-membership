package models;

public class FitnessClass {
    private String name;
    private String instructor;
    private Time time;

    FitnessClass(String name, String instructor, Time time){
        this.name = name;
        this.instructor = instructor;
        this.time = time;
    }
}
