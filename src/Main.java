import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;



public class Main {
    static Map<String, String> urls = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    static Set<String> set = new HashSet<>();
    static int count = 0;
    static Calendar calendar1 = Calendar.getInstance();
    static Calendar calendar2 = Calendar.getInstance();

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
            menu();
        } else if (choose.contains("выход")) {
            System.out.println("До свидания!");
            System.exit(0);
        } else {
            File file = new File(choose);
            if (file.exists()) {
                set.add(choose);
                ObjectInputStream ini = new ObjectInputStream(new FileInputStream(choose + "\\your_uuid.txt"));
                Set<String> aNewHashset = (HashSet<String>) ini.readObject();// to do обернуть считывание с файла в try-catch
                if (aNewHashset.contains(choose)) { // оставлю дополнительную проверку
                    read_calendar(); // проверка протухших ссылок, если установлена, иначе не находит файл
                    menu1();
                } else {
                    System.out.print("Неверный uuid!\n" +
                            "1.Ввеcти заново uuid\n" +
                            "2.Выйти\n");
                    String variant1 = input.nextLine();

                    switch (variant1) {
                        case "1":
                            menu();
                            break;
                        case "2":
                            System.out.println("До новых встреч!");
                            System.exit(1);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + variant1);
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

        System.out.print("Введите вашу ccылку: ");
        String link = input.next();
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
        final String git = (a + "\\urls_file.txt");
        BufferedWriter buffer_write = null;
        if (!(git.isEmpty())) {

//        ObjectOutputStream urls_output = new ObjectOutputStream(new FileOutputStream(urls_file));
//        urls_output.writeObject(urls); // записываем в файл
//        urls_output.close();

            try {
                buffer_write = new BufferedWriter(new FileWriter(git)); //urls_file
                for (Map.Entry<String, String> entry : urls.entrySet()) {
                    buffer_write.write(entry.getKey() + "=" + entry.getValue());
                    buffer_write.newLine();
                }
                buffer_write.flush();

            } catch (IOException e) {
                e.getMessage();
            } finally {

                try {
                    buffer_write.close();
                } catch (Exception e) {
                }
                menu1();
            }
        } else {
            File urls_file = new File(git);
            try {
                buffer_write = new BufferedWriter(new FileWriter(urls_file)); //urls_file
                for (Map.Entry<String, String> entry : urls.entrySet()) {
                    buffer_write.write(entry.getKey() + "=" + entry.getValue());
                    buffer_write.newLine();
                }
                buffer_write.flush();

            } catch (IOException e) {
                e.getMessage();
            } finally {

                try {
                    buffer_write.close();
                } catch (Exception e) {
                }
            }
            menu1();
        }
    }

    public static String getShortlink() throws IOException, URISyntaxException, ClassNotFoundException {
        /*
        5. Переход по короткой ссылке.
        При вводе короткой ссылки в консоль пользователь должен автоматически перенаправляться на
        исходный ресурс в браузере:
         */
        System.out.print("Введите вашу короткую ссылку: ");
        String b = input.next();
        HashMap<String, String> aNewHashMap = new HashMap<>();
        String name = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
        try {
            BufferedReader in_urls = new BufferedReader(new FileReader(name + "\\urls_file.txt"));
            String line;

            while ((line = in_urls.readLine()) != null) {
                String[] parts = line.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim();
                aNewHashMap.put(key, value);
            }

            in_urls.close();
////            File file = new File(name +"\\urls_file.txt");
////            FileInputStream f = new FileInputStream(file);
////            ObjectInputStream s = new ObjectInputStream(f);
////            HashMap<String, Object> fileObj2 = (HashMap<String, Object>) s.readObject();
////            s.close();
            Desktop.getDesktop().browse(new URI(aNewHashMap.get(b)));
            count++;
            System.out.println("Текущее число использования ссылки: " + count); // показываем пользователю, чтобы ориентировался
            clicking();
    } catch (FileNotFoundException e) {
        System.out.println("Ссылки не существует " + e.getMessage());
        menu1();
        }
        return getShortlink();
    }

    public static void clicking_times() throws IOException, URISyntaxException, ClassNotFoundException {
        //установление количество переходов
        System.out.print("Введите количество использования ссылки: ");
        int good = Integer.valueOf(input.next());
        String name = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
        File file = new File(name, "configuration_1.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(good);
        out.close();
        clicking();
        menu1();
    }

        public static void clicking() throws IOException, URISyntaxException, ClassNotFoundException {
            // проверка количества переходов
            InputStreamReader in = null;
            try {
                String name = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
                in = new InputStreamReader(new FileInputStream(name + "\\configuration_1.txt"));
                int a = Integer.valueOf(String.valueOf(in.read()));

                if (count < a) {
                    System.out.println("Установлен лимит переходов: " + a);
                    menu1();
                } else {
                    System.out.println("Истекло количество обращений к ссылке\n" +
                            "Задайте новое значение");
                    clicking_times();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Установите количество переходов ссылки " + e.getMessage());
                menu1();
            }
            in.close();
        }
    
    public static void mycalendar() throws IOException, URISyntaxException, ClassNotFoundException {
        // установка ссылки
        System.out.print("Установите день месяца существования ссылки: ");
        int data = input.nextInt();
        calendar1.set(Calendar.DAY_OF_MONTH, data);
        if (calendar1.before(calendar2)) {
            System.out.println("Вы устанавливаете дату раньше, текущей\n " +
                    "Установите другую дату");
            mycalendar();
        } else {
            String name = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
            File file = new File(name, "configuration_2.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(data);
            out.close();
            System.out.println("Дата установлена");
            read_calendar();
        }
    }

    public static void read_calendar() throws IOException, ClassNotFoundException, URISyntaxException {
        // проверка "протухших" ссылок
        try {
            String name = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
            BufferedReader in_data = new BufferedReader(new FileReader(name + "\\configuration_2.txt"));
            int line;
            line = Integer.valueOf(String.valueOf(in_data.read()));
            calendar1.set(Calendar.DAY_OF_MONTH, line);
            if (calendar2.after(calendar1)) {
                System.out.println("Ссылка устарела!");
                in_data.close();
                menu1();
            } else {
                System.out.println("Ссылка актуальна");
                in_data.close();
                menu1();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void menu1() throws IOException, URISyntaxException, ClassNotFoundException {
        boolean work = true;
        while (work) {
            System.out.print("Выберите нужный вам пункт меню:\n " +
                    "1. Cоздать короткую ссылку\n " +
                    "2. Получить ссылку для перехода\n " +
                    "3. Установить лимит использования ссылки\n " +
                    "4. Установить дату существования ссылки\n " +
                    "5. Выйти\n");

            String variant = input.next();

            switch (variant) {
                case "1":
                    createShortLink();
                    break;
                case "2":
                    getShortlink();
                    break;
                case "3":
                    clicking_times();
                    break;
                case "4":
                    mycalendar();
                case "5":
                    System.out.println("До новых встреч!");
                    System.exit(0);
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