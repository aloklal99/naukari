package org.hortonworks.qe;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hortonworks.qe.Trial1;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assume.*;
import static org.junit.Assert.*;

public class TestTrial1 {

	@Test
	public void testMultiply() {
		System.out.println("In testMultiply");
		Trial1 sut = new Trial1();
		assertEquals("1+2+3 == 0", 0, sut.add(1, 2, 3)); // should fail!
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

}
