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

class CisternTest {
	private Cistern mockCistern; //Ciszterna letrehozasa
	
	@BeforeEach
	void initiate() {
		mockCistern=mock(Cistern.class);
	}
	@Test
	void testCreatePump() {
		mockCistern.CreatePump();
		assertEquals(true, mockCistern.hasPump());
	}
	
	@Test
	void testCarryPump() {
		mockCistern.CreatePump();
		assertInstanceOf(Pump.class,mockCistern.CarryPump());
	}
	@Test
	void testPullWaterEmpty() {
		//üressel
		assertEquals(0,mockCistern.PullWater());
	}
	void testPullWater() {
		//TODO osszetett, mert eloszor csinalni kell meg csovet, amelybe vizet kell tenni
		assertEquals(0,mockCistern.PullWater());
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
		assertEquals(false,mockCistern.hasP());//mielott a create pipe meg van hivva ne legyen cso
		mockCistern.CreatePipe();
		assertEquals(true,mockCistern.hasP());
	}


	@Test
	void testGenerate() {
		//TODO ezt meg nem tudom hogy kellene tesztelni
	}

	@Test
	void testHasPump() {
		assertEquals(false,mockCistern.hasPump());
		mockCistern.CreatePump();
		assertEquals(true,mockCistern.hasPump());
		mockCistern.CarryPump();
		assertEquals(false,mockCistern.hasPump());
	}

	@Test
	void testGetPipe() {
		assertEquals(null,mockCistern.getPipe());
		mockCistern.CreatePipe();
		assertInstanceOf(Pipe.class,mockCistern.getPipe() );
		assertNotEquals(null,mockCistern.getPipe());
	}

	@Test
	void testHasP() {
		assertEquals(false,mockCistern.hasP());
		mockCistern.CreatePipe();
		assertEquals(true,mockCistern.hasP());
	}

	@Test
	void testGetpCount() {
		assertEquals(0,mockCistern.getpCount());
		mockCistern.CreatePipe();
		mockCistern.CreatePipe();
		assertEquals(2,mockCistern.getpCount());
	}

}
