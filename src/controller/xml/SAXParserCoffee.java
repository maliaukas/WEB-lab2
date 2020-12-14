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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SAXParserCoffee {
    private static final Logger logger =
            LogManager.getLogger(SAXParserCoffee.class.getName());

    public static List<Coffee> parse(File inputFile) throws
            ParserConfigurationException,
            SAXException, IOException {
        logger.info("SAX parsing started...");
        List<Coffee> coffeeList;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        Handler handler = new Handler();
        saxParser.parse(inputFile, handler);
        coffeeList = handler.getCoffee();

        logger.info("SAX parsing finished!");
        return coffeeList;
    }

    private static class Handler extends DefaultHandler {
        private static final ArrayList<Coffee> coffee = new ArrayList<>();

        private String name;
        private String country;
        private Roasting roasting;
        private double weight;
        private int arabicaPercent;
        private Date productionDate;
        private BigDecimal pricePerKilo;
        private CoffeeBeansProcessing processing;
        private GroundCoffeeGrinding grinding;
        private InstantCoffeeType type;
        private double jarVolume;
        private int sticksNumber;

        private boolean parsed = true;
        private String currentElement;

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "coffeeBeans" -> {
                    CoffeeBeansBuilder beansBuilder = new CoffeeBeansBuilder
                            (weight, country, name, processing);
                    setParameters(beansBuilder);
                    addCoffee(beansBuilder);
                }
                case "groundCoffee" -> {
                    GroundCoffeeBuilder groundCoffeeBuilder = new GroundCoffeeBuilder
                            (weight, country, name, grinding);
                    setParameters(groundCoffeeBuilder);
                    addCoffee(groundCoffeeBuilder);
                }
                case "instantCoffeeJars" -> {
                    InstantCoffeeJarsBuilder jarsBuilder = new InstantCoffeeJarsBuilder
                            (weight, country, name, jarVolume, type);
                    setParameters(jarsBuilder);
                    addCoffee(jarsBuilder);
                }
                case "instantCoffeeSticks" -> {
                    InstantCoffeeSticksBuilder sticksBuilder = new InstantCoffeeSticksBuilder
                            (weight, country, name, sticksNumber, type);
                    setParameters(sticksBuilder);
                    addCoffee(sticksBuilder);
                }
            }
        }

        private void addCoffee(CoffeeBuilder builder) {
            try {
                var c = builder.build();
                coffee.add(c);
                logger.debug("Added coffee " + c);
            } catch (CoffeeException e) {
                logger.error("Error while building coffee: " + e);
            }
        }

        private void setParameters(CoffeeBuilder builder) {
            builder.setArabicaPercent(arabicaPercent);
            builder.setPricePerKilo(pricePerKilo);
            builder.setProductionDate(productionDate);
            builder.setRoasting(roasting);
        }

        @Override
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes) {
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

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
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
                    case "processing" -> processing = CoffeeBeansProcessing.valueOf(value);
                    case "grinding" -> grinding = GroundCoffeeGrinding.valueOf(value);
                    case "pricePerKilo" -> pricePerKilo = new BigDecimal(value);
                    case "productionDate" -> {
                        try {
                            productionDate =
                                    new SimpleDateFormat("yyyy-MM-dd").parse(value);
                        } catch (ParseException e) {
                            logger.error("Error while parsing date " + e);
                        }
                    }
                }
                parsed = true;
            }
        }

        public List<Coffee> getCoffee() {
            return coffee;
        }
    }
}
