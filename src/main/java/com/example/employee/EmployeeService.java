package com.example.employee;

import com.example.employee.dao.Employee;
import com.example.employee.dto.EmployeeRequest;
import com.example.employee.dto.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class EmployeeService {

    public String addEmployee(HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Employee.save(mapper.readValue(requestBody, EmployeeRequest.class));

        return requestBody;
    }

    public String getEmployee(HttpServletRequest req) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            EmployeeResponse employee = Employee.getEmployeeById(pathParts[1]);

            if (employee != null) {
                return mapper.writeValueAsString(employee);
            }
        }

        return mapper.writeValueAsString(Employee.getAllEmployees());
    }

    public String updateEmployee(HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Employee.update(mapper.readValue(requestBody, EmployeeRequest.class), pathParts[1]);

        return requestBody;
    }

    public void deleteEmployee(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();

        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        Employee.delete(pathParts[1]);
    }
}
