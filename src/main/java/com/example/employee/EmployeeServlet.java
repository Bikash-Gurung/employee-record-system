package com.example.employee;

import com.example.employee.util.ResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/employees/*")
public class EmployeeServlet extends HttpServlet {

    private static EmployeeService employeeService = new EmployeeService();
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);
    private static ResponseWriter responseWriter = new ResponseWriter();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            responseWriter.write(resp, employeeService.addEmployee(req, resp));
        } catch (SQLException e) {
            logger.error("Error : {}", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseWriter.write(resp, "Internal server error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriter.write(resp, employeeService.getEmployee(req, resp));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriter.write(resp, employeeService.updateEmployee(req, resp));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        responseWriter.write(resp, employeeService.deleteEmployee(req, resp));
    }
}
