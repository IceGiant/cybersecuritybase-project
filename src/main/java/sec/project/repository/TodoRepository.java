package sec.project.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sec.project.domain.Todo;
import sec.project.domain.TodoList;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Returns latest 10 current Todos from given TodoList. 
    List<Todo> findFirst10ByTodoListAndCurrentOrderByTimeAddedDesc(TodoList todoList, Boolean current);

    // Returns all todos from given list 
    List<Todo> findByTodoList(TodoList todoList);
}
