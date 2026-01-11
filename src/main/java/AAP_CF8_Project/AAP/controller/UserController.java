package AAP_CF8_Project.AAP.controller;


import AAP_CF8_Project.AAP.domain.User;
import AAP_CF8_Project.AAP.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/*
User Controller using CRUD public classes
to create, save and delete a user from the DB of Users
 */
@Controller()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    //List with the users (R= read the db)
    @GetMapping()
    public String users(Model model){
        model.addAttribute("users", userService.findAll());

        //CKECK IF THEY ARE SAVED
        Iterable<User> users = userService.findAll();

        System.out.println("USERS FOUND:");
        users.forEach(System.out::println);

        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }


    @PostMapping()
    public String saveUser(@ModelAttribute("user") User formUser){
        LocalDateTime now = LocalDateTime.now();

        if (formUser.getId() != 0 && userService.existsById(formUser.getId())) {
            System.out.println("=== Edit POST /users HIT ===");
            // Update existing user
            User existingUser = userService.findById(formUser.getId());
            existingUser.setUsername(formUser.getUsername());
            existingUser.setEmail(formUser.getEmail());
            existingUser.setPasswordHash(formUser.getPasswordHash());
            existingUser.setBioText(formUser.getBioText());
            existingUser.setLastLogin(now); // optional
            userService.save(existingUser);
        } else {
            System.out.println("=== Create POST /users HIT ===");

            // New user
            formUser.setCreatedDate(now);
            formUser.setLastLogin(now);
            userService.save(formUser);
        }

        return "redirect:/users";
    }

    // Edit form
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/users"; // optional: user not found
        }
        model.addAttribute("user", user); // Thymeleaf will prefill fields
        return "user_form";
    }


    //Delete User
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/users";
    }




}
