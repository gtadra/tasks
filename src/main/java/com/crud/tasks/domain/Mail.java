package com.crud.tasks.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PUBLIC)
public class Mail {
    private final String mailTo;
    private final String toCc;
    private final String subject;
    private final String message;
}
