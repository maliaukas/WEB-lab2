package controller.xml;

import model.CoffeeException;
import model.builder.*;
import model.coffee.Coffee;
import model.coffee_enums.CoffeeBeansProcessing;
import model.coffee_enums.GroundCoffeeGrinding;
import model.coffee_enums.InstantCoffeeType;
import model.coffee_enums.Roasting;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DOMParserCoffee {
    private static final Logger logger =
            LogManager.getLogger(DOMParserCoffee.class.getName());
    private static final ArrayList<Coffee> coffee = new ArrayList<>();
    private static String name;
    private static String country;
    private static double weight;

    public static List<Coffee> parse(File inputFile) throws
            ParserConfigurationException,
            SAXException, IOException {
        logger.info("DOM parsing started...");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(inputFile);


        doc.getDocumentElement().normalize();

        NodeList coffeeBeans = doc.getElementsByTagName("coffeeBeans");
        NodeList groundCoffee = doc.getElementsByTagName("groundCoffee");
        NodeList instantCoffeeJars = doc.getElementsByTagName("instantCoffeeJars");
        NodeList instantCoffeeSticks = doc.getElementsByTagName("instantCoffeeSticks");

        for (int i = 0; i < coffeeBeans.getLength(); ++i) {
            Node node = coffeeBeans.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                setCommonParameters(element);
                CoffeeBeansProcessing processing = CoffeeBeansProcessing.valueOf(getText(element, "processing"));

                CoffeeBeansBuilder beansBuilder = new CoffeeBeansBuilder
                        (weight, country, name, processing);
                setParameters(element, beansBuilder);
                addCoffee(beansBuilder);
            }
        }

        for (int i = 0; i < groundCoffee.getLength(); ++i) {
            Node node = groundCoffee.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                setCommonParameters(element);
                GroundCoffeeGrinding grinding = GroundCoffeeGrinding.valueOf(getText(element, "grinding"));

                GroundCoffeeBuilder groundCoffeeBuilder = new GroundCoffeeBuilder
                        (weight, country, name, grinding);
                setParameters(element, groundCoffeeBuilder);
                addCoffee(groundCoffeeBuilder);
            }
        }

        InstantCoffeeType type;
        for (int i = 0; i < instantCoffeeJars.getLength(); ++i) {
            Node node = instantCoffeeJars.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                setCommonParameters(element);
                double jarVolume = Double.parseDouble(getText(element, "jarVolume"));
                type = InstantCoffeeType.valueOf(getText(element, "type"));

                InstantCoffeeJarsBuilder jarsBuilder = new InstantCoffeeJarsBuilder
                        (weight, country, name, jarVolume, type);

                setParameters(element, jarsBuilder);
                addCoffee(jarsBuilder);
            }
        }

        for (int i = 0; i < instantCoffeeSticks.getLength(); ++i) {
            Node node = instantCoffeeSticks.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                setCommonParameters(element);
                int sticksNumber = Integer.parseInt(getText(element, "sticksNumber"));
                type = InstantCoffeeType.valueOf(getText(element, "type"));

                InstantCoffeeSticksBuilder sticksBuilder = new InstantCoffeeSticksBuilder
                        (weight, country, name, sticksNumber, type);

                setParameters(element, sticksBuilder);
                addCoffee(sticksBuilder);
            }
        }
        logger.info("DOM parsing finished!");
        return coffee;
    }

    private static void setCommonParameters(Element element) {
        name = getText(element, "name");
        country = getText(element, "country");
        weight = Double.parseDouble(getText(element, "weight"));
    }

    private static void setParameters(Element element, CoffeeBuilder builder) {
        builder.setPricePerKilo(new BigDecimal(getText(element, "pricePerKilo")));
        builder.setArabicaPercent(Integer.parseInt(getText(element, "arabicaPercent")));
        builder.setRoasting(Roasting.valueOf(getText(element, "roasting")));
        try {
            builder.setProductionDate(new SimpleDateFormat("dd-MM-yyyy").parse(getText(element, "productionDate")));
        } catch (ParseException e) {
            logger.error("Error while parsing date " + e);
        }
    }

    private static String getText(Element element, String name) {
        return element.getElementsByTagName(name).item(0).getTextContent();
    }

    private static void addCoffee(CoffeeBuilder builder) {
        try {
            var c = builder.build();
            coffee.add(c);
            logger.debug("Added coffee " + c);
        } catch (CoffeeException e) {
            logger.error("Error while building coffee: " + e);
        }
    }
}
