package processors;

import org.apache.camel.Exchange;
import structure.Order;
import structure.Product;

/**
 * Created by AKrzos on 2016-08-09.
 */
public class FixPriceProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Order order = exchange.getIn().getBody(Order.class);
        for (Product product : order.getProducts().getProduct()) {
            if (product.isDiscountInd()) {
                product.setPrice("0");
                System.out.println("Rzeczy!");
            }
        }
        System.out.println(order.getId()+"price fixed!");
        exchange.getIn().setBody(order);
    }
}
