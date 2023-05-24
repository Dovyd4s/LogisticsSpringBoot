package com.logisticsspring.errors;

public class UserNotFound extends RuntimeException{
    public UserNotFound(Integer id){
        super("Can't find user with ID of: " + id);
    }
}
