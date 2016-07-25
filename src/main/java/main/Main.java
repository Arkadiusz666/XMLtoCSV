package main;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import structure.Order;
import structure.Products;

/**
 * Created by AKrzos on 2016-07-23.
 */
public class Main {
    public static void main(String[] args) {
//        Order order = new Order("ord_1469201617839", "dTQ5vSBRNP");
        MyRouteBuilder routeBuilder = new MyRouteBuilder();
        CamelContext context = new DefaultCamelContext();

        try {
            context.addRoutes(routeBuilder);
            context.start();
            Thread.sleep(6 * 1000);
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
