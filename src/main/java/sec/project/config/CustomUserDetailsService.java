package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.*;
import sec.project.dao.DAO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DAO dao;

    @PostConstruct
    public void init() {
        // Populate database 

        User guest = dao.createUser("guest", "g");
        TodoList list = dao.createTodoList("guest's todos", guest);
        dao.createTodo(1, 10, list, "food", "get chicken", guest);
        dao.createTodo(1, 12, list, "food", "get brocolli", guest);
        dao.createTodo(1, 30, list, "entertainment", "Rewatch Die Hard", guest);

        User user2 = dao.createUser("user2", "longerpassword");
        TodoList list2 = dao.createTodoList("user2's todos", user2);
        dao.createTodo(1, 10, list2, "food", "get chips", user2);
        dao.createTodo(1, 11, list2, "food", "get lettuce", user2);
        dao.createTodo(1, 12, list2, "food", "get ramen", user2);
        dao.createTodo(1, 12, list2, "food", "get eggs", user2);
        dao.createTodo(2, 16, list2, "resolution", "get a gym membership", user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findUserByLoginname(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getLoginname(),
                user.getEncodedPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

}
