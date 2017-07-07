package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import outliers.StatisticTest;
import outliers.StatisticTestImp;

/**
 * Unit test for StatisticTestImp
 * 
 * @author Marcin
 *
 */
public class StatisticTestImpTest {
	ArrayList<Double> list1;
	ArrayList<Double> list2;
	ArrayList<Double> list3;
	ArrayList<Double> list4;
	ArrayList<Double> list11;
	ArrayList<Double> list22;
	ArrayList<Double> list33;
	ArrayList<Double> testList;
	ArrayList<Double> testListWithMistake;
	Map<Integer, ArrayList<Double>> testDifferences;
	Map<Integer, ArrayList<Double>> testDifferencesWithMistake;
	Map<Integer, ArrayList<Double>> data;

	public void generateData() {
		list1 = new ArrayList<Double>();
		list1.add(1.0);
		list1.add(1.0);
		list1.add(1.0);
		list1.add(1.0);

		list2 = new ArrayList<Double>();
		list2.add(2.0);
		list2.add(2.0);
		list2.add(2.0);
		list2.add(2.0);

		list3 = new ArrayList<Double>();
		list3.add(9.0);
		list3.add(9.0);
		list3.add(9.0);
		list3.add(9.0);

		list4 = new ArrayList<Double>();
		list4.add(1.0);
		list4.add(1.0);
		list4.add(1.0);
		list4.add(1.0);

		list11 = new ArrayList<Double>();
		list11.add(3.0);
		list11.add(3.0);
		list11.add(3.0);
		list11.add(3.0);

		list22 = new ArrayList<Double>();
		list22.add(2.0);
		list22.add(2.0);
		list22.add(2.0);
		list22.add(2.0);

		list33 = new ArrayList<Double>();
		list33.add(5.0);
		list33.add(5.0);
		list33.add(5.0);
		list33.add(5.0);

		testList = new ArrayList<Double>();
		testList.add(4.0);
		testList.add(4.0);
		testList.add(4.0);
		testList.add(4.0);

		testListWithMistake = new ArrayList<Double>();
		testListWithMistake.add(4.0);
		testListWithMistake.add(5.0);
		testListWithMistake.add(4.0);
		testListWithMistake.add(4.0);

		testDifferences = new HashMap<Integer, ArrayList<Double>>();
		testDifferences.put(1, list11);
		testDifferences.put(2, list22);
		testDifferences.put(3, list33);

		testDifferencesWithMistake = new HashMap<Integer, ArrayList<Double>>();
		testDifferences.put(1, list11);
		testDifferences.put(2, list22);
		testDifferences.put(3, list11);

		data = new HashMap<Integer, ArrayList<Double>>();
		data.put(1, list1);
		data.put(2, list2);
		data.put(3, list3);
		Double[] table1 = { new Double(3.0), new Double(3.0), new Double(3.0), new Double(3.0) };
		Double[] table2 = { new Double(2.0), new Double(2.0), new Double(2.0), new Double(2.0) };
		Double[] table3 = { new Double(5.0), new Double(5.0), new Double(5.0), new Double(5.0) };
		testDifferences.put(1, new ArrayList<Double>(Arrays.asList(table1)));
		testDifferences.put(2, new ArrayList<Double>(Arrays.asList(table2)));
		testDifferences.put(3, new ArrayList<Double>(Arrays.asList(table3)));
	}

	@Test
	public void calculateAverage_whereAssertTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		List<Double> vectorAverage = new ArrayList<Double>();
		generateData();

