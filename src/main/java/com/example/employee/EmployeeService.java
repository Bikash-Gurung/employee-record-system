package com.example.employee;

import com.example.employee.dao.Employee;
import com.example.employee.dto.EmployeeRequest;
import com.example.employee.dto.EmployeeResponse;
import com.example.employee.util.ResponseWriter;
import com.example.employee.util.URLParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeService {

    private static ResponseWriter responseWriter = new ResponseWriter();

    public String addEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        EmployeeRequest employeeRequest = mapper.readValue(requestBody, EmployeeRequest.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<EmployeeRequest>> constraintViolations = validator.validate(employeeRequest);
        if (constraintViolations.size() > 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.write(resp, "Field missing or empty");
            throw new ConstraintViolationException(constraintViolations);
        }
        Employee.save(employeeRequest);

        return mapper.writeValueAsString(employeeRequest);
    }

    public String getEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URLParser urlParser = new URLParser();
        String employeeId = urlParser.getURLParam(req);
        if (employeeId != null) {
            EmployeeResponse employee = Employee.getEmployeeById(employeeId);

            if (employee != null) {
                return mapper.writeValueAsString(employee);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseWriter.write(resp, "Employee with given ID does not exist");
            }
        }

        return mapper.writeValueAsString(Employee.getAllEmployees());
    }

    public String updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URLParser urlParser = new URLParser();
        String employeeId = urlParser.getURLParam(req);
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        EmployeeResponse employee = Employee.getEmployeeById(employeeId);
        if (employee == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.write(resp, "Employee with given ID does not exist");
        }
        Employee.update(mapper.readValue(requestBody, EmployeeRequest.class), employeeId);

        return mapper.writeValueAsString(employee);
    }

    public String deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URLParser urlParser = new URLParser();
        int isDeleted = Employee.delete(urlParser.getURLParam(req));
        if (isDeleted == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.write(resp, "Employee with given ID does not exist");
        }

        return "Employee with given id deleted successfully";
    }
}
