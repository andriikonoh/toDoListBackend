package andriikonoh.todoListSpring.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {

    private @Id @GeneratedValue Long id;
    private String name;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> projectTasks = new ArrayList<>();
}
