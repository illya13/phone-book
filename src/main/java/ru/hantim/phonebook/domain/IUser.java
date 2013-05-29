package ru.hantim.phonebook.domain;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import ru.hantim.phonebook.domain.impl.User;

@JsonDeserialize(as=User.class)
public interface IUser {
    public Long getId();
    // public void setId(Long id);

    public String getLogin();
    public void setLogin(String login);

    public String getPhone();
    public void setPhone(String phone);
}
