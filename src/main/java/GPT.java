import java.util.Scanner;

public class GPT {
    public static void main(String[] args) {
        String banner = "  ____ ____ _____ \n"
                + " / ___|  _ \\_   _|\n"
                + "| |  _| |_) || |  \n"
                + "| |_| |  __/ | |  \n"
                + " \\____|_|    |_|  \n";
        String line = "____________________________________________________________";


        Scanner in = new Scanner(System.in); //get input

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Beep Boop, I'm GPT.");
        System.out.println("What's cooking, I'm gonna use all your tokens today.");
        System.out.println(line);
        System.out.println();
        while(true){
            String input = in.nextLine();
            if (input.equals("bye")){
                break;
            } else{
                System.out.println(line);
                System.out.println(input);
                System.out.println(line);
            }
        }

        System.out.println(line);
        System.out.println("Bye. Make sure to buy more tokens");
        System.out.println(line);


    }
}
