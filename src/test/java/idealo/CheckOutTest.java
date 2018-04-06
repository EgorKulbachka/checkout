package idealo;

import idealo.exceptions.SkuNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static java.util.Collections.singletonList;

public class CheckOutTest {

    @Test
    public void emptyCheckOutShouldHaveZeroTotal() {
        CheckOut checkOut = new CheckOut(Collections.emptyList());

        Assert.assertEquals(0, checkOut.total());
    }

    @Test
    public void priceShouldBeAccumulatedInCheckout() {
        PricingRule rule = new PricingRule("A", 40);
        CheckOut checkOut = new CheckOut(singletonList(rule));
        checkOut.scan("A");
        checkOut.scan("A");

        Assert.assertEquals(80, checkOut.total());
    }

    @Test
    public void specialPriceShouldBeCalculated() {
        PricingRule rule = new PricingRule("A", 40, SpecialPrice.ofCountForPrice(3, 100));

        CheckOut checkOut = new CheckOut(singletonList(rule));
        checkOut.scan("A");
        checkOut.scan("A");
        checkOut.scan("A");

        Assert.assertEquals(100, checkOut.total());
    }

    @Test
    public void specialPriceShouldBeAddedWithNormalPrice() {
        PricingRule rule = new PricingRule("A", 40, SpecialPrice.ofCountForPrice(3, 100));

        CheckOut checkOut = new CheckOut(singletonList(rule));
        checkOut.scan("A");
        checkOut.scan("A");
        checkOut.scan("A");
        checkOut.scan("A");

        Assert.assertEquals(140, checkOut.total());
    }

    @Test(expected = SkuNotFoundException.class)
    public void unknownSkuScanShouldThrowException() {
        CheckOut checkOut = new CheckOut(singletonList(new PricingRule("A", 500)));
        checkOut.scan("Z");
    }

}
