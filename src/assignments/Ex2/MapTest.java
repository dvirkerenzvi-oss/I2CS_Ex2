package assignments.Ex2;

import org.junit.jupiter.api.Assertions;
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

    /**
     * Sets up the test environment before each test.
     * Initializes standard maps (empty 4x4 and a specific 3x3 pattern) for reuse.
     */
    @BeforeEach
    public void setup() {
        _m3_3 = new Map(_map_3_3);
        _m0 = new Map(4,4,0);
        _m1 = new Map(4,4,0);
    }

    /**
     * Tests initialization with a large array to ensure dimensions are set correctly.
     * Also performs a basic check of the fill function.
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [50][50];
        _m1.init(bigarr);
        Assertions.assertEquals(bigarr.length, _m1.getWidth());
        Assertions.assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }
    /**
     * Verifies initialization from a specific 2D array and checks equality between two identical maps.
     */
    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    /**
     * Tests the 'equals' method to ensure it correctly identifies identical and different maps.
     */
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }
    /**
     * Verifies that getMap() returns a deep copy of the array.
     * Changing the returned array should NOT affect the original map.
     */
    @Test
    void testGetMap(){
        int[][] arr = _m0.getMap();
        assertEquals(0,arr[0][0]);
        arr[1][1] = 1;
        Assertions.assertEquals(0,_m0.getPixel(0,0));
    }
    /**
     * Verifies the getWidth() method returns the correct number of columns.
     */
    @Test
    void testGetWidth(){
        int w = _m0.getWidth();
        assertEquals(4, w);
    }
    /**
     * Verifies the getHeight() method returns the correct number of rows.
     */
    @Test
    void testGetHeight(){
        int h = _m0.getHeight();
        assertEquals(4, h);
    }
    /**
     * Tests retrieving a pixel value using (x, y) coordinates.
     */
    @Test
    void testGetPixel(){
        int p = _m0.getPixel(1,2);
        assertEquals(0, p);
    }
    /**
     * Tests retrieving a pixel value using a Pixel2D object.
     */
    @Test
    void testGetPixel1(){
        Index2D pixel = new Index2D(1,2);
        int p = _m0.getPixel(pixel);
        assertEquals(0, p);
    }
    /**
     * Tests setting a pixel value using (x, y) coordinates and verifying the update.
     */
    @Test
    void testSetPixel(){
        _m0.setPixel(1,2, 1);
        Assertions.assertEquals(1,_m0.getPixel(1,2));
    }
    /**
     * Tests setting a pixel value using a Pixel2D object.
     */
    @Test
    void testSetPixel1(){
        Index2D p = new Index2D(1,2);
        _m0.setPixel(p,1);
        Assertions.assertEquals(1,_m0.getPixel(p));
    }
    /**
     * Tests the isInside() method with various points (inside, outside, negative coordinates).
     */
    @Test
    void testIsInside(){
        Index2D p1 = new Index2D(1,2);
        Index2D p2 = new Index2D(3,5);
        Index2D p3 = new Index2D(5,3);
        Index2D p4 = new Index2D(-1,-1);
        Assertions.assertTrue(_m0.isInside(p1));
        Assertions.assertFalse(_m0.isInside(p2));
        Assertions.assertFalse(_m0.isInside(p4));
        Assertions.assertFalse(_m0.isInside(p3));
    }
    /**
     * Verifies the logic for comparing dimensions of two different maps.
     */
    @Test
    void testSameDimensions(){
        Assertions.assertFalse(_m0.sameDimensions(_m3_3));
        Assertions.assertTrue(_m0.sameDimensions(_m1));
    }
    /**
     * Tests adding values from another map to the current map (element-wise addition).
     */
    @Test
    void testAddMap2D(){
        _m3_3.addMap2D(_m3_3);
        Assertions.assertEquals(2,_m3_3.getPixel(0,1));
    }
    /**
     * Tests scalar multiplication: multiplying every pixel in the map by a number.
     */
    @Test
    void testMul(){
        _m3_3.mul(-1);
        Assertions.assertEquals(-1,_m3_3.getPixel(0,1));
    }
    /**
     * Tests resizing (scaling) the map dimensions and verifying the new size.
     */
    @Test
    void testRescale(){
        _m3_3.rescale(2.0,2.0);
        Assertions.assertEquals(6,_m3_3.getWidth());
        Assertions.assertEquals(6,_m3_3.getHeight());
    }
    /**
     * Tests the drawCircle method by verifying pixels within the radius are colored.
     */
    @Test
    void testDrawCircle(){
        Pixel2D center = new Index2D(2,2);
        _m0.drawCircle(center, 1,2);
        Assertions.assertEquals(2,_m0.getPixel(2,3));
        Assertions.assertEquals(0,_m0.getPixel(0,0));
    }
    /**
     * Tests drawing a line segment between two points.
     */
    @Test
    void testDrawLine(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(0,2);
        _m0.drawLine(p1,p2,2);
        Assertions.assertEquals(2,_m0.getPixel(0,1));
        Assertions.assertEquals(0,_m0.getPixel(0,3));
    }
    /**
     * Tests drawing a filled rectangle defined by two corners.
     */
    @Test
    void testDrawRect(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(2,2);
        _m0.drawRect(p1,p2,2);
        Assertions.assertEquals(2,_m0.getPixel(1,1));
        Assertions.assertEquals(0,_m0.getPixel(3,3));
    }
    /**
     * Tests the Flood Fill algorithm to ensure it correctly fills a bounded area.
     */
    @Test
    void testFill(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(3,3);
        _m0.drawRect(p1,p2,2);
        Pixel2D p = new Index2D(2,2);
        _m0.setPixel(p,0);
        _m0.fill(p,2,false);
        Assertions.assertEquals(2,_m0.getPixel(p));
    }
    /**
     * Tests the Shortest Path algorithm (BFS).
     * Verifies that a path exists and has the expected length around obstacles.
     */
    @Test
    void testShortestPath(){
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 = new Index2D(0,3);
        _m0.setPixel(0,2,-1);
        Pixel2D[] ans = _m0.shortestPath(p1,p2,-1,false);
        assertEquals(6,ans.length);
    }
    /**
     * Tests the generation of an 'all distance' map, verifying the distance value at a specific point.
     */
    @Test
    void testAllDistance(){
        Pixel2D p = new Index2D(0,0);
        Map2D map = _m0.allDistance(p,-1,false);
        Assertions.assertEquals(6,map.getPixel(3,3));
    }
}