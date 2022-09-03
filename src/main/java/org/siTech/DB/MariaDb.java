package org.siTech.DB;

import org.siTech.model.BillItem;
import org.siTech.model.Product;

import java.sql.*;
import java.util.Scanner;


public class MariaDb {

    private static final String url = "jdbc:mariadb://localhost:3305/billing_schema";
    private static final String username = "root";
    private static final String password = "123456";
    private static MariaDb dbInstance = null;
    private Connection dbConnection;
    private static Statement dbStatement;



    MariaDb() {
    }

    public static MariaDb getDbInstance() {
        if (dbInstance == null) {
            dbInstance = new MariaDb();
        }
        return dbInstance;
    }

    private void openDbConnection() {
        try {
//            System.out.println("Open Db Connection");
            if (dbConnection == null)
                dbConnection = DriverManager.getConnection(url, username, password);
            if (dbStatement == null)
                dbStatement = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private void cloeDbConnection() {
        try {
//            System.out.println("close Db Connection");
            if (dbStatement != null && !dbStatement.isClosed()) {
                dbStatement.close();
                dbStatement = null;
            }
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
                dbConnection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet find(String query) {
        try {
            openDbConnection();
            ResultSet resultSet = dbStatement.executeQuery(query);
            cloeDbConnection();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }








        }

