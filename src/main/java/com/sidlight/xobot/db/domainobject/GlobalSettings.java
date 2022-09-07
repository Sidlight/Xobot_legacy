package com.sidlight.xobot.db.domainobject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class GlobalSettings {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public GlobalSettings(){}

}
