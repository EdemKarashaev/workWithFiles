import java.io.*;

public class Basket {
    public String[] products;
    public int[] prices;
    public int[] cartBasket;

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
                s.append(products[i] + " " + cartBasket[i]+" ");
                oos.writeObject(s);

            }
        }
    }

    static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            StringBuilder s = new StringBuilder();
            s = (StringBuilder) ois.readObject();

            String[] k = s.toString().split(" ");
            String[] prod = new String[k.length/3];
            int[] pr = new int[k.length/3];
            int cart[] = new int[k.length/3];

            for (int i = 0; i < k.length / 3; i++) {
                prod[i] = (k[i * 3]);
                pr[i] = Integer.parseInt(k[(i * 3) + 1]);
                cart[i] = Integer.parseInt(k[(i * 3) + 2]);
            }

            Basket basket = new Basket(prod, pr,cart);
            return basket;
        }
    }
}



