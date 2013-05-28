package ru.hantim.phonebook.domain;

public interface IUser {
    public int getId();
    public String getLogin();
    public String getPhone();
    public void setId(int id);
    public void setLogin(String login);
    public void setPhone(String phone);
}
