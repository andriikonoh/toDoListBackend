package andriikonoh.todoListSpring.Controllers;

import andriikonoh.todoListSpring.Entity.Task;
import andriikonoh.todoListSpring.Repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getByProjectId(@PathVariable String projectId) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task newTask) {
        return repository.save(newTask);
    }

    @PutMapping("tasks/{id}")
    public Task replaceTask(@RequestBody Task newTask, @PathVariable Long id) {
        return repository.findById(id)
                .map(task -> {
                    task.setName(newTask.getName());
                    task.setProjectId(newTask.getProjectId());
                    task.setId(newTask.getId());
                    task.setDone(newTask.isDone());
                    return repository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return repository.save(newTask);
                });
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
