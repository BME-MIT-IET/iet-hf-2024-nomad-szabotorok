package hu.bme.mit.iet.pipe_game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PumpTest {

    private Pump pump;
    private SystemPart mockneighbour;
    private Pipe mockPipe1;
    private Pipe mockPipe2;

    @BeforeEach
    void initiate(){
         pump=new Pump();
         mockneighbour=mock(SystemPart.class);
         mockPipe1=mock(Pipe.class);
         mockPipe2=mock(Pipe.class);

         pump.neighbours.add(mockneighbour);
    }

    @Test
    void testAcceptNewFlow() {
        //megjegyzes: accept new flow hibat dob, amennyiben a from erteke null
        assertEquals(false, pump.acceptNewFlow(mockPipe1, mockPipe1));//nem lehet ugyabbol ugyanabba vezetni vizet
        assertEquals(true,pump.acceptNewFlow(mockPipe2, mockPipe1));//2 kulonbozobe viszont igen
    }

    @Test
    void testBreakPump() {//egyszeru setter
        
    }
    
    @Test
    void testPullWater() {//megjegyzes: PullWater mukodese elter a kommentbe irttol: MINDIG 0-val ter vissza!
        pump.breakPump();//pumpa eltorve
        assertEquals(0,pump.pullWater());//ekkor a beszivott 0
        pump.repair();
        
        /* megjegyzes: acceptNewFLow hibat dob, ha a from null, mivel meghivja a nullra az equals fgv-t
         * 
            pump.AcceptNewFlow(null, mockPipe1);//from null
            assertEquals(0,pump.PullWater());
         */

        pump.acceptNewFlow(mockPipe2, mockPipe1);
        when(mockPipe2.isBroken()).thenReturn(false);//from is not broken
        pump.pullWater();
        verify(mockPipe2,times(1)).getWater();//ha minden rendben el kell kernie a vizt a csobol


        
    }

    @Test
    void testPushWater() {
        pump.breakPump();//pumpa eltorve
        assertEquals(0,pump.pushWater());//ekkor a lyukasson kifolyt 0
        pump.repair();
        
        /* megjegyzes: acceptNewFLow hibat dob, ha a from null, mivel meghivja a nullra az equals fgv-t
         * 
            pump.AcceptNewFlow(mockPipe2, null);//to null
            assertEquals(0,pump.PullWater());
         */

        pump.acceptNewFlow(mockPipe2, mockPipe1);
        when(mockPipe1.isBroken()).thenReturn(false);//to is not broken
        pump.pushWater();
        verify(mockPipe1,times(1)).setWater(anyInt());//ha minden rendben el kell kernie a vizt a csobol
    }


}
