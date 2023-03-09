
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        String[] products = {"яблоки", "хлеб", "молоко", "печенье"};
        int[] prices = {58, 45, 98, 76};
        int[] cartBasket;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element config = document.createElement("Config");
        document.appendChild(config);

        Element load = document.createElement("load");
        config.appendChild(load);

        Element enable = document.createElement("enable");
        load.appendChild(enable);
        enable.setTextContent("false");
        Element fileName = document.createElement("fileName");
        load.appendChild(fileName);
        fileName.setTextContent("Basket.json");
        Element format = document.createElement("format");
        load.appendChild(format);
        format.setTextContent("json");

        Element save = document.createElement("save");
        config.appendChild(save);

        Element enable1 = document.createElement("enable");
        save.appendChild(enable1);
        enable1.setTextContent("true");
        Element fileName1 = document.createElement("fileName");
        save.appendChild(fileName1);
        fileName1.setTextContent("Basket.json");
        Element format1 = document.createElement("format");
        save.appendChild(format1);
        format1.setTextContent("json");

        Element log = document.createElement("log");
        config.appendChild(log);
        Element enable2 = document.createElement("enable");
        save.appendChild(enable2);
        enable2.setTextContent("true");
        Element fileName2 = document.createElement("fileName");
        save.appendChild(fileName2);
        fileName1.setTextContent("Client.csv");

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("Shop.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);

        Document doc = builder.parse(new File("Shop.xml"));
        Node root = doc.getDocumentElement();
        NodeList con = root.getChildNodes();
        NodeList con1 = con.item(0).getChildNodes();

        if (con1.item(0).getTextContent().equals("true")) {


        }

// Basket basket1 = Basket.loadFromTxtFile(new File("BasketAmounts.txt"));


        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Basket.json"));
            JSONObject basketJSON = (JSONObject) obj;
            int[] v = new int[products.length];
       /*     for (Object key: basketJSON.keySet()) {
                v = (int[]) key;
            }*/
            Basket basket = new Basket(products, prices, v) {
            };
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            Basket basket = new Basket(products, prices);
            System.out.println("список возможных товаров за килограмм, литр, штуку:");
            for (int i = 0; i < products.length; i++) {
                System.out.println(i + 1 + "  " + products[i] + " по цене  " + prices[i] + "  за килограмм, литр, штуку");
            }
            ClientLog cl = new ClientLog();
            basket.addToCart(3, 12);
            basket.addToCart(1, 8);
            basket.addToCart(2, 12);
            cl.log(3, 12);
            cl.log(1, 8);
            cl.log(2, 12);
            basket.printCart();
            //basket.saveTxt(new File("BasketAmounts.txt"));
            cl.exportAsCSV(new File("log.csv"));

            JSONObject basketJSON = new JSONObject();
            for (int i = 0; i < products.length; i++) {
                basketJSON.put(products[i], basket.cartBasket[i]);
            }
            try (FileWriter writer = new FileWriter("Basket.json")) {
                writer.write(basketJSON.toString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
