package com.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
public abstract class AIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
//    @Access(AccessType.PROPERTY)
//    @Basic(fetch = FetchType.EAGER)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
