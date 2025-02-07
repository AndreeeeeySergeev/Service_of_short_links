import java.util.Scanner;

public class Menu {
    static Scanner input = new Scanner(System.in);

    public String Menu() {
        String choose = input.nextLine();
        return choose;
    }
}
