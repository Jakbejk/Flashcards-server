package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.controller;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashcard/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws AuthenticationException {
        userService.register(token);
    }
}
