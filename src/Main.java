import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Objects;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Set<String> set = new HashSet<>();

        System.out.print("Здравствуйте! Введите ваш UUID или напишите 'нет': ");
        String choose = input.nextLine();
        if (choose.contains("нет")) {
            UUID uuid = UUID.randomUUID();
            set.add(String.valueOf(uuid));
            System.out.println(uuid);
        } else {
            System.out.println(set.contains(choose));
        }

    }
}


//public UUID () {
//    Set<String> set = new HashSet<>();
//    UUID uuid = UUID.randomUUID();
//    uuid.toString();
//    set.add(uuid);


//}