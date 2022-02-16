package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class TrelloList {
    private String id;
    private String name;
    private boolean isClosed;


}
