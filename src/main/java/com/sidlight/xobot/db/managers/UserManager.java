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
import org.springframework.stereotype.Service;

@Service
@EnableJpaRepositories(basePackages = "com.sidlight.xobot.db.repos")
public class UserManager implements InitializingBean {

    private static UserManager instance;

    private UserRepo userRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public static UserManager get() {
        return instance;
    }

    public UserManager() {
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public User getUserFromUserIdentifier(UserIdentifier userIdentifier) throws BotDataException {
        User user;
        try {
            user = userRepo.findFirstByChatIdAndMessenger(userIdentifier.chatId(), userIdentifier.messenger());
        } catch (Exception e) {
            throw new BotDataException("User not Found");
        }
        if (user == null) {
            throw new BotDataException("User not Found");
        }
        return user;
    }

    public void setRole(Role role, UserIdentifier userIdentifier) throws BotDataException {
        User user = getUserFromUserIdentifier(userIdentifier);
        user.setRole(role);
        userRepo.save(user);
    }

    public void setStateRegister(StateRegister stateRegister, UserIdentifier userIdentifier) throws BotDataException {
        User user = getUserFromUserIdentifier(userIdentifier);
        user.setStateRegister(stateRegister);
        userRepo.save(user);
    }

    public void register(UserIdentifier userIdentifier, String userName, Role role, String fio) {
        User user = new User();
        user.setFio(fio);
        user.setChatId(userIdentifier.chatId());
        user.setUserName(userName);
        user.setMessenger(userIdentifier.messenger());
        user.setRole(role);
        if (role == Role.DEVELOPER || role == Role.ADMIN) {
            user.setStateRegister(StateRegister.CONFIRMED);
        } else {
            user.setStateRegister(StateRegister.PENDING_CONFIRMATION);
        }
        userRepo.save(user);
    }
}
