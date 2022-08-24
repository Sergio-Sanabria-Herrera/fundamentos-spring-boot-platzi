package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Log LOGGER = LogFactory.getLog(Application.class);

    private ComponentDependency componentDependency;
    private MyBean myBean;

    private MyBeanWithDependency myBeanWithDependency;
    private MyBeanWithProperties myBeanWithProperties;
    private UserPojo userPojo;
    private UserRepository userRepository;
    private UserService userservice;


    public Application(@Qualifier("componentTwoimplement") ComponentDependency componentDependency,
                       MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties
                               myBeanWithProperties, UserPojo userPojo, UserRepository userRepository,
                       UserService userService) {

        this.componentDependency = componentDependency;
        this.myBean = myBean;
        this.myBeanWithDependency = myBeanWithDependency;
        this.myBeanWithProperties = myBeanWithProperties;
        this.userPojo = userPojo;
        this.userRepository = userRepository;
        this.userservice = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //ejemplosAnteriores();
        saveUsersInDataBase();
        //getInformationJpqlFromUser();
        saveWithErrorTransactional();
    }

    private void saveWithErrorTransactional(){
        User test1 = new User("Test1Transactional1",
                "TestTransactional1@domain.com", LocalDate.now());

        User test2 = new User("Test2Transactional1",
                "Test2Transactional1@domain.com", LocalDate.now());

        User test3 = new User("Test3Transactional1",
                "TestTransactional1@domain.com", LocalDate.now());

        User test4 = new User("Test4Transactional1",
                "Test4Transactional1@domain.com", LocalDate.now());

        List<User> users = Arrays.asList(test1, test2, test3, test4);
        try {
            userservice.saveTransactional(users);
        }catch (Exception e){
            LOGGER.error("esta es una exeption dentro de del metodo transaccional " + e);
        }

        userservice.getAllUsers().stream()
                .forEach(user->
                        LOGGER.info("Este es el usuario dentro del metodo transaccional " + user));
    }

    private void getInformationJpqlFromUser() {
        /*LOGGER.info("Usuario con el metodo findByUserEmail" +
                userRepository.findByUserEmail("julie@domain.com")
                        .orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

        userRepository.findAndSort("user", Sort.by("id").descending())
                .stream()
                .forEach(user -> LOGGER.info("Usuarios con metodo sort " + user));

        userRepository.findByName("John")
                .stream()
                .forEach(user -> LOGGER.info("Usuario con query method " + user));

        LOGGER.info("Usuario con query method findByEmailAndName" + userRepository
                .findByEmailAndName("Daniela@domain.com", "Daniela")
                .orElseThrow(() -> new RuntimeException("usuario no encontrado")));

        userRepository.findByNameLike("%u%")
                .stream()
                .forEach(user -> LOGGER.info("Usuario findByNameLike " + user));

        userRepository.findByNameOrEmail("user10", null)
                .stream()
                .forEach(user -> LOGGER.info("Usuario findByNameOrEmail " + user));*/

        userRepository
                .findByBirthDateBetween(LocalDate.of(2021, 3, 1),
                LocalDate.of(2021, 4, 2))
                .stream()
                .forEach(user -> LOGGER.info("Usuarios con intervalos de fechas" + user));

        userRepository.findByNameContainingOrderByIdDesc("user")
                .stream()
                .forEach(user -> LOGGER.info("usuarios encontrado con like y ordenado " + user));

        LOGGER.info("El usuario a partir del named parameter es: " + userRepository
                        .getAllByBirthDateAndEmail(LocalDate.of(2021, 4, 21),
                        "Daniela@domain.com")
                .orElseThrow(()-> new RuntimeException
                        ("No se encontro el usuario apartir del named paramater")));

    }

    private void saveUsersInDataBase() {
        User user1 = new User("John", "john@domain.com", LocalDate.of
                (2022, 3, 20));

        User user2 = new User("Julie", "julie@domain.com", LocalDate.of
                (2022, 5, 21));

        User user3 = new User("Daniela", "Daniela@domain.com", LocalDate.of
                (2022, 4, 21));

        User user4 = new User("user4", "user4@domain.com", LocalDate.of
                (2022, 1, 4));

        User user5 = new User("user5", "user5@domain.com", LocalDate.of
                (2022, 6, 25));

        User user6 = new User("user6", "user6@domain.com", LocalDate.of
                (2022, 8, 24));

        User user7 = new User("user7", "user7@domain.com", LocalDate.of
                (2022, 6, 16));

        User user8 = new User("user8", "user8@domain.com", LocalDate.of
                (2022, 9, 2));

        User user9 = new User("user9", "user9@domain.com", LocalDate.of
                (2022, 7, 1));

        User user10 = new User("user10", "user10@domain.com", LocalDate.of
                (2022, 10, 18));

        User user11 = new User("user11", "user11@domain.com", LocalDate.of
                (2022, 11, 17));

        User user12 = new User("user12", "user12@domain.com", LocalDate.of
                (2022, 2, 8));

        List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12);
        userRepository.saveAll(list);
    }

    private void ejemplosAnteriores() {
        componentDependency.saludar();
        myBean.print();
        myBeanWithDependency.printWithDependency();
        System.out.println(myBeanWithProperties.function());
        System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
        try {
            //error
            int value = 10 / 0;
            LOGGER.info("mi valor" + value);
        } catch (Exception e) {
            LOGGER.error("esto es un error al dividir por cero " + e.getMessage());

        }
    }
}
