package hu.bme.mit.iet.pipe_game;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class SaboteurTest {
    @Test
    void testMakePipeSlippery() {//meg kell hivnia a poziciojanak a setslippery fgv-t egyszer
        Saboteur s=new Saboteur();
        SystemPart mockPosition=mock(SystemPart.class);
        s.setPos(mockPosition);
        s.makePipeSlippery();
        verify(mockPosition,times(1)).setSlippery();
    }
}
