package ru.hantim.phonebook.service;

import ru.hantim.phonebook.domain.IUser;

import java.util.List;

public interface IUserAction {
    public long createUser(String login, String phone);
    public void updateUser(long id, String login, String phone);
    public void deleteUser(long id);

    public List<IUser> getAllUsers();
    public IUser getUserByLogin(String login);
    public IUser getUserById(long id);

    /**
     * This method returns list of ${@link IUser} by appropriate search key.
     * The method should search by login, phone and partial matches.
     * @param key search key
     * @return list of ${@link IUser}
     */
    public List<IUser> findByKey(String key);
}
