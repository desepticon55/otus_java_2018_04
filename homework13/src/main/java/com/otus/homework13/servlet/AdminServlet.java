package com.otus.homework13.servlet;

import com.otus.homework13.cache.CacheEngine;
import com.otus.homework13.service.PersonService;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServlet extends HttpServlet {

  private static final String DEFAULT_USER_NAME = "UNKNOWN";
  private static final String ADMIN_PAGE_TEMPLATE = "admin.ftl";

  @Autowired
  private TemplateProcessor templateProcessor;
  @Autowired
  private PersonService personService;
  private CacheEngine cacheEngine;

  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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
