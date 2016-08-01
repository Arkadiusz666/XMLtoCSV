import converter.ConverterXMLtoCSV;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import structure.Order;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by AKrzos on 2016-08-01.
 */
public class Tests {
    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Test
    public void testLoadingXml() {

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
        //
    }
}
