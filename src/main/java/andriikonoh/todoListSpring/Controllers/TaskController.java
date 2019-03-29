package andriikonoh.todoListSpring.Controllers;

import andriikonoh.todoListSpring.Entity.Task;
import andriikonoh.todoListSpring.EntityDTO.TaskDTO;
import andriikonoh.todoListSpring.Repository.ProjectRepository;
import andriikonoh.todoListSpring.Repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TaskController {
    private final TaskRepository repository;
    private final ProjectRepository projectRepository;

    public TaskController(TaskRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("tasks")
    public ResponseEntity all() {
        List<TaskDTO> tasksDTO = repository.findAll()
                .stream()
                .map(task -> mapIntoDTO(task))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity getByProjectId(@PathVariable String projectId) {
        List<TaskDTO> tasksDTO = repository.findAll()
                .stream()
                .filter(task -> task.getProject().getId().toString().equals(projectId))
                .map(task -> mapIntoDTO(task))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity createTask(@RequestBody TaskDTO taskDTO) {
        Task taskAfterSave = repository.save(mapFromDTO(taskDTO));

        TaskDTO taskDTOAfterSave = mapIntoDTO(taskAfterSave);
        return new ResponseEntity<>(taskDTOAfterSave, HttpStatus.CREATED);
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity replaceTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        Task newTask = mapFromDTO(taskDTO);
        Task taskAfterSave = repository.findById(id)
                .map(task -> {
                    task.setName(newTask.getName());
                    task.setProject(newTask.getProject());
                    task.setDone(newTask.isDone());
                    return repository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return repository.save(newTask);
                });
        TaskDTO taskDTOAfterSave = mapIntoDTO(taskAfterSave);
        return new ResponseEntity<>(taskDTOAfterSave, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }

   private Task mapFromDTO(TaskDTO taskDTO) {
       Task newTask = new Task();
       newTask.setName(taskDTO.getName());
       newTask.setDone(taskDTO.isDone());
       newTask.setProject(projectRepository.getOne(Long.parseLong(taskDTO.getProjectId())));
       return newTask;
   }

   private TaskDTO mapIntoDTO(Task task) {
       TaskDTO taskDTO = new TaskDTO();
       taskDTO.setName(task.getName());
       taskDTO.setDone(task.isDone());
       taskDTO.setId(task.getId());
       taskDTO.setProjectId(task.getProject().getId().toString());
       return taskDTO;
   }
}
