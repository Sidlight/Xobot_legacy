package com.sidlight.xobot.db.repos;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.db.domainobject.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findFirstById(Long id);

    User findFirstByChatIdAndMessenger(String chatId, Messenger messengers);

    User findFirstByUserName(String userName);

    List<User> findAllByRoleAndStateRegisterAndMessenger(Role role, StateRegister stateRegister, Messenger messenger);

    List<User> findAllByRoleAndMessenger(Role role, Messenger messenger);

    List<User> findAllByRoleAndStateRegister(Role role, StateRegister register);

    List<User> findAllByRole(Role role);

    List<User> findAllByStateRegisterAndMessenger(StateRegister register, Messenger messenger);

    List<User> findAllByStateRegister(StateRegister register);

    List<User> findAllByMessenger(Messenger messenger);
}
