package main;

import converters.ConverterXMLtoCSV;
import structure.Order;

import java.util.*;

/**
 * Created by AKrzos on 2016-07-25.
 */

//SubTask1:
//        You have to create Apache Camel application for transferring data from XML files to CSV.
//        Details:
//        - Use Apache Camel with Spring (SpringBoot also can be used). You can use XML or java configuration (We use both in our projects).
//        Steps:

//        - Create Apache Camel application with following steps:
//        - Transfer XML files to CSV files (to another directory):
//        - 1 Order = 1 CSV file
//        - Name of CSV file should be like order.id + order.fileName+".csv"
//        - CSV file should include all products with following columns: id, price, quantity, discount (not "discountInd")
//        - If discount = false then value of column "price" should be "0"
//
//        SubTask2:
//        Using of splitter and agregator
//        Steps:
//        - Add following steps:
//        - Save each product to XML file with fileName order.id + product.id + ".xml"
//        - XML file should include information of: product.id, price, quantity, discount, amount, discountInd
//
//        - Save each order to XML file with fileName order.id + "_amt.xml"
//        - XML file should include information of: order.id, totalAmount (amount of all included products)
//
//        SubTask3:
//        Using of CXF-RS & thread pool
//        Steps:
//        - Add following steps:
//        - After product amount calculation step add delay 1s
//        - Use thread pool with 5 threads for product amount calculation (including delay)
//        - Create CXF-RS endpoint for providing information of last 5 calculated products with average amount
//
//        SubTask4:
//        Using of Dynamic routing
//        Steps:
//        - Add following steps:
//        - Save all products with discountInd=true to XML (should be done in SubTask2)
//        - Save all products with discountInd=false to CSV (fields parameters should be like in XML)
//
//        Each SubTask should include unit/integration testing
public class MainApp {
    private static final String DATA_FOLDER_PATH = "data/";
    private static final String CONVERTED_FILES_FOLDER_PATH = "data/converted/";

    public static void main(String args[]) throws Exception {

        List<Order> ordersList = ConverterXMLtoCSV.loadXmlToObject(DATA_FOLDER_PATH);

        ConverterXMLtoCSV.saveOrdersToCsvUsingBindy(ordersList);

        ConverterXMLtoCSV.saveOrdersToCsvUsingCamelCsv(ordersList);
    }
}
