package com.sidlight.xobot.db.domainobject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class ModeratorSettings {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @OneToOne
    private User user;



    public ModeratorSettings(){}

    public ModeratorSettings(User user){
        this.user = user;
    }

}
