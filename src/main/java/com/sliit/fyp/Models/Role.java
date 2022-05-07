package com.sliit.fyp.Models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.NaturalId;

@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  private RoleName roleName;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private Set<User> user;

  public Role(RoleName roleName) {

    this.roleName = roleName;

  }

  public Role() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public RoleName getRoleName() {
    return roleName;
  }

  public void setRoleName(RoleName roleName) {
    this.roleName = roleName;
  }

  public Set<User> getUser() {
    return user;
  }

  public void setUser(Set<User> user) {
    this.user = user;
  }

}
