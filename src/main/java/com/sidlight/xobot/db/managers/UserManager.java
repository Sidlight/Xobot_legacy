package com.sidlight.xobot.db.managers;

import com.sidlight.xobot.core.BotException;
import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.message.UserIdentifier;
import com.sidlight.xobot.db.BotDataException;
import com.sidlight.xobot.db.domainobject.User;
import com.sidlight.xobot.db.repos.UserRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User getUserFromId(Long id) {
        User user = userRepo.findFirstById(id);
        if (user == null) throw new BotException("Пользователь не найден");
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

    public List<User> getAllUserFrom(Role role, Messenger messenger, StateRegister register) {
        return userRepo.findAllByRoleAndStateRegisterAndMessenger(role, register, messenger);
    }

    public List<User> getAllUserFrom(Role role, Messenger messenger) {
        return userRepo.findAllByRoleAndMessenger(role, messenger);
    }

    public List<User> getAllUserFrom(Role role, StateRegister register) {
        return userRepo.findAllByRoleAndStateRegister(role, register);
    }


    public List<User> getAllUserFrom(Role role) {
        return userRepo.findAllByRole(role);

    }

    public List<User> getAllUserFrom(Messenger messenger, StateRegister register) {
        return userRepo.findAllByStateRegisterAndMessenger(register, messenger);

    }

    public List<User> getAllUserFrom(StateRegister register) {
        return userRepo.findAllByStateRegister(register);
    }

    public List<User> getAllUserFrom(Messenger messenger) {
        return userRepo.findAllByMessenger(messenger);
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
