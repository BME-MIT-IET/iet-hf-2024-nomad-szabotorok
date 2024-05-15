import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CisternTest {
	Cistern c;
	
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
		
		assertEquals(0,c.PullWater());
	}

	@Test
	void testCistern() {
		fail("Not yet implemented");
	}

	@Test
	void testCreatePipe() {
		fail("Not yet implemented");
	}


	@Test
	void testGenerate() {
		fail("Not yet implemented");
	}

	@Test
	void testHasPump() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPipe() {
		fail("Not yet implemented");
	}

	@Test
	void testHasP() {
		fail("Not yet implemented");
	}

	@Test
	void testGetpCount() {
		fail("Not yet implemented");
	}

}