		StatisticTestImp cls = new StatisticTestImp();
		Field field = StatisticTestImp.class.getDeclaredField("lengthOfRows");
		field.setAccessible(true);
		field.set(cls, 4);
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateAverage", Map.class);
		method.setAccessible(true);
		vectorAverage = (List<Double>) method.invoke(cls, data);
		assertTrue(vectorAverage.equals(testList));
	}

	@Test
	public void calculateAverage_whereAssertFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Double> vectorAverage = new ArrayList<Double>();
		generateData();

		StatisticTestImp cls = new StatisticTestImp();
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateAverage", Map.class);
		method.setAccessible(true);
		vectorAverage = (List<Double>) method.invoke(cls, data);
		assertFalse(vectorAverage.equals(testListWithMistake));
	}

	@Test
	public void calculateDifferences_whereAssertTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		Map<Integer, ArrayList<Double>> result;
		List<Double> vectorAverage = new ArrayList<Double>();
		generateData();

		StatisticTestImp cls = new StatisticTestImp();
		Field field = StatisticTestImp.class.getDeclaredField("lengthOfRows");
		field.setAccessible(true);
		field.set(cls, 4);
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateAverage", Map.class);
		method.setAccessible(true);
		vectorAverage = (List<Double>) method.invoke(cls, data);

		Method method2 = StatisticTestImp.class.getDeclaredMethod("calculateDifferences", Map.class, ArrayList.class);
		method2.setAccessible(true);
		result = (Map<Integer, ArrayList<Double>>) method2.invoke(cls, data, vectorAverage);
		assertTrue(result.equals(testDifferences));
	}

	@Test
	public void calculateDifferences_whereAssertFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Map<Integer, ArrayList<Double>> result;
		List<Double> vectorAverage = new ArrayList<Double>();
		generateData();

		StatisticTestImp cls = new StatisticTestImp();
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateAverage", Map.class);
		method.setAccessible(true);
		vectorAverage = (List<Double>) method.invoke(cls, data);

		Method method2 = StatisticTestImp.class.getDeclaredMethod("calculateDifferences", Map.class, ArrayList.class);
		method2.setAccessible(true);
		result = (Map<Integer, ArrayList<Double>>) method2.invoke(cls, data, vectorAverage);

		assertFalse(result.equals(testDifferencesWithMistake));
	}

	@Test
	public void calculateSumOfDifferences_whereAssertTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		generateData();
		Map<Integer, Double> resultTest = new HashMap<Integer, Double>();
		resultTest.put(1, 4.0);
		resultTest.put(2, 8.0);
		resultTest.put(3, 36.0);
		StatisticTestImp cls = new StatisticTestImp();
		Field field = StatisticTestImp.class.getDeclaredField("lengthOfRows");
		field.setAccessible(true);
		field.set(cls, 4);
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateSumOfDifferences", Map.class);
		method.setAccessible(true);
		Map<Integer, Double> result = (Map<Integer, Double>) method.invoke(cls, data);
		assertTrue(result.equals(resultTest));
	}

	@Test
	public void calculateSumOfDifferences_whereAssertFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		generateData();
		Map<Integer, Double> resultTest = new HashMap<Integer, Double>();
		resultTest.put(1, 4.0);
		resultTest.put(2, 9.0);
		resultTest.put(3, 36.0);
		StatisticTestImp cls = new StatisticTestImp();
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateSumOfDifferences", Map.class);
		method.setAccessible(true);
		Map<Integer, Double> result = (Map<Integer, Double>) method.invoke(cls, data);
		assertFalse(result.equals(resultTest));
	}

	@Test
	public void removeOutlierElement_whereAssertTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		generateData();
		StatisticTestImp cls = new StatisticTestImp();
		Field field = StatisticTestImp.class.getDeclaredField("lengthOfRows");
		field.setAccessible(true);
		field.set(cls, 4);
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateSumOfDifferences", Map.class);
		method.setAccessible(true);
		Map<Integer, Double> sumOfDifferences = (Map<Integer, Double>) method.invoke(cls, data);

		method = StatisticTestImp.class.getDeclaredMethod("removeOutlierElement", Map.class, Map.class);
		method.setAccessible(true);
		method.invoke(cls, data, sumOfDifferences);

		Map<Integer, ArrayList<Double>> data2 = new HashMap<Integer, ArrayList<Double>>();
		data2.put(1, list1);
		data2.put(2, list2);
		assertTrue(data.equals(data2));
	}

	@Test
	public void removeOutlierElement_whereAssertFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		generateData();
		StatisticTestImp cls = new StatisticTestImp();
		Method method = StatisticTestImp.class.getDeclaredMethod("calculateSumOfDifferences", Map.class);
		method.setAccessible(true);
		Map<Integer, Double> sumOfDifferences = (Map<Integer, Double>) method.invoke(cls, data);

		method = StatisticTestImp.class.getDeclaredMethod("removeOutlierElement", Map.class, Map.class);
		method.setAccessible(true);
		method.invoke(cls, data, sumOfDifferences);

		Map<Integer, ArrayList<Double>> data2 = new HashMap<Integer, ArrayList<Double>>();
		data2.put(1, list1);
		data2.put(2, list11);
		assertFalse(data.equals(data2));
	}

	@Test
	public void remove2Outliers_WhereAssertTrue()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		generateData();
		data.put(4, list4);
		StatisticTest test = new StatisticTestImp();
		Field field = StatisticTestImp.class.getDeclaredField("lengthOfRows");
		field.setAccessible(true);
		field.set(test, 4);
		test.removeOutliers(2, data);

		Map<Integer, ArrayList<Double>> data2 = new HashMap<Integer, ArrayList<Double>>();
		data2.put(1, list1);
		data2.put(4, list1);
		assertTrue(data.equals(data2));
	}

	@Test
	public void remove2Outliers_WhereAssertFalse() {
		generateData();
		data.put(4, list4);
		StatisticTest test = new StatisticTestImp();
		test.removeOutliers(2, data);

		Map<Integer, ArrayList<Double>> data2 = new HashMap<Integer, ArrayList<Double>>();
		data2.put(1, list1);
		data2.put(4, list2);
		assertFalse(data.equals(data2));
	}

	@Test
	public void removeOutliersFromFile_WhereAssertTrue()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		generateData();
		Map<String, Map<Integer, ArrayList<Double>>> data2 = new HashMap<String, Map<Integer, ArrayList<Double>>>();
		Map<String, Map<Integer, ArrayList<Double>>> result = new HashMap<String, Map<Integer, ArrayList<Double>>>();
		Map<String, Map<Integer, ArrayList<Double>>> resultTest = new HashMap<String, Map<Integer, ArrayList<Double>>>();
		Map<Integer, ArrayList<Double>> dataTest = new HashMap<Integer, ArrayList<Double>>();
		data2.put("a", data);
		data2.put("b", data);
		data2.put("c", data);

		dataTest.put(1, list1);
		dataTest.put(2, list2);
		resultTest.put("a", dataTest);
		resultTest.put("b", dataTest);
		resultTest.put("c", dataTest);
		StatisticTest test = new StatisticTestImp();
		Field field = StatisticTestImp.class.getDeclaredField("lengthOfRows");
		field.setAccessible(true);
		field.set(test, 4);
		result = test.removeOutliersFromFile(1, data2);
		assertTrue(result.equals(resultTest));
	}
}
