import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Basket {
    String products[];
    int[] prices;
    int[] cartBasket;

    Basket() {

    }


    Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.cartBasket = new int[products.length];
        for (int i = 0; i < products.length; i++) {
            cartBasket[i] = 0;
        }
    }

    public void addToCart(int productNum, int amount) {

        cartBasket[productNum - 1] += amount;

    }


    public void printCart() {
        System.out.println("В продуктовой корзине: ");
        for (int i = 0; i < products.length; i++) {
            if (cartBasket[i] > 0) {
                System.out.println(" " + products[i] + "  " + cartBasket[i] + " штук/клограмм/литров");
            }
        }
    }

    public void saveTxt(File textFile) throws IOException {
        PrintWriter printWriter = new PrintWriter(textFile);
        for (int i = 0; i < products.length; i++) {
            printWriter.println(products[i] + "\t" + prices[i] + "\t" + cartBasket[i]);

        }

    }

    static Basket loadFromTxtFile(File textFile) {
        String products[] = null;
        int[] prices = null;
        int[] cartBasket = null;

        Basket basket = new Basket();
        basket.products = products;
        basket.prices = prices;
        basket.cartBasket = cartBasket;

        return basket;
    }
}
