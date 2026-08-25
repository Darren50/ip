import java.util.Scanner;

public class GPT {
    public static void main(String[] args) {
        String banner = "  ____ ____ _____ \n"
                + " / ___|  _ \\_   _|\n"
                + "| |  _| |_) || |  \n"
                + "| |_| |  __/ | |  \n"
                + " \\____|_|    |_|  \n";
        String line = "____________________________________________________________";
        String[] tasks = new String[100];
        int count = 0;

        Scanner in = new Scanner(System.in); //get input

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Beep Boop, I'm GPT.");
        System.out.println("What's cooking, I'm gonna use all your tokens today.");
        System.out.println(line);
        System.out.println();
        while (true){
            String input = in.nextLine();
            if (input.equals("bye")){
                break;
            } else if(input.equals("list")){
                System.out.println(line);
                for(int i = 0; i < count; i++){
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);
            } else{
                tasks[count] = input;
                count++;
                System.out.println(line);
                System.out.println("added: " + input);
                System.out.println(line);
            }
        }

        System.out.println(line);
        System.out.println("Bye. Make sure to buy more tokens");
        System.out.println(line);


    }
}
