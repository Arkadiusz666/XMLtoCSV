package converter;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.spi.DataFormat;
import structure.Order;
import structure.Product;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by AKrzos on 2016-08-01.
 */
public class ConverterXMLtoCSV {
    private static final String CURRENT_FOLDER_PATH = "data/";
    private static final String CONVERTED_FILES_FOLDER_PATH = "data/converted/";

    public static void saveOrdersToCsvUsingCamelCsv(List<Order> ordersList) throws Exception {
        for (Order order : ordersList) {
            saveSingleOrderToCsvUsingCamelCsv(order);
        }
    }

    public static void saveSingleOrderToCsvUsingCamelCsv(Order order) throws Exception {
        CamelContext context = new DefaultCamelContext();
        final CsvDataFormat csvDataFormat = new CsvDataFormat();
        csvDataFormat.setDelimiter(";");
        List<String> firstColumn = new LinkedList<String>();
        firstColumn.add("id");
        firstColumn.add("price");
        firstColumn.add("quantity");
        csvDataFormat.setHeader(firstColumn);

        context.addRoutes(new RouteBuilder() {

            public void configure() {
                from("direct:toCsv")
                        .marshal(csvDataFormat)
                        .to("file://data/csvCamel?fileName=${property.filename}");
            }
        });

        ProducerTemplate template = context.createProducerTemplate();
        context.start();

        List<Map<String, String>> body = new LinkedList<Map<String, String>>();
        for (Product product : order.getProducts().getProduct()) {
            Map<String, String> singleRow = new LinkedHashMap<String, String>();
            singleRow.put("quantity", String.valueOf(product.getQuantity()));
            singleRow.put("id", product.getId());
            if (product.isDiscountInd()) {
                singleRow.put("price", product.getPrice());
            } else {
                singleRow.put("price", "0");
            }
            singleRow.put("quantity", String.valueOf(product.getQuantity()));
            body.add(singleRow);
        }
        String filename = order.getFileName()+order.getId()+".csv";
        template.sendBodyAndProperty("direct:toCsv", body, "filename", filename);
        Thread.sleep(100);
        context.stop();
    }

    public static void saveOrdersToCsvUsingBindy(List<Order> ordersList) throws Exception {

        CamelContext context = new DefaultCamelContext();
        final DataFormat format = new BindyCsvDataFormat(Product.class);

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("direct:toCsv")
                        .marshal(format)
                        .to("file://data/csvBindy?fileName=${property.filename}");
            }
        });

        ProducerTemplate template = context.createProducerTemplate();

        context.start();

        for (Order order : ordersList) {
            List<Product> body = order.getProducts().getProduct();
            String filename = order.getFileName()+order.getId()+".csv";
            template.sendBodyAndProperty("direct:toCsv", body, "filename", filename);
        }
        Thread.sleep(100);
        context.stop();
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

    public static List<Order> xmlToObject() throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance( "structure" );
        Unmarshaller u = jc.createUnmarshaller();
        List<Order> orderList = new ArrayList<Order>();

        for (File file : getFilesInFolder(CURRENT_FOLDER_PATH)) {
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

    public static File[] getFilesInFolder(String path) {
        File sourceFolder = new File(path);
        File[] filesInSourceFolder = sourceFolder.listFiles();
        return filesInSourceFolder;
    }
}
