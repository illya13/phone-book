package ru.hantim.phonebook.service.impl;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.hantim.phonebook.domain.IUser;
import ru.hantim.phonebook.domain.impl.User;
import ru.hantim.phonebook.repository.UserRepository;
import ru.hantim.phonebook.repository.UserRepositoryTest;
import ru.hantim.phonebook.service.IUserAction;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserActionTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserAction userAction;

    @After
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testCreateUserDuplicateLoginThrowsException() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        userAction.createUser("login", "phone");
    }

    @Test
    public void testUpdateUser() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);
        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        userAction.updateUser(id, "loginUpdated", "phone");

        user = userRepository.findOne(id);
        assertEquals("loginUpdated", user.getLogin());
    }

    @Test
    public void testGetUserById() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        IUser found = userAction.getUserById(id);
        assertTestUser(found);
    }

    @Test
    public void testGetUserByLogin() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        IUser found = userAction.getUserByLogin(user.getLogin());
        assertTestUser(found);
    }

    @Test
    public void testGetAllUsers() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        id = userAction.createUser("login1", "phone");
        assertNotNull(id);

        List<IUser> found = userAction.getAllUsers();
        assertEquals(2, found.size());
    }

    @Test
    public void testFindByKey() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        id = userAction.createUser("login1", "phone");
        assertNotNull(id);

        List<IUser> found = userAction.findByKey("log");
        assertEquals(2, found.size());

        found = userAction.findByKey("hon");
        assertEquals(2, found.size());
    }

    @Test
    public void testDeleteUser() {
        Long id = userAction.createUser("login", "phone");
        assertNotNull(id);

        User user = userRepository.findOne(id);
        UserRepositoryTest.assertTestUser(user);

        userAction.deleteUser(id);

        User found = userRepository.findOne(id);
        assertNull(found);
    }

    public static void assertTestUser(IUser user) {
        assertNotNull(user.getId());
        assertEquals("login", user.getLogin());
        assertEquals("phone", user.getPhone());
    }
}
