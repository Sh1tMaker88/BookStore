package com.anton.controller.InModelPackage.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestDto {

    //library for mapping mapstruct or modelMapper  and add it in service module

    private Long id;
    private String name;
    private Integer age;
    private RoomDto room;
}
