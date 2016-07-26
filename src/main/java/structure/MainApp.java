package structure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKrzos on 2016-07-25.
 */
public class MainApp {
    private static final String CURRENT_FOLDER_PATH = "data/";

    public static void main(String args[]) throws Exception {

        objectToXML(xmlToObject());
    }

    private static List<Order> xmlToObject() throws Exception {

        JAXBContext jc = JAXBContext.newInstance( "structure" );
        Unmarshaller u = jc.createUnmarshaller();
        List<Order> orderList = new ArrayList<Order>();

        for (File file : getFilesInCurrentFolder()) {
            if (file.getName().matches(".*\\.xml")) { //todo double check formula (end line?)
                FileInputStream fileInputStream = new FileInputStream("data/"+file.getName());
                Order order = (Order)u.unmarshal( fileInputStream );
                System.out.println(order.getId());
                orderList.add(order);
            }
        }
        return orderList;
    }
    private static void objectToXML(List<Order> orderList) throws Exception {
        JAXBContext jc = JAXBContext.newInstance( "structure" );

        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        for (Order order : orderList) {
            m.marshal( order, System.out );
        }
    }

    private static File[] getFilesInCurrentFolder() {
        File sourceFolder = new File(CURRENT_FOLDER_PATH);
        File[] filesInSourceFolder = sourceFolder.listFiles();
        return filesInSourceFolder;
    }
}
