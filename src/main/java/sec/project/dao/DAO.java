package sec.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import sec.project.domain.*;
import sec.project.repository.*;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class DAO {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ReadAccessRepository readAccessRepository;

    @Autowired
    WriteAccessRepository writeAccessRepository;

    public User findUserByLoginname(String loginname) {
        return userRepository.findOneByLoginname(loginname);
    }

    public TodoList findTodoListById(Long todoListId) {
        return todoListRepository.findOne(todoListId);
    }

    public Todo findTodoById(Long todoListId) {
        return todoRepository.findOne(todoListId);
    }

    public boolean hasWriteAccess(User user, TodoList todoList) {
        WriteAccess writeAccess = writeAccessRepository.findOneByTodoListAndUser(todoList, user);
        return (writeAccess != null);
    }

    public User createUser(String userName, String clearTextPassword) {
        User user = new User(userName, encode(clearTextPassword));
        userRepository.save(user);
        return user;
    }

    public Category createCategory(String categoryName) {
        Category category = new Category(categoryName);
        categoryRepository.save(category);
        return category;
    }

    //Gets todolist using a couple potential fallbacks
    @Transactional
    public TodoList getLatestTodoListForUser(User user) {
        TodoList latest = user.getLatestRead();
        if (latest == null) {
            // Assign any accessible list as latest
            for (ReadAccess r : user.getReadAccessSet()) {
                latest = r.getTodoList();
                setTodoListAsLatestForUser(latest, user);
                break;
            }
        }
        if (latest == null) {
            // If user has deleted all their lists
            latest = createTodoList("New list", user);
            setTodoListAsLatestForUser(latest, user);
        }
        return latest;
    }

    public Category getCategoryByName(String categoryName) {
        Category category = categoryRepository.findOneByName(categoryName);
        if (category == null) {
            category = new Category(categoryName);
            categoryRepository.save(category);
        }
        return category;
    }

    public TodoList createTodoList(String todoListName, User user) {
        TodoList todoList = new TodoList(todoListName, user);
        todoListRepository.save(todoList);
        readAccessRepository.save(new ReadAccess(todoList, user));
        writeAccessRepository.save(new WriteAccess(todoList, user));
        return todoList;
    }
    private void setTodoListAsLatestForUser(TodoList todoList, User user) {
        user.setLatestRead(todoList);
        userRepository.save(user);
    }

    private String encode(String plaintextPassword) {
        return BCrypt.hashpw(plaintextPassword, BCrypt.gensalt());
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Todo> findRecentTodos(TodoList todoList) {
        return todoRepository.findFirst10ByTodoListAndCurrentOrderByTimeAddedDesc(todoList, true);
    }

    public Todo createTodo(int day, int month, TodoList todoList, String category, String note, User user) {
        Category lowestSubCategory = getCategoryByName(category);
        Todo todo = new Todo(day, month, todoList, lowestSubCategory, note, user);
        todoRepository.save(todo);
        return todo;
    }

    //Activated when todo is changed
    @Transactional
    public void updateVersionHistory(Todo current, String previousVersion) {
        Long prevId = Long.parseLong(previousVersion);
        Todo previous = todoRepository.findOne(prevId);
        previous.setCurrent(false);
        previous.setNextVersionId(current.getId());
        todoRepository.save(previous);
        current.setPreviousVersionId(prevId);
        todoRepository.save(current);
    }

    public void deleteTodo(Todo todo) {
        todo.setCurrent(false);
        todoRepository.save(todo);
    }
}
