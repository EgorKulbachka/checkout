package idealo;

import java.util.Optional;

public class PricingRule {

    private final String sku;
    private final int price;
    private final SpecialPrice specialPrice;

    public PricingRule(String sku, int price) {
        this(sku, price, null);
    }

    public PricingRule(String sku, int price, SpecialPrice specialPrice) {
        this.sku = sku;
        this.price = price;
        this.specialPrice = specialPrice;
    }

    public String getSku() {
        return sku;
    }

    public int getPrice() {
        return price;
    }

    public Optional<SpecialPrice> getSpecialPrice() {
        return Optional.ofNullable(specialPrice);
    }
}
