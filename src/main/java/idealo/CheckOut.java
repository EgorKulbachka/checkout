package idealo;

import idealo.exceptions.SkuNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class CheckOut {

    private final Map<String, PricingRule> skuPricingRules;
    private final Map<String, Integer> basket = new HashMap<>();

    public CheckOut(List<PricingRule> pricingRules) {
        Map<String, PricingRule> rulesMap = pricingRules.stream().collect(toMap(PricingRule::getSku, Function.identity()));
        this.skuPricingRules = unmodifiableMap(rulesMap);
    }

    public void scan(String sku) {
        if (!skuPricingRules.containsKey(sku)) {
            throw new SkuNotFoundException("Unknown product sku: " + sku);
        }

        basket.merge(sku, 1, (a, b) -> a + b);
    }

    public int total() {
        return basket.entrySet().stream()
                .mapToInt(item -> calculatePrice(item.getKey(), item.getValue()))
                .sum();
    }

    private int calculatePrice(String sku, int itemsCount) {
        PricingRule pricingRule = skuPricingRules.get(sku);

        int specialPrice = applySpecialPrice(pricingRule, itemsCount);
        int normalPrice = applyNormalPrice(pricingRule, itemsCount);

        return specialPrice + normalPrice;
    }

    private int applySpecialPrice(PricingRule pricingRule, int count) {
        return pricingRule.getSpecialPrice()
                .map(specialPriceRule -> calculateSpecialPrice(specialPriceRule, count))
                .orElse(0);
    }

    private int calculateSpecialPrice(SpecialPrice specialPrice, int count) {
        int specialPriceMatches = count / specialPrice.getCount();
        return specialPriceMatches * specialPrice.getPrice();
    }

    private int applyNormalPrice(PricingRule pricingRule, int count) {
        int normalPriceCount = pricingRule.getSpecialPrice()
                .map(specialPriceRule -> count % specialPriceRule.getCount())
                .orElse(count);

        return normalPriceCount * pricingRule.getPrice();
    }
}
