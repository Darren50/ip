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

    /**
     * Starts the chatbot and handles user commands until the user exits.
     */
    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Beep Boop, I'm GPT.");
        System.out.println("What's cooking, I'm gonna use all your tokens today.");
        System.out.println(LINE);
        System.out.println();

        while (true) {
            String input = scanner.nextLine();
            String[] words = input.split(" ");
            String commandWord = words[0];

            System.out.println(LINE);
            if (commandWord.equals("bye")) {
                break;
            } else if (commandWord.equals("list")) {
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (commandWord.equals("mark")) {
                int taskNumber = Integer.parseInt(words[1]) - 1;
                tasks[taskNumber].markAsDone();
                System.out.println("Beep boop, task has been marked.");
                System.out.println("  " + tasks[taskNumber]);
            } else if (commandWord.equals("unmark")) {
                int taskNumber = Integer.parseInt(words[1]) - 1;
                tasks[taskNumber].markAsNotDone();
                System.out.println("Beep boop, task has been unmarked.");
                System.out.println("  " + tasks[taskNumber]);
            } else if (commandWord.equals("todo")) {
                String description = input.substring("todo".length()).trim();
                tasks[taskCount] = Task.createTodo(description);
                taskCount++;
                printAddedTask(tasks[taskCount - 1], taskCount);
            } else if (commandWord.equals("deadline")) {
                String arguments = input.substring("deadline".length()).trim();
                String[] parts = arguments.split(" /by ", 2);
                tasks[taskCount] = Task.createDeadline(parts[0].trim(), parts[1].trim());
                taskCount++;
                printAddedTask(tasks[taskCount - 1], taskCount);
            } else if (commandWord.equals("event")) {
                String arguments = input.substring("event".length()).trim();
                String[] fromParts = arguments.split(" /from ", 2);
                String[] toParts = fromParts[1].split(" /to ", 2);
                tasks[taskCount] = Task.createEvent(fromParts[0].trim(), toParts[0].trim(),
                        toParts[1].trim());
                taskCount++;
                printAddedTask(tasks[taskCount - 1], taskCount);
            } else {
                System.out.println("Beep boop, I don't know that command yet.");
            }
            System.out.println(LINE);
        }

        System.out.println("Bye. Make sure to buy more tokens");
        System.out.println(LINE);
    }

    /**
     * Prints the confirmation shown after a task is added to the list.
     */
    private static void printAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}