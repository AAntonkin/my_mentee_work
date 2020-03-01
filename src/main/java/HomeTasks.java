import HomeTask1.ArrayCounter;
import HomeTask2.PropertyMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomeTasks {
    static {
        Thread.currentThread().setName("Home Tasks Thread");
    }

    public static void main(String[] args) {
//         HOME TASK 1:
        System.out.println("===================== HOME TASK 1: ============================");
        HomeTask1();
        System.out.println("\n\n===================== HOME TASK 2: ============================");
//         HOME TASK 2:
        HomeTask2();
    }

    static void HomeTask1() {
        Integer[][] array = new Integer[][]{
            //   0  1  2  3  4  5  6  7  8  9 10 11 12 13  14 15 16 17 18
            /*0*/   {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
            /*1*/   {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            /*2*/   {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            /*3*/   {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            /*4*/   {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            /*5*/   {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            /*6*/   {1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            /*7*/   {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
            /*8*/   {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            /*9*/   {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
        };
//        array  =  HomeTask1.ArrayCounter.generateArray(10,10); //TODO - not done.
        ArrayCounter ac = new ArrayCounter(array);

        String separator = new String(new char[array[0].length * 2]).replace('\0', '=');
        log.info("Found: " + ac.countRectangles() + " arrays");
        System.out.println(separator);
        ArrayCounter.printArray(array);
        System.out.println(separator);
    }

    static void HomeTask2() {
        PropertyMap pr = new PropertyMap();
        PropertyMap.print(pr.toMapAsIntKeys());
        System.out.println("=================================================");
        PropertyMap.print(pr.toMapAsStringKeys());
        pr.saveToFile();
    }
}
