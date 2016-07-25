package main;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by AKrzos on 2016-07-23.
 */
public class MyRouteBuilder extends RouteBuilder {
    public void configure() throws Exception {
        from("file://data/")
                .process(new MyProcessor())
                .transform(body().regexReplaceAll("<\\?.*\\?>", "id;price;quantiti;discount"))
                .transform(body().regexReplaceAll("<order.*>\n", ""))
                .transform(body().regexReplaceAll(".<products>\n", ""))//to trzeba lepiej napisac!!! najlepiej z ^
                .transform(body().regexReplaceAll("...*<product id=\"", ""))//to trzeba lepiej napisac!!! najlepiej z ^
                .transform(body().regexReplaceAll("\" price=\"", ";"))
                .transform(body().regexReplaceAll("\" quantity=\"", ";"))
                .transform(body().regexReplaceAll("\" discountInd=\"", ";"))
                .transform(body().regexReplaceAll("\"/>", ""))
                .to("file://data/converted/");
    }
}


