import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by amit.nagar on 7/7/16.
 */
class UItoJSON {
    private static final String ORIENTATION = "orientation";
    private static final String CHILDREN = "children";
    private static final String COMPONENT_CONFIG = "componentConfig";
    //    private static final String VERTICAL = "VERTICAL";
    private static final String HORIZONTAL = "HORIZONTAL";
    private static FileWriter writer;

    public static void toJSON (File html, File result_json) throws Exception{
        writer = new FileWriter(result_json.getAbsoluteFile());

        Document document = Jsoup.parse(html, "UTF-8");
        System.out.println(document.head());


    }
}
