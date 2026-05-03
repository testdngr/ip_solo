package trump;
import trump.task.Task;
import java.util.Scanner;

public class Trump {
    public static void main(String[] args) {
        Trump trump = new Trump();
        trump.run();
    }

    private final Scanner scanner;
    private Task[] tasklist;
    private int taskIndex;
    private boolean isExit;

    public Trump() {
        this.scanner = new Scanner(System.in);
        this.tasklist = new Task[100];
        this.taskIndex = 0;
        this.isExit = false;
    }

    public void run() {
        displayWelcome();
        while(!this.isExit) {
            String userInput = getInput();
            this.isExit = processInput(userInput);
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

    public boolean processInput(String userInput) {
        String[] inputParts = userInput.strip().split(" ");
        return switch (inputParts[0].toLowerCase()) {
            case "bye" -> {
                displayGoodbye();
                this.scanner.close();
                yield true;
            }
            case "list" -> {
                listTask();
                yield false;
            }
            case "mark" -> {
                int index = Integer.parseInt(inputParts[1]) - 1;
                markTask(index);
                yield false;
            }
            case "unmark" -> {
                int index = Integer.parseInt(inputParts[1]) - 1;
                unmarkTask(index);
                yield false;
            }
            default -> {
                addTask(userInput);
                yield false;
            }
        };
    }


    public String getInput() {
        System.out.println("-------------------------------------------------------------------------------------------");
        String userInput = this.scanner.nextLine();
        return userInput;
    }

    public static void displayGoodbye() {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Get back to work soon, we have a lot of winning left to do—it's going to be huge!");
        System.out.println("-------------------------------------------------------------------------------------------");
    }

    public void addTask(String userInput) {
        Task t = new Task(userInput);
        this.tasklist[this.taskIndex] = t;
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Added: " + userInput);
        this.taskIndex++;
    }

    public void listTask() {
        System.out.println("-------------------------------------------------------------------------------------------");
        for(int i = 0; i < this.tasklist.length; i++) {
            if(this.tasklist[i] != null) {
                System.out.println(i + 1 + "." + this.tasklist[i].toString());
            }
        }
    }

    public void markTask(int index) {
        this.tasklist[index].markAsDone();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Congratulative message");
        System.out.println(this.tasklist[index].toString());
    }

    public void unmarkTask(int index) {
        this.tasklist[index].markAsNotDone();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Noted message");
        System.out.println(this.tasklist[index].toString());
    }


}
