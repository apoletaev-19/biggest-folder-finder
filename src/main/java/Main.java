import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.zip.InflaterInputStream;

public class Main {

    //"C:/Users/Эндрю/Desktop/SPBMetro"
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        System.out.println("Введите путь до папки:");
        try {

            String folderPath = console.nextLine();
            File file = new File(folderPath);

            FolderSizeCalculator calculator = new FolderSizeCalculator(file);
            ForkJoinPool pool = new ForkJoinPool();
            long size = pool.invoke(calculator);
            System.out.println("Размер папки " + folderPath + " составляет " + FolderSizeCalculator.getHumanReadableSize(size));
        } catch (Exception ex) {
            System.out.println("Введенный путь неверный");
        }
    }
}

