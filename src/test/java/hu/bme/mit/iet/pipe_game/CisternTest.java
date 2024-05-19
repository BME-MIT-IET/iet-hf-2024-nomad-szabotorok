package hu.bme.mit.iet.pipe_game;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CisternTest {
	private Cistern cistern; //Ciszterna letrehozasa
	
	@BeforeEach
	void initiate() {
		cistern=new Cistern();
	}
	@Test
	void testCreatePump() {
		cistern.createPump();
		assertEquals(true, cistern.hasPump());
	}
	
	@Test
	void testCarryPump() {
		cistern.createPump();
		assertInstanceOf(Pump.class,cistern.carryPump());
	}
	@Test
	void testPullWaterEmpty() {
		//üressel
		assertEquals(0,cistern.pullWater());
	}
	void testPullWater() {
		//TODO osszetett, mert eloszor csinalni kell meg csovet, amelybe vizet kell tenni
		assertEquals(0,cistern.pullWater());
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
		assertEquals(false,cistern.hasP());//mielott a create pipe meg van hivva ne legyen cso
		cistern.createPipe();
		assertEquals(true,cistern.hasP());
	}


	@Test
	void testGenerate() {
		//TODO ezt meg nem tudom hogy kellene tesztelni
	}

	@Test
	void testHasPump() {
		assertEquals(false,cistern.hasPump());
		cistern.createPump();
		assertEquals(true,cistern.hasPump());
		cistern.carryPump();
		assertEquals(false,cistern.hasPump());
	}

	@Test
	void testGetPipe() {
		assertEquals(null,cistern.getPipe());
		cistern.createPipe();
		assertInstanceOf(Pipe.class,cistern.getPipe() );
		assertNotEquals(null,cistern.getPipe());
	}

	@Test
	void testHasP() {
		assertEquals(false,cistern.hasP());
		cistern.createPipe();
		assertEquals(true,cistern.hasP());
	}

	@Test
	void testGetpCount() {
		assertEquals(0,cistern.getpCount());
		cistern.createPipe();
		cistern.createPipe();
		assertEquals(2,cistern.getpCount());
	}

}
