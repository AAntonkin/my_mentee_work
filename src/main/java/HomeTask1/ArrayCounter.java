package HomeTask1;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.List;
import java.util.*;

@Getter
@Slf4j
public class ArrayCounter {

    public ArrayCounter(Integer[][] array) {
        this.arrayMain = Arrays.stream(array).map(Integer[]::clone).toArray(Integer[][]::new);
        this.arrayLength = array.length;
    }

    Integer[][] arrayMain;
    int arrayLength;
    Map<Integer, Rectangle> rectangles;

    public void print() {
        this.printArray(getArrayMain());
    }

    public static void printArray(Integer[][] array) {
        for (int i = 0; i < array.length; i++) {
            String row = "";
            for (int j = 0; j < array[i].length; j++) {
                row += (array[i][j] == 0 ? " " : "X") + " ";
            }
            System.out.println(row);
        }
    }

    public Map<Integer, Rectangle> getRectangles() {
        if (this.rectangles == null) {
            this.rectangles = new HashMap<>();
            int numberOfRect = 0;
            for (int y = 0; y < getArrayLength(); y++) {
                int rowLength = getArrayMain()[y].length;
                for (int x = 0; x < rowLength; x++) {
                    Integer elem = getArrayMain()[y][x];
                    if (elem == 1) {
                        Rectangle currentPoint = new Rectangle();
                        int ySize = 0;
                        currentPoint.setLocation(x, y);
                        numberOfRect++;
                        while (inBoundaries(y, x) && getArrayMain()[y][x] != 0) {
                            while (inBoundaries(y, x) && getArrayMain()[y][x] != 0) {
                                getArrayMain()[y][x] = 0;
                                y++;
                            }
                            x++;
                            ySize = y;
                            y = (int) currentPoint.getY();
                        }
                        currentPoint.setSize(x - (int) currentPoint.getX(), ySize - y);
                        this.rectangles.put(numberOfRect, currentPoint);
                        y = (int) currentPoint.getY();
                    }
                }
            }
        }
        return rectangles;
    }

    boolean inBoundaries(int y, int x) {
        return (y < getArrayLength() && x < getArrayMain()[y].length);
    }

    static boolean inBoundaries(Integer[][] array, int y, int x) {
        return y >= 0 && x >= 0 && y < array.length && x < array[y].length;
    }

    public int countRectangles() {
        return getRectangles().size();
    }

    static void setZerosNearBy(Integer[][] array, int y, int x) {
        // -1;-1 | 0;-1 | 1;-1 |
        // -1; 0 | 0; 0 | 1; 0 |
        // -1; 1 | 0; 1 | 1; 1 |
        List<Point> pointAround = new ArrayList<Point>() {
            {
                add(new Point(-1, -1));
                add(new Point(0, -1));
                add(new Point(1, -1));
                add(new Point(-1, 0));
                add(new Point(1, 0));
                add(new Point(-1, 1));
                add(new Point(0, 1));
                add(new Point(1, 1));
            }
        };
        for (Point point : pointAround) {
            try {
                if (inBoundaries(array, y + (int) point.getY(), x + (int) point.getX()) &&
                    array[y + (int) point.getY()][x + (int) point.getX()] < 0)
                    array[y + (int) point.getY()][x + (int) point.getX()] = 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                log.info("{}: x:{}, y:{} ", e.getMessage(), x, y);
                System.out.println(e.getMessage() + ": x:" + x + ",  y:" + y);
            }
        }
    }

    public static Integer[][] generateArray(int ySize, int xSize) {
        Integer[][] newArray = fillArrayWith(new Integer[xSize][ySize], -1);
        for (int y = 0; y < newArray.length; y++) {
            for (int x = 0; x < newArray[y].length; x++) {
                if (newArray[y][x] == -1 && new Random().nextInt(2) == 1) {
                    Rectangle rect = getRandomRect(xSize / 2, xSize / 2);
                    log.info("Rectangle size: {}x{} (width x height)", rect.getWidth(), rect.getHeight());
                    int yPoint = (int) rect.getWidth();
                    while (inBoundaries(newArray, y, x) && yPoint-- > 0) {
                        int xPoint = (int) rect.getHeight();
                        while (xPoint-- > 0) {
                            if (inBoundaries(newArray, y + yPoint, x + xPoint)) {
                                newArray[y + yPoint][x + xPoint] = 1;
                                setZerosNearBy(newArray, (y + yPoint), (x + xPoint));
                            }
                        }
                    }
                }
            }
        }
        return newArray;
    }

    private static Integer[][] fillArrayWith(Integer[][] array, int value) {
        for (int y = 0; y < array.length; y++)
            for (int x = 0; x < array[y].length; x++) array[y][x] = value;
        return array;
    }

    private static Rectangle getRandomRect(int maxWidth, int maxHeight) {
        Rectangle rect = new Rectangle();
        int width = Math.max(new Random().nextInt(maxWidth + 1), 1);
        int height = Math.max(new Random().nextInt(maxHeight + 1), 1);
        rect.setSize(width, height);
        return rect;
    }

    private static List<Rectangle> getRandomRectangles(int numberOfRectangles, int maxWidth, int maxHeight) {
        List<Rectangle> rects = new ArrayList<>(numberOfRectangles);
        rects.forEach(r -> r.add(getRandomRect(maxWidth, maxHeight)));
        return rects;
    }

}
