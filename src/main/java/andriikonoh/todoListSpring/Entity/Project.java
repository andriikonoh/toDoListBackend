package andriikonoh.todoListSpring.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Project {

    private @Id @GeneratedValue Long id;
    private String name;

    public Project() {}

    public Project(String name) {
        this.name = name;
    }
}
