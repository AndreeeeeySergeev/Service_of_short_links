import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Objects;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        Set<String> set = new HashSet<>();

        System.out.print("Здравствуйте! Введите ваш UUID или напишите 'нет': ");
        String choose = input.nextLine();
        if (choose.contains("нет")) {
            UUID uuid = UUID.randomUUID();
            set.add(String.valueOf(uuid));
            File your_uuid = new File("your_uuid.txt");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(your_uuid));
            output.writeObject(set);
            output.close();
            System.out.println(uuid);
            System.out.println(set.contains(String.valueOf(uuid)));
        } else {
            ObjectInputStream ini = new ObjectInputStream(new FileInputStream("your_uuid.txt"));
            Set<String> aNewSet = (HashSet<String>) ini.readObject();
            System.out.println(aNewSet.contains(choose));
        }

    }
}


//public UUID () {
//    Set<String> set = new HashSet<>();
//    UUID uuid = UUID.randomUUID();
//    uuid.toString();
//    set.add(uuid);


//}