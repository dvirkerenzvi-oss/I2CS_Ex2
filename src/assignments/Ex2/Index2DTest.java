package assignments.Ex2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {
    private Index2D p;

    /**
     * Initializes a standard Index2D point (3, 4) before each test execution.
     */
    @BeforeEach
    public void setup(){
        p = new Index2D(3, 4);
    }
    /**
     * Verifies that the constructor correctly sets the X and Y coordinates and the getters retrieve them.
     */
    @Test
    void testIndex2DAndGetters(){
        assertEquals(3,p.getX());
        assertEquals(4,p.getY());
    }

    /**
     * Tests the copy constructor to ensure it creates a new instance with the same coordinates as the original.
     */
    @Test
    void testCopyIndex2D(){
        Index2D CopyP = new Index2D(p);
        assertEquals(3,CopyP.getX());
        assertEquals(4,CopyP.getY());
    }

    /**
     * Tests the 2D Euclidean distance calculation between two points (specifically checking the 3-4-5 triangle).
     */
    @Test
    void testDistance2D(){
        Index2D p0 = new Index2D(0,0);
        assertEquals(5,p0.distance2D(p),0.0001);
    }

    /**
     * Verifies the string representation of the point follows the expected format "x,y".
     */
    @Test
    void testToString(){
        assertEquals("3,4",p.toString());
    }

    /**
     * Tests the equality logic: returns true for points with identical coordinates and false otherwise.
     */
    @Test
    void testEquals(){
        Index2D sameP = new Index2D(3,4);
        Index2D difP = new Index2D(4,5);
        assertTrue(p.equals(sameP));
        assertFalse(p.equals(difP));
    }
}
