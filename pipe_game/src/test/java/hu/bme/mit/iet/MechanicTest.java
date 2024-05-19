package hu.bme.mit.iet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.mockito.internal.invocation.InvocationComparator;
import org.mockito.internal.matchers.Any;

import net.bytebuddy.utility.RandomString;

import static org.mockito.AdditionalMatchers.and;

import java.util.List;

public class MechanicTest {

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
        when(mockSystemPart.CarryPipeEnd(mockPipe)).thenReturn(true);
        mechanic.CarryPipeEnd(mockPipe);
        assertNotEquals("nothing", mechanic.getPart());
    }

    @Test
    void testCarryPipeEndUnsuccessfull() {
        mechanic.position=mockSystemPart;
        when(mockSystemPart.CarryPipeEnd(mockPipe)).thenReturn(false);
        mechanic.CarryPipeEnd(mockPipe);
        assertEquals("nothing", mechanic.getPart());
    }

    @Test
    void testCarryPump() {
        when(mockSystemPart.CarryPump()).thenReturn(mockPump);
        mockPump.id="10";
        mechanic.CarryPump();
        assertEquals("10",mechanic.getPart());
    }

    @Test
    void testLayPipe() {
        assertEquals(false,mechanic.LayPipe());//ha nincs a kezeben semmi 
        when(mockSystemPart.CarryPipeEnd(any(SystemPart.class))).thenReturn(true);//ha van a kezeben pipe
        when(mockSystemPart.LayPipe(any(SystemPart.class))).thenReturn(true);//ha a hely ahol all engedi a lerekast
        mechanic.CarryPipeEnd(mockPipe);
        assertEquals(true,mechanic.LayPipe());
    }

    @Test
    void testLayPump() {
        mechanic.position=mockCistern;
        assertEquals(null,mechanic.LayPump());//ha nincs a kezeben semmi 
        when(mockCistern.CarryPump()).thenReturn(mockPump);//ha van a kezeben pump
        when(mockCistern.LayPipe(any(SystemPart.class))).thenReturn(true);//ha a hely ahol all engedi a lerekast
        mechanic.CarryPump();
        assertEquals(mockPump,mechanic.LayPump());
    }

    @Test
    void testRepair() {
        mechanic.position=mockPump;
        mechanic.Repair();
        verify(mockPump,times(1)).Repair();
    }

    @Test
    void testGetPart() {
        assertEquals("nothing", mechanic.getPart());//nincs semmi a kezeben
        String stringID="100";
        mockPipe.id=stringID;
        mechanic.position=mockCistern;//cisternan all
        when(mockCistern.CarryPipeEnd(mockPipe)).thenReturn(true);//cistern engedi, hogy felvegyek
        mechanic.CarryPipeEnd(mockPipe);//igy ez a pipe lesz a carried part
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
