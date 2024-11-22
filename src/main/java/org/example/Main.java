package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    // Статические счетчики для длин 3, 4 и 5.
    public static AtomicInteger countLength3 = new AtomicInteger(0);
    public static AtomicInteger countLength4 = new AtomicInteger(0);
    public static AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];

        // Генерация 100,000 текстов
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Создание и запуск потоков для проверки "красивых" слов
        Thread thread3 = new Thread(() -> checkBeautifulWords(texts, 3));
        Thread thread4 = new Thread(() -> checkBeautifulWords(texts, 4));
        Thread thread5 = new Thread(() -> checkBeautifulWords(texts, 5));

        thread3.start();
        thread4.start();
        thread5.start();

        // Ожидание завершения всех потоков
        thread3.join();
        thread4.join();
        thread5.join();

        // Вывод результатов
        System.out.println("Красивых слов с длиной 3: " + countLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + countLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + countLength5.get() + " шт");
    }

    // Метод генерации текста
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // Метод проверки "красивых" слов
    public static void checkBeautifulWords(String[] texts, int length) {
        for (String text : texts) {
            if (text.length() == length) {
                if (isPalindrome(text) || isSameLetter(text) || isSorted(text)) {
                    if (length == 3) {
                        countLength3.incrementAndGet();
                    } else if (length == 4) {
                        countLength4.incrementAndGet();
                    } else if (length == 5) {
                        countLength5.incrementAndGet();
                    }
                }
            }
        }
    }

    // Проверка на палиндром
    public static boolean isPalindrome(String text) {
        int len = text.length();
        for (int i = 0; i < len / 2; i++) {
            if (text.charAt(i) != text.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    // Проверка на одинаковые буквы
    public static boolean isSameLetter(String text) {
        char firstChar = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (c != firstChar) {
                return false;
            }
        }
        return true;
    }

    // Проверка на сортировку
    public static boolean isSorted(String text) {
        char[] chars = text.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < chars[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
