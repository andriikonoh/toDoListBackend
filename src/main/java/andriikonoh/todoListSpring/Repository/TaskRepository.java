package andriikonoh.todoListSpring.Repository;

import andriikonoh.todoListSpring.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
