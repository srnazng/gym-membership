package tests;

import models.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {
    @org.junit.jupiter.api.Test
    public void testMonthRange(){
        Date date1 = new Date("-1/1/2022");
        assertFalse(date1.isValid());

        Date date2 = new Date("0/1/2022");
        assertFalse(date2.isValid());

        Date date3 = new Date("1/1/2022");
        assertTrue(date3.isValid());

        Date date4 = new Date("13/1/2022");
        assertFalse(date4.isValid());
    }
    @org.junit.jupiter.api.Test
    public void testDayRangeInLongMonth(){
        Date date1 = new Date("1/-1/2003");
        assertFalse(date1.isValid());

        Date date2 = new Date("1/0/2003");
        assertFalse(date2.isValid());

        Date date3 = new Date("1/31/2003");
        assertTrue(date3.isValid());

        Date date4 = new Date("10/32/2023");
        assertFalse(date4.isValid());
    }

    @org.junit.jupiter.api.Test
    public void testDayRangeInShortMonth(){
        Date date1 = new Date("4/-1/2022");
        assertFalse(date1.isValid());

        Date date2 = new Date("4/0/2022");
        assertFalse(date2.isValid());

        Date date3 = new Date("4/30/2022");
        assertTrue(date3.isValid());

        Date date4 = new Date("6/31/2003");
        assertFalse(date4.isValid());
    }

    public void testYearRange(){
        Date date1 = new Date("4/1/-1");
        assertFalse(date1.isValid());

        Date date2 = new Date("4/1/0");
        assertFalse(date2.isValid());

        Date date3 = new Date("4/1/1");
        assertTrue(date3.isValid());
    }


    @org.junit.jupiter.api.Test
    public void testYearNotMultipleOfFour(){
        Date date1 = new Date("2/28/2009");
        assertTrue(date1.isValid());

        Date date2 = new Date("2/29/2009");
        assertFalse(date2.isValid());
    }

    @org.junit.jupiter.api.Test
    public void testYearMultipleOfFourNotHundred(){
        Date date1 = new Date("2/29/2008");
        assertTrue(date1.isValid());

        Date date2 = new Date("2/30/2008");
        assertFalse(date2.isValid());
    }

    @org.junit.jupiter.api.Test
    public void testYearMultipleofHundredNotFourHundred(){
        Date date1 = new Date("2/28/1900");
        assertTrue(date1.isValid());

        Date date2 = new Date("2/29/1900");
        assertFalse(date2.isValid());
    }

    @org.junit.jupiter.api.Test
    public void testYearMultipleOfFourHundred(){
        Date date1 = new Date("2/29/2008");
        assertTrue(date1.isValid());

        Date date2 = new Date("2/30/2008");
        assertFalse(date2.isValid());
    }

}