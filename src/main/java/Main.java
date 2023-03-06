
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] products = {"яблоки", "хлеб", "молоко", "печенье"};
        int[] prices = {58, 45, 98, 76};
        int[] cartBasket;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("Basket.json"));
            JSONObject basketJSON = (JSONObject) obj;
            int[] v = new int[products.length];
            for (int i = 0; i < products.length; i++) {
                v = (int[]) basketJSON.get("basket.cartBasket[]");
            }
            Basket basket = new Basket(products, prices) {

            };


            // Basket basket1 = Basket.loadFromTxtFile(new File("BasketAmounts.txt"));

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
                basketJSON.put(Basket.products[i], basket.cartBasket[i]);
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
