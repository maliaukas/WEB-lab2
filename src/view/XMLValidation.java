package view;

import controller.xml.DOMParserCoffee;
import controller.xml.SAXParserCoffee;
import controller.xml.StAXParserCoffee;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidation {
    private static final Logger logger =
            LogManager.getLogger(StAXParserCoffee.class.getName());

    public static void main(String[] args) {
        File schemaFile = new File("xml\\CoffeeSchema.xsd");
        File sourceFile = new File("xml\\Coffee.xml");

        Source xmlFile = new StreamSource(sourceFile);
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();

            validator.validate(xmlFile);
            logger.info(xmlFile.getSystemId() + " is valid");
        } catch (SAXException e) {
            logger.error(xmlFile.getSystemId() + " is NOT valid. Reason:" + e);
            return;
        } catch (IOException e) {
            logger.info("IOException ", e);
            return;
        }

        try {
            var coffee = SAXParserCoffee.parse(sourceFile);
            logger.info("SAX parser result: " + coffee);

            coffee = DOMParserCoffee.parse(sourceFile);
            logger.info("DOM parser result: " + coffee);

            coffee = StAXParserCoffee.parse(sourceFile);
            logger.info("StAX parser result: " + coffee);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Error while parsing", e);
        }
    }
}
