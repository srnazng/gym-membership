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
        testClass = new FitnessClass("Pilates", "Jennifer", Time.MORNING, Location.toLocation("Bridgewater"));
    }

    @Test
    void testAddStandardMember() {
        // Standard members must not already be in the database, and must be checking in at the same location
        // as where they live
        Member member1 = new Member("Standard", "A", new Date("12/12/2012"), Location.toLocation("Bridgewater"));
        Member member2 = new Member("Standard", "B", new Date("12/11/2012"), Location.toLocation("Piscataway"));
        Member member3 = new Member("Standard", "C", new Date("12/12/1986"), Location.toLocation("Bridgewater"));

        testClass.add(member1);

        //Member must not already be checked in to class
        assertEquals(testClass.add(member1),
                "Standard A already checked in.\n");

        //Member must be in the same location as class
        assertEquals(testClass.add(member2),
                "Standard B checking in BRIDGEWATER, 08807, SOMERSET - standard membership location restriction.\n");

        //Success
        assertEquals(testClass.add(member3),
                "Standard C checked in PILATES - JENNIFER, 9:30, BRIDGEWATER");
    }

    @Test
    void testAddFamilyMember(){
        // Family and Premium members do not have to be in the same location as class
        Member member1 = new Family("Family", "A", new Date("12/11/2012"), Location.toLocation("Piscataway"));

        //Success
        assertEquals(testClass.add(member1),
                "Family A checked in PILATES - JENNIFER, 9:30, BRIDGEWATER");
    }

    @Test
    void testAddMemberTimeConflict(){
        //A member can't check into two different classes that occur at the same time
        Member member1 = new Family("Standard", "A", new Date("12/12/2012"), Location.toLocation("Franklin"));
        FitnessClass otherClass = schedule.getClass(new FitnessClass("Pilates", "Kim", "Franklin"));
        otherClass.add(member1);
        assertEquals(testClass.add(member1),
              "Time conflict - PILATES - JENNIFER, 9:30, BRIDGEWATER, 08807, SOMERSET\n");

        //Success
        FitnessClass otherClass2 = schedule.getClass(new FitnessClass("Spinning", "Kim", "Franklin"));
        assertEquals(otherClass2.add(member1),
                "Standard A checked in SPINNING - KIM, 14:00, FRANKLIN");
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
        //Test dropping class for member, need to be in checked in list to drop.
        Member member1 = new Member("Standard", "A", new Date("12/12/2012"), Location.toLocation("Bridgewater"));
        Member member2 = new Member("Standard", "B", new Date("12/11/2012"), Location.toLocation("Bridgewater"));
        testClass.add(member1);

        //Cannot drop class if you are not already checked in
        assertEquals(testClass.dropClass(member2), "Standard B did not check in.");

        //Success
        assertEquals(testClass.dropClass(member1), "Standard A done with the class.");
    }

    @Test
    void dropGuestClass() {
        //Test dropping class for guest of member, need to be in checked in list to drop.
        Member member1 = new Family("Family", "A", new Date("12/12/2012"), Location.toLocation("Bridgewater"));
        Member member2 = new Family("Family", "B", new Date("12/11/2012"), Location.toLocation("Bridgewater"));

        //Success
        testClass.addGuest(member1);
        assertEquals(testClass.dropGuestClass(member1),
                "Family A Guest done with the class.");

        //A guest cannot drop class if not checked in under member
        assertEquals(testClass.dropGuestClass(member2),
                "Family B Guest did not check in.\n");
    }
}