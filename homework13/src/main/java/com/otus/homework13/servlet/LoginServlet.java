package com.otus.homework13.servlet;

import com.otus.homework13.entity.Person;
import com.otus.homework13.entity.Phone;
import com.otus.homework13.service.PersonService;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class LoginServlet extends HttpServlet {

  public static final String LOGIN_PARAMETER_NAME = "login";
  private static final String LOGIN_VARIABLE_NAME = "login";
  private static final String HELP_VARIABLE_NAME = "help";
  private static final String LOGIN_PAGE_TEMPLATE = "login.ftl";

  @Autowired
  private TemplateProcessor templateProcessor;
  @Autowired
  private PersonService personService;

  private String getPage(String login, String help) throws IOException {
    Map<String, Object> pageVariables = new HashMap<>();
    pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
    pageVariables.put(HELP_VARIABLE_NAME, help == null ? "" : help);
    return templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
  }

  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    //todo костыль, надо подумать и вынести это дело куда-то
    Person person = new Person()
            .setId(1L)
            .setFirstName("Alexey")
            .setLastName("Bodyak")
            .setLogin("bodyak")
            .setAge(23)
            .setSex("мужской");
    List<Phone> phones = Arrays.asList(
            new Phone().setId(1L).setNumber("+79852458695").setPerson(person),
            new Phone().setId(2L).setNumber("+79854588888").setPerson(person));
    person.setPhones(phones);
    System.out.println("Save Person method: " + personService.save(person));
  }

  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
    String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
    if (requestLogin == null) requestLogin = (String) request.getSession().getAttribute("login");

    if (accessAccepted(requestLogin)) {
      saveToSession(request, requestLogin); //request.getSession().getAttribute("login");
      setOK(response);
      String l = (String) request.getSession().getAttribute("login");
      String page = getPage(l, "Вы авторизованы!"); //save to the page
      response.getWriter().println(page);
    } else {
      saveToSession(request, null); //request.getSession().getAttribute("login");
      setForbidden(response);
      String l = (String) request.getSession().getAttribute("login");
      String page = getPage(l, "Неверное имя пользователя!"); //save to the page
      response.getWriter().println(page);
    }

  }

  private boolean accessAccepted(String requestLogin) {
    if (requestLogin == null) return false;
    return Objects.nonNull(personService.findByLogin(requestLogin));
  }

  private void saveToCookie(HttpServletResponse response, String requestLogin) {
    response.addCookie(new Cookie("L12.1-login", requestLogin));
  }

  private void saveToServlet(HttpServletRequest request, String requestLogin) {
    request.getServletContext().setAttribute("login", requestLogin);
  }

  private void saveToSession(HttpServletRequest request, String requestLogin) {
    request.getSession().setAttribute("login", requestLogin);
  }

  private void setOK(HttpServletResponse response) {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
  }

  private void setForbidden(HttpServletResponse response) {
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
  }
}
