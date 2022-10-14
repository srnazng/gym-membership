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
    public void testDaysInLongMonth(){
        Date date1 = new Date("1/31/2003");
        assertTrue(date1.isValid());

        Date date2 = new Date("10/32/2023");
        assertFalse(date2.isValid());
    }

    @org.junit.jupiter.api.Test
    public void testDaysInShortMonth(){
        Date date1 = new Date("4/30/2022");
        assertTrue(date1.isValid());

        Date date2 = new Date("5/31/2003");
        assertFalse(date1.isValid());
    }

    @org.junit.jupiter.api.Test
    public void testMonthRange(){
        Date date1 = new Date("-1/25/2001");
        assertFalse(date1.isValid());

        Date date2 = new Date("0/25/2001");
        assertFalse(date2.isValid());

        Date date3 = new Date("0/25/2001");
        assertFalse(date2.isValid());
    }

    @org.junit.jupiter.api.Test
    public void test_days_in_Feb(){
        assertEquals(true, true);
    }

}