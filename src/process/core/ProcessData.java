package process.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * container to store key-value pairs of String - String
 * 
 */
public class ProcessData implements IProcessData {

	public ProcessData() {
		data = new HashMap<String, String>();
	}

	private HashMap<String, String> data;

	/**
	 * puts specified key-value pair to container
	 * 
	 * @param key
	 * 
	 * @param value
	 */
	public void put(String key, String value) {
		data.put(key, value);
	}

	/**
	 * returns a Set view of the mappings contained in this map
	 * 
	 * @return set of map entries
	 */
	public Set<Map.Entry<String, String>> entrySet() {
		return data.entrySet();
	}

	/**
	 * returns a Set view of the keys contained in this map
	 * 
	 * @return a Set view of the keys contained in this map
	 */
	public Set<String> keySet() {
		return data.keySet();
	}

	/**
	 * tests if value for key exists in the container
	 * 
	 * @param key
	 *            - key that is being tested
	 * @return true if key exists in the container, false - otherwise
	 */
	@Override
	public boolean has(String key) {
		return data.containsKey(key) && data.get(key) != null;
	}

	/**
	 * obtain value for key
	 * 
	 * @param key
	 * @return value
	 */
	@Override
	public String getValue(String key) {
		return data.get(key);
	}

	/**
	 * obtain string value of container
	 * 
	 * @return string value of container
	 */
	@Override
	public String toString() {
		return data.toString();
	}

}
