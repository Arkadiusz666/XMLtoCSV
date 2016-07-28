package structure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by AKrzos on 2016-07-25.
 */
@XmlRootElement
public class Order {
    private String id;
    private String fileName;
    private Products products;

    public Order() {
    }

    public Order(Products products, String fileName, String id) {
        this.products = products;
        this.fileName = fileName;
        this.id = id;
    }
    public String getId() {
        return id;
    }
    @XmlAttribute(name = "id")
    public void setId(String id) {
        this.id = id;
    }
    public String getFileName() {
        return fileName;
    }
    @XmlAttribute(name = "fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", products=" + products +
                '}';
    }
}
