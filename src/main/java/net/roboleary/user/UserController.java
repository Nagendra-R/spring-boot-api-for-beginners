package net.roboleary.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/")
public class UserController {
    List<User> users = new ArrayList<User>();

    //We create data to use in our Controller. You can learn how to get the data from a database later, which is
    //usually the case!
    public UserController() {
        users.add(new User(1, "Rob OLeary", 21));
        users.add(new User(2, "Angela Merkel", 20));
        users.add(new User(3, "Tamer", 20));
        users.add(new User(4, "Tamer", 20));
    }

    //GET for http://localhost:8080/users/getAllUsers . Returns all users. Alternatively, you can use @GetMapping
    //@RequestMapping(method=GET, value="/users")
    //specify endpoints clearly with clear names
    @GetMapping("getAllUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    //GET for http://localhost:8080/users/id .Returns all users with the id provided
    @GetMapping("getUserById/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable("id") long id) {

        User userFound = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(new User(-1, "User Not Found", -1));

        if (userFound.getId() == -1) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userFound);

//         return ResponseEntity.
//        User userFound = null;
//
//        for(User user: users){
//            if(user.getId() == id){
//                userFound = user;
//                break;
//            }
//        }
//
//        if (userFound == null) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//
//        //found
//        return new ResponseEntity(userFound, HttpStatus.OK);
    }

    //GET for http://localhost:8080/users?name=rob oleary .Returns all users with the name provided

    @GetMapping("getUsersByName/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable("name") String name) {

        List<User> filteredUsers = users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (filteredUsers.isEmpty()) {
            return ResponseEntity.ok("User not found with this name ::" + name);
        }

        return ResponseEntity.ok(filteredUsers);

////        System.out.println(filteredUsers);
//
//        for(User user: users){
//            if(user.getName().equalsIgnoreCase(name)) {
//                filteredUsers.add(user);
//            }
//        }
//
//        if (filteredUsers.isEmpty() == true) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//
//        //found
//        return new ResponseEntity(filteredUsers, HttpStatus.OK);
    }

    //GET for http://localhost:8080/users?age=21 .Returns all users with the age provided
    @GetMapping("getUsersByAge/{age}")
    public ResponseEntity<?> getUsersByAge(@PathVariable("age") int age) {

        List<User> filteredUsers = users.stream()
                                        .filter(user -> user.getAge() == age)
                                        .collect(Collectors.toList());

        if (filteredUsers.isEmpty()) {
            return ResponseEntity.ok("User not found with this age :: " + age);
        }

        return ResponseEntity.ok(filteredUsers);

//        for (User user : users) {
//            if (user.getAge() == age) {
//                filteredUsers.add(user);
//            }
//        }
//
//        if (filteredUsers.isEmpty() == true) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//
//        //found
//        return new ResponseEntity(filteredUsers, HttpStatus.OK);
    }

    @PostMapping("addUser")
    public ResponseEntity<?> add(@RequestBody User user) {
        users.add(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("addOrUpdate")
    public ResponseEntity<?> addOrUpdate(@RequestBody User user) {

        boolean isUser=false;
        for(int i=0;i<users.size();i++){
            if (users.get(i).getName().equalsIgnoreCase(user.getName())){
                isUser = true;
                User updateUser = new User();
                updateUser.setId(user.getId());
                updateUser.setName(user.getName());
                updateUser.setAge(user.getAge());
                users.add(updateUser);
                users.remove(users.get(i));
            }

        }

        if(!isUser){
            users.add(user);
        }

        return ResponseEntity.ok(users);


//        =users.contains(user);
//        if(!isUser){
//            users.add(user);
//        }else{
//            for (User u:users){
//
//            }
//        }


//        ResponseEntity response;
//
//        if (users.contains(u)) {
//            //update by setting it at the specified position
//            int index = users.indexOf(u);
//            users.set(index, u);
//            response = new ResponseEntity(u, HttpStatus.OK);
//        } else {
//            users.add(u);
//            response = new ResponseEntity(u, HttpStatus.CREATED);
//        }
//
//        return response;
    }


    @DeleteMapping(value = "deleteUserById/{id}")
    public boolean delete(@PathVariable("id") long id) {

       return users.removeIf(user -> user.getId() == id);
//
//        boolean found = false;
//
//        for (User user : users) {
//            if (user.getId() == id) {
//                users.remove(user);
//                found = true;
//                break;
//            }
//        }
//
//        if (found == false) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//
//        //found
//        return new ResponseEntity(HttpStatus.OK);
    }
}

