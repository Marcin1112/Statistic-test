package outliers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Interface with methods to remove outliers
 * 
 * @author Marcin Zabadaj
 *
 */
public interface StatisticTest {
	/**
	 * A method that remove outliers from 'data' variable
	 * 
	 * @param numberOfRowsToRemove
	 *            is number of rows to remove in each class of data
	 * @param data
	 *            is {@code Map<Integer, ArrayList<Double>>}. This variable
	 *            storage a data for a single class of measurement points.
	 *            {@code ArrayList<Double>} contain a series of of measurements
	 *            points
	 */
	public void removeOutliers(int numberOfRowsToRemove, Map<Integer, ArrayList<Double>> data);

	/**
	 * A method that allow to read *.csv file and return a map with a data
	 * 
	 * @param path
	 *            A string that represent a path to file
	 * @return {@code Map<String, Map<Integer, ArrayList<Double>>>} a variable
	 *         that storage an input and an output data. 'String' parameter is a
	 *         name of a class of measured data.
	 * @throws IOException
	 *             when remove outliers has failed
	 */
	public Map<String, Map<Integer, ArrayList<Double>>> readCsvFile(String path) throws IOException;

	/**
	 * This method remove outliers from 'data' variable. 'data' variable storage
	 * a data loaded directly from file
	 * 
	 * @param numberOfRowsToRemove
	 *            is number of rows to remove in each class of data
	 * @param data
	 *            is {@code Map<String, Map<Integer, ArrayList<Double>>>}. This
	 *            variable storage a data. 'String' parameter is a name of a
	 *            class of measured data.
	 * @return {@code HashMap<String, Map<Integer, ArrayList<Double>>>}
	 */
	public HashMap<String, Map<Integer, ArrayList<Double>>> removeOutliersFromFile(int numberOfRowsToRemove,
			Map<String, Map<Integer, ArrayList<Double>>> data);

	/**
	 * A method to save processed data in a file
	 * 
	 * @param data
	 *            is {@code Map<String, Map<Integer, ArrayList<Double>>>}. This
	 *            variable contain data to save in a file. 'String' parameter is
	 *            a name of a class of measured data.
	 * @param path
	 *            A string that represent a path to file
	 * @throws IOException
	 *             when save file has failed
	 */
	public void saveFile(Map<String, Map<Integer, ArrayList<Double>>> data, String path) throws IOException;
}
