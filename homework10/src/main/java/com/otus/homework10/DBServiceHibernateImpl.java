package com.otus.homework10;

import com.otus.homework10.entity.DataSet;
import com.otus.homework9.annotations.Component;
import com.otus.homework9.annotations.InitMethod;
import com.otus.homework9.annotations.InjectByType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.function.Function;

@Component
public class DBServiceHibernateImpl {

  @InjectByType
  private HibernateConfig hibernateConfig;

  private SessionFactory sessionFactory;

  @InitMethod
  public void init () {
    sessionFactory = createSessionFactory(hibernateConfig.getConfig());
  }


  public <T> void save(T entity) {
    runInSession(session -> session.save(entity));
  }

  public <T> T findById(Long id, Class<T> clazz) {
    return runInSession(session -> session.get(clazz, id));
  }

  public <T> List<T> findAll(Class<T> clazz) {
    return runInSession(session -> {
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(clazz);
      criteria.from(clazz);
      return session.createQuery(criteria).list();
    });
  }

  private static SessionFactory createSessionFactory(Configuration configuration) {
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
    builder.applySettings(configuration.getProperties());
    ServiceRegistry serviceRegistry = builder.build();
    return configuration.buildSessionFactory(serviceRegistry);
  }

  public String getLocalStatus() {
    return runInSession(session -> session.getTransaction().getStatus().name());
  }

  public void shutdown() {
    sessionFactory.close();
  }

  private <R> R runInSession(Function<Session, R> function) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      R result = function.apply(session);
      transaction.commit();
      return result;
    }
  }
}
