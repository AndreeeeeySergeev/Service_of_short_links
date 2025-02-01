import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        menu();
    }
        public static void menu() throws IOException, ClassNotFoundException {
            Scanner input = new Scanner(System.in);
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
                System.out.println(uuid);
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
                    menu();
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
                            break;
                    }
                }
            }
        }
    }

//    public static void createShortLink() throws FileNotFoundException, IOException {
//        Scanner input = new Scanner(System.in);
//        System.out.print("Введите вашу ccылку: ");
//        String link = input.nextLine();
//        String shortlink = link + UUID.randomUUID().toString().substring(0, 8);// создаем ссылку
//        System.out.println("Ваша короткая ссылка: " + shortlink);
//        //link == shortlink;
//        File myfile = new File("links.txt"); // записываем в файл,
//        OutputStreamWriter writered = new OutputStreamWriter(new FileOutputStream(myfile));
//        writered.write(shortlink);// чтобы дальше взаимодействовать с ней
//        writered.close();
//    }
//
//    public static void deletelink() throws IOException {
//        try {
//            InputStreamReader reading = new InputStreamReader(new FileInputStream("links.txt"));
//            String a = String.valueOf(reading.read());
//        } catch (FileNotFoundException e) {
//            System.out.println("Ошибка:" + e.getMessage());
//        }
//        Calendar days = Calendar.getInstance();
//        if (days.after(3)) { // время существования ссылки
////            a.resolveConstantDesc(createShortLink());
//            File file = new File("links.txt");
//            file.delete();
//            createShortLink();// Здесь получается, что будем снова перезаписывать ссылку после 3 дней
//        }
//    }

//        public static void clicking() throws IOException {
//            Scanner onesmore = new Scanner(System.in);
//            System.out.print("Введите количество использования ссылки: ");
//            int good = Integer.valueOf(onesmore.nextLine());
//            File file = new File("links.txt");
//            file.exists(); // проверяем существование файла
////            BufferedReader read = new BufferedReader(file, good); // BufferedInputSTREAM
//            InputStreamReader reading = new InputStreamReader(new FileInputStream(file));
//            reading.read();
//            Integer c = Integer.valueOf(reading.read());
//            if ( c > good) {
//                file.delete(); // если превышает количество обращений к файлу ссылки, то файл удаляется
//        }
//    }
//

//    public static void menu() throws IOException {
//            Scanner input = new Scanner(System.in);
//            boolean work = true;
//            while (work) {
//                System.out.print("Выберите нужный вам пункт меню:\n " +
//                        "1. Войти\n " +
//                        "2. Посчитать доходы\n " +
//                        "3. Посчитать расходы\n " +
//                        "4. Установить бюджет\n " +
//                        "5. Показать итог\n " +
//                        "6. Посчитать бюджет\n " +
//                        "7. Выйти\n");
//                String choose = input.nextLine();
//
//                switch (choose) {
//                    case "1":
//                        break;
//                    case "2":
//
//                        break;
//                    case "3":
//
//                    case "4":
//
//                    case "5":
//
//                    case "6":
//
//                    case "7":
//                        System.out.println("До новых встреч!");
//                        work = false;
//                        break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + choose);
//                }
//            }
//        }




//public UUID () {
//    Set<String> set = new HashSet<>();
//    UUID uuid = UUID.randomUUID();
//    uuid.toString();
//    set.add(uuid);


//}