package com.sidlight.xobot.db.domainobject;

import com.sidlight.xobot.core.access.Role;
import com.sidlight.xobot.core.access.StateRegister;
import com.sidlight.xobot.core.message.Messenger;
import com.sidlight.xobot.core.message.UserIdentifier;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @Column
    private Role role;

    @Setter
    @Getter
    @Column
    private Messenger messenger;

    @Setter
    @Getter
    @Column
    private String chatId;

    @Setter
    @Getter
    @Column
    private String userName;

    @Setter
    @Getter
    @Column
    private String fio;

    @Getter
    @Setter
    @Column
    private StateRegister stateRegister;

    public User(Messenger messenger, String userName, String chatId) {
        this.messenger = messenger;
        this.userName = userName;
        this.chatId = chatId;
        stateRegister = StateRegister.NON;
        role = Role.USER;
    }

    public User() {
        stateRegister = StateRegister.NON;
        role = Role.USER;
    }

    public UserIdentifier getUserIdentifier() {
        return new UserIdentifier(chatId, messenger);
    }

    @Override
    public String toString() {
        return id + ". " + userName + ". " + fio + ". " + messenger.toString();
    }

}

