package hu.bme.mit.iet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

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
        //megjegyzes: accept new flow hibat dob, amennyiben a from erteke null
        assertEquals(false, pump.AcceptNewFlow(mockPipe1, mockPipe1));//nem lehet ugyabbol ugyanabba vezetni vizet
        assertEquals(true,pump.AcceptNewFlow(mockPipe2, mockPipe1));//2 kulonbozobe viszont igen
    }

    @Test
    void testBreakPump() {//egyszeru setter
        
    }
    
    @Test
    void testPullWater() {//megjegyzes: PullWater mukodese elter a kommentbe irttol: MINDIG 0-val ter vissza!
        pump.BreakPump();//pumpa eltorve
        assertEquals(0,pump.PullWater());//ekkor a beszivott 0
        pump.Repair();
        
        /* megjegyzes: acceptNewFLow hibat dob, ha a from null, mivel meghivja a nullra az equals fgv-t
         * 
            pump.AcceptNewFlow(null, mockPipe1);//from null
            assertEquals(0,pump.PullWater());
         */

        pump.AcceptNewFlow(mockPipe2, mockPipe1);
        when(mockPipe2.isBroken()).thenReturn(false);//from is not broken
        pump.PullWater();
        verify(mockPipe2,times(1)).getWater();//ha minden rendben el kell kernie a vizt a csobol


        
    }

    @Test
    void testPushWater() {
        pump.BreakPump();//pumpa eltorve
        assertEquals(0,pump.PushWater());//ekkor a lyukasson kifolyt 0
        pump.Repair();
        
        /* megjegyzes: acceptNewFLow hibat dob, ha a from null, mivel meghivja a nullra az equals fgv-t
         * 
            pump.AcceptNewFlow(mockPipe2, null);//to null
            assertEquals(0,pump.PullWater());
         */

        pump.AcceptNewFlow(mockPipe2, mockPipe1);
        when(mockPipe1.isBroken()).thenReturn(false);//to is not broken
        pump.PushWater();
        verify(mockPipe1,times(1)).setWater(anyInt());//ha minden rendben el kell kernie a vizt a csobol
    }

    @Test
    void testRepair() {//egyszeru setter

    }

    @Test
    void testGetFrom() {//egyszeru getter

    }

    @Test
    void testGetTo() {//egyszeru getter
    
    }

    @Test
    void testIsBroken() {//egyszeru getter

    }
}
