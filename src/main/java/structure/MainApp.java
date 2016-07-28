package structure;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.spi.DataFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by AKrzos on 2016-07-25.
 */

//SubTask1:
//        You have to create Apache Camel application for transferring data from XML files to CSV.
//        Details:
//        - Use Apache Camel with Spring (SpringBoot also can be used). You can use XML or java configuration (We use both in our projects).
//        Steps:

//        - Create Apache Camel application with following steps:
//        - Transfer XML files to CSV files (to another directory):
//        - 1 Order = 1 CSV file
//        - Name of CSV file should be like order.id + order.fileName+".csv"
//        - CSV file should include all products with following columns: id, price, quantity, discount (not "discountInd")
//        - If discount = false then value of column "price" should be "0"
//
//        SubTask2:
//        Using of splitter and agregator
//        Steps:
//        - Add following steps:
//        - Save each product to XML file with fileName order.id + product.id + ".xml"
//        - XML file should include information of: product.id, price, quantity, discount, amount, discountInd
//
//        - Save each order to XML file with fileName order.id + "_amt.xml"
//        - XML file should include information of: order.id, totalAmount (amount of all included products)
//
//        SubTask3:
//        Using of CXF-RS & thread pool
//        Steps:
//        - Add following steps:
//        - After product amount calculation step add delay 1s
//        - Use thread pool with 5 threads for product amount calculation (including delay)
//        - Create CXF-RS endpoint for providing information of last 5 calculated products with average amount
//
//        SubTask4:
//        Using of Dynamic routing
//        Steps:
//        - Add following steps:
//        - Save all products with discountInd=true to XML (should be done in SubTask2)
//        - Save all products with discountInd=false to CSV (fields parameters should be like in XML)
//
//        Each SubTask should include unit/integration testing
public class MainApp {
    private static final String CURRENT_FOLDER_PATH = "data/";
    private static final String CONVERTED_FILES_FOLDER_PATH = "data/converted/";

    public static void main(String args[]) throws Exception {


        List<Order> ordersList = xmlToObject();

//        SaveOrdersToCSVBindy(ordersList);

        SaveOrdersToCSVCamel(ordersList);

    }

    private static void SaveOrdersToCSVCamel(List<Order> ordersList) throws Exception {


        CamelContext context = new DefaultCamelContext();
        final CsvDataFormat csvDataFormat = new CsvDataFormat();
        csvDataFormat.setDelimiter(";");
//        csvDataFormat.setHeader(asList("Id", "Q", "Price"));

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:toCsv")
                        .marshal(csvDataFormat)
//                        .convertBodyTo(String.class)
                        .to("file://data/csvCamel");
            }
        });

        ProducerTemplate template = context.createProducerTemplate();
        context.start();

        for (Order order : ordersList) {
            List<Map<String, String>> body = new LinkedList<Map<String, String>>();
            for (Product product : order.getProducts().getProduct()) {
                Map<String, String> singleRow = new LinkedHashMap<String, String>();
                singleRow.put("quantity", String.valueOf(product.getQuantity()));
                singleRow.put("id", product.getId());
                singleRow.put("price", product.getPrice());
                body.add(singleRow);
            }
            template.sendBody("direct:toCsv", body);
        }

        Thread.sleep(1000);
        context.stop();


    }

    private static void SaveOrdersToCSVBindy(List<Order> ordersList) throws Exception {
        CamelContext context = new DefaultCamelContext();
        final DataFormat format = new BindyCsvDataFormat(Product.class);

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:toCsv")
                        .marshal(format)
                        .to("file://data/csv");
            }
        });

        ProducerTemplate template = context.createProducerTemplate();

        context.start();

        for (Order order : ordersList) {
            template.sendBody("direct:toCsv", order.getProducts().getProduct());
        }
        Thread.sleep(1000);
        context.stop();
    }
//todo -commented code
//        List<Order> OrdersList = xmlToObject();
//        Map<String, List<String>> productsMap;
//
//        for (Order order : OrdersList) {
//            productsMap= MapProductColumns(order);
////            ObjectToCSV(ProductsMap);
////            template.sendBody("file://data/csv", "Hello");
////            template.sendBody("test-jms:queue:test.queue", productsMap);
//
//        }



    private static void ObjectToCSV(Map<String, List<String>> productsMap) {
    }

    private static Map<String, List<String>> MapProductColumns(Order order) {
        Map<String, List<String>> columnsMap = new LinkedHashMap<String, List<String>>();
        List<String> idList = new LinkedList<String>();
        List<String> priceList = new LinkedList<String>();
        List<String> quantityList = new LinkedList<String>();
        List<String> discountList = new LinkedList<String>();
        columnsMap.put("id", idList);
        columnsMap.put("price", priceList);
        columnsMap.put("quantity", quantityList);
        columnsMap.put("discount", discountList);
        for (Product product : order.getProducts().getProduct()) {
            idList.add(product.getId());
            priceList.add(product.getPrice());
            quantityList.add(String.valueOf(product.getQuantity()));
        }
        return columnsMap;
    }

    private static List<Order> xmlToObject() throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance( "structure" );
        Unmarshaller u = jc.createUnmarshaller();
        List<Order> orderList = new ArrayList<Order>();

        for (File file : getFilesInCurrentFolder()) {
            if (file.getName().matches(".*\\.xml")) {
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream("data/"+file.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Order order = (Order)u.unmarshal( fileInputStream );
                System.out.println(order.getId());
                orderList.add(order);
            }
        }
        return orderList;
    }
    private static void objectToXML(List<Order> orderList) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance( "structure" );

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File orderFile;
        for (Order order : orderList) {
            String path = CONVERTED_FILES_FOLDER_PATH+order.getId()+".xml";
            orderFile = new File(path);
            System.out.println(order);
            marshaller.marshal( order, orderFile );
        }
    }

//    private static void objectToCSV(List<Order> orderList) throws JAXBException {
//        CsvDataFormat csv = new CsvDataFormat();
//        csv.setDelimiter(";");
//
//        MyRouteBuilder routeBuilder = new MyRouteBuilder();
//        CamelContext context = new DefaultCamelContext();
//    }

    private static File[] getFilesInCurrentFolder() {
        File sourceFolder = new File(CURRENT_FOLDER_PATH);
        File[] filesInSourceFolder = sourceFolder.listFiles();
        return filesInSourceFolder;
    }


}
