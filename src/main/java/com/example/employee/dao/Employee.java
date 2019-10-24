package com.example.employee.dao;

import com.example.employee.dto.EmployeeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Employee {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "bikash", "bikash");

        } catch (Exception e) {
            logger.error("Error while connecting to database: ", e);
        }
        return con;
    }

    public static int save(EmployeeRequest employee) {
        int status = 0;
        try {
            Connection con = Employee.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "insert into emp_detail(first_name,middle_name,last_name,address,phone,email,department) values " +
                            "(?,?,?,?,?,?,?)");

            logger.info("Null pointer occured here");
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getMiddle_name());
            ps.setString(3, employee.getLast_name());
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getPhone());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getDepartment());

            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            logger.error("Error while saving data to database: ", e);
        }

        return status;
    }

    public static int update(EmployeeRequest employee) {
        int status = 0;
        try {
            Connection con = Employee.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "update emp_detail set first_name=?,middle_name=?,last_name=?,address=?, phone=?, email=?, " +
                            "department=? where id=?");
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getMiddle_name());
            ps.setString(3, employee.getLast_name());
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getPhone());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getDepartment());

            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            logger.error("Error while saving data to database: ", e);
        }

        return status;
    }

    public static int delete(int id) {
        int status = 0;
        try {
            Connection con = Employee.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from emp_detail where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            logger.error("Error while saving data to database: ", e);
        }

        return status;
    }

    public static EmployeeRequest getEmployeeById(int id) {
        EmployeeRequest employee = new EmployeeRequest();

        try {
            Connection con = Employee.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from emp_detail where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//                employee.setId(rs.getString(1));
                employee.setFirst_name(rs.getString(2));
                employee.setMiddle_name(rs.getString(3));
                employee.setLast_name(rs.getString(4));
                employee.setAddress(rs.getString(5));
                employee.setPhone(rs.getString(6));
                employee.setEmail(rs.getString(7));
                employee.setDepartment(rs.getString(8));
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            logger.error("Error while saving data to database: ", e);
        }

        return employee;
    }

    public static List<EmployeeRequest> getAllEmployees() {
        List<EmployeeRequest> list = new ArrayList<EmployeeRequest>();

        try {
            Connection con = Employee.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from emp_detail");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmployeeRequest employee = new EmployeeRequest();
                employee.setFirst_name(rs.getString(1));
                employee.setMiddle_name(rs.getString(2));
                employee.setLast_name(rs.getString(3));
                employee.setAddress(rs.getString(4));
                employee.setPhone(rs.getString(5));
                employee.setEmail(rs.getString(6));
                employee.setDepartment(rs.getString(7));
                list.add(employee);
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            logger.error("Error while saving data to database: ", e);
        }

        return list;
    }
}
