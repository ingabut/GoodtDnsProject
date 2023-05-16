package dnstest.tests;

import dnstest.base.BaseTests;
import org.testng.annotations.Test;

public class DnsTests extends BaseTests {

    @Test(description = "Check amount of items are equal to total amount")
    public void testAmounts(){
        mp.assertAmounts();
    }
}
