package ru.hantim.phonebook.rest.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import ru.hantim.phonebook.domain.IUser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerITest extends BaseControllerITest {
    @Autowired
    RestTemplate restTemplate;

    @Test()
    public void testGetRestEndpoint() throws Exception {
        assertEquals("http://localhost:7900/phonebook/rest/user/13", getRestEndpoint("user", "13"));
    }

    @Test
    public void testCreateUser() throws Exception {
        Long response = restTemplate.postForObject(
                getRestEndpoint("user")+"?login={login}&phone={phone}",
                null, Long.class,
                expectedUser.getLogin(), expectedUser.getPhone());
        assertNotNull(response);

        restTemplate.delete(getRestEndpoint("user", response.toString()));
    }

    @Test()
    public void testGetUser() throws Exception {
        Long id = restTemplate.postForObject(
                getRestEndpoint("user")+"?login={login}&phone={phone}",
                null, Long.class,
                expectedUser.getLogin(), expectedUser.getPhone());

        IUser response = restTemplate.getForObject(getRestEndpoint("user", id.toString()), IUser.class);
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(expectedUser.getLogin(), response.getLogin());
        assertEquals(expectedUser.getPhone(), response.getPhone());

        restTemplate.delete(getRestEndpoint("user", id.toString()));
    }

    @Test()
    public void testGetUsers() throws Exception {
        Long id = restTemplate.postForObject(
                getRestEndpoint("user")+"?login={login}&phone={phone}",
                null, Long.class,
                expectedUser.getLogin(), expectedUser.getPhone());

        IUser[] response = restTemplate.getForObject(getRestEndpoint("user"),
                IUser[].class);
        assertNotNull(response);
        assertEquals(1, response.length);
        assertEquals(id, response[0].getId());
        assertEquals(expectedUser.getLogin(), response[0].getLogin());
        assertEquals(expectedUser.getPhone(), response[0].getPhone());

        restTemplate.delete(getRestEndpoint("user", id.toString()));
    }

    @Test()
    public void testDeleteUser() throws Exception {
        Long id = restTemplate.postForObject(
                getRestEndpoint("user")+"?login={login}&phone={phone}",
                null, Long.class,
                expectedUser.getLogin(), expectedUser.getPhone());

        restTemplate.delete(getRestEndpoint("user", id.toString()));
    }
}
