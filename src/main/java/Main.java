import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        List<Employee> list = parseXML("/Users/wisnap/IdeaProjects/XML - JSON parser/data.xml");
        String json = listToJson(list);
        File file = new File("/Users/wisnap/IdeaProjects/XML - JSON parser/data2.json");
        writeSrtring(json, file);
    }

    public static List<Employee> parseXML(String path) {

        List<Employee> list = new ArrayList<Employee>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(path));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                List<String> objInsance = new ArrayList<>();
                Node node_ = nodeList.item(i);
                if (Node.ELEMENT_NODE == node_.getNodeType()) {
                    Element element = (Element) node_;
                    NamedNodeMap map = element.getAttributes();
                    for (int a = 0; a < map.getLength(); a++) {
                        Node el = map.item(a);
                        if (Node.TEXT_NODE != el.getNodeType()) {
                            String val = el.getNodeValue();
                            objInsance.add(val);
                        }
                    }
                    Employee employee = new Employee(
                            Long.parseLong(objInsance.get(0)),
                            objInsance.get(1),
                            objInsance.get(2),
                            objInsance.get(3),
                            Integer.parseInt(objInsance.get(4))
                    );
                    list.add(employee);
                }

            }
            return list;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }

    public static void writeSrtring(String json, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] bytes = json.getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


