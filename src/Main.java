import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;


public class Main {
    static HashMap<String, String> urls = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    static Set<String> set = new HashSet<>();
//    static String choose = input.nextLine();

    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {
        menu();
    }

    public static void menu() throws IOException, ClassNotFoundException, URISyntaxException {

        System.out.print("Здравствуйте! Введите ваш UUID или напишите 'нет' или 'выход', для выхода: ");
        String choose = input.nextLine();
        // проверяем существование UUID
        if (choose.contains("нет")) {
            UUID uuid = UUID.randomUUID();
            set.add(String.valueOf(uuid));
            File file = new File(String.valueOf(uuid));
            file.mkdir();
            File your_uuid = new File(file, "your_uuid.txt");
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
            File file = new File(choose);
            if (file.exists()) {
                ObjectInputStream ini = new ObjectInputStream(new FileInputStream(choose + "\\your_uuid.txt"));
                Set<String> aNewSet = (HashSet<String>) ini.readObject(); // to do обернуть считывание с файла в try-catch
                if (aNewSet.contains(choose)) { // оставлю дополнительную проверку
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
                        default:
                            throw new IllegalStateException("Unexpected value: " + variant);
                    }
                }
            } else {
                System.out.println("Такого имени не существует ");
                menu();
            }
        }
    }


    public static void createShortLink() throws FileNotFoundException, IOException, URISyntaxException,
            ClassNotFoundException {
        Calendar calendar = Calendar.getInstance();

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
        String a = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
        File urls_file = new File(a, "urls_file.txt");
        ObjectOutputStream urls_output = new ObjectOutputStream(new FileOutputStream(urls_file));
        urls_output.writeObject(urls); // записываем в файл
        urls_output.close();
        menu1();


//        int day = calendar.get(Calendar.DAY_OF_MONTH);// устанавливаем дату создания короткой ссылки
//        System.out.print("Введите количество дней существования ссылки: ");// чтобы установить время ее жизни
//        int c = input.nextInt();
//        if (calendar.after(c)) {
//            InputStreamReader reading = new InputStreamReader(new FileInputStream(urls_file));
//            urls.remove(shortlink);
//            System.out.println("Превышено количество дней существования ссылки");
//            menu1();
//    }
    }




    public static void getShortlink() throws IOException, URISyntaxException, ClassNotFoundException {
        /*
        5. Переход по короткой ссылке.
        При вводе короткой ссылки в консоль пользователь должен автоматически перенаправляться на
        исходный ресурс в браузере:
         */
        String name = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
        try {
            BufferedReader in_urls = new BufferedReader(new FileReader(name + "\\urls_file.txt"));

            HashMap a = new HashMap<>(in_urls.read());
            System.out.print("Введите вашу короткую ссылку: ");
            String b = input.nextLine();
            if (a.containsKey(b)) {
                Desktop.getDesktop().browse(new URI(a.get(b).toString()));
            } else {
                System.out.println("Ничего нет");
                }
        } catch (FileNotFoundException e) {
            System.out.println("Ссылки не существует " + e.getMessage());
            menu1();
        }
    }

        public static void clicking() throws IOException {
            Scanner onesmore = new Scanner(System.in);
            System.out.print("Введите количество использования ссылки: ");
            int good = Integer.valueOf(onesmore.nextLine());
            try { File file = new File("links.txt");
            file.exists(); // проверяем существование файла
//            BufferedReader read = new BufferedReader(file, good); // BufferedInputSTREAM
            InputStreamReader reading = new InputStreamReader(new FileInputStream(file));
            reading.read();
            Integer c = Integer.valueOf(reading.read());
            if ( c > good) {
                file.delete();// если превышает количество обращений к файлу ссылки, то файл удаляется
            }
            } catch (FileNotFoundException e) {
                System.out.println("Внимание " + e.getMessage());
            }
    }


    public static void menu1() throws IOException, URISyntaxException, ClassNotFoundException {
        boolean work = true;
        while (work) {
            System.out.print("Выберите нужный вам пункт меню:\n " +
                    "1. Cоздать короткую ссылку\n " +
                    "2. Получить ссылку для перехода\n " +
                    "3. Количество раз для перехода\n " +
                    "4. Выйти\n");
            String variant = input.nextLine();

            switch (variant) {
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
                    throw new IllegalStateException("Unexpected value: " + variant);
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