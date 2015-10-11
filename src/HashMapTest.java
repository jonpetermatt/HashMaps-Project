import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HashMapTest {

	@Test
	public void test() throws Exception {
		HashMap<Integer, Integer> test = new HashMap<>(40000000, 3, 7);
		test.put(50, 1000000);
		assertEquals(new Integer(1000000), test.get(50));
		assertEquals(null, test.get(90));
		List testList = new ArrayList<>();
		testList.add(50);
		assertEquals(testList, test.keys());
		test.put(50, 10);
		assertEquals(new Boolean(false), test.isEmpty());
		assertEquals(2, test.size());
	}

}
