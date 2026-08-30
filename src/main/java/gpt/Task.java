package gpt;

/**
 * Represents a task stored by the chatbot.
 */
public class Task {
    private String type;
    private String description;
    private boolean isDone;
    private String by;
    private String from;
    private String to;

    /**
     * Creates an incomplete task of the given type.
     * Kept private so that tasks are always built through the create methods below,
     * which make the caller's intent clear.
     */
    private Task(String type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a new todo with the given description.
     */
    public static Task createTodo(String description) {
        return new Task("T", description);
    }

    /**
     * Returns a new deadline with the given description and due date.
     */
    public static Task createDeadline(String description, String by) {
        Task task = new Task("D", description);
        task.by = by;
        return task;
    }

    /**
     * Returns a new event with the given description, start, and end.
     */
    public static Task createEvent(String description, String from, String to) {
        Task task = new Task("E", description);
        task.from = from;
        task.to = to;
        return task;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        if (isDone()) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        String base = "[" + type + "]" + getIcon() + " " + description;
        if (type.equals("D")) {
            return base + " (by: " + by + ")";
        } else if (type.equals("E")) {
            return base + " (from: " + from + " to: " + to + ")";
        } else {
            return base;
        }
    }
}
