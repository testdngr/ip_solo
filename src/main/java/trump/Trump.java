package trump;
import java.util.Scanner;

public class Trump {
    public static void main(String[] args) {
        displayWelcome();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String userInput = getInput(scanner);
            displayInput(userInput);
            if(userInput.equalsIgnoreCase("bye")) {
                displayGoodbye();
                scanner.close();
                break;
            }
        }
    }

    public static void displayWelcome() {
        String logo = """
             _________  ____  __  ___  ____
            /_  __/ _ \\/ / / /  |/  / _ \\_ \\
             / / / , _/ /_/ / /|_/ / ___/_/
            /_/ /_/|_|\\____/_/  /_/_/ (_)
            """;

        System.out.println("Hello from\n" + logo);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("I’m Donald Trump and we’re going to make your productivity great again!");

    }

    public static String getInput(Scanner scanner) {
        System.out.println("-------------------------------------------------------------------------------------------");
        String userInput = scanner.next();
        return userInput;
    }

    public static void displayInput(String userInput) {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println(userInput);
    }

    public static void displayGoodbye() {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Get back to work soon, we have a lot of winning left to do—it's going to be huge!");
    }
}
