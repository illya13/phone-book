package ru.hantim.phonebook.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ru.hantim.phonebook.domain.IUser;
import ru.hantim.phonebook.service.IUserAction;

import java.util.List;

@Controller
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private IUserAction userAction;

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestParam String login, @RequestParam String phone) {
        logger.info("createUser, login=" + login + ", phone=" + phone);
        return userAction.createUser(login, phone);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@PathVariable Long id, @RequestParam String login, @RequestParam String phone) {
        logger.info("updateUser, id=" + id +", login=" + login + ", phone=" + phone);
        userAction.updateUser(id, login, phone);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public IUser getUserById(@PathVariable Long id) {
        logger.info("getUserById, id=" + id);
        return userAction.getUserById(id);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public IUser getUserByLogin(@RequestParam String login) {
        logger.info("getUserByLogin, login=" + login);
        return userAction.getUserByLogin(login);
    }

    @RequestMapping(value = "/user/key", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IUser> findByKey(@RequestParam String key) {
        logger.info("findByKey, key=" + key);
        return userAction.findByKey(key);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IUser> getUsers() {
        logger.info("getUsers");
        return userAction.getAllUsers();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@PathVariable Long id) {
        logger.info("deleteUserById, id=" + id);
        userAction.deleteUser(id);
    }
}
