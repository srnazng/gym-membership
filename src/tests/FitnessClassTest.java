package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import models.*;

import java.io.FileNotFoundException;

class FitnessClassTest {
    private static MemberDatabase database;
    private static ClassSchedule schedule;
    @BeforeAll()
    static void prepareTest() throws FileNotFoundException {
        database = new MemberDatabase();
        database.loadMembers();
        schedule = new ClassSchedule();
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
    void addGuest() {
        //FitnessClass testClass =
    }

    @Test
    void dropClass() {
    }

    @Test
    void dropGuestClass() {
    }
}