import org.w3c.dom.Document;

import java.net.MalformedURLException;
import java.net.URL;

public class Rate {
    private URL url;
    private String rate;
    private String parentCode;
    private String date;

    public Rate(String parentCode, String date) throws MalformedURLException {
        this.parentCode = parentCode;
        this.date = date;
        this.url = new URL(String.format("https://cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s", date, date, parentCode));
        Document document = DocumentHelper.getDocument(url);
        try {
            this.rate = document.getElementsByTagName("Value").item(0).getTextContent();
        } catch (NullPointerException e) {
            this.rate = null;
        }
    }

    public String getRate() {
        return rate;
    }
}
