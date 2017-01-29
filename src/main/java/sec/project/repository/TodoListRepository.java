package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    TodoList findOneByName(String name);
}
