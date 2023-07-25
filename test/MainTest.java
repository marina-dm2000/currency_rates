import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void getRate() throws IOException, ParserConfigurationException, SAXException {
        String result1 = Main.getRate("05/05/2022", "USD");
        assertEquals(result1, "USD (Доллар США): 69,4160");

        String result2 = Main.getRate("08/10/2022", "GEL");
        assertEquals(result2, "Данных на эту дату нет");

        String result3 = Main.getRate("08/10/2022", "JDH");
        assertEquals(result3, "Неверно введен код валюты");
    }
}