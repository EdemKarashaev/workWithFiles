
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

// Путь к файлу, который нужно распарсить
        File file = new File("Shop.xml");
// Распарсить XML-документ
        Document doc = builder.parse(file);

        Node root = doc.getDocumentElement();
        NodeList con = root.getChildNodes();

        NodeList con1 = con.item(1).getChildNodes();
        NodeList con2 = con.item(3).getChildNodes();
        NodeList con3 = con.item(5).getChildNodes();

        String name = (con1.item(1).getTextContent());
        String name1 = (con3.item(1).getTextContent());

        if (con1.item(1).getTextContent().equals("txt")) {
            Basket basket1 = Basket.loadFromTxtFile(new File("BasketAmounts.txt"));
        } else {
            if (con1.item(1).getTextContent().equals("true")) {
                JSONParser parser = new JSONParser();
                try {
                    Object obj = parser.parse(new FileReader(name));
                    JSONObject basketJSON = (JSONObject) obj;
                    int[] v = new int[products.length];
                    for (Object key : basketJSON.keySet()) {
                        v = (int[]) key;
                    }
                    Basket basket = new Basket(products, prices, v) {
                    };
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                {
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

                    if (con2.item(1).getChildNodes().equals("txt")) {
                        basket.saveTxt(new File("BasketAmounts.txt"));
                    } else {
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
                    if (con2.item(1).getChildNodes().equals("true")) {
                        cl.exportAsCSV(new File(name1));
                    }
                }
            }
        }
    }
}
