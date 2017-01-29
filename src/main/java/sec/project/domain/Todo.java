package sec.project.domain;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Todo {

    private long id;
    private boolean current;
    private int day;
    private int month;
    private TodoList todoList;
    private Category lowestSubCategory;
    private String note;
    private User user;
    private Long previousVersionId;
    private Long nextVersionId;
    private long timeAdded;

    public Todo() {
        super();
    }

    public Todo(int day, int month, TodoList todoList, Category lowestSubCategory, String note, User user) {
        this();
        this.current = true;
        this.day = day;
        this.month = month;
        this.todoList = todoList;
        this.lowestSubCategory = lowestSubCategory;
        this.note = note;
        this.user = user;
        this.timeAdded = DateTime.now().getMillis();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPreviousVersionId() {
        return previousVersionId;
    }

    public void setPreviousVersionId(Long previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    public Long getNextVersionId() {
        return nextVersionId;
    }

    public void setNextVersionId(Long nextVersionId) {
        this.nextVersionId = nextVersionId;
    }

    @ManyToOne
    @JoinColumn(name = "todoList_id", nullable = false)
    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category getLowestSubCategory() {
        return lowestSubCategory;
    }

    public void setLowestSubCategory(Category lowestSubCategory) {
        this.lowestSubCategory = lowestSubCategory;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
