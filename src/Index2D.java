

public class Index2D implements Pixel2D {

    private int _x;
    private int _y;

    public Index2D(int w, int h) {
        _x=w;
        _y=h;
    }
    public Index2D(Pixel2D other) {
         this._x = other.getX();
         this._y = other.getY();
    }
    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        double dx = this.getX() - p2.getX();
        double dy = this.getY() - p2.getY();
        double t = (dx*dx+dy*dy);
        return Math.sqrt(t);
    }

    @Override
    public String toString() {
        String ans = null;
        ans = _x+","+_y;
        return ans;
    }

    @Override
    public boolean equals(Object p) {
        boolean ans = true;
        if (p==null || !(p instanceof Index2D)){
            ans = false;
        }
        else{
            Index2D p2 = (Index2D)p;
            if(p2.getX()==this._x && p2.getY()==this._y){
                ans = true;
            }
        }
        return ans;
    }
}
