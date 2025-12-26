import java.awt.*;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * <a href="https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html">...</a>
 *
 *
 */
public static void drawMap(Map2D map) {
    StdDraw.setCanvasSize(map.getWidth()*20,map.getHeight()*20);
    StdDraw.setXscale(0, map.getWidth());
    StdDraw.setYscale(0, map.getHeight());
    for(int j=0;j<map.getHeight();j++){
        for(int i=0;i< map.getWidth();i++){
            if(map.getPixel(i,j)==-1){
                StdDraw.setPenColor(StdDraw.BLACK);
            }
            else if(map.getPixel(i,j)==-2){
                StdDraw.setPenColor(StdDraw.WHITE);
            }
            else if(map.getPixel(i,j)==0){
                StdDraw.setPenColor(StdDraw.YELLOW);
            }
            else{
                int g = map.getPixel(i,j)%255;
                StdDraw.setPenColor(new Color(0,g,255-g));
            }
            StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
        }
    }
    StdDraw.show();
}

/**
 * @param mapFileName
 * @return
 */
public static Map2D loadMap(String mapFileName) {
    Map2D ans = null;
    try {
        FileReader map2d = new FileReader(mapFileName);
        Scanner scanner = new Scanner(map2d);
        scanner.useDelimiter(",|\\s+");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        ans = new Map(width, height, 0);
        for (int i = 0; i < ans.getHeight(); i++) {
            for (int j = 0; j < ans.getWidth(); j++) {
                if (scanner.hasNextInt()) {
                    ans.setPixel(j, i, scanner.nextInt());
                }
            }
        }
        scanner.close();
    } catch (IOException e) {
        e.printStackTrace();;
    }
    return ans;
}

/**
 *
 * @param map
 * @param mapFileName
 */
public static void saveMap(Map2D map, String mapFileName) {
    try {
        FileWriter map2d = new FileWriter(mapFileName);
        map2d.write(map.getWidth() + "," + map.getHeight() + "\n");
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                map2d.write(map.getPixel(j, i) + " ");
            }
            map2d.write("\n");
        }
        map2d.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

void main() {
    String mapFile = "map.txt";
    Map2D map = loadMap(mapFile);
    drawMap(map);
}
