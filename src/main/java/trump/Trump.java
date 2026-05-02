package trump;
import java.util.Scanner;

public class Trump {
    public static void main(String[] args) {
        displayWelcome();
        Scanner scanner = new Scanner(System.in);
        String[] tasklist = new String[100];
        int taskIndex = 0;

        while(true) {
            String userInput = getInput(scanner);

            if(userInput.equalsIgnoreCase("bye")) {
                displayGoodbye();
                scanner.close();
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                listTask(tasklist);
            } else {
                addTask(userInput, tasklist, taskIndex);
                taskIndex++;
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
        String userInput = scanner.nextLine();
        return userInput;
    }

    public static void displayGoodbye() {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Get back to work soon, we have a lot of winning left to do—it's going to be huge!");
        System.out.println("-------------------------------------------------------------------------------------------");
    }

    public static String[] addTask(String userInput, String[] tasklist, int taskIndex) {
        tasklist[taskIndex] = userInput;
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Added: " + userInput);
        return tasklist;
    }

    public static void listTask(String[] tasklist) {
        for(int i = 0; i < tasklist.length; i++) {
            if(tasklist[i] != null) {
                System.out.println(i + 1 + ". " + tasklist[i]);
            }
        }
    }
}
