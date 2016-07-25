package structure;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Created by AKrzos on 2016-07-25.
 */
@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Order createOrder() {
        return new Order();
    }
}