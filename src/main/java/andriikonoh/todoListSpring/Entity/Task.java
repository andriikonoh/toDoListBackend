package andriikonoh.todoListSpring.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Task {

    private @Id @GeneratedValue Long id;
    private String name;
    private String projectId;
    private boolean done;

    public Task() {}

    public Task(String name, String projectId, boolean done) {
        this.name = name;
        this.projectId = projectId;
        this.done = done;
    }
}
