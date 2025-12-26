

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0, _m1, _m3_3;
    @BeforeEach
    public void setup() {
        _m3_3 = new Map(_map_3_3);
        _m0 = new Map(4,4,0);
        _m1 = new Map(4,4,0);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [50][50];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }
    @Test
    void testGetMap(){
        int[][] arr = _m0.getMap();
        assertEquals(0,arr[0][0]);
        arr[1][1] = 1;
        assertEquals(0,_m0.getPixel(0,0));
    }
    @Test
    void testGetWidth(){
        int w = _m0.getWidth();
        assertEquals(4, w);
    }
    @Test
    void testGetHeight(){
        int h = _m0.getHeight();
        assertEquals(4, h);
    }
    @Test
    void testGetPixel(){
        int p = _m0.getPixel(1,2);
        assertEquals(0, p);
    }
    @Test
    void testGetPixel1(){
        Index2D pixel = new Index2D(1,2);
        int p = _m0.getPixel(pixel);
        assertEquals(0, p);
    }
    @Test
    void testSetPixel(){
        _m0.setPixel(1,2, 1);
        assertEquals(1,_m0.getPixel(1,2));
    }
    @Test
    void testSetPixel1(){
        Index2D p = new Index2D(1,2);
        _m0.setPixel(p,1);
        assertEquals(1,_m0.getPixel(p));
    }
    @Test
    void testIsInside(){
        Index2D p1 = new Index2D(1,2);
        Index2D p2 = new Index2D(3,5);
        Index2D p3 = new Index2D(5,3);
        Index2D p4 = new Index2D(-1,-1);
        assertTrue(_m0.isInside(p1));
        assertFalse(_m0.isInside(p2));
        assertFalse(_m0.isInside(p4));
        assertFalse(_m0.isInside(p3));
    }
    @Test
    void testSameDimensions(){
        assertFalse(_m0.sameDimensions(_m3_3));
        assertTrue(_m0.sameDimensions(_m1));
    }
    @Test
    void testAddMap2D(){
        _m3_3.addMap2D(_m3_3);
        assertEquals(2,_m3_3.getPixel(0,1));
    }
    @Test
    void testMul(){
        _m3_3.mul(-1);
        assertEquals(-1,_m3_3.getPixel(0,1));
    }
    @Test
    void testRescale(){
        _m3_3.rescale(2.0,2.0);
        assertEquals(6,_m3_3.getWidth());
        assertEquals(6,_m3_3.getHeight());
    }
    @Test
    void testDrawCircle(){
        Pixel2D center = new Index2D(2,2);
        _m0.drawCircle(center, 1,2);
        assertEquals(2,_m0.getPixel(2,3));
        assertEquals(0,_m0.getPixel(0,0));
    }
    @Test
    void testDrawLine(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(0,2);
        _m0.drawLine(p1,p2,2);
        assertEquals(2,_m0.getPixel(0,1));
        assertEquals(0,_m0.getPixel(0,3));
    }
    @Test
    void testDrawRect(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(2,2);
        _m0.drawRect(p1,p2,2);
        assertEquals(2,_m0.getPixel(1,1));
        assertEquals(0,_m0.getPixel(3,3));
    }
    @Test
    void testFill(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(3,3);
        _m0.drawRect(p1,p2,2);
        Pixel2D p = new Index2D(2,2);
        _m0.setPixel(p,0);
        _m0.fill(p,2,false);
        assertEquals(2,_m0.getPixel(p));
    }
    @Test
    void testShortestPath(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(0,3);
        _m0.setPixel(0,2,-1);
        Pixel2D[] ans = _m0.shortestPath(p1,p2,-1,false);
        assertEquals(6,ans.length);
    }
    @Test
    void testAllDistance(){
        Pixel2D p = new Index2D(0,0);
        Map2D map = _m0.allDistance(p,-1,false);
        assertEquals(6,map.getPixel(3,3));
    }
}