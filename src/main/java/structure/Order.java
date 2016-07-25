package structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by AKrzos on 2016-07-23.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String fileName;

    public Order(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
}
