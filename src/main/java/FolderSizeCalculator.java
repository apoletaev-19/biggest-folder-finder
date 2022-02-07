import org.w3c.dom.Node;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {
    private File folder;
    public static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};

    public FolderSizeCalculator(File folder) {
        this.folder = folder;
    }

    @Override
    protected Long compute() {

        if (folder.isFile()) {
            return folder.length();
        }

        long sum = 0;
        List<FolderSizeCalculator> subTasks = new LinkedList<>();
        File[] files = folder.listFiles();
        for (File file : files) {
            FolderSizeCalculator task = new FolderSizeCalculator(file);
            task.fork(); // запустим асинхронно
            subTasks.add(task);
        }
        for (FolderSizeCalculator task : subTasks) {
            sum += task.join(); // дождёмся выполнения задачи и прибавим результат
        }
        return sum;
    }

    public static String getHumanReadableSize(long size) {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double value = size / Math.pow(1024, i);
            if (value < 1024) {
                return Math.round(value) + "" + sizeMultipliers[i] +
                        (i > 0 ? "b" : "");
            }
        }
        return "Very big";
    }
}

