package com.innovup.meto.controller;

import com.innovup.meto.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class UserController<SERVICE extends UserService> {


    @Getter(AccessLevel.PROTECTED)
    private final SERVICE service;

}
