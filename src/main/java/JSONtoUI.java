import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by amit.nagar on 5/7/16.
 */

class JSONtoUI {
    private static final String ORIENTATION = "orientation";
    private static final String CHILDREN = "children";
    private static final String COMPONENT_CONFIG = "componentConfig";
//    private static final String VERTICAL = "VERTICAL";
    private static final String HORIZONTAL = "HORIZONTAL";
    private static FileWriter writer;

    static void toHTML (File json, File result_html) throws Exception {
        FileReader reader = new FileReader(json);
        writer = new FileWriter(result_html.getAbsoluteFile());

        writer.write("<head>\n" +
                "<title>Div works</title>\n" +
                "</head>\n" +
                "\n");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//            System.out.println(jsonObject.toString());

        JSONtoUI.divMaker(jsonObject, "");

        writer.close();
        reader.close();
    }

    private static void divMaker(JSONObject jsonObject, String parentOrientation) {

        JSONObject design = (JSONObject) jsonObject.get("design");
        String first = (String) jsonObject.get(ORIENTATION);
        if (first != null) {
            startMaker(parentOrientation, first, design);
            JSONArray children = (JSONArray) jsonObject.get(CHILDREN);
            if (children != null) {
//                System.out.println(children.toString());
                for (Object aChildren : children) {
                    divMaker((JSONObject) aChildren, first);
//                    System.out.println(children.get(i).toString());
                }
            }
        }
        else {
            JSONObject leaf = (JSONObject) jsonObject.get(COMPONENT_CONFIG);
            leafMaker(parentOrientation, leaf);
        }

        endMaker();

    }

    private static void startMaker(String parentOrientation, String first, JSONObject design) {
        if(design != null){
            String style = getStyle(parentOrientation, design);
            Transformer.writeToFile(writer, "<div orientation=\"" + first + "\"" + " style=\""+ style + "\">");
        }
        else {
            Transformer.writeToFile(writer, "<div orientation=\"" + first + "\">");
        }
    }

    private static void leafMaker (String parentOrientation, JSONObject leaf) {

        Long id = (Long) leaf.get("slotId");
        JSONObject design = (JSONObject) leaf.get("design");
        String style = getStyle(parentOrientation, design);
        Transformer.writeToFile(writer, "<div class=\"leaf\" id=\"" + id.toString() + "\" align=\"Center\" " + "style=\""+ style  + "border: 1px solid black;"+ "\" >");

        String dataProviderId = (String) leaf.get("dataProviderId");
        JSONArray widgetTypes = (JSONArray) leaf.get("widgetTypes");
        JSONObject slotParams  = (JSONObject) leaf.get("slotParams");

        String viewable = getViewable(dataProviderId, widgetTypes, slotParams);
        Transformer.writeToFile(writer, viewable);
    }

    private static void endMaker() {
        Transformer.writeToFile(writer, "</div>");
    }

    private static String getStyle(String parentOrientation, JSONObject design) {
        String style;

        if (parentOrientation.compareTo(HORIZONTAL)!=0){
            style = " ";
        }
        else {
            style = "float: left; ";
        }

        String width = (String) design.get("width");
        if (width != null) {
            style = style + "width: "+ width + "; ";
        }

        String height = (String) design.get("height");
        if (height != null) {
            style = style + "height: " + height + "; ";
        }

        String color = (String) design.get("background-color");
        if (color != null) {
            style = style + "background-color: " + color + "; ";
        }

        style = style + "position: relative; box-sizing: border-box; overflow:auto; ";

        return style;
    }

    private static String getViewable (String dataProviderId, JSONArray widgetTypes, JSONObject slotParams) {
        String viewable =  "<p>Data Provider is: " + dataProviderId + "</p><hr width=\"98%\" align=\"center\">\n";

        for (Object widgetType : widgetTypes) {
            JSONObject o = (JSONObject) widgetType;
            viewable = viewable + "<p> clientWidgetId:" + o.get("clientWidgetId").toString() + " dataProviderWidgetId:" + o.get("dataProviderWidgetId").toString() + "</p><hr width=\"5%\" align=\"center\">\n";
        }

        viewable = viewable + "<p> theme:" + slotParams.get("theme") + " store:" + slotParams.get("store") + " </p>";

        return viewable;
    }

}
