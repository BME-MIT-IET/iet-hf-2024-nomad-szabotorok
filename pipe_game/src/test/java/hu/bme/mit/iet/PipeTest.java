package hu.bme.mit.iet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hu.bme.mit.iet.Pipe.State;



public class PipeTest {

    private Pipe pipe;
    private Player mockPlayer;
    private SystemPart mockSystemPart;
    private SystemPart mockneighbour;

    @BeforeEach
    void initiate(){
         pipe=new Pipe();
         mockPlayer=mock(Player.class);
         mockSystemPart=mock(SystemPart.class);
         mockneighbour=mock(SystemPart.class);
         pipe.neighbours.add(mockneighbour);
    }

    @Test
    void testAcceptPlayer() {

        when(mockneighbour.AcceptPlayer(mockPlayer)).thenReturn(true);//a szomszed mezo fogadja a jatekost
        

        pipe.setCarried(true);//a csovet viszik
        assertEquals(false,pipe.AcceptPlayer(mockPlayer));//ekkor nem fogadhat jatekost

        pipe.setCarried(false);//ha nem viszik
        pipe.setSlippery();//de csuszos
        
        assertEquals(true, pipe.AcceptPlayer(mockPlayer));//akkor engedi, de tovabb is kuldi a szomszednak
        verify(mockneighbour,times(1)).AcceptPlayer(mockPlayer);//azt ellenorzi, hogy valoban tovabbcsuszott-e
        

        //miutan vki elcsuszik rajta magatol normal allapotba kerul
        assertEquals(true, pipe.AcceptPlayer(mockPlayer));//ekkor el kell fogadnia
        assertEquals(false, pipe.AcceptPlayer(mockPlayer));//masodszor viszont nem, hiszen csak 1 jatekos lehet rajta
        
    }

    @Test
    void testBreakPipe() {
        assertEquals(false, pipe.isBroken());
        pipe.timeTillNewBreak=10;
        pipe.BreakPipe();
        assertEquals(false, pipe.isBroken());//amikor meg van a torhetetlensegi idobol, akkor nem rontja el a fgv 
        pipe.timeTillNewBreak=0;
        assertEquals(true, pipe.isBroken());
    }

    @Test
    void testCarryPipeEnd() {
        assertEquals(false, pipe.CarryPipeEnd(mockSystemPart)); //pipe-rol pipe-ot nem lehet felvenni, ezert mindig false
    }

    @Test
    void testLayPipe() {
        assertEquals(false, pipe.LayPipe(mockSystemPart)); //pipe-ra pipot nem lehet tenni, ezert mindig false
    }

    @Test
    void testLayPump() {
        pipe.LayPump(mockSystemPart);
        verify(mockSystemPart,times(2)).AddNeighbour(any(SystemPart.class));//a pumpanak 2 szomszedot kell beallitani: az uj es a regi csovet

    }

    @Test
    void testRepair() {
        pipe.Repair();//megjavitja
        assertEquals(false,pipe.isBroken());//miutan semmikepp nem lehet elromolva a cso
        assertEquals(2, pipe.timeTillNewBreak);//es torhetetlen kell, hogy legyen 2 ideig
    }

    @Test
    void testDecTimeTillNewBreak() {
        int startTime=10;
        pipe.timeTillNewBreak=startTime;
        pipe.decTimeTillNewBreak();//csokkenteni kell 1-el az idot, mivel az 0 felett van
        assertEquals(startTime-1, pipe.timeTillNewBreak);
        pipe.timeTillNewBreak=0;
        pipe.decTimeTillNewBreak();//nem csokkenti az idot, mert nem nagyobb 0-nal
        assertEquals(0, pipe.timeTillNewBreak);
    }

    @Test
    void testGetCapacity() {
        
    }

    @Test
    void testGetWater() {
    }

    @Test
    void testIsBroken() {

    }

    @Test
    void testIsGlued() {

    }

    @Test
    void testIsSlippery() {

    }

    @Test
    void testSetCarried() {

    }

    @Test
    void testSetGlued() {

    }

    @Test
    void testSetNormal() {

    }

    @Test
    void testSetSlippery() {

    }

    @Test
    void testSetWater() {

    }
}
