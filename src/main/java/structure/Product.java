package structure;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by AKrzos on 2016-07-25.
 */
@CsvRecord(separator = ";")

public class Product {
    @DataField(pos=1)
    private String id;
    @DataField(pos=2)
    private String price;
    @DataField(pos=3)
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
