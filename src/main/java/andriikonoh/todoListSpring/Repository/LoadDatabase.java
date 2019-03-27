package andriikonoh.todoListSpring.Repository;

import andriikonoh.todoListSpring.Entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    public CommandLineRunner initDatabase(ProjectRepository repository) {
        return args -> {
            repository.save(new Project("Project1"));
        };
    }
}
