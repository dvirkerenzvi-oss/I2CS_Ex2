import assignments.Ex2.*;

import java.awt.*;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for assignments.Ex2.Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the assignments.Ex2.StdDraw class, as in:
 * <a href="https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html">...</a>
 *
 *
 */

/**
 * Visualizes the current state of the map on the StdDraw window.
 * Iterates through the map grid, assigns colors based on pixel values
 * (e.g., walls, start/end points, path), and draws a grid overlay.
 * @param map The map object to be drawn.
 */
public static void drawMap(Map2D map) {
    for(int j=0;j<map.getHeight();j++){
        for(int i=0;i< map.getWidth();i++){
            if(map.getPixel(i,j)==-1){
                StdDraw.setPenColor(StdDraw.BLACK);
            }
            else if(map.getPixel(i,j)==0){
                StdDraw.setPenColor(StdDraw.WHITE);
            }
            else if(map.getPixel(i,j)==1){
                StdDraw.setPenColor(StdDraw.YELLOW);
            }
            else if(map.getPixel(i,j)==2){
                StdDraw.setPenColor(StdDraw.GREEN);
            }
            else if(map.getPixel(i,j)==3){
                StdDraw.setPenColor(StdDraw.RED);
            }
            else if(map.getPixel(i,j)==4){
                StdDraw.setPenColor(StdDraw.BLUE);
            }
            else{
                int g = map.getPixel(i,j)%255;
                StdDraw.setPenColor(new Color(0,g,255-g));
            }
            StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.square(i + 0.5, j + 0.5, 0.5);
        }
    }
    StdDraw.show();
}

/**
 * Loads a Map2D object from a specified text file.
 * The method parses the file to get the width, height, and pixel data.
 * @param mapFileName The name/path of the file to load.
 * @return A new Map2D object populated with the data from the file, or null if an error occurs.
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
        e.printStackTrace();
    }
    return ans;
}

/**
 * Saves the current Map2D object to a text file.
 * Writes the map's dimensions followed by the matrix of pixel values.
 * @param map The map object to save.
 * @param mapFileName The name of the file to create/overwrite.
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
/**
 * The main entry point of the application.
 * Initializes the GUI window (canvas), sets up the map, and runs the infinite
 * event loop that handles user inputs (mouse clicks for drawing, keyboard for modes).
 */
void main() {
    Map2D map = new Map(20,20,0);
    StdDraw.setCanvasSize(map.getWidth()*20,map.getHeight()*20);
    StdDraw.setXscale(0, map.getWidth());
    StdDraw.setYscale(0, map.getHeight());
    StdDraw.enableDoubleBuffering();
    int mode = 0;
    char w = 'p';
    Pixel2D p1 = null;
    Pixel2D s = null;
    Pixel2D e = null;
    while(true){
        StdDraw.clear();
        drawMap(map);
        if(StdDraw.hasNextKeyTyped()){
            char h = StdDraw.nextKeyTyped();
            if(h=='m'){
                if(mode==1){
                    mode=0;
                    System.out.print("\rmode change to 0" + "                 ");
                }
                else{
                    mode=1;
                    System.out.print("\rmode change to 1" + "                 ");
                }
            }
            if(h=='p'){
                w='p';
                System.out.print("\rThe tool has been changed to a point" + "                 ");
            }
            if(h=='f'){
                w='f';
                System.out.print("\rThe tool has been changed to delete" + "                 ");
            }
            if(h=='s'){
                w='s';
                System.out.print("\rChoose a starting point" + "                 ");
            }
            if(h=='e'){
                w='e';
                System.out.print("\rChoose an ending point" + "                 ");
            }
            if(h=='l'){
                w='l';
                System.out.print("\rThe tool has been changed to a line" + "                 ");
            }
            if(h=='c'){
                w='c';
                System.out.print("\rThe tool has been changed to a circle" + "                 ");
            }
            if(h=='r'){
                w='r';
                System.out.print("\rThe tool has been changed to a rectangle" + "                 ");
            }
            if(h=='0'){
                Pixel2D[] shortestPath = map.shortestPath(s,e,-1,false);
                if(shortestPath!=null) {
                    for(int i=0;i<shortestPath.length;i++){
                        map.setPixel(shortestPath[i], 4);
                    }
                    map.setPixel(s,2);
                    map.setPixel(e,3);
                }
                else{
                    System.out.println("There is no path!" + "                 ");
                }
            }
            if(h==' '){
                map.init(20,20,0);
                p1 = null;
                s = null;
                e = null;
                System.out.print("\rThe map has been cleared" + "                 ");
            }
            if(h=='a'){
                saveMap(map, "map.txt");
                System.out.print("Map saved successfully" + "                 ");
            }
            if(h=='y'){
                Map2D newMap = loadMap("map.txt");
                if(newMap!=null) {
                    map = newMap;
                    System.out.print("\rMap loaded successfully from map.txt" + "                 ");
                    for (int i = 0; i < map.getWidth(); i++) {
                        for (int j = 0; j < map.getHeight(); j++) {
                            int pixelValue = map.getPixel(i, j);

                            if (pixelValue == 2) { // מצאנו התחלה (ירוק)
                                s = new Index2D(i, j);
                            }
                            if (pixelValue == 3) { // מצאנו סוף (אדום)
                                e = new Index2D(i, j);
                            }
                        }
                    }
                }
            }
        }
        if(StdDraw.isMousePressed()){
            Pixel2D p0 = new Index2D((int) StdDraw.mouseX(),(int) StdDraw.mouseY());
            if(map.isInside(p0)){
                StdDraw.pause(300);
                if(mode==0){
                    if(w=='p'){
                        map.setPixel(p0,-1);
                    }
                    if(w=='f'){
                        map.fill(p0,0,false);
                    }
                    if(w=='s'){
                        s=p0;
                        map.setPixel(s,2);
                    }
                    if(w=='e'){
                        e=p0;
                        map.setPixel(e,3);
                    }
                    if(w=='l'){
                        if(p1==null){
                            p1 = p0;
                        }
                        else {
                            map.drawLine(p1, p0, -1);
                            p1=null;
                        }
                    }
                    if(w=='c'){
                        if(p1==null) {
                            p1 = p0;
                        }
                        else {
                            double rad = p1.distance2D(p0);
                            map.drawCircle(p1, rad, -1);
                            p1=null;
                        }
                    }
                    if(w=='r'){
                        if(p1==null) {
                            p1 = p0;
                        }
                        else {
                            map.drawRect(p1, p0, -1);
                            p1=null;
                        }
                    }
                }
                if(mode==1){
                    if(w=='l' && map.getPixel(p0)!=-1){
                        if(p1==null){
                            p1 = p0;
                        }
                        else {
                            map.drawLine(p1, p0, 1);
                            p1=null;
                        }
                    }
                    if(w=='p'){
                        map.setPixel(p0,1);
                    }
                    if(w=='f' && map.getPixel(p0)!=-1){
                        map.fill(p0, 0, false);
                    }
                }
            }
        }
    }
}
