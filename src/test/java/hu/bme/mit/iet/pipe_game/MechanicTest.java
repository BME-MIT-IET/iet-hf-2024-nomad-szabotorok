package hu.bme.mit.iet.pipe_game;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MechanicTest {

    private Mechanic mechanic;
    private Cistern mockCistern;
    private Pipe mockPipe;
    private Pump mockPump;
    private SystemPart mockSystemPart;
    @BeforeEach
	void initiate() {
        mechanic=new Mechanic();
		mockCistern=mock(Cistern.class);
        mockPipe=mock(Pipe.class);
        mockPump=mock(Pump.class);
        mockSystemPart=mock(SystemPart.class);
        mechanic.position=mockSystemPart;
	}

    @Test
    void testCarryPipeEndSuccessfull() {
        when(mockSystemPart.carryPipeEnd(mockPipe)).thenReturn(true);
        mechanic.carryPipeEnd(mockPipe);
        assertNotEquals("nothing", mechanic.getPart());
    }

    @Test
    void testCarryPipeEndUnsuccessfull() {
        mechanic.position=mockSystemPart;
        when(mockSystemPart.carryPipeEnd(mockPipe)).thenReturn(false);
        mechanic.carryPipeEnd(mockPipe);
        assertEquals("nothing", mechanic.getPart());
    }

    @Test
    void testCarryPump() {
        when(mockSystemPart.carryPump()).thenReturn(mockPump);
        mockPump.id="10";
        mechanic.carryPump();
        assertEquals("10",mechanic.getPart());
    }

    @Test
    void testLayPipe() {
        assertEquals(false,mechanic.layPipe());//ha nincs a kezeben semmi 
        when(mockSystemPart.carryPipeEnd(any(SystemPart.class))).thenReturn(true);//ha van a kezeben pipe
        when(mockSystemPart.layPipe(any(SystemPart.class))).thenReturn(true);//ha a hely ahol all engedi a lerekast
        mechanic.carryPipeEnd(mockPipe);
        assertEquals(true,mechanic.layPipe());
    }

    @Test
    void testLayPump() {
        mechanic.position=mockCistern;
        assertEquals(null,mechanic.layPump());//ha nincs a kezeben semmi 
        when(mockCistern.carryPump()).thenReturn(mockPump);//ha van a kezeben pump
        when(mockCistern.layPipe(any(SystemPart.class))).thenReturn(true);//ha a hely ahol all engedi a lerekast
        mechanic.carryPump();
        assertEquals(mockPump,mechanic.layPump());
    }

    @Test
    void testRepair() {
        mechanic.position=mockPump;
        mechanic.repair();
        verify(mockPump,times(1)).repair();
    }

    @Test
    void testGetPart() {
        assertEquals("nothing", mechanic.getPart());//nincs semmi a kezeben
        String stringID="100";
        mockPipe.id=stringID;
        mechanic.position=mockCistern;//cisternan all
        when(mockCistern.carryPipeEnd(mockPipe)).thenReturn(true);//cistern engedi, hogy felvegyek
        mechanic.carryPipeEnd(mockPipe);//igy ez a pipe lesz a carried part
        assertEquals(stringID, mechanic.getPart());//valoban a 100-as id-ú pipe legyen a mechanicnal

    }

    @Test
    void testSetPump() {
        assertEquals("nothing", mechanic.getPart());//nincs semmi a kezeben
        String stringID="100";
        mockPump.id=stringID;
        mechanic.setPump(mockPump);//felveszi a 100-as id-ú
        assertEquals(stringID, mechanic.getPart());
    }

   
    
}
