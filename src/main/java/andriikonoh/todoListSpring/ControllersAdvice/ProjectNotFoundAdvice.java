package andriikonoh.todoListSpring.ControllersAdvice;

import andriikonoh.todoListSpring.Exceptions.ProjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProjectNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String projectNotFoundHandler(ProjectNotFoundException ex) {
        return ex.getMessage();
    }
}
