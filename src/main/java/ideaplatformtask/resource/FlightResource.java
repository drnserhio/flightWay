package ideaplatformtask.resource;

import ideaplatformtask.model.Resultes;
import ideaplatformtask.util.CalculateHelper;
import ideaplatformtask.util.UtilParse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class FlightResource {

    private final UtilParse utilParse = new UtilParse();
    private Resultes resultes;

    public void run() {
        menu();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        try {
            while ((in = reader.readLine()) != null) {
                choose(in, reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void choose(String in, BufferedReader reader) throws Exception {
        switch (in) {
            case "1":
               resultes = utilParse.jsonParseToTicket();
                break;
            case "2":
                if (Objects.isNull(resultes)) {
                    System.out.println("Please read file .json in menu.");
                    break;
                }
                CalculateHelper.avgFlights(resultes, "Владивосток", "Тель-Авив");
                break;
            case "3":
                if (Objects.isNull(resultes)) {
                    System.out.println("Please read file .json in menu.");
                    break;
                }
                CalculateHelper.procentil(resultes, "Владивосток", "Тель-Авив", 90);
                break;
            case "4":
                System.exit(0);
            default:
                System.out.println("Entry wrong...");
        }
        menu();
    }

    private void menu() {
        System.out.println(
                "\n\t\t| If you entry : |\n" +
                        "--------------------------------------------\n" +
                        " 1 - Read file json from resource\n" +
                        " 2 - Average flight way time between city 'Владивосток' and 'Тель-Авив'.\n" +
                        " 3 - Precentil flight way time between city 'Владивосток' and 'Тель-Авив'.\n" +
                        " 4 - Exit\n" +
                        "--------------------------------------------"
        );
    }

}
