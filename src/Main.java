import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите дату в виде dd/MM/YYYY:");
            String date = reader.readLine();
            System.out.println("Введите код валюты, например USD:");
            String code = reader.readLine();

            System.out.println(getRate(date, code));

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRate(String date, String code) throws IOException, ParserConfigurationException, SAXException {
        String parentCode = null;
        String nameCode = null;

        URL url = new URL("https://www.cbr.ru/scripts/XML_valFull.asp");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream stream = url.openStream();
        Document document = db.parse(stream);
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Item");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;

            if (eElement.getElementsByTagName("ISO_Char_Code").item(0).getTextContent().equals(code)) {
                parentCode = eElement.getElementsByTagName("ParentCode").item(0).getTextContent();
                parentCode = parentCode.strip();
                nameCode = eElement.getElementsByTagName("Name").item(0).getTextContent();
                break;
            }
        }

        if (parentCode == null) {
            return "Неверно введен код валюты";
        }

        URL urlCode = new URL(String.format("https://cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s", date, date, parentCode));
        InputStream streamRes = urlCode.openStream();
        Document documentRes = db.parse(streamRes);
        documentRes.getDocumentElement().normalize();

        String rate;
        try {
            rate = documentRes.getElementsByTagName("Value").item(0).getTextContent();
        } catch (NullPointerException e) {
            return "Данных на эту дату нет";
        }

        return String.format("%s (%s): %s", code, nameCode, rate);
    }
}