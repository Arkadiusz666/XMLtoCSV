package components;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import processors.FixPriceProcessor;
import processors.ObjectToCsvProcessor;
import processors.XmlToObjectProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKrzos on 2016-08-08.
 */
public class MyCamelRoute extends RouteBuilder {
    final String INPUT_FILES_PATH = "//data";
    final String OUTPUT_FILES_PATH = "//data/csv";
    final CsvDataFormat csvDataFormat = new CsvDataFormat();
    @Override
    public void configure() throws Exception {
        configureCsvDataFormat(csvDataFormat);

        from("file:"+INPUT_FILES_PATH+"?noop=true") //todo only xml files
                .process(new XmlToObjectProcessor())
                .process(new FixPriceProcessor())
                .process(new ObjectToCsvProcessor())
                .marshal(csvDataFormat)
                .to("file:"+OUTPUT_FILES_PATH+"?fileName=${header.filename}");

    }
    private static void configureCsvDataFormat(org.apache.camel.model.dataformat.CsvDataFormat csvDataFormat) {
        csvDataFormat.setDelimiter(";");
        List<String> firstColumn = new LinkedList<String>();
        firstColumn.add("id");
        firstColumn.add("price");
        firstColumn.add("quantity");
        csvDataFormat.setHeader(firstColumn);
    }
}
