package ru.hantim.phonebook.rest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.hantim.phonebook.domain.IUser;
import ru.hantim.phonebook.domain.impl.User;
import ru.hantim.phonebook.service.impl.UserAction;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserAction userAction;

    @InjectMocks
    private UserController userConstroller;

    private IUser expectedUser;
    private List<IUser> expectedUsers;

    @Before
    public void before() {
        User.Builder builder = new User.Builder();
        expectedUser = builder.newUser(13l).
                setLogin("login").setPhone("phone").
                build();

        expectedUsers = new LinkedList<IUser>();
        expectedUsers.add(expectedUser);
    }

    @Test
    public void getUserByIdOkTest() {
        given(userAction.getUserById(expectedUser.getId())).willReturn(expectedUser);

        // when
        IUser actualUser = userConstroller.getUserById(expectedUser.getId());

        // then
        assertThat(actualUser, isUserEquals(expectedUser));
    }

    @Test
    public void createUserTest() {
        given(userAction.createUser(expectedUser.getLogin(), expectedUser.getPhone())).willReturn(expectedUser.getId());

        // when
        Long actualId = userConstroller.createUser(expectedUser.getLogin(), expectedUser.getPhone());

        // then
        assertThat(actualId, is(expectedUser.getId()));
    }

    @Test
    public void deleteUserTest() {
        willDoNothing().given(userAction).deleteUser(expectedUser.getId());

        // when
        userConstroller.deleteUser(expectedUser.getId());

        // then no exception
    }

    @Test
    public void getUsersTest() {
        given(userAction.getAllUsers()).willReturn(expectedUsers);

        // when
        List<IUser> actualUsers = userConstroller.getUsers();

        // then
        assertThat(actualUsers.size(), is(1));
        assertThat(actualUsers.get(0), isUserEquals(expectedUser));
    }

    public static Matcher<IUser> isUserEquals(final IUser excepted) {
        return new TypeSafeMatcher<IUser>() {
            @Override
            protected boolean matchesSafely(IUser user) {
                return user.getId().equals(excepted.getId()) &&
                        user.getLogin().equals(excepted.getLogin()) &&
                        user.getPhone().equals(excepted.getPhone());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(excepted.toString());
            }
        };
    }

}
