package sec.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sec.project.domain.TodoList;
import sec.project.domain.User;
import sec.project.repository.TodoListRepository;
import sec.project.repository.UserRepository;

import javax.transaction.Transactional;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
}
