package hu.bme.mit.iet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PumpTest {

    private Pump pump;
    private Player mockPlayer;
    private SystemPart mockSystemPart;
    private SystemPart mockneighbour;
    private Pipe mockPipe1;
    private Pipe mockPipe2;

    @BeforeEach
    void initiate(){
         pump=new Pump();
         mockPlayer=mock(Player.class);
         mockSystemPart=mock(SystemPart.class);
         mockneighbour=mock(SystemPart.class);
         mockPipe1=mock(Pipe.class);
         mockPipe2=mock(Pipe.class);

         pump.neighbours.add(mockneighbour);
    }

    @Test
    void testAcceptNewFlow() {
        assertEquals(false, pump.AcceptNewFlow(mockPipe1, mockPipe1));//nem lehet ugyabbol ugyanabba vezetni vizet
        assertEquals(true,pump.AcceptNewFlow(mockPipe2, mockPipe1));//2 kulonbozobe viszont igen
    }

    @Test
    void testBreakPump() {//egyszeru setter
        
    }
    
    @Test
    void testPullWater() {
        pump.BreakPump();//pumpa eltorve
        assertEquals(0,pump.PullWater());//ekkor a behuzott vizmennyiseg 0
    
        
    }

    @Test
    void testPushWater() {

    }

    @Test
    void testRepair() {

    }

    @Test
    void testGetFrom() {

    }

    @Test
    void testGetTo() {

    }

    @Test
    void testIsBroken() {

    }
}
