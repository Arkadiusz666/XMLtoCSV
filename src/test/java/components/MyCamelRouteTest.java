package components;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AKrzos on 2016-08-08.
 */
public class MyCamelRouteTest {
    MyCamelRoute route = new MyCamelRoute();

    @Test
    public void name() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(route);
        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}