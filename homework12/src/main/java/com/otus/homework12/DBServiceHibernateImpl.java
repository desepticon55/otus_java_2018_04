package com.otus.homework12;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl {

  private HibernateConfig hibernateConfig;

  private SessionFactory sessionFactory;

  public DBServiceHibernateImpl() throws Exception {
    this.hibernateConfig = new HibernateConfig();
    this.sessionFactory = createSessionFactory(hibernateConfig.getConfig());
  }

  public <T> void save(T entity) {
    runInSession(session -> session.save(entity));
  }

  public <T> T findById(Long id, Class<T> clazz) {
    return runInSession(session -> session.get(clazz, id));
  }

  public <T> List<T> findByField(Class<T> clazz, String fieldName, String value) {
    return runInSession(session -> {
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(clazz);
      Root<T> from = criteria.from(clazz);
      criteria.select(from);
      criteria.where(builder.equal(from.get(fieldName), value));
      return session.createQuery(criteria).list();
    });
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
