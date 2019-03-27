package andriikonoh.todoListSpring.Exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("Could not find project " + id);
    }
}
