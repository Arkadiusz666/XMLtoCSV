package structure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
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

        for (File file : getFilesInCurrentFolder()) {
            if (file.getName().matches(".*.xml")) { //todo double check formula (end line?)
                FileInputStream fileInputStream = new FileInputStream("data/"+file.getName());
                Order order = (Order)u.unmarshal( fileInputStream );
                System.out.println(order.getId());
            }
        }
    }

    private static File[] getFilesInCurrentFolder() {
        File sourceFolder = new File("data/");
        File[] filesInSourceFolder = sourceFolder.listFiles();
        return filesInSourceFolder;
    }
}
