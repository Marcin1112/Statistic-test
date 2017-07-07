package outliers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class that implement StatisticTest interface
 * 
 * @author Marcin Zabadaj
 *
 */
public class StatisticTestImp implements StatisticTest {
	private int lengthOfRows;
	private static String delimiter = ";";

	/**
	 * {@inheritDoc}
	 */
	public void removeOutliers(int numberOfRowsToRemove, Map<Integer, ArrayList<Double>> data) {
		while (numberOfRowsToRemove > 0) {
			ArrayList<Double> vectorAverage = calculateAverage(data);
			Map<Integer, ArrayList<Double>> differences = calculateDifferences(data, vectorAverage);
			Map<Integer, Double> sumOfDifferences = calculateSumOfDifferences(differences);
			removeOutlierElement(data, sumOfDifferences);
			--numberOfRowsToRemove;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveFile(Map<String, Map<Integer, ArrayList<Double>>> data, String path) throws IOException {
		@SuppressWarnings("resource")
		BufferedWriter bWritter = new BufferedWriter(new FileWriter(path));
		for (Entry<String, Map<Integer, ArrayList<Double>>> key : data.entrySet()) {
			for (Entry<Integer, ArrayList<Double>> row : key.getValue().entrySet()) {
				bWritter.write(key.getKey() + delimiter);
				for (Double value : row.getValue()) {
					bWritter.write(value + delimiter);
				}
				bWritter.write("\n");
				bWritter.flush();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public HashMap<String, Map<Integer, ArrayList<Double>>> removeOutliersFromFile(int numberOfRowsToRemove,
			Map<String, Map<Integer, ArrayList<Double>>> data) {
		HashMap<String, Map<Integer, ArrayList<Double>>> result = new HashMap<String, Map<Integer, ArrayList<Double>>>();
		for (Entry<String, Map<Integer, ArrayList<Double>>> i : data.entrySet()) {
			Map<Integer, ArrayList<Double>> partOfData = new HashMap<Integer, ArrayList<Double>>();
			partOfData.putAll(i.getValue());
			removeOutliers(numberOfRowsToRemove, partOfData);
			result.put(i.getKey(), partOfData);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("resource")
	public Map<String, Map<Integer, ArrayList<Double>>> readCsvFile(String path) throws IOException {
		BufferedReader bReader;
		bReader = new BufferedReader(new FileReader(path));
		String currentLine;
		Integer row = 1;
		Map<Integer, ArrayList<String>> rawData = new HashMap<Integer, ArrayList<String>>();
		while ((currentLine = bReader.readLine()) != null) {
			String[] parts = currentLine.split(";");
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(parts));
			rawData.put(row, list);
			++row;
		}
		lengthOfRows = rawData.get(1).size() - 1;

		Map<String, Map<Integer, ArrayList<Double>>> data = new HashMap<String, Map<Integer, ArrayList<Double>>>();
		for (Entry<Integer, ArrayList<String>> i : rawData.entrySet()) {
			String key = i.getValue().get(0);
			if (!data.containsKey(key)) {
				Map<Integer, ArrayList<Double>> map = new HashMap<Integer, ArrayList<Double>>();
				Integer counter = 1;
				for (Entry<Integer, ArrayList<String>> j : rawData.entrySet()) {
					String key2 = j.getValue().get(0);
					if (key2.equals(key)) {
						ArrayList<Double> arrayList = new ArrayList<Double>();
						ArrayList<String> stringList = j.getValue();

						// convert String to Double
						for (int a = 1; a < stringList.size(); ++a) {
							arrayList.add(Double.parseDouble(stringList.get(a).replace(",", ".")));
						}

						map.put(counter, arrayList);
						++counter;
					}
				}
				data.put(key, map);
			}
		}
		return data;
	}

	/**
	 * Auxiliary method. Remove one outlier element from a single class
	 * 
	 * @param data
	 *            is {@code Map<Integer, ArrayList<Double>>}. Contain an input
	 *            and output data
	 * @param sumOfDifferences
	 *            is {@code Map<Integer, Double>} calculated by
	 *            calculateSumOfDifferences
	 */
	private void removeOutlierElement(Map<Integer, ArrayList<Double>> data, Map<Integer, Double> sumOfDifferences) {
		ArrayList<Double> list = (new ArrayList<Double>(sumOfDifferences.values()));
		Collections.sort(list);
		for (Integer keyToRemove : sumOfDifferences.keySet()) {
			if (sumOfDifferences.get(keyToRemove).equals(list.get(list.size() - 1))) {
				data.remove(keyToRemove);
				break;
			}
		}
	}

	/**
	 * Auxiliary method to calculate cum of differences in one class of
	 * measutement data.
	 * 
	 * @param differences
	 *            is a single class of data -
	 *            {@code Map<Integer, ArrayList<Double>>}
	 * @return {@code Map<Integer, Double>}
	 */
	private Map<Integer, Double> calculateSumOfDifferences(Map<Integer, ArrayList<Double>> differences) {
		Map<Integer, Double> sumOfDifferences = new HashMap<Integer, Double>();
		for (Integer j : differences.keySet()) {
			double sum = 0.0;
			for (int i = 0; i < lengthOfRows; ++i) {
				sum += differences.get(j).get(i);
			}
			sumOfDifferences.put(j, sum);
		}
		return sumOfDifferences;
	}

	/**
	 * Auxiliary method to calculate differences between a raw data and average
	 * calculated by 'calculateAverage' method
	 * 
	 * @param data
	 *            contain a raw data
	 * @param vectorAverage
	 *            is calculated by calculated by 'calculateAverage'
	 * @return {@code Map<Integer, ArrayList<Double>>}
	 */
	private Map<Integer, ArrayList<Double>> calculateDifferences(Map<Integer, ArrayList<Double>> data,
			ArrayList<Double> vectorAverage) {
		Map<Integer, ArrayList<Double>> differences = new HashMap<Integer, ArrayList<Double>>();
		// int lengthOfRows = data.get(1).size();
		for (Entry<Integer, ArrayList<Double>> j : data.entrySet()) {
			ArrayList<Double> diff = new ArrayList<Double>();
			for (int i = 0; i < lengthOfRows; ++i) {
				try {
					diff.add(Math.abs(data.get(j.getKey()).get(i) - vectorAverage.get(i)));
				} catch (Exception e) {
					System.out.println(i + " " + j);
				}
			}
			differences.put(j.getKey(), diff);
		}
		return differences;
	}

	/**
	 * Auxiliary method to calculate average
	 * 
	 * @param data
	 *            contain a raw measurement data
	 * @return {@code ArrayList<Double>}
	 */
	private ArrayList<Double> calculateAverage(Map<Integer, ArrayList<Double>> data) {
		ArrayList<Double> vectorAverage = new ArrayList<Double>();
		for (int i = 0; i < lengthOfRows; ++i) {
			Double sum = 0.0;
			for (Integer j : data.keySet()) {
				sum += data.get(j).get(i);
			}
			vectorAverage.add(sum / data.size());
		}
		return vectorAverage;
	}
}
