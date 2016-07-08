import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by amit.nagar on 4/7/16.
 */

public class Transformer {
    private static final String JSON_FILE = "/home/amit.nagar/flipkart/Transformer/test2.json";
    private static final String HTML_FILE = "/home/amit.nagar/flipkart/Transformer/div_test2.html";
    private static final String RESULT_HTML = "/home/amit.nagar/flipkart/Transformer/result.html";
    private static final String RESULT_JSON = "/home/amit.nagar/flipkart/Transformer/result.json";

    public static void main(String[] args) {
        try {
           // FileReader reader = new FileReader(JSON_FILE);
            File json =  new File(JSON_FILE);
            File html = new File(HTML_FILE);
            File result_html = new File(RESULT_HTML);
            File result_json = new File(RESULT_JSON);

            JSONtoUI.toHTML(json, result_html);

            // UItoJSON.toJSON(html, result_json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void writeToFile(FileWriter writer, String s) {
        try {
            writer.write(s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
