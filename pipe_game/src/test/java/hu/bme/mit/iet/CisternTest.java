package hu.bme.mit.iet;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;

class CisternTest {
	Cistern c; //Ciszterna letrehozasa
	
	@BeforeEach
	void initiate() {
		c=new Cistern();
	}
	@Test
	void testCreatePump() {
		c.CreatePump();
		assertEquals(true, c.hasPump());
	}
	
	@Test
	void testCarryPump() {
		c.CreatePump();
		assertInstanceOf(Pump.class,c.CarryPump());
	}
	@Test
	void testPullWaterEmpty() {
		//üressel
		assertEquals(0,c.PullWater());
	}
	void testPullWater() {
		
		//TODO osszetett, mert eloszor csinalni kell meg csovet, amelybe vizet kell tenni
		assertEquals(0,c.PullWater());
	}

	@Test
	void testCistern() {
		Cistern c2=new Cistern();
		Cistern c3=new Cistern();
		assertNotEquals(c2.id,c3.id);
		assertInstanceOf(Cistern.class, c2);
		assertInstanceOf(Cistern.class, c3);
	}

	@Test
	void testCreatePipe() {
		assertEquals(false,c.hasP());//mielott a create pipe meg van hivva ne legyen cso
		c.CreatePipe();
		assertEquals(true,c.hasP());
	}


	@Test
	void testGenerate() {
		//TODO meg nem talaltam ki, hogy mit kellene ezen pontosan tesztelni
		fail("Not yet implemented");
	}

	@Test
	void testHasPump() {
		assertEquals(false,c.hasPump());
		c.CreatePump();
		assertEquals(true,c.hasPump());
		c.CarryPump();
		assertEquals(false,c.hasPump());
	}

	@Test
	void testGetPipe() {
		assertEquals(null,c.getPipe());
		c.CreatePipe();
		assertInstanceOf(Pipe.class,c.getPipe() );
		assertNotEquals(null,c.getPipe());
	}

	@Test
	void testHasP() {
		assertEquals(false,c.hasP());
		c.CreatePump();
		assertEquals(true,c.hasP());
	}

	@Test
	void testGetpCount() {
		assertEquals(0,c.getpCount());
		c.CreatePipe();
		c.CreatePipe();
		assertEquals(2,c.getpCount());
	}

}
