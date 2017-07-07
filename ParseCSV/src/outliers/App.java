package outliers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for testing 'StatisticTest' methods
 * 
 * @author Marcin Zabadaj
 *
 */
public class App {

	/**
	 * Main function
	 * 
	 * @param args
	 *            contain the program parameters
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			Scanner scn = new Scanner(System.in);
			System.out.println("Podaj ścieżkę do pliku: ");
			String path = scn.nextLine();

			StatisticTest statisticTest = new StatisticTestImp();
			Map<String, Map<Integer, ArrayList<Double>>> array = statisticTest.readCsvFile(path);

			Map<String, Map<Integer, ArrayList<Double>>> array2 = statisticTest.removeOutliersFromFile(2, array);
			System.out.println("Plik został‚ przetworzony. ");
			statisticTest.saveFile(array2, path + "_.csv");
			System.out.println("Plik został‚ zapisany. ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
