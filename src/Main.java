import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


public class Main {
    static HashMap<String, String> urls = new HashMap<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {
        menu();
    }

    public static void menu() throws IOException, ClassNotFoundException, URISyntaxException {

        Set<String> set = new HashSet<>();

        System.out.print("Здравствуйте! Введите ваш UUID или напишите 'нет' или 'выход', для выхода: ");
        String choose = input.nextLine();
        // проверяем существование UUID
        if (choose.contains("нет")) {
            UUID uuid = UUID.randomUUID();
            set.add(String.valueOf(uuid));
            File your_uuid = new File("your_uuid.txt");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(your_uuid));
            output.writeObject(set); // to do перекодировать файл в utf-8
            output.close();
            System.out.println("Ваш uuid: " + uuid);
            System.out.println(set.contains(String.valueOf(uuid))); // проверка для себя
            menu();
        } else if (choose.contains("выход")) {
            System.out.println("До свидания!");
            System.exit(1);
        } else {
            ObjectInputStream ini = new ObjectInputStream(new FileInputStream("your_uuid.txt"));
            Set<String> aNewSet = (HashSet<String>) ini.readObject(); // to do обернуть считывание с файла в try-catch

            if (aNewSet.contains(choose)) {
                System.out.println(aNewSet.contains(choose));
                menu1();
            } else {
                System.out.print("Неверный uuid!\n" +
                        "1.Ввеcти заново uuid\n" +
                        "2.Выйти\n");
                String variant = input.nextLine();

                switch (variant) {
                    case "1":
                        menu();
                        break;
                    case "2":
                        System.out.println("До новых встреч!");
                        System.exit(1);
                        break;
                }
            }
        }
    }


    public static void createShortLink() throws FileNotFoundException, IOException, URISyntaxException {
        Calendar calendar = Calendar.getInstance();
        Scanner input = new Scanner(System.in);


        System.out.print("Введите вашу ccылку: ");
        String link = input.nextLine();
        String shortlink = link + UUID.randomUUID().toString().substring(0, 8);// создаем ссылку

        if (!urls.containsKey(shortlink)) { //проверяем наличие ссылки
            urls.put(shortlink, link);
            System.out.println("Ваша короткая ссылка: " + shortlink);
        } else {
            String shortlink1 = shortlink + UUID.randomUUID().toString().substring(0, 15);
            urls.put(shortlink1, link);
            System.out.println("Ваша короткая ссылка: " + shortlink1);
        }
        File urls_file = new File("urls_file.txt");
        ObjectOutputStream urls_output = new ObjectOutputStream(new FileOutputStream(urls_file));
        urls_output.writeObject(urls); // записываем в файл
        urls_output.close();

        int day = calendar.get(Calendar.DAY_OF_MONTH);// устанавливаем дату создания короткой ссылки
        System.out.print("Введите количество дней существования ссылки: ");// чтобы установить время ее жизни
        int c = input.nextInt();
        if (calendar.after(c)) {
            InputStreamReader reading = new InputStreamReader(new FileInputStream("urls_file.txt"));
            urls.remove(shortlink);
            System.out.println("Превышено количество дней существования ссылки");
            menu1();
        }
    }
    public static void getShortlink() throws IOException, URISyntaxException {
        /*
        5. Переход по короткой ссылке.
        При вводе короткой ссылки в консоль пользователь должен автоматически перенаправляться на
        исходный ресурс в браузере:
         */
        try {
            FileReader in_urls = new FileReader("urls_file.txt");
            in_urls.read();
            System.out.print("Введите вашу короткую ссылку: ");
            String a = input.nextLine();
            Desktop.getDesktop().browse(new URI(urls.get(a)));
        } catch (FileNotFoundException e) {
            System.out.println("Ссылки не существует " + e.getMessage());
            menu1();
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


    public static void menu1() throws IOException, URISyntaxException {
        Scanner input = new Scanner(System.in);
        boolean work = true;
        while (work) {
            System.out.print("Выберите нужный вам пункт меню:\n " +
                    "1. Cоздать короткую ссылку\n " +
                    "2. Получить ссылку для перехода\n " +
                    "3. Количество раз для перехода\n " +
                    "4. Выйти\n");
            String choose = input.nextLine();

            switch (choose) {
                case "1":
                    createShortLink();
                    break;
                case "2":
                    getShortlink();
                    break;
                case "3":
                    clicking();
                    break;
                case "4":
                    System.out.println("До новых встреч!");
                    System.exit(1);
                    work = false;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choose);
            }
        }
    }
}




//public UUID () {
//    Set<String> set = new HashSet<>();
//    UUID uuid = UUID.randomUUID();
//    uuid.toString();
//    set.add(uuid);


//}