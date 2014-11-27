package com.epam.prokopov.shop.repository;


public class DAOException extends RuntimeException{

    private  static final String MESSAGE = "Unable to call database! ";

    public DAOException(String message){
        super(MESSAGE + message);
    }

}
