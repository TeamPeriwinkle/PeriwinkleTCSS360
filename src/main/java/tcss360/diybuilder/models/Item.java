package tcss360.diybuilder.models;

public class Item {

    private String name;

    private double price;

    private int unit;

    public Item(String theName, double thePrice, int theUnit) {
        name = theName;
        price = thePrice;
        unit = theUnit;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getUnit() {
        return unit;
    }

    public void setName(String theName) {
        name = theName;
    }

    public void setPrice(double thePrice) {
        price = thePrice;
    }

    public void setUnit(int theUnit) {
        unit = theUnit;
    }

    public double getTotalCost() {
        return price * unit;
    }

    @Override
    public String toString() {
        return String.format("%s, price per unit: $%.2f, total unit: %d, total price: $%.2f", name, price, unit, price * unit);
    }

}


