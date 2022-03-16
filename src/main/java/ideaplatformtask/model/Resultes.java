package ideaplatformtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Resultes {
    private List<Ticket> tickets;
}
