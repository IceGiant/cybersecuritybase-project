package sec.project.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "todoList_id", "user_id" })})
public class ReadAccess implements Serializable {

    private long id;
    private TodoList todoList;
    private User user;

    public ReadAccess() {
        super();
    }

    public ReadAccess(TodoList todoList, User user) {
        this();
        this.todoList = todoList;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "todoList_id", nullable = false)
    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
