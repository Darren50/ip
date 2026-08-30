package gpt;

import java.util.Scanner;

/**
 * Runs the GPT chatbot application.
 */
public class Gpt {
    private static final int MAX_TASKS = 100;
    private static final String BANNER = "  ____ ____ _____ \n"
            + " / ___|  _ \\_   _|\n"
            + "| |  _| |_) || |  \n"
            + "| |_| |  __/ | |  \n"
            + " \\____|_|    |_|  \n";
    private static final String LINE = "____________________________________________________________";

    private final Task[] tasks = new Task[MAX_TASKS];
    private int taskCount = 0;

    /**
     * Starts the chatbot.
     */
    public static void main(String[] args) {
        new Gpt().run();
    }

    /**
     * Greets the user, runs commands until the user exits, then says goodbye.
     */
    private void run() {
        Scanner scanner = new Scanner(System.in);
        printGreeting();

        while (true) {
            String input = scanner.nextLine();
            String commandWord = input.split(" ")[0];

            System.out.println(LINE);
            if (commandWord.equals("bye")) {
                break;
            }
            handleCommand(commandWord, input);
            System.out.println(LINE);
        }

        printFarewell();
    }

    /**
     * Runs the command named by the given command word.
     * The full input line is passed on because most commands need their arguments.
     */
    private void handleCommand(String commandWord, String input) {
        if (commandWord.equals("list")) {
            printTasks();
        } else if (commandWord.equals("mark")) {
            markTask(input);
        } else if (commandWord.equals("unmark")) {
            unmarkTask(input);
        } else if (commandWord.equals("todo")) {
            addTask(parseTodo(input));
        } else if (commandWord.equals("deadline")) {
            addTask(parseDeadline(input));
        } else if (commandWord.equals("event")) {
            addTask(parseEvent(input));
        } else {
            System.out.println("Beep boop, I don't know that command yet.");
        }
    }

    /**
     * Adds the given task to the list and confirms the addition.
     */
    private void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints the tasks in the list, numbered from 1.
     */
    private void printTasks() {
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    /**
     * Marks the task named in the given input as done.
     */
    private void markTask(String input) {
        Task task = getTask(input);
        task.markAsDone();
        System.out.println("Beep boop, task has been marked.");
        System.out.println("  " + task);
    }

    /**
     * Marks the task named in the given input as not done.
     */
    private void unmarkTask(String input) {
        Task task = getTask(input);
        task.markAsNotDone();
        System.out.println("Beep boop, task has been unmarked.");
        System.out.println("  " + task);
    }

    /**
     * Returns the task named by the number in the given input.
     * The number shown to the user starts at 1, so it is shifted to a 0-based index.
     */
    private Task getTask(String input) {
        int taskNumber = Integer.parseInt(input.split(" ")[1]);
        return tasks[taskNumber - 1];
    }

    /**
     * Returns a todo parsed from the given input.
     */
    private static Task parseTodo(String input) {
        String description = input.substring("todo".length()).trim();
        return new Todo(description);
    }

    /**
     * Returns a deadline parsed from the given input, which is split on the /by marker.
     */
    private static Task parseDeadline(String input) {
        String arguments = input.substring("deadline".length()).trim();
        String[] parts = arguments.split(" /by ", 2);
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    /**
     * Returns an event parsed from the given input, which is split on the /from and /to markers.
     */
    private static Task parseEvent(String input) {
        String arguments = input.substring("event".length()).trim();
        String[] fromParts = arguments.split(" /from ", 2);
        String[] toParts = fromParts[1].split(" /to ", 2);
        return new Event(fromParts[0].trim(), toParts[0].trim(), toParts[1].trim());
    }

    /**
     * Prints the startup banner and welcome message.
     */
    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Beep Boop, I'm GPT.");
        System.out.println("What's cooking, I'm gonna use all your tokens today.");
        System.out.println(LINE);
        System.out.println();
    }

    /**
     * Prints the message shown when the user exits.
     */
    private static void printFarewell() {
        System.out.println("Bye. Make sure to buy more tokens");
        System.out.println(LINE);
    }
}