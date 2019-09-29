/**
 * This is part of a CSE331 Assignment: Environment Setup and Java Introduction.
 */
package setup;

import java.util.Scanner;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Adder asks the user for two ints and computes their sum.
 */
public class Adder {

    /**
     * @param args Command Line Arguments provided to the program
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in, UTF_8.name());;
        System.out.print("Enter first number: ");
        int x = console.nextInt();
        System.out.print("Enter second number: ");
        int y = console.nextInt();
        int sum = computeSum(x, y);
        System.out.println(x + " + " + y + " = " + sum);
    }

    /**
     * 
     * @param x First number to sum.
     * @param y Second number to sum.
     * @return sum of x and y.
     */
    public static int computeSum(int x, int y) {
        return x - y;
    }
}
