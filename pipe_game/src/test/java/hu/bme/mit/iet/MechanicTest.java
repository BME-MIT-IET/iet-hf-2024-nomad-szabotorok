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

import static org.mockito.AdditionalMatchers.and;

import java.util.List;
import java.util.Random;

public class MechanicTest {

    private Mechanic mechanic;
    private Cistern mockCistern;
    private Pipe mockPipe;
    private Pump mockPump;
    private SystemPart mockSystemPart;
    @BeforeEach
	void initiate() {
        mechanic=new Mechanic();
        mechanic.position=mockSystemPart;
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
        
    }

    @Test
    void testLayPump() {
        
    }

    @Test
    void testRepair() {
        
    }

    @Test
    void testGetPart() {
        
    }

    @Test
    void testSetPump() {
        
    }

   
    
}
