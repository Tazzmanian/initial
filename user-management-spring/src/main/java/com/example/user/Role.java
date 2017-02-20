/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user;

import com.example.access.Privilege;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author I.Mutafov
 */
@Entity
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private Long id;

    private String name;

    public Role() {
    }

    public Role(String role) {
        this.name = role;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "PRIVILEGE_ID"))
    private Collection<Privilege> privileges;

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    public List<User> users;
}
