package structure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;

/**
 * Created by AKrzos on 2016-07-25.
 */
public class MainApp {
    public static void main(String args[]) throws Exception {

        xmlToObject();
    }

    private static void xmlToObject() throws Exception {
        JAXBContext jc = JAXBContext.newInstance( "structure" );
        Unmarshaller u = jc.createUnmarshaller();
        FileInputStream fileInputStream = new FileInputStream("sheet.xml");
        Order order = (Order)u.unmarshal( fileInputStream );
        System.out.println(order);
    }
}
