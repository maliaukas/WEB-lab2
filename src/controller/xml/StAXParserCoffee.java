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
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StAXParserCoffee {
    private static final Logger logger =
            LogManager.getLogger(StAXParserCoffee.class.getName());

    private static final List<Coffee> coffee = new ArrayList<>();

    private static String name;
    private static String country;
    private static Roasting roasting;
    private static double weight;
    private static int arabicaPercent;
    private static Date productionDate;
    private static BigDecimal pricePerKilo;
    private static CoffeeBeansProcessing processing;
    private static GroundCoffeeGrinding grinding;
    private static InstantCoffeeType type;
    private static double jarVolume;
    private static int sticksNumber;
    private static boolean parsed = true;
    private static String currentElement = "";

    public static List<Coffee> parse(File inputFile) throws
            ParserConfigurationException,
            SAXException, IOException {
        logger.info("StAX parsing started...");

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader;
        try {
            eventReader = factory.createXMLEventReader(new FileReader(inputFile));
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.error("StAX parser error " + e);
            return null;
        }

        while (eventReader.hasNext()) {
            XMLEvent event;
            try {
                event = eventReader.nextEvent();
            } catch (XMLStreamException e) {
                logger.error("Fail to get eventReader ", e);
                return null;
            }

            String qName;
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    StartElement startElement = event.asStartElement();
                    qName = startElement.getName().getLocalPart();
                    currentElement = qName;
                    if (qName.equals("name") ||
                            qName.equals("country") ||
                            qName.equals("roasting") ||
                            qName.equals("weight") ||
                            qName.equals("arabicaPercent") ||
                            qName.equals("productionDate") ||
                            qName.equals("pricePerKilo") ||
                            qName.equals("type") ||
                            qName.equals("processing") ||
                            qName.equals("grinding") ||
                            qName.equals("jarVolume") ||
                            qName.equals("sticksNumber")
                    ) {
                        parsed = false;
                    }
                }

                case XMLStreamConstants.CHARACTERS -> {
                    String value = event.asCharacters().getData();
                    if (value.isEmpty() || currentElement.isEmpty()) {
                        break;
                    }
                    if (!parsed) {
                        switch (currentElement) {
                            case "name" -> name = value;
                            case "country" -> country = value;
                            case "weight" -> weight = Double.parseDouble(value);
                            case "arabicaPercent" -> arabicaPercent = Integer.parseInt(value);
                            case "jarVolume" -> jarVolume = Double.parseDouble(value);
                            case "sticksNumber" -> sticksNumber = Integer.parseInt(value);
                            case "roasting" -> roasting = Roasting.valueOf(value);
                            case "type" -> type = InstantCoffeeType.valueOf(value);
                            case "processing" -> processing =
                                    CoffeeBeansProcessing.valueOf(value);
                            case "grinding" -> grinding = GroundCoffeeGrinding.valueOf(value);
                            case "pricePerKilo" -> pricePerKilo = new BigDecimal(value);
                            case "productionDate" -> {
                                try {
                                    productionDate =
                                            new SimpleDateFormat("yyyy-MM-dd").
                                                    parse(value);
                                } catch (ParseException e) {
                                    logger.error("Error while parsing date " + e);
                                }
                            }
                        }
                        parsed = true;
                    }
                }

                case XMLStreamConstants.END_ELEMENT -> {
                    EndElement endElement = event.asEndElement();
                    qName = endElement.getName().getLocalPart();

                    switch (qName) {
                        case "coffeeBeans" -> {
                            CoffeeBeansBuilder beansBuilder =
                                    new CoffeeBeansBuilder
                                            (weight, country, name, processing);
                            setParameters(beansBuilder);
                            addCoffee(beansBuilder);
                        }
                        case "groundCoffee" -> {
                            GroundCoffeeBuilder groundCoffeeBuilder =
                                    new GroundCoffeeBuilder
                                            (weight, country, name, grinding);
                            setParameters(groundCoffeeBuilder);
                            addCoffee(groundCoffeeBuilder);
                        }
                        case "instantCoffeeJars" -> {
                            InstantCoffeeJarsBuilder jarsBuilder =
                                    new InstantCoffeeJarsBuilder
                                            (weight, country, name, jarVolume, type);
                            setParameters(jarsBuilder);
                            addCoffee(jarsBuilder);
                        }
                        case "instantCoffeeSticks" -> {
                            InstantCoffeeSticksBuilder sticksBuilder =
                                    new InstantCoffeeSticksBuilder
                                            (weight, country, name, sticksNumber, type);
                            setParameters(sticksBuilder);
                            addCoffee(sticksBuilder);
                        }
                    }
                }
            }
        }
        logger.info("StAX parsing finished!");
        return coffee;
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

    private static void setParameters(CoffeeBuilder builder) {
        builder.setArabicaPercent(arabicaPercent);
        builder.setPricePerKilo(pricePerKilo);
        builder.setProductionDate(productionDate);
        builder.setRoasting(roasting);
    }
}
