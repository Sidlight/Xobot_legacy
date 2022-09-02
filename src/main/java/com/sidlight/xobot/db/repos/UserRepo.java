package com.sidlight.xobot.db.repos;

import com.sidlight.xobot.core.Messenger;
import com.sidlight.xobot.db.domainobject.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findFirstById(Long id);

    User findFirstByChatIdAndMessenger(String chatId, Messenger messengers);

    User findFirstByUserName(String userName);

}
