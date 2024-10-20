package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.user.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;

    private UserController userController;
    @BeforeEach
    void init() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void whenGetRegistrationPageThenReturnRegistrationView() {
        var view = userController.getRegisterPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    void whenGetLoginPageThenReturnLoginView() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    void whenRegisterNewUserThenRedirectToFilms() {
        var user = new User("test1", "test1", "test1");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/films");
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenRegisterUserIsNotSuccessThenGetErrorPageWithMessage() {
        when(userService.save(any(User.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, new User());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Пользователь с такой почтой уже существует");
    }

    @Test
    void whenRequestLogoutThenClearSessionAndGetLoginPage() {
        var session = new MockHttpSession();
        var view = userController.logout(session);
        assertThat(view).isEqualTo("redirect:/users/login");
    }

    @Test
    void whenPostLoginUserIsSuccessThenGetRedirectToFilms() {
        var user = new User("test1", "test1", "test1");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var httpRequest = new MockHttpServletRequest();
        var view = userController.loginUser(model, user, httpRequest);
        var session = httpRequest.getSession();
        var actualUser = session.getAttribute("user");

        assertThat(view).isEqualTo("redirect:/films");
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenUserAlreadyExistThenGetRedirectToLoginWithMessage() {
        var user = new User("test1", "test1", "test1");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());
        var expectedMessage = "Почта или пароль введены неверно";

        var model = new ConcurrentModel();
        var httpRequest = new MockHttpServletRequest();
        var view = userController.loginUser(model, user, httpRequest);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}