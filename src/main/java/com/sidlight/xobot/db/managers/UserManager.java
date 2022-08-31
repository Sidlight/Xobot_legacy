package com.sidlight.xobot.db.managers;

import com.sidlight.xobot.core.Role;
import com.sidlight.xobot.core.StateRegister;
import com.sidlight.xobot.core.UserIdentifier;
import com.sidlight.xobot.db.BotDataException;
import com.sidlight.xobot.db.domainobject.User;
import com.sidlight.xobot.db.repos.UserRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories
public class UserManager implements InitializingBean {

    private UserRepo repo;

    @Autowired
    public void setUserRepo(UserRepo repo) {
        this.repo = repo;
    }

    private static UserManager instance;

    public static UserManager get() {
        return instance;
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public User getUserFromUserIdentifier(UserIdentifier userIdentifier) throws BotDataException {
        User user = repo.findFirstByChatIdAndMessenger(userIdentifier.chatId(), userIdentifier.messenger());
        if (user == null) {
            throw new BotDataException("User not Found");
        }
        return user;
    }

    public void setRole(Role role, UserIdentifier userIdentifier) throws BotDataException {
        User user = getUserFromUserIdentifier(userIdentifier);
        user.setRole(role);
        repo.save(user);
    }

    public void setStateRegister(StateRegister stateRegister, UserIdentifier userIdentifier) throws BotDataException {
        User user = getUserFromUserIdentifier(userIdentifier);
        user.setStateRegister(stateRegister);
        repo.save(user);
    }
}
