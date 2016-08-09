package processors;

import org.apache.camel.Exchange;
import structure.Order;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by AKrzos on 2016-08-09.
 */
public class XmlToObjectProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        File body =  exchange.getIn().getBody(File.class);
        JAXBContext jc = JAXBContext.newInstance("structure");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Order order = (Order)unmarshaller.unmarshal(body);
        System.out.println(order.getFileName());

    }
}
