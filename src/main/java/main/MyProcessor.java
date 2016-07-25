package main;

import org.apache.camel.Exchange;

/**
 * Created by AKrzos on 2016-07-23.
 */
public class MyProcessor implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        System.out.println("processing stuff...");
    }
}
