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

class CisternTest {
	private Cistern cistern; //Ciszterna letrehozasa
	
	@BeforeEach
	void initiate() {
		cistern=new Cistern();
	}
	@Test
	void testCreatePump() {
		cistern.CreatePump();
		assertEquals(true, cistern.hasPump());
	}
	
	@Test
	void testCarryPump() {
		cistern.CreatePump();
		assertInstanceOf(Pump.class,cistern.CarryPump());
	}
	@Test
	void testPullWaterEmpty() {
		//üressel
		assertEquals(0,cistern.PullWater());
	}
	void testPullWater() {
		//TODO osszetett, mert eloszor csinalni kell meg csovet, amelybe vizet kell tenni
		assertEquals(0,cistern.PullWater());
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
		cistern.CreatePipe();
		assertEquals(true,cistern.hasP());
	}


	@Test
	void testGenerate() {
		//TODO ezt meg nem tudom hogy kellene tesztelni
	}

	@Test
	void testHasPump() {
		assertEquals(false,cistern.hasPump());
		cistern.CreatePump();
		assertEquals(true,cistern.hasPump());
		cistern.CarryPump();
		assertEquals(false,cistern.hasPump());
	}

	@Test
	void testGetPipe() {
		assertEquals(null,cistern.getPipe());
		cistern.CreatePipe();
		assertInstanceOf(Pipe.class,cistern.getPipe() );
		assertNotEquals(null,cistern.getPipe());
	}

	@Test
	void testHasP() {
		assertEquals(false,cistern.hasP());
		cistern.CreatePipe();
		assertEquals(true,cistern.hasP());
	}

	@Test
	void testGetpCount() {
		assertEquals(0,cistern.getpCount());
		cistern.CreatePipe();
		cistern.CreatePipe();
		assertEquals(2,cistern.getpCount());
	}

}
