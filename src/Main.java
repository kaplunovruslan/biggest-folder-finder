import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};
    public static void main(String[] args) {
//        MyThread thread = new MyThread(1);
//        MyThread thread1 = new MyThread(2);
//
//        thread.start();
//        thread1.start();
        System.out.println(getSizeFromHumanReadable("235K"));
        System.exit(0);



        String folderPAth = "J:/kaplu";
        File file = new File(folderPAth);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        Void size = pool.invoke(calculator);

        System.out.println(getFolderSize(file));

        long duration = (System.currentTimeMillis() - start) / 1000;
        System.out.println(duration + "seconds");

    }
    public static long getFolderSize(File folder){
        if (folder.isFile()){
            return folder.length();
        }

        long sum = 0;
        File[] files = folder.listFiles();
        for (File file: files){
            sum += getFolderSize(file);
        }
        return sum;
    }
    public String getHumanReadableSize(long size){
        char[] multipliers = {'B', 'K', 'M', 'G', 'T'};
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double value = size / Math.pow(1024, i);
            if (value < 1024){
                return Math.round(value) + "" + sizeMultipliers[i] +
                        (i > 0 ? "b" : "");
            }
        }
        return "to big";
    }
    public static long getSizeFromHumanReadable(String size){
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size
                .replaceAll("[0-9\\s+]+","")
                .charAt(0);
        int multiplier  = char2multiplier.get(sizeFactor);
        long length = multiplier * Long.valueOf(
                size.replaceAll("[^0-9]",""));

        return 0;
    }
    private static HashMap<Character, Integer> getMultipliers(){
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultipliers.length; i++) {
            char2multiplier.put(
                    sizeMultipliers[i],
                    (int) Math.pow(1024, i)
            );
        }
        return char2multiplier;
    }

}