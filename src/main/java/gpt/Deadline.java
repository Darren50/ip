package gpt;

/**
 * Represents a task that must be completed before a given date or time.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Creates an incomplete deadline with the given description and due date.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}