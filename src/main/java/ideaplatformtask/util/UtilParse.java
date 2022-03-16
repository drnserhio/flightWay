package ideaplatformtask.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ideaplatformtask.model.Resultes;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UtilParse {

    public Resultes jsonParseToTicket() throws IOException {
        File file = new File("src/main/resources/tickets.json");
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        System.out.println("File .json read successful.");
        return gson.fromJson(reader, Resultes.class);

    }
}
