package senla_courses.java.lection_3_task_1;

import java.util.Random;

public class methods_task_1 {
    public static void main(String[] args) {
        System.out.println(maxNumberOfThree(new Random().nextInt(901) + 99));
        System.out.println();
        System.out.println(sumOfFirstNumbers(new Random().nextInt(901) + 99,
                new Random().nextInt(901) + 99,new Random().nextInt(901) + 99));
        System.out.println();
        System.out.println(sumOfNumbers(new Random().nextInt(901) + 99));
    }

    public static int maxNumberOfThree (int n){
        System.out.println(n);
        char[] s = String.valueOf(n).toCharArray();
        int max = 0;
        for (Character num : s){
            int number = num - 48;
            if (number > max){
                max = number;
            }
        }
        return max;
    }

    public static int sumOfFirstNumbers(int a, int b, int c){
        System.out.printf("Numbers are: %d, %d, %d\n", a, b ,c);
        char[][] chars = {String.valueOf(a).toCharArray(), String.valueOf(b).toCharArray(),
                String.valueOf(c).toCharArray()};
        return chars[0][0] + chars[1][0] + chars[2][0] - 48*3;
    }

    public static int sumOfNumbers(int n){
        System.out.println(n);
        char[] s = String.valueOf(n).toCharArray();
        int sum = 0;
        for (Character num : s){
            sum += num - 48;
        }
        return sum;
    }
}
