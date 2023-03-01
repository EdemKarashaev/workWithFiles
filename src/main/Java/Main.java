import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String[] products = {"яблоки", "хлеб", "молоко", "печенье"};
        int[] prices = {58, 45, 98, 76};

       try {
            Basket basket1 = Basket.loadFromBinFile(new File("Basket.bin"));
        } finally {
            Basket basket = new Basket(products, prices);
            System.out.println("список возможных товаров за килограмм, литр, штуку:");
            for (int i = 0; i < products.length; i++) {
                System.out.println(i + 1 + "  " + products[i] + " по цене  " + prices[i] + "  за килограмм, литр, штуку");
            }
            basket.addToCart(3, 12);
            basket.addToCart(2, 10);
            basket.addToCart(2, 10);
            basket.addToCart(1, 8);
           basket.addToCart(4, 4);
            basket.printCart();
            basket.saveBin(new File("Basket.bin"));
       }
    }
}