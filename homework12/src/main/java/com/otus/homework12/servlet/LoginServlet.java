package com.otus.homework12.servlet;

import com.otus.homework12.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginServlet extends HttpServlet {

  private List<Person> employeesList = new ArrayList<>();

  @Override
  public void init() throws ServletException {
    super.init();
    Person rahu_bangalore = new Person().setName("Rahu Bangalore").setAge(23);
    rahu_bangalore.setId(1L);
    employeesList.add(rahu_bangalore);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setAttribute("employees", employeesList);
    request.getRequestDispatcher("/index.ftl").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    if (null != name && !name.isEmpty()) {
      Person person = new Person().setName(name).setAge(Integer.valueOf(age));
      person.setId(Long.valueOf(id));
      employeesList.add(person);
    }
    doGet(request, response);
  }
}
