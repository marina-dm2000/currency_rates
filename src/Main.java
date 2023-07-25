import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите дату в виде dd/MM/YYYY:");
            String date = reader.readLine();
            System.out.println("Введите код валюты, например USD:");
            String codeChar = reader.readLine();

            System.out.println(getRate(date, codeChar));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRate(String date, String codeChar) throws IOException {
        Code code = new Code(new URL("https://www.cbr.ru/scripts/XML_valFull.asp"), codeChar);

        if (code.getParentCode() == null) {
            return "Неверно введен код валюты";
        }

        Rate rate = new Rate(code.getParentCode(), date);

        if (rate.getRate() == null) {
            return "Данных на эту дату нет";
        }

        return String.format("%s (%s): %s", code.getIsoCharCode(), code.getNameCode(), rate.getRate());
    }
}