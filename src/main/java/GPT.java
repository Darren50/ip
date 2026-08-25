import java.util.Scanner;

public class GPT {
    public static void main(String[] args) {
        String banner = "  ____ ____ _____ \n"
                + " / ___|  _ \\_   _|\n"
                + "| |  _| |_) || |  \n"
                + "| |_| |  __/ | |  \n"
                + " \\____|_|    |_|  \n";
        String line = "____________________________________________________________";
        Task[] tasks = new Task[100];

        int count = 0;

        Scanner in = new Scanner(System.in); // get input

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
                    System.out.println(" " + (i + 1) + "." + tasks[i].getIcon() + " " + tasks[i].getDescription());
                }
            } else if (firstCommand.equals("mark")) {
                int taskNo = Integer.parseInt(words[1]) - 1;
                tasks[taskNo].setAsDone();
                System.out.println("Beep boop, task has been marked.");
                System.out.println(tasks[taskNo].getIcon() + " " + tasks[taskNo].getDescription());
            } else if (firstCommand.equals("unmark")) {
                int taskNo = Integer.parseInt(words[1]) - 1;
                tasks[taskNo].setAsNotDone();
                System.out.println("Beep boop, task has been unmarked.");
                System.out.println(tasks[taskNo].getIcon() + " " + tasks[taskNo].getDescription());
            } else {
                tasks[count] = new Task(input);
                count++;
                System.out.println("added: " + input);
            }
            System.out.println(line);
        }


        System.out.println("Bye. Make sure to buy more tokens");
        System.out.println(line);
    }
}
