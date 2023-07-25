import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;

public class Code {
    private URL url;
    private String isoCharCode;
    private String parentCode;
    private String nameCode;

    public Code(URL url, String isoCharCode) {
        this.url = url;
        this.isoCharCode = isoCharCode;
        Document document = DocumentHelper.getDocument(url);
        NodeList nList = document.getElementsByTagName("Item");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Element eElement = (Element) nNode;

            if (eElement.getElementsByTagName("ISO_Char_Code").item(0).getTextContent().equals(isoCharCode)) {
                parentCode = eElement.getElementsByTagName("ParentCode").item(0).getTextContent();
                parentCode = parentCode.strip();
                nameCode = eElement.getElementsByTagName("Name").item(0).getTextContent();
                break;
            }
        }
    }

    public String getIsoCharCode() {
        return isoCharCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public String getNameCode() {
        return nameCode;
    }
}
