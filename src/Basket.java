import java.io.*;
import java.util.ArrayList;

public class Basket implements Serializable {
    private static String[] products;
    static int[] prices;
    private static int[] cartBasket;
    
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

    public void saveTxt(File file) throws IOException {
        try (FileWriter saveFile = new FileWriter(file)) {
            for (int i = 0; i < products.length; i++) {
                    saveFile.write(products[i]  +" "+ cartBasket[i]+" ");
            }
        }
    }

    public static Basket loadFromTxtFile(File file) throws IOException {
        try (FileReader loadFile = new FileReader(file)) {
            Basket basket = new Basket();
            basket.products = products;
            basket.prices = prices;
            basket.cartBasket = new int[products.length];

            StringBuilder s = new StringBuilder();
            int c = 0;
            while ((c = loadFile.read()) != -1) {
                s.append(Character.toChars(c));
            }
            String[] k = s.toString().split(" ");

            for (int i = 0; i < k.length; i++) {
                cartBasket[i] = Integer.parseInt(k[i*2+1]);
            }
            return basket;
        }
   }
}


