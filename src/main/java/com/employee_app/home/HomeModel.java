package com.employee_app.home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.employee_app.dbUnit.dbConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class HomeModel {

    Connection conn = null;
    private ObservableList<EmployeeData> employeeData;

    public HomeModel() {
        this.conn = dbConnection.getConnection();

        if (this.conn == null) {
            System.exit(0);
        }
    }

    public ObservableList<EmployeeData> getEmployees() {
        String query = "SELECT * FROM employees_tbl ORDER BY id ASC";

        try {
            this.employeeData = FXCollections.observableArrayList();

            ResultSet resultSet = conn.createStatement().executeQuery(query);

            // id | createAt | name | department

            while (resultSet.next()) {
                this.employeeData.add(new EmployeeData(
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }

            return employeeData;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void addEmployee(String name, String department) {
        String query = "INSERT INTO employees_tbl (name, department) VALUES (?, ?)";
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, department);

            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteEmployee(int index) {
        String query = "DELETE FROM employees_tbl WHERE id = ?";
        PreparedStatement statement = null;

        try {
            int id = Integer.parseInt(employeeData.get(index).getId());

            statement = conn.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEmployee(int index, String name, String department) {

        String query = "UPDATE employees_tbl SET name = CASE WHEN ? = '' THEN ? ELSE ? END, department = CASE WHEN ? = '' THEN ? ELSE ? END WHERE id = ?";
        PreparedStatement statement = null;

        try {
            int id = Integer.parseInt(employeeData.get(index).getId());
            String originalName = employeeData.get(index).getName();
            String originalDepartment = employeeData.get(index).getDepartment();

            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, originalName);
            statement.setString(3, name);

            statement.setString(4, department);
            statement.setString(5, originalDepartment);
            statement.setString(6, department);
            statement.setInt(7, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}