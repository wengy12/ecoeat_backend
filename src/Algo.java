import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Algo {
    public static ArrayList<Data> dataset_1 = new ArrayList<>();

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
            int j = 1;
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

    }

    public static void main(String[] args) throws IOException{
        init();
        System.out.print(1);
    }
}
