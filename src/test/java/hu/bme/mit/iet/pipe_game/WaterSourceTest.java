package hu.bme.mit.iet.pipe_game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class WaterSourceTest {

    @Test
    void testPushWater() {
        WaterSource ws=new WaterSource();
        Pipe mockneighbour=mock(Pipe.class);
        ws.neighbours.add(mockneighbour);

        when(mockneighbour.getCapacity()).thenReturn(10); //10es kapacitassal ter vissza a mockolt szomszed

        when(mockneighbour.isBroken()).thenReturn(true);//ha torott
        assertEquals(10, ws.pushWater());//10nek kell mellefolyni, mert annyi a kapacitasa a szomszednak
        
        when(mockneighbour.isBroken()).thenReturn(false);//ha nem torott
        ws.pushWater();
        verify(mockneighbour,times(1)).setWater(10);//10et kell beletolnia a pipeba, egyszer

    }
}
