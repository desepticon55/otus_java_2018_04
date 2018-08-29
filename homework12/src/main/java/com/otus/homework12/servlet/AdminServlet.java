package com.otus.homework12.servlet;

import com.otus.homework12.cache.CacheEngine;
import com.otus.homework12.entity.Person;
import com.otus.homework12.service.PersonService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

  private static final String DEFAULT_USER_NAME = "UNKNOWN";
  private static final String ADMIN_PAGE_TEMPLATE = "admin.ftl";

  private final TemplateProcessor templateProcessor;
  private final CacheEngine cacheEngine;
  private PersonService personService;

  public AdminServlet() throws IOException {
    this.templateProcessor = new TemplateProcessor();
    this.personService = PersonService.getInstance();
    this.cacheEngine = personService.getPersonCacheEngine();
  }

  private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
    Map<String, Object> pageVariables = new HashMap<>();
    pageVariables.put("hitCount", cacheEngine.getHitCount());
    pageVariables.put("missCount", cacheEngine.getMissCount());

    String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
    pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);
    pageVariables.put("isAuthorized", login != null);

    return pageVariables;
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {

    Map<String, Object> pageVariables = createPageVariablesMap(request);

    if (Boolean.TRUE.equals(pageVariables.get("isAuthorized"))) {
      response.setContentType("text/html;charset=utf-8");
      String page = templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
      response.getWriter().println(page);
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.sendRedirect("/login");
    }
  }

}
