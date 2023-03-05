package com.springboot3.sample.rest.controller.user;

import com.springboot3.sample.rest.entity.User;
import com.springboot3.sample.rest.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    private static AtomicLong userIdGenerator = new AtomicLong();
    private static Map<Long, User> userStore = new ConcurrentHashMap<>();
    private static UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createUser(HttpServletRequest request,
                                     @Valid @RequestBody UserCreateRequest userCreateRequest) {

        User user = userMapper.userCreateModelToUser(userCreateRequest);
        user.setId(userIdGenerator.incrementAndGet());
        user.setCreationTime(LocalDateTime.now());
        userStore.put(user.getId(), user);

        String newUserUri = request.getRequestURI() + "/" + user.getId();
        return ResponseEntity.created(URI.create(newUserUri)).build();
    }

    @GetMapping(value = "/{user-id}",  produces = "application/json")
    public ResponseEntity fetchUser(@PathVariable("user-id") long userId) {
        User user = userStore.get(userId);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                userMapper.userToFetchUserResponse(user));
    }
}
