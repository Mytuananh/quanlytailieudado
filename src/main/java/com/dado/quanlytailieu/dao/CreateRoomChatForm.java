package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomChatForm {
    private String name;
    private List<Long> userEntityList;
}
