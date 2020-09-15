package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
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
     */
    private static int[][]buildMatrix(String first, String second){
        int[][] matrix = new int[first.length() + 1][second.length() + 1];
        for(int i = 0; i<=first.length(); i++){
            for (int j = 0; j<= second.length(); i++){
                if ((i == 0) || (j == 0)) {
                    matrix[i][j] = 0;
                } else {
                    if (first.charAt(i - 1) == second.charAt(j - 1)){
                        matrix[i][j] = matrix[i - 1][j - 1];
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
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
