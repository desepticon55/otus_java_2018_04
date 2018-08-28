package com.otus.homework12.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

  private static final String LOGIN_PARAMETER_NAME = "login";
  private static final String LOGIN_VARIABLE_NAME = "login";
  private static final String HELP_VARIABLE_NAME = "help";
  private static final String LOGIN_PAGE_TEMPLATE = "login.ftl";

  private TemplateProcessor templateProcessor;

  LoginServlet(TemplateProcessor templateProcessor) {
    this.templateProcessor = templateProcessor;
  }

  public LoginServlet() throws IOException {
    this(new TemplateProcessor());
  }

  private String getPage(String login, String help) throws IOException {
    Map<String, Object> pageVariables = new HashMap<>();
    pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
    pageVariables.put(HELP_VARIABLE_NAME, help == null ? "" : help);
    return templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
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
      String page = getPage(l, "Вход только для тех, у кого имя состоит из восемнадцати букв!"); //save to the page
      response.getWriter().println(page);
    }

  }

  private boolean accessAccepted(String requestLogin) {
    if (requestLogin == null) return false;
    return requestLogin.length() == 18;
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
