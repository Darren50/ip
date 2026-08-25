import java.util.Scanner;

public class GPT {
    public static void main(String[] args) {
        String banner = "  ____ ____ _____ \n"
                + " / ___|  _ \\_   _|\n"
                + "| |  _| |_) || |  \n"
                + "| |_| |  __/ | |  \n"
                + " \\____|_|    |_|  \n";
        String line = "____________________________________________________________";
        String[] descriptions = new String[100];
        boolean[] isDone = new boolean[100];

        int count = 0;

        Scanner in = new Scanner(System.in);

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Beep Boop, I'm GPT.");
        System.out.println("What's cooking, I'm gonna use all your tokens today.");
        System.out.println(line);
        System.out.println();
        while (true) {
            String input = in.nextLine();
            String[] words = input.split(" ");
            String firstCommand = words[0];
            System.out.println(line);
            if (firstCommand.equals("bye")) {
                break;
            } else if (firstCommand.equals("list")) {
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < count; i++) {
                    String icon = isDone[i] ? "[X]" : "[ ]";
                    System.out.println(" " + (i + 1) + "." + icon + " " + descriptions[i]);
                }
            } else if (firstCommand.equals("mark")) {
                int taskNo = Integer.parseInt(words[1]) - 1;
                isDone[taskNo] = true;
                System.out.println("Beep boop, task has been marked.");
                System.out.println("[X] " + descriptions[taskNo]);
            } else if (firstCommand.equals("unmark")) {
                int taskNo = Integer.parseInt(words[1]) - 1;
                isDone[taskNo] = false;
                System.out.println("Beep boop, task has been unmarked.");
                System.out.println("[ ] " + descriptions[taskNo]);
            } else {
                descriptions[count] = input;
                isDone[count] = false;
                count++;
                System.out.println("added: " + input);
            }
            System.out.println(line);
        }

        System.out.println("Bye. Make sure to buy more tokens");
        System.out.println(line);
    }
}