package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sec.project.domain.*;
import sec.project.dao.DAO;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    DAO dao;

    @RequestMapping("/")
    public String defaultMapping(Model model, Principal auth) {
        User user = dao.findUserByLoginname(auth.getName());
        TodoList todoList = dao.getLatestTodoListForUser(user);

        model.addAttribute("todoList", todoList);
        model.addAttribute("categories", dao.findAllCategories());
        model.addAttribute("todos", dao.findRecentTodos(todoList));
        return "index";
    }

    /** Adding or modifying an todo. */
    @Transactional
    @PostMapping("/postTodo")
    public String postTodo(
            @RequestParam int day,
            @RequestParam int month,
            @RequestParam long todoListId,
            @RequestParam String category,
            @RequestParam String note,
            @RequestParam String previousVersion,
            Principal auth
    ) {
        User user = dao.findUserByLoginname(auth.getName());
        TodoList todoList = dao.findTodoListById(todoListId);
        if (dao.hasWriteAccess(user, todoList)) {
            Todo current = dao.createTodo(day, month, todoList, category, note, user);
            if (!previousVersion.isEmpty()) {
                dao.updateVersionHistory(current, previousVersion);
            }
        }
        return "redirect:/";
    }

    @DeleteMapping("/deleteTodo")
    public String deleteTodo(
            @RequestParam long id,
            Principal auth
    ) {
        User user = dao.findUserByLoginname(auth.getName());
        Todo todo = dao.findTodoById(id);
        TodoList todoList = todo.getTodoList();
        if (dao.hasWriteAccess(user, todoList)) {
            dao.deleteTodo(todo);
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
