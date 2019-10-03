package lesson1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

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
    static public void sortTimes(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        // O(N)
        while (scanner.hasNextLine()) {
            String time = scanner.next();
            String s = scanner.next();
            scanner.nextLine();

            if (time.startsWith("12")) {
                time = "00" + time.substring(2);
            }

            if (s.equals("AM")) {
                list1.add(time + " " + s);
            } else {
                list2.add(time + " " + s);
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
     */
    //O(nlogn)
    static public void sortAddresses(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));
        List<String[]> list = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] person = new String[3];
            for (int i = 0; i < person.length; i++) {
                person[i] = scanner.next();
                if (i == 0) {
                    person[i] += scanner.next();
                    scanner.next();
                }
            }
            list.add(person);
        }

        list.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                int compareStreet = o1[1].compareTo(o2[1]);
                return compareStreet == 0 ? o1[2].compareTo(o2[2]) : compareStreet;
            }
        });

        for (String[] person : list) {
            writer.println(person[1] + " " + person[2] + " - " + person[0]);
        }
        writer.close();
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
    //O(nlogn)
    static public void sortTemperatures(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));
        List<Double> list = new ArrayList<>();
        while (scanner.hasNext()) {
            String s = scanner.next();
            double t = Double.parseDouble(s);
            list.add(t);
        }
        Collections.sort(list);
        for (double temp : list) {
            writer.println(temp);
        }
        writer.close();
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
    //O(nlogn)
    static public void sortSequence(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));
        List<Integer> listTemp = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            String string = scanner.next();
            int x = Integer.parseInt(string);
            listTemp.add(x);
            list.add(x);
        }
        listTemp.sort((i1, i2) -> -Integer.compare(i1, i2));

        int maxCount = 0;
        int count = 0;
        int curValue = 0;

        for (int i = 0; i < listTemp.size() ; i++) {
            count++;
            if (i == listTemp.size() - 1 || !listTemp.get(i).equals(listTemp.get(i + 1))) {
                maxCount = Math.max(maxCount, count);
                if (maxCount == count) {
                    curValue = listTemp.get(i);
                }
                count = 0;
            }
        }

        int finalCurValue = curValue;
        list.removeIf(x -> x == finalCurValue);

        for (int i : list) {
            writer.println(i);
        }
        for (int i = 0; i < maxCount; i++) {
            writer.println(curValue);
        }

        writer.close();
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
