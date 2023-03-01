import java.io.*;

public class Basket {
    static String[] products;
    static int[] prices;
    static int[] cartBasket;

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
            System.out.println(" " + products[i] + "  " + cartBasket[i] + " штук/клограмм/литров");
        }
    }


    public void saveBin(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < products.length; i++) {
                s.append(products[i] + "  " + cartBasket[i]);
                oos.writeObject(s);
            }
        }
    }

    static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {


        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            StringBuilder s = new StringBuilder();
            s = (StringBuilder) ois.readObject();

/*
            int c = 0;
            while ((c = file.read()) != -1) {
                s.append(Character.toChars(c));
            }*/
            String[] k = s.toString().split(" ");
            Basket basket = new Basket();
            basket.products = products;
            basket.prices = prices;
            basket.cartBasket = new int[k.length];
            
            for (int i = 0; i < k.length; i++) {
                cartBasket[i] = Integer.parseInt(k[i*2+1]);
            }
            return basket;
        }
    }
}
