package com.epam.prokopov.shop.repository;

import java.sql.Connection;	
import java.sql.SQLException;

/**
 * Contains request to DB.
 */
public interface Transaction {	
        
    <T> T execute(Connection connection) throws SQLException;	
            
}