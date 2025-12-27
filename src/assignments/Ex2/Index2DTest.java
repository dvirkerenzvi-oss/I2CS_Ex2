package assignments.Ex2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {
    private Index2D p;
    @BeforeEach
    public void setup(){
        p = new Index2D(3, 4);
    }
    @Test
    void testIndex2DAndGetters(){
        assertEquals(3,p.getX());
        assertEquals(4,p.getY());
    }
    @Test
    void testCopyIndex2D(){
        Index2D CopyP = new Index2D(p);
        assertEquals(3,CopyP.getX());
        assertEquals(4,CopyP.getY());
    }
    @Test
    void testDistance2D(){
        Index2D p0 = new Index2D(0,0);
        assertEquals(5,p0.distance2D(p),0.0001);
    }
    @Test
    void testToString(){
        assertEquals("3,4",p.toString());
    }
    @Test
    void testEquals(){
        Index2D sameP = new Index2D(3,4);
        Index2D difP = new Index2D(4,5);
        assertTrue(p.equals(sameP));
        assertFalse(p.equals(difP));
    }
}
