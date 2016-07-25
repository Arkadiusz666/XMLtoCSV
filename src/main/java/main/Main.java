package main;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import structure.Order;
import structure.Products;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * Created by AKrzos on 2016-07-23.
 */
public class Main {
    public static void main(String[] args) {
        try {
            xmlToObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void xmlToObject() throws Exception {
        JAXBContext jc = JAXBContext.newInstance( "factory" );
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Order order = (Order)unmarshaller.unmarshal(ClassLoader.getSystemResourceAsStream("sheet.xml"));
        System.out.println(order);
    }
}
