package lesson2;

import kotlin.Pair;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // O(n)
    // T(n)
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File(inputName));
        final List<Integer> list = new ArrayList<>();

        while (scanner.hasNext()) {
            int x = scanner.nextInt();
            list.add(x);
        }
        int n = list.size();
        int[] postMax = new int[n];

        postMax[n - 1] = list.get(n - 1);
        for (int i = n - 2; i >= 0; i--) {
            postMax[i] = Math.max(list.get(i), postMax[i + 1]);
        }

        int max = 0;
        int buyIndex = -1;
        for (int i = 0; i < n - 1; i++) {
            int value = postMax[i + 1] - list.get(i);
            if (max < value) {
                max = value;
                buyIndex = i;
            }
        }

        int saleIndex = buyIndex + 1;
        for (int i = buyIndex + 2; i < n; i++) {
            if (list.get(i) > list.get(saleIndex)) {
                saleIndex = i;
            }
        }

        return new Pair<>(++buyIndex, ++saleIndex);
    }


    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */

    // O(n)
    // T(1)
    static public int josephTask(int menNumber, int choiceInterval) {
        int res = 0;
        for (int i = 1; i <= menNumber; ++i) {
            res = (res + choiceInterval) % i;
        }
        return res + 1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    // O(a.length^2)
    // T(s1.length*s2.length)
    static public String longestCommonSubstring(String s1, String s2) { // В этом алгоритме есть ошибка, которую не успела найти
        if (s1.length() < s2.length()) {
            String tmp = s1;
            s1 = s2; //?? for what?
            s2 = tmp;
        } //зачем меня местами s1, s2

        int[][] a = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 1; i < s1.length() + 1; i++) {
            for (int j = 1; j < s2.length() + 1; j++) {
                if (s1.charAt(i) == s2.charAt(j)) { // в этой строке ошибка
                    a[i][j] = a[i - 1][j - 1] + 1;
                } else {
                    a[i][j] = Math.max(a[i][j - 1], a[i - 1][j]);
                }
            }
        }

        int i = s1.length() - 1;
        int j = s2.length() - 1;
        final StringBuilder lcs = new StringBuilder();

        while (i >= 0 && j >= 0) {
            if (s1.charAt(i) == s1.charAt(j)) {
                lcs.append(s1.charAt(i));
                i--;
                j--;
            } else if (a[i - 1][j] > a[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //O(n)
    //T(n)
    static public int calcPrimesNumber(int limit) {
        int interval = 2;
        int simple = 1;

        if (limit <= 1) {
            return 0;
        }

        if (limit == 2) {
            return 1;
        }

        while (interval != limit) {
            interval++;
            boolean check = true;
            for (int i = 2; i * i <= interval; i++) {
                if (interval % i == 0) {
                    check = false;
                    break;
                }
            }

            if (check) {
                simple++;
            }
        }

        return simple;
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     *
     * O(words.size() * avg(length(word)))
     * T(n*m)
     */



    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        List<char[]> lines = new ArrayList<>();
        Map<Character, List<Point>> begins = new HashMap<>(100);
        Path path = Paths.get(inputName);
        try (BufferedReader scanner = Files.newBufferedReader(path)) {
            String line = scanner.readLine();
            while (line != null && !line.isEmpty()) {
                lines.add(line.replaceAll(" ", "").toCharArray());
                line = scanner.readLine();
            }
        }

        char[][] a = new char[lines.size()][];

        for (int i = 0; i < a.length; i++) {
            a[i] = lines.get(i);
        }

        int n = a.length;
        if (n == 0) {
            return new HashSet<>();
        }
        int m = a[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                List<Point> list = begins.getOrDefault(a[i][j], new ArrayList<>());
                list.add(new Point(i, j));
                begins.put(a[i][j], list);
            }
        }

        final Set<String> result = new HashSet<>(words.size() << 1);

        for (String word : words) {
            for (Point p : begins.getOrDefault(word.charAt(0), new ArrayList<>())) {
                if (dfs(a, new HashSet<>(), p, word, new StringBuilder())) {
                    result.add(word);
                    break;
                }
            }
        }

        return result;
    }

    static final Point[] dpoints = {
            new Point(-1, 0),
            new Point(1, 0),
            new Point(0, -1),
            new Point(0, 1)
    };


    private static boolean dfs(char[][] graph, HashSet<Point> used, Point p, String searchWord, StringBuilder word) {
        if (used.contains(p)) {
            return false;
        }

        if (!searchWord.contains(graph[p.x][p.y] + "")) {
            used.add(p);
            return false;
        }

        word.append(graph[p.x][p.y]);
        if (word.length() == searchWord.length() && word.toString().equals(searchWord)) {
            return true;
        }

        if (word.length() >= searchWord.length()) {
            return false;
        }

        if (!searchWord.startsWith(word.toString())) {
            return false;
        }

        for (Point dp : dpoints) {
            Point nextPoint = new Point(p.x + dp.x, p.y + dp.y);
            if (isGoodPoint(graph, nextPoint) && dfs(graph, used, nextPoint, searchWord, new StringBuilder(word))) {
                used.add(p);
                return true;
            }
        }

        return false;
    }

    private static boolean isGoodPoint(char[][] a, Point p) {
        if (a.length == 0) {
            return false;
        }
        return p.x >= 0 && p.y >= 0 && p.x < a.length && p.y < a[0].length;
    }
}



