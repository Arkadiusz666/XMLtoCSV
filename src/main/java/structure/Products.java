package structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKrzos on 2016-07-25.
 */
public class Products {
    private List<Product> product = new ArrayList<Product>();

    public Products() {
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Products{" +
                "product=" + product +
                '}';
    }
}
