package andriikonoh.todoListSpring.Controllers;

import andriikonoh.todoListSpring.Entity.Project;
import andriikonoh.todoListSpring.Exceptions.ProjectNotFoundException;
import andriikonoh.todoListSpring.Repository.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProjectController {

    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping("projects")
    public List<Project> getAll() {
        return repository.findAll();
    }

    @GetMapping("/projects/{id}")
    public Project getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
    }

    @PostMapping("/projects")
    public Project newProject(@RequestBody Project newProject) {
        return repository.save(newProject);
    }

    @PutMapping("projects/{id}")
    public Project replaceProject(@RequestBody Project newProject, @PathVariable Long id) {
        return repository.findById(id)
                .map(project -> {
                    project.setName(newProject.getName());
                    project.setId(newProject.getId());
                    return repository.save(project);
                })
                .orElseGet(()-> {
                    newProject.setId(id);
                    return repository.save(newProject);
                });
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
