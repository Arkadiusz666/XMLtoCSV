import converters.ConverterXMLtoCSV;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;
import structure.Order;
import structure.Product;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by AKrzos on 2016-08-01.
 */
public class Tests {
    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;
    private Order testOrder = new Order("name", "id");

    @Before
    public void prepareTestXmlFile() {
        String content =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<order id=\"id\" fileName=\"name\">\n" +
                        "    <products>\n" +
                        "        <product id=\"id1\" price=\"11\" quantity=\"1\" discountInd=\"false\"/>\n" +
                        "        <product id=\"id2\" price=\"22\" quantity=\"2\" discountInd=\"true\"/>\n" +
                        "        <product id=\"id3\" price=\"33\" quantity=\"3\" discountInd=\"false\"/>\n" +
                        "    </products>\n" +
                        "</order>";
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("data/testData/test.xml"), "utf-8"))) {
                writer.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void prepareTestOrder() {
        testOrder.getProducts().getProduct().add(new Product("id1", "11", "1", false));
        testOrder.getProducts().getProduct().add(new Product("id2", "22", "2", true));
        testOrder.getProducts().getProduct().add(new Product("id3", "33", "3", false));
    }

    @Test
    public void testLoadingXml() {
        List<Order> orderListLoaded = new LinkedList<>();
        List<Order> orderListExpected = new LinkedList<>();
        orderListExpected.add(testOrder);
        try {
            orderListLoaded = ConverterXMLtoCSV.loadXmlToObject("data/testData/");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        assertEquals(orderListLoaded.toString(), orderListExpected.toString());
    }

    @Test
    public void csvFileSavedWithTheRightName() throws Exception {
        Order order = new Order();
        order.setId("ID");
        order.setFileName("FILENAME");

        ConverterXMLtoCSV.saveSingleOrderToCsvUsingCamelCsv(order);

        File target = new File("data/csvCamel/FILENAMEID.csv");
        assertTrue("File not saved correctly", target.exists());

        //todo czy dobrze do pojo laduje
        //todo czy zapisuje odpowiednio do csv czy id;asd;das
        //todo czy czyta tylko .xml
        //nieprawidlowe dane itn>string
        //co jesli nie ma kolumny?
    }
}
