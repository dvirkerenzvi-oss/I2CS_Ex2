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
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
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
	@Override
	public int getWidth() {
        int ans = -1;
        ans = this._map.length;
        return ans;
    }
	@Override
	public int getHeight() {
        int ans = -1;
        ans = this._map[0].length;
        return ans;
    }
	@Override
	public int getPixel(int x, int y) {
        int ans = -1;
        if(x>=0 && x<getWidth() && y>=0 && y<getHeight()) {
            ans = this._map[x][y];
        }
        return ans;
    }
	@Override
	public int getPixel(Pixel2D p) {
        int ans = -1;
        if(isInside(p)==true) {
            ans = getPixel(p.getX(),p.getY());
        }
        return ans;
	}
	@Override
	public void setPixel(int x, int y, int v) {
        if(x>=0 && x<getWidth() && y>=0 && y<getHeight()) {
            this._map[x][y] = v;
        }
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        if(isInside(p)){
            setPixel(p.getX(),p.getY(),v);
        }
	}

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

    @Override
    public void mul(double scalar) {
        for(int i=0;i<getWidth();i++){
            for(int j=0;j<getHeight();j++){
                this._map[i][j] = (int) (scalar*this._map[i][j]);
            }
        }
    }

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
    private double distanceFromCenter(int i, int j, Pixel2D center){
        double ans = -1;
        double x = center.getX()-i;
        double y = center.getY()-j;
        double dist = x*x+y*y;
        ans = Math.sqrt(dist);
        return ans;
    }
    private double f(Pixel2D p1, Pixel2D p2, int x){
        double a = (double) (p2.getY() - p1.getY()) /(p2.getX()-p1.getX());
        return (double) p1.getY() + a*(x-p1.getX());
    }
    private double g(Pixel2D p1, Pixel2D p2, int y){
        double a = (double) (p2.getX() - p1.getX()) /(p2.getY()-p1.getY());
        return (double) p1.getX() + a*(y-p1.getY());
    }
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
