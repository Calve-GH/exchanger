package com.example.demo.service;

import com.example.demo.domain.Rate;
import com.example.demo.repository.RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.springframework.transaction.annotation.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Loader {
    public static final String RESOURCE = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final String MARK = "//Cube/Cube";

    private RateRepository repository;

    @Autowired
    public Loader(RateRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 16 * * *", zone = "Europe/Moscow")
    public void load() {
        try {
            URL url = new URL(RESOURCE);
            NodeList nodeList = getNodeListFromUrl(url);
            updateDataBaseRates(parseNodesToList(nodeList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NodeList getNodeListFromUrl(URL url) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(url.openStream());
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (NodeList) xPath.compile(MARK).evaluate(xmlDocument, XPathConstants.NODESET);
    }

    @Transactional
    void updateDataBaseRates(List<Rate> source) {
        repository.deleteAll();
        repository.saveAll(source);
    }

    private static List<Rate> parseNodesToList(NodeList nodeList) {
        List<Rate> result = new ArrayList<>();
        for (int i = 1; i < nodeList.getLength(); i++) {
            NamedNodeMap attributes = nodeList.item(i).getAttributes();
            Node currency = attributes.getNamedItem("currency");
            Node rate = attributes.getNamedItem("rate");
            result.add(new Rate(currency.getTextContent(), new BigDecimal(rate.getTextContent())));
        }
        return result;
    }
}
