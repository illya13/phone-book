package ru.hantim.phonebook.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.hantim.phonebook.domain.impl.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByLogin(String login);

    @Query("select u from User u where u.login like %?1%")
    public List<User> findAllByLogin(String login);

    @Query("select u from User u where u.phone like %?1%")
    public List<User> findAllByPhone(String phone);
}
