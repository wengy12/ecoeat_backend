import java.util.ArrayList;

public class Data {
    public String product_name;
    public ArrayList<String> category = new ArrayList<>();
    public String key;
    public String quantity;
    public Data(String k, String q, String p){
        product_name = p;
        key = k;
        quantity = q;
    }
    public Data add_c(String s){
        category.add(s);
        return this;
    }
}
