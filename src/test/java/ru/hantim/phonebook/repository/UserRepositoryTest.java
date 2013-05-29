package ru.hantim.phonebook.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.hantim.phonebook.domain.impl.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User.Builder builder;

    @Before
    public void before() {
        builder = new User.Builder();
    }

    @After
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    public void testInsert() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void testInsertDuplicateLoginThrowsException() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        user = createTestUser();
        userRepository.save(user);
    }

    @Test
    public void testUpdate() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        user.setLogin("loginUpdated");
        user = userRepository.save(user);
        assertEquals("loginUpdated", user.getLogin());
    }

    @Test
    public void testFindById() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        User found = userRepository.findOne(user.getId());
        assertTestUser(found);
    }

    @Test
    public void testFindByLogin() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        User found = userRepository.findByLogin(user.getLogin());
        assertTestUser(found);
    }

    @Test
    public void testFindAllByLogin() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        user = createTestUser();
        user.setLogin("login1");
        userRepository.save(user);

        List<User> found = userRepository.findAllByLogin("log");
        assertEquals(2, found.size());
    }

    @Test
    public void testFindAllByPhone() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        user = createTestUser();
        user.setLogin("login1");
        userRepository.save(user);

        List<User> found = userRepository.findAllByPhone("hon");
        assertEquals(2, found.size());
    }

    @Test
    public void testDelete() {
        User user = createTestUser();
        user = userRepository.save(user);
        assertTestUser(user);

        userRepository.delete(user.getId());

        User found = userRepository.findByLogin(user.getLogin());
        assertNull(found);
    }

    private User createTestUser() {
        return builder.newUser().
                setLogin("login").
                setPhone("phone").
                build();
    }

    public static void assertTestUser(User user) {
        assertNotNull(user.getId());
        assertEquals("login", user.getLogin());
        assertEquals("phone", user.getPhone());
    }
}
