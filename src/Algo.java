import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Algo {
    public static ArrayList<Data> dataset_1 = new ArrayList<>();
    public static ArrayList<classificator> dataset_2 = new ArrayList<>();
	private static HashSet<Integer> d2;

    private static int find_from_d2(String s) {
    	for (int i = 0; i < dataset_2.size(); i++) {
    		if (dataset_2.get(i).category_name.equals(s)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
	public static void sort (  ArrayList<Data> x ) {
		qSort2(x, 0, x.size()-1);
	}
	
	private static void swap (ArrayList<Data> x, int l, int r) {
		Data t = x.get(l);
		x.set(l, x.get(r));
		x.set(r, t);
	}
	
	private static void qSort2 ( ArrayList<Data> x, int l, int r ) {
        if (l < r) { 
            int pi = partition2(x, l, r); 
            qSort2(x, l, pi-1); 
            qSort2(x, pi+1, r); 
        } 
	}
	
	private static int partition2 (ArrayList<Data> x, int l, int r) {
		int randomNum = ThreadLocalRandom.current().nextInt(l, r);
		int i = l-1;
		Data v = x.get(randomNum);
		swap(x, randomNum, r);
        for (int j = l; j < r; j++) {
            if (!x.get(j).compare_to(v)) { 
            	i++;
            	swap(x, i, j);
            } 
        }  
        swap(x, i+1, r);
        return i+1; 
	}
    
	private static int bin_search(ArrayList<Data> x, int l, int r, String id) {
		if (r >= l) {
			int mid = (l + r)/2;
			if (x.get(mid).key.equals(id)) {
				return mid;
			}
			if (x.get(mid).key.compareTo(id) > 0) {
				return bin_search(x, l, mid-1, id);
			}
			return bin_search(x, mid+1, r, id);
		}
		return -1;
	}
	
	private static boolean weak_compare(String s1, String s2) {
		char[] ss1 = s1.toUpperCase().toCharArray();
		char[] ss2 = s2.toUpperCase().toCharArray();
		int score = 0;
		for (int i = 0; i < min(ss1.length, ss2.length); i++) {
			if (ss1[i] == ss2[i]) {
				score++;
			}
		}
		if (Double.valueOf(score) >= Double.valueOf(min(ss1.length, ss2.length))*0.75) {
			return true;
		}
		return false;
	}
	
	private static int min(int length, int length2) {
		if (length < length2) {
			return length;
		}
		return length2;
	}

	private static int search_d2(String s) {
		for (int i = 0; i < dataset_2.size(); i++) {
			for (int j = 1; j < dataset_2.get(i).dataset.Heap.size(); j++) {
				if (weak_compare(dataset_2.get(i).dataset.Heap.get(j).name, s)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	//Function for find
	private static String find(String id) {
		int res = bin_search(dataset_1, 0, dataset_1.size()-1, id);
		if (res == -1) {
			return "WTF this product is not in the list!";
		}
		d2 = new HashSet<>();
		int d21 = -1;
		for (int i = 0; i < dataset_1.get(res).category.size(); i++) {
			d21 = search_d2(dataset_1.get(res).category.get(i));
			if (d21 != -1) {
				d2.add(d21);
				//System.out.println(dataset_1.get(res).category.get(i));
			}
		}
		d21 = search_d2(dataset_1.get(res).product_name);
		if (d21 != -1) {
			//System.out.println(dataset_1.get(res).product_name);
			d2.add(d21);
		}
		if (d2.size() == 0) {
			return "Congrads! You found the best product!";
		}
		Iterator<Integer> iterator = d2.iterator();
		String r = "This is the list of best alternatives! \n";
		while (iterator.hasNext()) {
			r += (dataset_2.get(iterator.next()).get() + "\n");
		}
		return r;
	}
	
    //Fucking call this function PLIZZZZZZ
    public static void init() throws IOException {
        java.io.BufferedReader in1 = new BufferedReader(new FileReader(".\\src\\big_dataset.csv"));
        ArrayList<String> lines = new ArrayList<>();
        String str;
        while((str = in1.readLine()) != null){
            lines.add(str);
        }
        String[] linesArray = lines.toArray(new String[lines.size()]);
        for (int i = 1; i < linesArray.length; i++) {
            int j = 0;
            String badCurrentLine = linesArray[i];
            char[] currentLine = badCurrentLine.toCharArray();
            int ncc = 0;
            String pn = "", q = "", code = "";
            while (j < currentLine.length) {
                if (currentLine[j] == ';') {
                    ncc++;
                    j++;
                    continue;
                }
                if (ncc == 0) {
                    code += currentLine[j];
                }
                if (ncc == 1) {
                    pn += currentLine[j];
                }
                if (ncc == 2) {
                    q += currentLine[j];
                }
                if (ncc == 3){
                    break;
                }
                j++;
            }
            dataset_1.add(new Data(code, q, pn));
            String category = "";
            while (j < currentLine.length){
                if (currentLine[j] == ','){
                    if (category.length() == 0 || (category.charAt(0) == 'f'
                            && category.charAt(0) == 'r'
                            && category.charAt(0) == ':')) {
                        category = "";
                        j++;
                        continue;
                    }
                    dataset_1.set(dataset_1.size()-1, dataset_1.get(dataset_1.size()-1).add_c(category));
                    category = "";
                    j++;
                    continue;
                }
                category += currentLine[j];
                j++;
            }
            if (category.length() == 0 || (category.charAt(0) == 'f'
                    && category.charAt(0) == 'r'
                    && category.charAt(0) == ':')){

            }
            else{
                dataset_1.set(dataset_1.size()-1, dataset_1.get(dataset_1.size()-1).add_c(category));
            }
        }
        java.io.BufferedReader in2 = new BufferedReader(new FileReader(".\\src\\Carbon_Emissions_per_Serving_and_per_Calorie.csv"));
        lines = new ArrayList<>();
        str = null;
        while((str = in2.readLine()) != null){
            lines.add(str);
        }
        linesArray = lines.toArray(new String[lines.size()]);
        for (int i = 1; i < linesArray.length; i++) {
            int j = 0;
            String badCurrentLine = linesArray[i];
            char[] currentLine = badCurrentLine.toCharArray();
            int ncc = 0;
            String n = "", gps = "", cps = "", c = "";
            while (j < currentLine.length) {
                if (currentLine[j] == ',') {
                    ncc++;
                    j++;
                    continue;
                }
                if (ncc == 0) {
                    n += currentLine[j];
                }
                if (ncc == 1) {
                    c += currentLine[j];
                }
                if (ncc == 4) {
                    gps += currentLine[j];
                }
                if (ncc == 8){
                    cps += currentLine[j];
                }
                j++;
            }
            int k = find_from_d2(c);
            if (k == -1) {
            	dataset_2.add(new classificator(c));
            	k = dataset_2.size()-1;
            }
            dataset_2.set(k, dataset_2.get(k).insert(n, gps, cps));
        }
        sort(dataset_1);
    }

    public static void main(String[] args) throws IOException{
        init();
        //System.out.print(dataset_1.get(bin_search(dataset_1, 0, dataset_1.size()-1, "0010700021526")).product_name);
        System.out.println(find("3095756947010"));
        System.out.println(find("3095757645106"));
        System.out.println(find("3100490805080"));
        System.out.println(find("3111952018481"));
        System.out.println(find("3095756947321"));
        System.out.println(find("3095756947930"));
        
    }
}
