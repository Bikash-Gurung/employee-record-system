package com.example.employee;

import com.example.employee.dao.Employee;
import com.example.employee.dto.EmployeeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/employees")
public class RegisterEmployeeServlet extends HttpServlet {

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
}
