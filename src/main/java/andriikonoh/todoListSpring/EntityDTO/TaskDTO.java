package andriikonoh.todoListSpring.EntityDTO;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private boolean done;
    private String projectId;
}
