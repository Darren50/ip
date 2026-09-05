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
            try {
                handleCommand(commandWord, input);
            } catch (GptException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(LINE);
        }

        printFarewell();
    }

    /**
     * Runs the command named by the given command word.
     * The full input line is passed on because most commands need their arguments.
     */
    private void handleCommand(String commandWord, String input) throws GptException {
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
            throw new GptException("OOPS!!! I don't know what that command means.");
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
    private void markTask(String input) throws GptException {
        Task task = getTask(input);
        task.markAsDone();
        System.out.println("Beep boop, task has been marked.");
        System.out.println("  " + task);
    }

    /**
     * Marks the task named in the given input as not done.
     */
    private void unmarkTask(String input) throws GptException {
        Task task = getTask(input);
        task.markAsNotDone();
        System.out.println("Beep boop, task has been unmarked.");
        System.out.println("  " + task);
    }

    /**
     * Returns the task named by the number in the given input.
     * The number shown to the user starts at 1, so it is shifted to a 0-based index.
     */
    private Task getTask(String input) throws GptException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new GptException("OOPS!!! Please tell me which task number to update.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new GptException("OOPS!!! Task numbers must be whole numbers.");
        }

        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new GptException("OOPS!!! That task number does not exist.");
        }
        return tasks[taskNumber - 1];
    }

    /**
     * Returns a todo parsed from the given input.
     */
    private static Task parseTodo(String input) throws GptException {
        String description = input.substring("todo".length()).trim();

        if (description.isEmpty()) {
            throw new GptException("OOPS!!! The description of a todo cannot be empty.");
        }

        return new Todo(description);
    }

    /**
     * Returns a deadline parsed from the given input, which is split on the /by marker.
     */
    private static Task parseDeadline(String input) throws GptException {
        String arguments = input.substring("deadline".length()).trim();
        if (arguments.isEmpty()) {
            throw new GptException("OOPS!!! The description of a deadline cannot be empty.");
        }

        int byMarkerIndex = findMarkerIndex(arguments, "/by");
        if (byMarkerIndex < 0) {
            throw new GptException("OOPS!!! A deadline needs a /by date or time.");
        }
        String description = arguments.substring(0, byMarkerIndex).trim();
        String by = arguments.substring(byMarkerIndex + getMarkerLength(byMarkerIndex, "/by")).trim();

        if (description.isEmpty()) {
            throw new GptException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new GptException("OOPS!!! The deadline date or time cannot be empty.");
        }
        return new Deadline(description, by);
    }

    /**
     * Returns an event parsed from the given input, which is split on the /from and /to markers.
     */
    private static Task parseEvent(String input) throws GptException {
        String arguments = input.substring("event".length()).trim();
        if (arguments.isEmpty()) {
            throw new GptException("OOPS!!! The description of an event cannot be empty.");
        }

        int fromMarkerIndex = findMarkerIndex(arguments, "/from");
        int toMarkerIndex = findMarkerIndex(arguments, "/to");
        if (fromMarkerIndex < 0) {
            throw new GptException("OOPS!!! An event needs a /from date or time.");
        }
        if (toMarkerIndex < 0 || toMarkerIndex < fromMarkerIndex) {
            throw new GptException("OOPS!!! An event needs a /to date or time.");
        }

        String description = arguments.substring(0, fromMarkerIndex).trim();
        String from = arguments.substring(fromMarkerIndex + getMarkerLength(fromMarkerIndex, "/from"),
                toMarkerIndex).trim();
        String to = arguments.substring(toMarkerIndex + getMarkerLength(toMarkerIndex, "/to")).trim();

        if (description.isEmpty()) {
            throw new GptException("OOPS!!! The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new GptException("OOPS!!! The event start date or time cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new GptException("OOPS!!! The event end date or time cannot be empty.");
        }
        return new Event(description, from, to);
    }

    /**
     * Returns the index of a command marker that appears at the start or after a space.
     */
    private static int findMarkerIndex(String text, String marker) {
        if (text.startsWith(marker)) {
            return 0;
        }
        return text.indexOf(" " + marker);
    }

    /**
     * Returns the marker length, including the leading space when the marker is not at the start.
     */
    private static int getMarkerLength(int markerIndex, String marker) {
        return markerIndex == 0 ? marker.length() : marker.length() + 1;
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
