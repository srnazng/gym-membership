package tests;
import models.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PremiumTest {
    private static final double EXPECTED_PREMIUM_FEE = 659.89;

    @Test
    void testPremiumFee() {
        //Premium fee should be 659.89, as seen in the the expected output
        Premium premiumMember = new Premium("John", "Doe", new Date("12/12/2012"), Location.toLocation("Franklin"));
        double premiumFee = premiumMember.membershipFee();
        assertEquals(premiumFee, EXPECTED_PREMIUM_FEE);
    }
}