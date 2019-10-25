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

        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        EmployeeRequest employee = mapper.readValue(body, EmployeeRequest.class);

        Employee.save(employee);

        return body;
    }

    public String getEmployee(HttpServletRequest req) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            String id = pathParts[1];
            EmployeeResponse employee = Employee.getEmployeeById(id);

            if (employee != null) {
                String responseBody = mapper.writeValueAsString(employee);
                return responseBody;
            }
        }

        String responseBody = mapper.writeValueAsString(Employee.getAllEmployees());

        return responseBody;
    }

    public String updateEmployee(HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String id = pathParts[1];

        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        EmployeeRequest employeeRequest = mapper.readValue(body, EmployeeRequest.class);
        Employee.update(employeeRequest, id);

        return body;
    }

    public void deleteEmployee(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();

        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String id = pathParts[1];

        Employee.delete(id);
    }
}
