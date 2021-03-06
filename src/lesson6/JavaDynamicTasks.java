package lesson6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Трудоёмкость О(M*N)
     * Ресурсоёмкость О(M*N)
     */
    private static int[][] buildMatrix(String first, String second)  {
        int length = first.length() + 1;
        int height = second.length() + 1;
        int[][] matrix = new int[length][height];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if ((i == 0) || (j == 0)) {
                    matrix[i][j] = 0;
                } else {
                    if (first.charAt(i - 1) == second.charAt(j - 1)) {
                        matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    } else {
                        matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
                    }
                }
            }
        }
        return matrix;
    }

    public static String longestCommonSubSequence(String first, String second){
        int [][] matrix = buildMatrix(first, second);
        int i = first.length();
        int j = second.length();
        StringBuilder answer = new StringBuilder();
        while ((i > 0) && (j > 0)) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                answer.append(first.charAt(i - 1));
                i--;
                j--;
            }else if (matrix[i][j] == matrix[i - 1][j]){
                i--;
            }else if (matrix[i][j] == matrix[i][j - 1]){
                j--;
            }
        }
        return answer.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * Трудоёмкость О(N^2)
     * Ресурсоёмкость О(N)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() == 0 || list.size() == 1){
            return list;
        }
        int[] lengthOfSubsequence = new int[list.size()];
        for (int num1 = list.size() - 2; num1 >= 0; num1--) {
            for (int num2 = list.size() - 1; num2 > num1; num2--) {
                if (list.get(num1) < list.get(num2) && lengthOfSubsequence[num1] <= lengthOfSubsequence[num2]) {
                    lengthOfSubsequence[num1]++;
                }
            }
        }
        int max = lengthOfSubsequence[0];
        for (int i = 1; i < lengthOfSubsequence.length; i++) {
            if (max < lengthOfSubsequence[i]) {
                max = lengthOfSubsequence[i];
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < lengthOfSubsequence.length; i++) {
            if (lengthOfSubsequence[i] == max) {
                result.add(list.get(i));
                max--;
            }
        }
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     * Трудоёмкость О(M*N)
     * Ресурсоёмкость О(M*N)
     */
    private static int getIntegerValueByIndices(List<String> input, int i, int j) {
        return Integer.parseInt(input.get(i).split(" ")[j]);
    }

    public static int shortestPathOnField(String inputName) throws IOException, URISyntaxException {
        String line;
        List<String> input = readDataFromFile(inputName);
        int length = input.get(0).split(" ").length;
        int height = input.size();
        int[][] requiredWay = new int[height][length];
        requiredWay[0][0] = getIntegerValueByIndices(input, 0, 0);
        for (int i = 1; i < height; i++) {
            requiredWay[i][0] = getIntegerValueByIndices(input, i, 0) + requiredWay[i - 1][0];
        }
        for (int j = 1; j < length; j++) {
            requiredWay[0][j] = requiredWay[0][j - 1] + getIntegerValueByIndices(input, 0, j);
        }
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < length; j++) {
                requiredWay[i][j] = Math.min(requiredWay[i - 1][j - 1], Math.min(requiredWay[i][j - 1], requiredWay[i - 1][j]))
                        + getIntegerValueByIndices(input, i, j);
            }
        }
        return requiredWay[height - 1][length - 1];
    }

    private static List<String> readDataFromFile(String src) {
        try {
            Path path = Paths.get(src);
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}