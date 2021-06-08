package com.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public abstract class AIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
//    @JsonIdentityInfo()
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
