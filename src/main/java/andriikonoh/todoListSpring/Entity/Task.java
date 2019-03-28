package andriikonoh.todoListSpring.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Task {

    private @Id @GeneratedValue Long id;
    private String name;
    private boolean done;
    private @ManyToOne Project project;


}
