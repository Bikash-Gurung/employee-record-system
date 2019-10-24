package com.example.employee;

import com.example.employee.dao.Employee;
import com.example.employee.dto.EmployeeRequest;
import com.example.employee.dto.EmployeeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/employees/*")
public class EmployeeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        PrintWriter out = resp.getWriter();

        ObjectMapper mapper = new ObjectMapper();

        // Read data from request body
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String body = buffer.toString();

        // converting json to object
        EmployeeRequest employee = mapper.readValue(body, EmployeeRequest.class);

        int status = Employee.save(employee);
        if (status > 0) {
            out.write(body);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("Bad Request");
        }

        out.close();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        PrintWriter out = resp.getWriter();

        ObjectMapper mapper = new ObjectMapper();

//        String employeeId = req.getParameter("id");

//        if(employeeId != null){
//            EmployeeResponse employee = Employee.getEmployeeById(employeeId);
//
//            if (employee != null) {
//                String responseBody = mapper.writeValueAsString(employee);
//                out.write(responseBody);
//                out.close();
//            } else {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                out.write("No employee with the provided employee id");
//            }
//        }

        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String id = pathParts[1];

        if(id != null){
            EmployeeResponse employee = Employee.getEmployeeById(id);

            if (employee != null) {
                String responseBody = mapper.writeValueAsString(employee);
                out.write(responseBody);
                out.close();
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("No employee with the provided employee id");
            }
        }


        List<EmployeeResponse> employees = Employee.getAllEmployees();

        // converting obj to json
        String responseBody = mapper.writeValueAsString(employees);

        if (!employees.isEmpty()) {
            out.write(responseBody);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("Bad Request");
        }

        out.close();
    }
}
