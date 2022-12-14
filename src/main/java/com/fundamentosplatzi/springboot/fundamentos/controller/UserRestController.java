package com.fundamentosplatzi.springboot.fundamentos.controller;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.CreateUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.DeleteUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.UpdateUser;
import com.fundamentosplatzi.springboot.fundamentos.dto.UserDto;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {
    //CREATE, GET, DELETE, UPDATE
    private final GetUser getUser;
    private final CreateUser createUser;
    private final DeleteUser deleteUser;
    private UpdateUser updateUser;
    private final UserRepository userRepository;

    public UserRestController(GetUser getUser, CreateUser createUser, DeleteUser deleteUser,
                              UpdateUser updateUse, UserRepository userRepository) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.userRepository = userRepository;

    }

    @GetMapping("/")
    @ApiOperation("Get user rest")
    @ApiResponse(code = 200, message =  "ok")
    ResponseEntity<List<User>> get() {
        return ResponseEntity.ok(getUser.getAll());

    }

    @PostMapping("/")
    ResponseEntity<User> newUser(@RequestBody UserDto newUser) {
        return new ResponseEntity<>(createUser.save(new User(newUser)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user rest")
    @ApiResponses({
            @ApiResponse(code = 204, message = "ok"),
            @ApiResponse(code = 404, message = "user rest no content"),
    })
    @ApiParam(value = "the id of the user rest", required = true, example = "7")
    ResponseEntity deleteUser(@PathVariable Long id) {
                deleteUser.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> replaceUser(@RequestBody UserDto newUser, @PathVariable Long id) {
        return new ResponseEntity<>(updateUser.update(new User(newUser), id), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    List<User> getUserPageable(@RequestParam int page, @RequestParam int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();

    }

}
