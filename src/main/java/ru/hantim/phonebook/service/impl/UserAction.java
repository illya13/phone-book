package ru.hantim.phonebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.hantim.phonebook.domain.IUser;
import ru.hantim.phonebook.domain.impl.User;
import ru.hantim.phonebook.repository.UserRepository;
import ru.hantim.phonebook.service.IUserAction;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class UserAction implements IUserAction {
    @Autowired
    UserRepository repository;

    User.Builder builder;

    public UserAction() {
        builder = new User.Builder();
    }

    @Override
    public long createUser(String login, String phone) {
        User user = builder.newUser().
                setLogin(login).setPhone(phone).
                build();
        return repository.save(user).getId();
    }

    @Override
    public void updateUser(long id, String login, String phone) {
        User user = builder.newUser(id).
                setLogin(login).setPhone(phone).
                build();
        repository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        repository.delete(id);
    }

    @Override
    public List<IUser> getAllUsers() {
        List<IUser> list = new LinkedList<IUser>();
        for (User user:repository.findAll())
            list.add(user);
        return list;
    }

    @Override
    public IUser getUserByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public IUser getUserById(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<IUser> findByKey(String key) {
        List<IUser> list = new LinkedList<IUser>();
        for (User user:repository.findAllByLogin(key))
            list.add(user);
        for (User user:repository.findAllByPhone(key))
            list.add(user);
        return list;
    }
}
