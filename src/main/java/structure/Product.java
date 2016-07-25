package structure;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by AKrzos on 2016-07-25.
 */
public class Product {
    private String id;
    private String price;
    private int quantity;
    private boolean discountInd;

    public Product() {
    }

    public String getId() {
        return id;
    }

    @XmlAttribute(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    @XmlAttribute(name = "price")
    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    @XmlAttribute(name = "quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDiscountInd() {
        return discountInd;
    }

    @XmlAttribute(name = "discountInd")
    public void setDiscountInd(boolean discountInd) {
        this.discountInd = discountInd;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", discountInd=" + discountInd +
                '}' +"\n";
    }
}
