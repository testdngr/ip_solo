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
        while (!this.isExit) {
            try {
                String userInput = getInput();
                this.isExit = processInput(userInput);
            } catch (InvalidInputException e) {
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
        String command = inputParts[0].toLowerCase();

        boolean needsArguments = switch (command) {
            case "mark", "unmark", "todo", "deadline", "event" -> true;
            default -> false;
        };

        if (needsArguments && inputParts.length < 2) {
            throw new InvalidInputException("Excuse me, you forgot the details for the '" + command + "' command. It's empty!");
        }
        return switch (command) {
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
                markTask(parseTaskNumber(inputParts[1]));
                yield false;
            }
            case "unmark" -> {
                unmarkTask(parseTaskNumber(inputParts[1]));
                yield false;
            }
            case "todo", "deadline", "event" -> {
                addTask(command, inputParts[1]);
                yield false;
            }
            default -> {
                System.out.println("-------------------------------------------------------------------------------------------");
                System.out.println("I don't know what \"" + command + "\" is. Nobody knows.");
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
        for (int i = 0; i < this.tasklist.length; i++) {
            if (this.tasklist[i] != null) {
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

    public void addTask(String taskType, String taskInfo) {
        Task newTask = switch (taskType) {
            case "todo" -> {
                String checkFlags = taskInfo.toLowerCase();
                if (!checkFlags.contains("/from") && !checkFlags.contains("/to") && !checkFlags.contains("/by")) {
                    yield new Todo(taskInfo);
                }
                throw new InvalidInputException("Wrong format! A Todo must be simple, just a description. " +
                        "Do not use /from, /to, or /by. Formula: todo [description]");
            }
            case "deadline" -> {
                String regex = "^(.+?)\\s+/by\\s+((?!.*/from|.*/to|.*/by).+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(taskInfo);

                if (matcher.matches()) {
                    String description = matcher.group(1).trim();
                    String by = matcher.group(2).trim();
                    yield new Deadline(description, by);
                }
                throw new InvalidInputException("Wrong format! This deadline is not great. You need a /by flag with a date/time. " +
                        "Formula: deadline [description] /by [date/time]");
            }
            case "event" -> {
                String regex = "^(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+((?!.*/from|.*/to|.*/by).+)$";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(taskInfo);

                if (matcher.matches()) {
                    String description = matcher.group(1).trim();
                    String from = matcher.group(2).trim();
                    String to = matcher.group(3).trim();
                    yield new Event(description, from, to);
                }
                throw new InvalidInputException("Wrong format! Terrible event structure. You need both /from and /to flags. " +
                        "Formula: event [description] /from [date/time] /to [date/time]");
            }
            default -> throw new InvalidInputException("Fake news! This task type does not exist: " + taskType);
        };
        this.tasklist[this.taskIndex] = newTask;
        this.taskIndex++;
        displayAddTask();
    }

    public void displayAddTask() {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Adding a fantastic new task. It’s going to be a yuge success. Exceptional!");
        System.out.println("Added: " + tasklist[taskIndex - 1].toString());
        System.out.println("We have a tremendous list. A beautiful list of " + (this.taskIndex) + " tasks.");
}

    public void displayError(InvalidInputException e) {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println(e.getMessage());

    }

    public int parseTaskNumber(String taskNumberString) {
        try {
            int taskNumber = Integer.parseInt(taskNumberString.strip()) - 1;
            if (taskNumber <= -1) {
                throw new InvalidInputException("Total disaster! The task number cannot be 0 or less. " +
                        "That is fake news, completely made up by bad input!"
                );
            }

            if (taskNumber > this.taskIndex - 1) {
                throw new InvalidInputException("Total disaster! The task number you entered is way too big.");
            }
            return taskNumber;
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("Wrong! You have to enter a number, okay? " +
                    "Entering letters is a complete and total failure. Nobody has ever seen a worse input, believe me!"
            );
        }
    }

}

