package fr.lernejo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    @Test
    void testFactorielle(){
        Sample test = new Sample();
        int y = -1;
        int expected = 1;
        int res = test.fact(0);
        int res2 = test.fact(4);
        assertEquals(expected,res);
        assertEquals(24,res2);
        Assertions.assertThrows(IllegalArgumentException.class,() -> test.fact(y));
    }

    @Test
    void testAddition(){
        Sample add = new Sample();
        int x = 1;
        int y = 2;
        int z = 3;
        int res = add.op(Sample.Operation.ADD, x,y);
        assertEquals(3,res);

        int res2 = add.op(Sample.Operation.ADD,y,z);
        assertEquals(5,res2);
    }

    @Test
    void testMultiplication(){
        Sample mult = new Sample();
        int x = 2;
        int y = 3;
        int z = 4;
        int res = mult.op(Sample.Operation.MULT,x,y);
        assertEquals(6,res);

        int res2 = mult.op(Sample.Operation.MULT,y,z);
        assertEquals(12,res2);
    }
}
