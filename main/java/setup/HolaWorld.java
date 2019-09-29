/**
 * This is part of a CSE331 Assignment: Environment Setup and Java Introduction.
 */
package setup;

/**
 * HolaWorld is like HelloWorld except it can say hello in Spanish!
 */
public class HolaWorld extends HelloWorld {

    /** Greeting in Spanish */
    public static final String SPANISH_GREETING = "Hola Mundo!";

    /**
     * Shows what happens when the getGreeting() method
     * of both HelloWorld and HolaWorld are invoked
     * @param argv Command Line Arguments provided to the program
     */
    public static void main(String[] argv) {

        // Create the Hello World objects.
        HelloWorld myFirstHW = new HelloWorld();
        HolaWorld world = new HolaWorld();

        // Print out greetings
        System.out.println(myFirstHW.getGreeting());
        System.out.println(world.getGreeting());
    }

    /**
     * @return Returns a greeting (in Spanish).
     */
    @Override
    public String getGreeting() {
        return SPANISH_GREETING;
    }

}
