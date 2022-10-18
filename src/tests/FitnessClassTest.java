package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import models.*;

import java.io.FileNotFoundException;

class FitnessClassTest {
    private FitnessClass testClass;
    private ClassSchedule schedule;
    @BeforeEach
    public void init() throws FileNotFoundException {
        schedule = GymManager.createTestSchedule();
        schedule.loadSchedule();
    }

    @Test
    void testAddMember() {
        //        Member is added if not already checked in, otherwise is not added
        FitnessClass testClass = new FitnessClass("Spinning", "Emma", Time.MORNING, Location.toLocation("Franklin"));
        Member member1 = new Member("John", "Doe", new Date("12/12/2012"), Location.toLocation("Franklin"));
        Member member2 = new Member("Jane", "Doe", new Date("12/11/2012"), Location.toLocation("Piscataway"));
        Member member3 = new Member("John", "Doe", new Date("12/12/2013"), Location.toLocation("Piscataway"));

        assertEquals(testClass.add(member1), "John Doe checked in SPINNING - EMMA, 9:30, FRANKLIN");
        assertEquals(testClass.add(member1), "John Doe already checked in.\n");
        assertEquals(testClass.add(member2), "Jane Doe checking in FRANKLIN, 08873, SOMERSET - standard membership location restriction.\n");

    }

    @Test
    void testAddStandardGuest() {
        //A member under the Standard plan cannot check in guests
        Member member1 = new Member("Standard", "A", new Date("12/12/2012"), Location.toLocation("Bridgewater"));
        assertEquals(testClass.addGuest(member1), "Standard membership - guest check-in is not allowed.\n");
    }

    @Test
    void testAddFamilyGuest(){
        Member member1 = new Family("Family", "A", new Date("12/11/2012"), Location.toLocation("Bridgewater"));

        //A member under the Family plan only has 1 guest pass
        testClass.addGuest(member1);
        assertEquals(testClass.addGuest(member1), "Family A ran out of guest pass.\n");

        //Once the previous guest is done with the class, the guest pass can again be used
        testClass.dropGuestClass(member1);
        assertEquals(testClass.addGuest(member1), "Family A (guest) checked in PILATES - JENNIFER, 9:30, BRIDGEWATER");
    }

    @Test
    void testAddPremiumGuest(){
        Member member1 = new Premium("Premium", "A", new Date("12/12/1986"), Location.toLocation("Bridgewater"));

        //A member under the Family plan has 3 guest passes
        testClass.addGuest(member1);
        assertEquals(testClass.addGuest(member1), "Premium A (guest) checked in PILATES - JENNIFER, 9:30, BRIDGEWATER");
        assertEquals(testClass.addGuest(member1), "Premium A (guest) checked in PILATES - JENNIFER, 9:30, BRIDGEWATER");
        assertEquals(testClass.addGuest(member1), "Premium A ran out of guest pass.\n");

        //Once the previous guest is done with the class, the guest pass can again be used
        testClass.dropGuestClass(member1);
        assertEquals(testClass.addGuest(member1), "Premium A (guest) checked in PILATES - JENNIFER, 9:30, BRIDGEWATER");
    }

    @Test
    void testAddGuestWrongLocation() {
        //A guest can only be checked in at the gym location where the member is registered
        Member member1 = new Family("Family", "A", new Date("12/11/2012"), Location.toLocation("Piscataway"));
        Member member2 = new Premium("Premium", "A", new Date("12/12/1986"), Location.toLocation("Piscataway"));
        assertEquals(testClass.addGuest(member1), "Family A Guest checking in BRIDGEWATER, 08807, SOMERSET - guest location restriction.\n");
        assertEquals(testClass.addGuest(member2), "Premium A Guest checking in BRIDGEWATER, 08807, SOMERSET - guest location restriction.\n");
    }

    @Test
    void dropClass() {
    }

    @Test
    void dropGuestClass() {
    }
}