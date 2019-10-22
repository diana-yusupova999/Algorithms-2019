package lesson1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // асимптотика О(nlogn)
    // T(n)
    static public void sortTimes(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        // O(N)
        while (scanner.hasNextLine()) {
            String time = scanner.next();
            String s = scanner.next();
            scanner.nextLine();
            Pattern p = Pattern.compile("(0[1-9]:)|(1[0-1]:)[0-5][0-9]:[0-5][0-9]");
            Matcher m = p.matcher(time);

            if (time.startsWith("12")) {
                time = "00" + time.substring(2);
            }

            if(m.matches()) {
                if (s.equals("AM")) {
                    list1.add(time + " " + s);
                } else {
                    list2.add(time + " " + s);
                }

            }

        }
        Collections.sort(list1);// O(nlogn)
        Collections.sort(list2);
        list1.addAll(list2);// O(n)

        // O(n)
        for (String s : list1) {
            if (s.startsWith("00")) {
                s = "12" + s.substring(2);
            }
            writer.println(s);
        }
        writer.close();
        scanner.close();
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * <p>
     * Людей, живущих в одном доме, выводить через запятую по алфавиту
     */

    //O(nlogn)
    //T(n)
    static public void sortAddresses(String inputName, String outputName) throws IOException {

        Map<String, List<String>> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] add1 = o1.split(" ");
                String[] add2 = o2.split(" ");

                int compareStreet = add1[0].compareTo(add2[0]);
                return compareStreet == 0 ? Integer.compare(Integer.parseInt(add1[1]), Integer.parseInt(add2[1])) : compareStreet;
            }
        });

        try (BufferedReader scanner = new BufferedReader(new FileReader(inputName))) { //расширение StandardCharsets.UTF_8 добавила, т.к. в файл output выводились иероглифы
            String line = scanner.readLine();
            while (line != null && !line.isEmpty()) {

                String[] words = line.split(" ");

                String address = words[3] + " " + words[4];
                List<String> persons = map.getOrDefault(address, new ArrayList<>());
                persons.add(words[0] + " " + words[1]);
                map.put(address, persons);

                line = scanner.readLine();
            }
        }

        try (PrintWriter writer = new PrintWriter(new File(outputName), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {

                String address = entry.getKey();
                List<String> persons = entry.getValue();

                Collections.sort(persons);
                writer.print(address + " - ");

                Iterator<String> iterator = persons.iterator();
                while (iterator.hasNext()) {
                    String person = iterator.next();
                    writer.print(person);
                    if (iterator.hasNext()) {
                        writer.print(", ");
                    } else {
                        writer.println();
                    }
                }
            }
        }
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //O(n)
    //T(1)
    static public void sortTemperatures(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));

        int[] temps = new int[7731];
        while (scanner.hasNext()) {
            String s = scanner.next();
            double t = Double.parseDouble(s);
            if (t <= 500.0 && t >= -273.0) {
                int tInt = (int) Math.round((t + 273) * 10);
                temps[tInt]++;
            }
        }

        try (PrintWriter writer = new PrintWriter(new File(outputName))) {
            for (int t = 0; t < temps.length; t++) {
                for (int i = 0; i < temps[t]; i++) {
                    int tempWrite = t - 2730;
                    if (tempWrite < 0) {
                        writer.print("-");
                    }
                    tempWrite = Math.abs(tempWrite);

                    writer.println(tempWrite / 10 + "." + tempWrite % 10);
                }
            }
        }
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    //O(n)
    //T(n)
    static public void sortSequence(String inputName, String outputName) throws IOException {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();

        try (BufferedReader scanner = new BufferedReader(new FileReader(inputName))) {
            String line = scanner.readLine();
            while (line != null && !line.isEmpty()) {
                int x = Integer.parseInt(line);
                map.put(x, map.getOrDefault(x, 0) + 1);
                list.add(x);
                line = scanner.readLine();
            }
        }

        int maxValue = -1;
        int minKey = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int value = entry.getValue();
            if (value > maxValue) {
                minKey = entry.getKey();
                maxValue = value;
            } else if (value == maxValue) {
                minKey = Math.min(entry.getKey(), minKey);
            }
        }

        int finalCurValue = minKey;
        list.removeIf(x -> x == finalCurValue);

        for (int i = 0; i < maxValue; i++) {
            list.add(minKey);
        }

        try (PrintWriter writer = new PrintWriter(new File(outputName))) {
            for (int i : list) {
                writer.println(i);
            }
        }
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    // асимптотика O(second.length) или O(n)
    // T(second.length)
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int firstIndex = 0;
        int secondIndex = first.length;
        for (int i = 0; i < second.length; i++) {
            if (firstIndex < first.length && (secondIndex == second.length || first[firstIndex].compareTo(second[secondIndex]) <= 0)) {
                second[i] = first[firstIndex++];
            } else {
                second[i] = second[secondIndex++];
            }
        }
    }
}
