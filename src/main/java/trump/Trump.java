package trump;

import trump.task.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            try {
                String userInput = getInput();
                this.isExit = processInput(userInput);
            }
            catch (TrumpException e) {
                displayError(e);
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

    public boolean processInput(String userInput) {
        String[] inputParts = userInput.strip().split(" ", 2);
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
            case "todo" -> {
                if(inputParts.length != 2) {
                    throw new TrumpException("Add task description...");
                }
                addTask("todo", inputParts[1]);
                yield false;
            }
            case "deadline" -> {
                addTask("deadline", inputParts[1]);
                yield false;
            }
            case "event" -> {
                addTask("event", inputParts[1]);
                yield false;
            }
            default -> {
                System.out.println("-------------------------------------------------------------------------------------------");
                System.out.println("I dont know whats " + inputParts[0]);
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

    public void listTask() {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Here are the tasks in your big league list:");
        for(int i = 0; i < this.tasklist.length; i++) {
            if(this.tasklist[i] != null) {
                System.out.println(i + 1 + "." + this.tasklist[i].toString());
            }
        }
    }

    public void markTask(int index) {
        this.tasklist[index].markAsDone();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Total victory on that task. Nobody finishes like you do. Big league!");
        System.out.println(this.tasklist[index].toString());
    }

    public void unmarkTask(int index) {
        this.tasklist[index].markAsNotDone();
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Listen, I’m putting this back on the list because it’s not finished yet, but when we do it, it’s going to be tremendous!");
        System.out.println(this.tasklist[index].toString());
    }

    public void addTask(String taskType, String taskInfo) throws TrumpException{
        Task newTask = switch (taskType) {
            case "todo" -> new Todo(taskInfo);
            case "deadline" -> {
                String[] parts = taskInfo.split(" /by ");
                String description = parts[0];
                String by = parts[1];
                yield new Deadline(description, by);
            }
            default -> {
                String regex = "^(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)$";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(taskInfo);

                if(matcher.matches()) {
                    String description = matcher.group(1).trim();
                    String from = matcher.group(2).trim();
                    String to = matcher.group(3).trim();
                    yield new Event(description, from, to);
                }

                throw new TrumpException("Event Format wrong...");

            }
        };
        this.tasklist[this.taskIndex] = newTask;
        displayAddTask();
        taskIndex++;
}

    public void displayAddTask() {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Message of adding task");
        System.out.println("Added: " + tasklist[taskIndex].toString());
        System.out.println("Message of how many task in list");
    }

    public void displayError(TrumpException e) {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.err.println(e.getMessage());
    }

}
