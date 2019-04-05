
public class classificator {
	heap dataset = new heap();
	String category_name;
	public void insert(String name, String g, String CO2) {
		dataset.insert(new data0_o(name, Double.parseDouble(CO2)/Double.parseDouble(g)));
	}
}
