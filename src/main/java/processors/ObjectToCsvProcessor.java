package processors;

import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.CsvDataFormat;
import structure.Order;
import structure.Product;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by AKrzos on 2016-08-09.
 */
public class ObjectToCsvProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Order order = exchange.getIn().getBody(Order.class);

        List<Map<String, String>> body = new LinkedList<Map<String, String>>();
        for (Product product : order.getProducts().getProduct()) {
            Map<String, String> singleRow = fillSingleProductWithData(product);
            body.add(singleRow);
        }
        exchange.getIn().setBody(body);
        String filename = order.getFileName() + order.getId() + ".csv";
        exchange.getIn().setHeader("filename", filename);

    }

    private static Map<String, String> fillSingleProductWithData(Product product) {
        Map<String, String> singleRow = new LinkedHashMap<String, String>();
        singleRow.put("quantity", String.valueOf(product.getQuantity()));
        singleRow.put("id", product.getId());
        singleRow.put("price", product.getPrice());
        singleRow.put("quantity", String.valueOf(product.getQuantity()));
        return singleRow;
    }
}
