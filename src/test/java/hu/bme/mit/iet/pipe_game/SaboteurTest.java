package hu.bme.mit.iet;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class SaboteurTest {
    @Test
    void testMakePipeSlippery() {//meg kell hivnia a poziciojanak a setslippery fgv-t egyszer
        Saboteur s=new Saboteur();
        SystemPart mockPosition=mock(SystemPart.class);
        s.setPos(mockPosition);
        s.MakePipeSlippery();
        verify(mockPosition,times(1)).setSlippery();
    }
}
