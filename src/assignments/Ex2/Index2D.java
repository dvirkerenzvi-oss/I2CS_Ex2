package assignments.Ex2;

public class Index2D implements Pixel2D {

    private int _x;
    private int _y;

    /**
     * Constructs a new 2D point with the specified x and y coordinates.
     * @param w
     * @param h
     */
    public Index2D(int w, int h) {
        _x=w;
        _y=h;
    }

    /**
     * Copy constructor: Creates a new point with the same coordinates as the given 'other' point.
     * @param other
     */
    public Index2D(Pixel2D other) {
         this._x = other.getX();
         this._y = other.getY();
    }

    /**
     * Returns the X coordinate of this point.
     * @return
     */
    @Override
    public int getX() {
        return _x;
    }

    /**
     * Returns the Y coordinate of this point.
     * @return
     */
    @Override
    public int getY() {
        return _y;
    }

    /**
     * Computes the 2D Euclidean distance between this point and another point (p2).
     * Throws a RuntimeException if p2 is null.
     * @param p2
     * @return
     */
    @Override
    public double distance2D(Pixel2D p2) {
        if(p2==null){
            throw new RuntimeException("Error: p2 cannot be null");
        }
        double dx = this.getX() - p2.getX();
        double dy = this.getY() - p2.getY();
        double t = (dx*dx+dy*dy);
        return Math.sqrt(t);
    }

    /**
     * Returns a string representation of the point in the format "x,y".
     * @return
     */
    @Override
    public String toString() {
        String ans = null;
        ans = _x+","+_y;
        return ans;
    }

    /**
     * Checks if this point is equal to another object.
     *      * Returns true only if the other object is an instance of Index2D with the same x and y values.
     * @param p the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object p) {
        boolean ans = true;
        if (p==null || !(p instanceof Index2D)){
            ans = false;
        }
        else{
            Index2D p2 = (Index2D)p;
            if(p2.getX()!=this._x && p2.getY()!=this._y){
                ans = false;
            }
        }
        return ans;
    }
}
