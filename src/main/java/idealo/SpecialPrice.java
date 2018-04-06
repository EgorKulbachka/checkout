package idealo;

public class SpecialPrice {

    private final int count;
    private final int price;

    public static SpecialPrice ofCountForPrice(int count, int price) {
        return new SpecialPrice(count, price);
    }

    private SpecialPrice(int count, int price) {
        this.count = count;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }
}
