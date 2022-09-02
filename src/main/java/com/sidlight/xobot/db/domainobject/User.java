package com.sidlight.xobot.db.domainobject;

import com.sidlight.xobot.core.Messenger;
import com.sidlight.xobot.core.Role;
import com.sidlight.xobot.core.StateRegister;
import com.sidlight.xobot.core.UserIdentifier;
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


}

