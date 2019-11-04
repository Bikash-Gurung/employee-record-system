package com.example.employee.dao;

import com.example.employee.dto.EmployeeRequest;
import com.example.employee.dto.EmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "bikash", "bikash");
        } catch (Exception e) {
            logger.error("Error while connecting to database: ", e);
            return null;
        }
    }

    public static void save(EmployeeRequest employee) throws SQLException {
        try (Connection con = Employee.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "insert into emp_detail(first_name,middle_name,last_name,address,phone,email,department) values " +
                            "(?,?,?,?,?,?,?)");
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getMiddleName());
            ps.setString(3, employee.getLastName());
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getPhone());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getDepartment());
            ps.executeUpdate();
            ps.close();
        }
    }

    public static int update(EmployeeRequest employee, String id) {
        try (Connection con = Employee.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "update emp_detail set first_name=?,middle_name=?,last_name=?,address=?, phone=?, email=?, " +
                            "department=? where id=?");
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getMiddleName());
            ps.setString(3, employee.getLastName());
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getPhone());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getDepartment());
            ps.setString(8, id);
            int isUpdated = ps.executeUpdate();
            ps.close();

            return isUpdated;
        } catch (Exception e) {
            logger.error("Error while updating data to database: ", e);
            return 0;
        }
    }

    public static int delete(String id) {
        try (Connection con = Employee.getConnection()) {
            PreparedStatement ps = con.prepareStatement("delete from emp_detail where id=?");
            ps.setString(1, id);
            int isDeleted = ps.executeUpdate();
            ps.close();

            return isDeleted;
        } catch (Exception e) {
            logger.error("Error while deleting data from database: ", e);
            return 0;
        }
    }

    public static EmployeeResponse getEmployeeById(String id) {
        EmployeeResponse employee = new EmployeeResponse();

        try (Connection con = Employee.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from emp_detail where id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs == null) {
                return null;
            }
            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setMiddleName(rs.getString(3));
                employee.setLastName(rs.getString(4));
                employee.setAddress(rs.getString(5));
                employee.setPhone(rs.getString(6));
                employee.setEmail(rs.getString(7));
                employee.setDepartment(rs.getString(8));
                ps.close();

                return employee;
            }

            return null;
        } catch (Exception e) {
            logger.error("Error while retrieving data from database: ", e);
            return null;
        }
    }

    public static List<EmployeeResponse> getAllEmployees() {
        List<EmployeeResponse> list = new ArrayList<EmployeeResponse>();
        try (Connection con = Employee.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from emp_detail");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeResponse employee = new EmployeeResponse();
                employee.setId(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setMiddleName(rs.getString(3));
                employee.setLastName(rs.getString(4));
                employee.setAddress(rs.getString(5));
                employee.setPhone(rs.getString(6));
                employee.setEmail(rs.getString(7));
                employee.setDepartment(rs.getString(8));
                list.add(employee);
            }
            ps.close();
        } catch (Exception e) {
            logger.error("Error while retrieving data from database: ", e);
        }

        return list;
    }
}
