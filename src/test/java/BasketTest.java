import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

class BasketTest {
    String[] products = {"яблоки", "хлеб", "молоко", "печенье"};
    int[] prices = {58, 45, 98, 76};
    int[] cartBasket;



  main.java.Basket basket1 = new main.java.Basket(products, prices);

    @org.junit.jupiter.api.Test
    void addToCart() {
        int[] result = new int[products.length];
        result[1] = 3;
        basket1.addToCart(2, 3);
        Assertions.assertArrayEquals(result,basket1.cartBasket);
    }

    @Test
    void saveTxt() throws IOException {
        basket1.addToCart(3,2);
        basket1.addToCart(1,1);
        basket1.saveTxt(new File("BasketAmounts.txt"));
        String expectedOutput="яблоки 58 1 хлеб 45 0 молоко 98 2 печенье 76 0 ";
        String actualOutput = new String(Files.readAllBytes(Paths.get("BasketAmounts.txt")));
        Assertions.assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void loadFromTxtFile() throws Exception {
        basket1.addToCart(3,2);
        basket1.addToCart(1,1);
        main.java.Basket basket2 = main.java.Basket.loadFromTxtFile(new File("BasketAmounts.txt"));
        Assertions.assertTrue(Objects.equals(basket1,basket2));

    }
}