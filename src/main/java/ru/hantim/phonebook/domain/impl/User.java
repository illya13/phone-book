package ru.hantim.phonebook.domain.impl;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.hantim.phonebook.domain.IUser;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User extends AbstractPersistable<Long> implements IUser {
    private static final long serialVersionUID = -2952735933715107252L;

    @Column(unique = true)
    private String login;

    private String phone;

    private User() {
        // left blank
    }

    private User(Long id) {
        setId(id);
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return super.isNew();
    }


    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class Builder {
        private User user;

        public Builder() {
        }

        public Builder newUser() {
            user = new User();
            return this;
        }

        public Builder newUser(Long id) {
            user = new User(id);
            return this;
        }

        public Builder setLogin(String login) {
            user.login = login;
            return this;
        }

        public Builder setPhone(String phone) {
            user.phone = phone;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
