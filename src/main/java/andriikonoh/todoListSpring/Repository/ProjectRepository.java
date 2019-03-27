package andriikonoh.todoListSpring.Repository;

import andriikonoh.todoListSpring.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
