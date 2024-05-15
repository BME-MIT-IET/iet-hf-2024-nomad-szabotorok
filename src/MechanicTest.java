import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MechanicTest {
	
	Mechanic m;
	Pipe pipe;
	@BeforeEach
	void initiate() {
		
		m=new Mechanic();
	}
	@Test
	void testSetPump() {
		Pump p=new Pump();
		m.setPump(p);
		assertNotEquals("nothing", m.getPart());
	}

	void initiatePipe() {
		m=new Mechanic();
		pipe=new Pipe();
		m.setPos(pipe);
	}
	@Test
	void testRepair() {
		initiatePipe();
		pipe.BreakPipe();
		assertEquals(true,pipe.isBroken());
		m.Repair();
		assertEquals(false,pipe.isBroken());
	}

	@Test
	void testCarryPipeEnd() {
		initiatePipe();
		assertEquals("nothing",m.getPart());
		m.CarryPipeEnd(pipe);
		assertNotEquals("nothing",m.getPart());
	}

	@Test
	void testLayPipe() {
		assertEquals(false,m.LayPipe());
		Pipe pipe=new Pipe();
		Pump pump=new Pump();
		m.setPos(pump);
		m.CarryPipeEnd(pipe);
		assertEquals(true,m.LayPipe());
	}

	@Test
	void testCarryPump() {
		fail("Not yet implemented");
	}

	@Test
	void testLayPump() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPart() {
		Pump pump=new Pump();
		assertEquals("nothing",m.getPart());
		m.setPump(pump);
		assertEquals(pump.id,m.getPart());
	}

	@Test
	void testMechanic() {
		Mechanic m2=new Mechanic();
		assertNotEquals(m, m2);
		assertInstanceOf(Mechanic.class, m2);
	}

}
