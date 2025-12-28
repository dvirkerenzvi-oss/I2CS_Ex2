package assignments.Ex2;

import java.io.Serializable;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{

    // edit this class below
    private int[][] _map;
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w the width of the underlying 2D array.
	 * @param h the height of the underlying 2D array.
	 * @param v the init value of all the entries in the 2D array.
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size the number of rows and columns in the map.
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data a 2D array of integers representing the map values.
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
        this._map = new int[w][h];
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++) {
                this._map[j][i] = v;
            }
        }
	}

    /**
     * Initializes the map using a deep copy of the given 2D array.
     * @param arr a 2D int array.
     */
	@Override
	public void init(int[][] arr) {
        if(arr==null || arr.length==0 || !isRectArray(arr)){
            throw new RuntimeException("ERR: The entered array is invalid");
        }
        int w = arr.length;
        int h = arr[0].length;
        this._map = new int[w][h];
        for(int i = 0;i<h;i++){
            for(int j = 0;j<w;j++){
                _map[j][i] = arr[j][i];
            }
        }
	}

    /**
     * Returns a deep copy of the 2D map array.
     * @return a copy of the underlying 2D array.
     */
	@Override
	public int[][] getMap() {
		int[][] ans = null;
        int w = getWidth();
        int h = getHeight();
        ans = new int[w][h];
        for(int i = 0;i<h;i++){
            for(int j = 0;j<w;j++){
                ans[j][i] = this._map[j][i];
            }
        }
		return ans;
	}

    /**
     * Returns the width (number of columns) of the map.
     * @return the number of columns in the map.
     */
	@Override
	public int getWidth() {
        int ans = -1;
        ans = this._map.length;
        return ans;
    }

    /**
     * Returns the height (number of rows) of the map.
     * @return the number of rows in the map.
     */
	@Override
	public int getHeight() {
        int ans = -1;
        ans = this._map[0].length;
        return ans;
    }

    /**
     * Returns the value of the pixel at (x,y), or -1 if the coordinates are out of bounds.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the color at (x,y), or -1 if invalid.
     */
	@Override
	public int getPixel(int x, int y) {
        int ans = -1;
        if(x>=0 && x<getWidth() && y>=0 && y<getHeight()) {
            ans = this._map[x][y];
        }
        return ans;
    }

    /**
     * Returns the value of the pixel at the given point, or -1 if out of bounds.
     * @param p the x,y coordinate
     * @return the color of the pixel, or -1 if invalid.
     */
	@Override
	public int getPixel(Pixel2D p) {
        int ans = -1;
        if(isInside(p)==true) {
            ans = getPixel(p.getX(),p.getY());
        }
        return ans;
	}

    /**
     * Sets the value 'v' at coordinates (x,y) if they are within the map boundaries.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param v the value that the entry at the coordinate [x][y] is set to.
     */
	@Override
	public void setPixel(int x, int y, int v) {
        if(x>=0 && x<getWidth() && y>=0 && y<getHeight()) {
            this._map[x][y] = v;
        }
    }

    /**
     * Sets the value 'v' at the given point 'p' if it is inside the map.
     * @param p the coordinate in the map.
     * @param v the value that the entry at the coordinate [p.x][p.y] is set to.
     */
	@Override
	public void setPixel(Pixel2D p, int v) {
        if(isInside(p)){
            setPixel(p.getX(),p.getY(),v);
        }
	}

    /**
     * Checks if the given point is within the map boundaries.
     * @param p the 2D pixel.
     * @return true if the point is within the map boundaries.
     */
    @Override
    public boolean isInside(Pixel2D p) {
        boolean ans = true;
        if(p.getX()>=getWidth() || p.getX()<0){
            ans = false;
        }
        if(p.getY()>=getHeight() || p.getY()<0){
            ans = false;
        }
        return ans;
    }

    /**
     * Checks if the given map has the same width and height as this map.
     * @param p the other map to compare dimensions with.
     * @return true if the maps have the same width and height.
     */
    @Override
    public boolean sameDimensions(Map2D p) {
        boolean ans = false;
        if(p!=null) {
            if (getWidth() == p.getWidth() && getHeight() == p.getHeight()) {
                ans = true;
            }
        }
        return ans;
    }

    /**
     * Adds the values of the given map to this map, if dimensions match.
     * @param p - the map that should be added to this map.
     */
    @Override
    public void addMap2D(Map2D p) {
        if(sameDimensions(p)){
            for(int i=0;i<getWidth();i++){
                for(int j=0;j<getHeight();j++){
                    this._map[i][j] += p.getPixel(i,j);
                }
            }
        }
    }

    /**
     * Multiplies every value in the map by the given scalar.
     * @param scalar the number to multiply every pixel by.
     */
    @Override
    public void mul(double scalar) {
        for(int i=0;i<getWidth();i++){
            for(int j=0;j<getHeight();j++){
                this._map[i][j] = (int) (scalar*this._map[i][j]);
            }
        }
    }

    /**
     * Resizes the map based on scaling factors sx (width) and sy (height).
     * @param sx the scaling factor for the width (x-axis).
     * @param sy the scaling factor for the height (y-axis).
     */
    @Override
    public void rescale(double sx, double sy) {
        int w = (int) (getWidth()*sx);
        int h = (int) (getHeight()*sy);
        int[][] changeMap = new int[w][h];
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                int x = (int) (i/sx);
                int y = (int) (j/sy);
                changeMap[i][j] = this._map[x][y];
            }
        }
        this._map = changeMap;
    }

    /**
     * Draws a filled circle around the center point with the specified radius and color.
     * @param center the center point of the circle.
     * @param rad the radius of the circle.
     * @param color - the new color to be used in the drawing.
     */
    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        int minX = (int) Math.max(0, center.getX()-rad);
        int maxX = (int) Math.min(center.getX()+rad, getWidth());
        int minY = (int) Math.max(0, center.getY()-rad);
        int maxY = (int) Math.min(center.getY()+rad, getHeight());
        for(int i=minX;i<=maxX;i++){
            for(int j=minY;j<=maxY;j++){
                if(distanceFromCenter(i,j,center)<=rad){
                    this._map[i][j] = color;
                }
            }
        }
    }

    /**
     * Draws a line between p1 and p2.
     * @param p1 the starting point of the line.
     * @param p2 the ending point of the line.
     * @param color - the (new) color to be used in the drawing.
     */
    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        int dx = Math.abs(p2.getX()-p1.getX());
        int dy = Math.abs(p2.getY()-p1.getY());
        int x1 = p1.getX();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int y2 = p2.getY();
        if(p1.equals(p2)){
            setPixel(p1,color);
        }
        if(dx>=dy && x1<x2){
            for(int i = x1;i<=x2;i++){
                setPixel(i, (int) Math.round(f(p1, p2, i)), color);
            }
        }
        if(dx>=dy && x1>x2) {
            drawLine(p2, p1, color);
        }
        if(dx<dy && y1<y2){
            for(int i = y1;i<=y2;i++){
                setPixel((int) Math.round(g(p1, p2, i)), i, color);
            }
        }
        if(dx<dy && y1>y2) {
            drawLine(p2, p1, color);
        }
    }

    /**
     * Draws a filled rectangle defined by two opposite corners (p1, p2) with the given color.
     * @param p1 one corner of the rectangle.
     * @param p2 the opposite corner of the rectangle.
     * @param color - the (new) color to be used in the drawing.
     */
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        int maxX = Math.max(p1.getX(),p2.getX());
        int minX = Math.min(p1.getX(),p2.getX());
        int maxY = Math.max(p1.getY(),p2.getY());
        int minY = Math.min(p1.getY(),p2.getY());
        for(int i = minX;i<=maxX;i++){
            for(int j = minY;j<=maxY;j++){
                setPixel(i, j, color);
            }
        }
    }

    /**
     * Checks if this map is equal to another object (same dimensions and same pixel values).
     * @param ob the reference object with which to compare.
     * @return true if the given object is a Map2D with the same dimensions and values.
     */
    @Override
    public boolean equals(Object ob) {
        boolean ans = false;
        if(ob instanceof Map2D){
            Map2D object = (Map2D) ob;
            if(sameDimensions(object)){
                ans = true;
                for(int i = 0;i<getWidth();i++){
                    for(int j = 0;j<getHeight();j++){
                        if(this._map[i][j]!=object.getPixel(i,j)){
                            ans = false;
                            break;
                        }
                    }
                }
            }
        }
        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
     * @param xy the starting pixel.
     * @param new_v the new color.
     * @param cyclic if true, the map boundaries are cyclic.
     * @return the number of pixels that were modified.
	 */
	public int fill(Pixel2D xy, int new_v, boolean cyclic) {
		int ans = -1;
        int x = xy.getX();
        int y = xy.getY();
        int color = getPixel(xy);
        if(getPixel(xy)!=new_v) {
            ans = fillHelp(xy, color, new_v, cyclic);
        }
		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
     * @param p1 the start point.
     * @param p2 the destination point.
     * @param obsColor the pixel value of obstacle.
     * @param cyclic if true, the map boundaries are cyclic.
     * @return an array of Pixel2D of shortest path from p1 to p2, or null if no path exists.
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.
        Map2D map1 = allDistance(p1, obsColor, cyclic);
        if(map1.getPixel(p2)!=-2 && map1.getPixel(p2)!=-1) {
            ans = new Pixel2D[map1.getPixel(p2)+1];
            ans[map1.getPixel(p2)] = p2;
            for(int i=ans.length-2;i>=0;i--){
                int x = p2.getX();
                int y = p2.getY();
                Pixel2D p = cyclic(x+1, y, cyclic);
                if(map1.isInside(p) && map1.getPixel(p)==i){
                    ans[i] = p;
                    p2 = p;
                    continue;
                }
                p = cyclic(x-1, y, cyclic);
                if(map1.isInside(p) && map1.getPixel(p)==i){
                    ans[i] = p;
                    p2 = p;
                    continue;
                }
                p = cyclic(x, y+1, cyclic);
                if(map1.isInside(p) && map1.getPixel(p)==i){
                    ans[i] = p;
                    p2 = p;
                    continue;
                }
                p = cyclic(x, y-1, cyclic);
                if(map1.isInside(p) && map1.getPixel(p)==i){
                    ans[i] = p;
                    p2 = p;
                }
            }
        }
		return ans;
	}

    /**
     * Generates a new map where each pixel represents its distance from the start point.
     * Obstacles are marked with -1, and unreachable areas with -2.
     * @param start the start point
     * @param obsColor the color representing obstacles
     * @param cyclic if true, the map boundaries are cyclic.
     * @return a distance map.
     */
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.
        ans = new Map(getWidth(), getHeight(), -2);
        for(int i=0;i<getWidth();i++){
            for(int j=0;j<getHeight();j++){
                if(_map[i][j]==obsColor){
                    ans.setPixel(i,j,-1);
                }
            }
        }
        if(ans.getPixel(start)!=-1) {
            allDistanceHelp(start, ans, cyclic, 0);
        }
        return ans;
    }
	////////////////////// Private Methods ///////////////////////

    /**
     * Checks if a 2D array is jagged or rectangular.
     * @param arr 2D array.
     * @return true if all rows have the same length.
     */
    private boolean isRectArray(int[][] arr){
        boolean ans = true;
        int row1 = arr[0].length;
        for(int i = 1;i<arr.length;i++){
            if(row1 != arr[i].length){
                ans = false;
                break;
            }
        }
        return ans;
    }

    /**
     * Calculates the Euclidean distance between a point (i,j) and the center.
     * @param i the x of the point.
     * @param j the y of the point.
     * @param center the reference center point.
     * @return the distance between the point (i,j) and the center.
     */
    private double distanceFromCenter(int i, int j, Pixel2D center){
        double ans = -1;
        double x = center.getX()-i;
        double y = center.getY()-j;
        double dist = x*x+y*y;
        ans = Math.sqrt(dist);
        return ans;
    }

    /**
     * Linear function calculation for Y coordinate based on X (y = mx + b).
     * @param p1 first point.
     * @param p2 second point.
     * @param x the x coordinate.
     * @return the y coordinate on the line for the given x.
     */
    private double f(Pixel2D p1, Pixel2D p2, int x){
        double a = (double) (p2.getY() - p1.getY()) /(p2.getX()-p1.getX());
        return (double) p1.getY() + a*(x-p1.getX());
    }

    /**
     * Linear function calculation for X coordinate based on Y (x = my + b).
     * @param p1 first point.
     * @param p2 second point.
     * @param y the y coordinate.
     * @return the x coordinate on the line for the given y.
     */
    private double g(Pixel2D p1, Pixel2D p2, int y){
        double a = (double) (p2.getX() - p1.getX()) /(p2.getY()-p1.getY());
        return (double) p1.getX() + a*(y-p1.getY());
    }

    /**
     * Recursive helper function for the Flood Fill algorithm.
     * @param xy the current pixel.
     * @param color the original color.
     * @param new_v the new color.
     * @param cyclic if true, the map boundaries are cyclic.
     * @return the number of pixels filled.
     */
    private int fillHelp(Pixel2D xy, int color, int new_v, boolean cyclic){
        int ans = 0;
        xy = cyclic(xy.getX(),xy.getY(),cyclic);
        int x = xy.getX();
        int y = xy.getY();
        if(x>=0 && x<getWidth() && y>=0 && y<getHeight()){
            if(getPixel(xy) == color) {
                setPixel(xy, new_v);
                ans = 1;
                Pixel2D up = new Index2D(x, y + 1);
                Pixel2D down = new Index2D(x, y - 1);
                Pixel2D right = new Index2D(x + 1, y);
                Pixel2D left = new Index2D(x - 1, y);
                ans += fillHelp(up, color, new_v, cyclic);
                ans += fillHelp(down, color, new_v, cyclic);
                ans += fillHelp(right, color, new_v, cyclic);
                ans += fillHelp(left, color, new_v, cyclic);
            }
        }
        return ans;
    }

    /**
     * Recursive helper function for calculating all distances (BFS logic).
     * @param start the current pixel
     * @param ans the result map where distances are updated.
     * @param cyclic if true, the map boundaries are cyclic.
     * @param distance the number of steps from the start point.
     */
    private void allDistanceHelp(Pixel2D start, Map2D ans, boolean cyclic, int distance){
        start = cyclic(start.getX(), start.getY(), cyclic);
        int x = start.getX();
        int y = start.getY();
        if(x>=0 && x<ans.getWidth() && y>=0 && y<ans.getHeight() && ans.getPixel(start)!=-1){
            if(ans.getPixel(start)==-2 || distance<ans.getPixel(start)){
                ans.setPixel(start, distance);
                Pixel2D up = new Index2D(x, y + 1);
                Pixel2D down = new Index2D(x, y - 1);
                Pixel2D right = new Index2D(x + 1, y);
                Pixel2D left = new Index2D(x - 1, y);
                allDistanceHelp(up, ans, cyclic, distance+1);
                allDistanceHelp(down, ans, cyclic, distance+1);
                allDistanceHelp(right, ans, cyclic, distance+1);
                allDistanceHelp(left, ans, cyclic, distance+1);
            }
        }
    }

    /**
     * Adjusts coordinates for cyclic (toroidal) map behavior.
     * @param x the input x.
     * @param y the input y.
     * @param cyclic if true, the map boundaries are cyclic.
     * @return the corrected coordinate
     */
    private Pixel2D cyclic(int x, int y, boolean cyclic){
        if(cyclic){
            if(y>=getHeight()){
                y = 0;
            }
            if(y<0){
                y = getHeight()-1;
            }
            if(x>=getWidth()){
                x = 0;
            }
            if(x<0){
                x = getWidth()-1;
            }
        }
        return new Index2D(x,y);
    }
}
