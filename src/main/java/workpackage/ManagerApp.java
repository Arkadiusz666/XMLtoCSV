package workpackage;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileOutputStream;

public class ManagerApp {
    public static void main(String args[]) throws Exception {
        // enable the next line to print xml to the console
        //objectToXML();

        // copy the output to the console to manager.xml

        // enable the following line to read from manager.xml in the root of the project.
        xmlToObject();
//        objectToXML();
    }

    /**
     * Marshall information into an xml file.
     */
    private static void objectToXML() throws Exception {
        JAXBContext jc = JAXBContext.newInstance( "workpackage" );
        Manager manager = new Manager();
        manager.setManagerId(1);
        manager.setFirstName("Neeraj");
        manager.setLastName("Verma");
        manager.getEmployees().add(new Employee(1, "John", "Doe"));
        manager.getEmployees().add(new Employee(2, "Jane", "Doe"));

        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal( manager, System.out );

    }

    private static void xmlToObject() throws Exception {
        JAXBContext jc = JAXBContext.newInstance( "workpackage" );
        Unmarshaller u = jc.createUnmarshaller();
        Manager manager = (Manager)u.unmarshal( ClassLoader.getSystemResourceAsStream("sheet.xml") );
        System.out.println(manager);
    }

}