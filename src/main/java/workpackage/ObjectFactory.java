package workpackage;

/**
 * Created by AKrzos on 2016-07-25.
 */
import structure.Order;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Manager createCustomer() {
        return new Manager();
    }
}
