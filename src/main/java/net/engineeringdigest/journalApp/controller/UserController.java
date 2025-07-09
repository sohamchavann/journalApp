package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> list =  userService.getAll();
        if(list != null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id){
        Optional<User> user =  userService.findbyId(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        if(!userService.checkId(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.deletebyId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable String userName){
        User userInDB = userService.findByUserName(userName);
        if(userInDB != null){
            userInDB.setUserName(newUser.getUserName());
            userInDB.setPassword(newUser.getPassword());
            userService.saveEntry(userInDB);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
