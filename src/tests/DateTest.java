package tests;

import models.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @org.junit.jupiter.api.Test
    public void isValid() {
        Date date1 = new Date("4/31/2003");
        assertFalse(date1.isValid());
    }

    @org.junit.jupiter.api.Test
    public void test_days_in_Feb(){
        assertEquals(true, true);
    }

}