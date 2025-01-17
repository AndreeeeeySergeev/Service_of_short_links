import com.sun.jdi.Value;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        Set<String> set = new HashSet<>();

        System.out.print("Здравствуйте! Введите ваш UUID или напишите 'нет': ");
        String choose = input.nextLine();
        // проверяем существование UUID
        if (choose.contains("нет")) {
            UUID uuid = UUID.randomUUID();
            set.add(String.valueOf(uuid));
            File your_uuid = new File("your_uuid.txt");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(your_uuid));
            output.writeObject(set); // to do перекодировать файл в utf-8
            output.close();
            System.out.println(uuid);
            System.out.println(set.contains(String.valueOf(uuid)));
        } else {
            ObjectInputStream ini = new ObjectInputStream(new FileInputStream("your_uuid.txt"));
            Set<String> aNewSet = (HashSet<String>) ini.readObject(); // to do обернуть считывание с файла в try-catch
            System.out.println(aNewSet.contains(choose));
        }
    }

    public static void createShortLink() throws FileNotFoundException, IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Введите вашу ccылку: ");
        String link = input.nextLine();
        String shortlink = link + UUID.randomUUID().toString().substring(0, 8);// создаем ссылку
        System.out.println("Ваша короткая ссылка: " + shortlink);
        File myfile = new File("links.txt"); // записываем в файл,
        OutputStreamWriter writered = new OutputStreamWriter(new FileOutputStream(myfile));
        writered.write(shortlink);// чтобы дальше взаимодействовать с ней
        writered.close();
    }

    public static void deletelink() throws IOException {
        try {
            InputStreamReader reading = new InputStreamReader(new FileInputStream("links.txt"));
            String a = String.valueOf(reading.read());
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка:" + e.getMessage());
        }
        Calendar days = Calendar.getInstance();
        if (days.after(3)) { // время существования ссылки
//            a.resolveConstantDesc(createShortLink());
            File file = new File("links.txt");
            file.delete();
            createShortLink();// Здесь получается, что будем снова перезаписывать ссылку после 3 дней
        }
    }

        public static void clicking() throws IOException {
            Scanner onesmore = new Scanner(System.in);
            System.out.print("Введите количество использования ссылки: ");
            int good = Integer.valueOf(onesmore.nextLine());
            File file = new File("links.txt");
            file.exists(); // проверяем существование файла
//            BufferedReader read = new BufferedReader(file, good); // BufferedInputSTREAM
            InputStreamReader reading = new InputStreamReader(new FileInputStream(file));
            reading.read();
            Integer c = Integer.valueOf(reading.read());
            if ( c > good) {
                file.delete(); // если превышает количество обращений к файлу ссылки, то файл удаляется
        }
    }

}


//public UUID () {
//    Set<String> set = new HashSet<>();
//    UUID uuid = UUID.randomUUID();
//    uuid.toString();
//    set.add(uuid);


//}