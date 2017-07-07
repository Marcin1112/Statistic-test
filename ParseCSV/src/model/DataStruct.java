package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Simple model. Storage loaded and processed data
 * 
 * @author Marcin
 *
 */
public class DataStruct {
	private Map<String, Map<Integer, ArrayList<Double>>> data;

	public Map<String, Map<Integer, ArrayList<Double>>> getData() {
		return data;
	}

	public void setData(Map<String, Map<Integer, ArrayList<Double>>> data) {
		this.data = data;
	}

}
