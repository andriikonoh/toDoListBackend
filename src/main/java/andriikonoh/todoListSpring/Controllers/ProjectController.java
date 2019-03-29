package andriikonoh.todoListSpring.Controllers;

import andriikonoh.todoListSpring.Entity.Project;
import andriikonoh.todoListSpring.EntityDTO.ProjectDTO;
import andriikonoh.todoListSpring.Exceptions.ProjectNotFoundException;
import andriikonoh.todoListSpring.Repository.ProjectRepository;
import andriikonoh.todoListSpring.Repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ProjectController {

    private final ProjectRepository repository;
    private final TaskRepository taskRepository;

    public ProjectController(ProjectRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("projects")
    public ResponseEntity getAll() {
        List<ProjectDTO> projectsDTO = repository.findAll()
                .stream()
                .map(this::mapIntoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(projectsDTO, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        Project project = repository.findById(id).orElseThrow(()->new ProjectNotFoundException(id));
        ProjectDTO projectDTO = mapIntoDTO(project);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping("/projects")
    public ResponseEntity newProject(@RequestBody ProjectDTO projectDTO) {
        Project projectAfterSave = repository.save(mapFromDTO(projectDTO));

        ProjectDTO projectDTOAfterSave = mapIntoDTO(projectAfterSave);
        return new ResponseEntity<>(projectDTOAfterSave, HttpStatus.CREATED);
    }

    @PutMapping("projects/{id}")
    public ResponseEntity replaceProject(@RequestBody ProjectDTO projectDTO, @PathVariable Long id) {
        Project newProject = mapFromDTO(projectDTO);
        Project projectAfterSave = repository.findById(id)
                .map(project -> {
                    project.setName(newProject.getName());
                    return repository.save(project);
                })
                .orElseGet(()-> {
                    newProject.setId(id);
                    return repository.save(newProject);
                });
        ProjectDTO projectDTOAfterSave = mapIntoDTO(projectAfterSave);
        return new ResponseEntity<>(projectDTOAfterSave, HttpStatus.OK);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private Project mapFromDTO(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        return project;
    }

    private ProjectDTO mapIntoDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        return projectDTO;
    }
}
