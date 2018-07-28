//package com.otus.homework10;
//
//import com.otus.homework9.entity.DataSet;
//import com.otus.homework9.services.DBService;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//
//import java.util.List;
//import java.util.function.Function;
//
//public class DBServiceHibernateImpl {
//
//  private SessionFactory sessionFactory;
//
////  @Override
//  public <T extends DataSet> void save(T entity) {
//
//  }
//
////  @Override
//  public <T extends DataSet> T load(Long id, Class<T> clazz) {
//    return null;
//  }
//
////  @Override
//  public <T extends DataSet> List<T> load(Class<T> clazz) {
//    return null;
//  }
//
////  public DBServiceHibernateImpl () {
////    Configuration configuration = new Configuration();
////
////    configuration.addAnnotatedClass(Person.class);
////    configuration.addAnnotatedClass(Address.class);
////    configuration.addAnnotatedClass(Phone.class);
////
////    configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
////    configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
////    configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/dbexample");
////    configuration.setProperty("hibernate.connection.username", "tully");
////    configuration.setProperty("hibernate.connection.password", "tully");
////    configuration.setProperty("hibernate.show_sql", "true");
////    configuration.setProperty("hibernate.hbm2ddl.auto", "create");
////    configuration.setProperty("hibernate.connection.useSSL", "false");
////    configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
////
////    sessionFactory = createSessionFactory(configuration);
////  }
//
//  public DBServiceHibernateImpl(Configuration configuration) {
//    sessionFactory = createSessionFactory(configuration);
//  }
//
//  private static SessionFactory createSessionFactory(Configuration configuration) {
//    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
//    builder.applySettings(configuration.getProperties());
//    ServiceRegistry serviceRegistry = builder.build();
//    return configuration.buildSessionFactory(serviceRegistry);
//  }
//
//  public String getLocalStatus() {
//    return runInSession(session -> {
//      return session.getTransaction().getStatus().name();
//    });
//  }
//
//  public void save(UserDataSet dataSet) {
//    try (Session session = sessionFactory.openSession()) {
//      //UserDataSetDAO dao = new UserDataSetDAO(session);
//      //dao.save(dataSet);
//      session.save(dataSet);
//    }
//  }
//
//  public UserDataSet read(long id) {
//    return runInSession(session -> {
//      UserDataSetDAO dao = new UserDataSetDAO(session);
//      return dao.read(id);
//    });
//  }
//
//  public UserDataSet readByName(String name) {
//    return runInSession(session -> {
//      UserDataSetDAO dao = new UserDataSetDAO(session);
//      return dao.readByName(name);
//    });
//  }
//
//  public List<UserDataSet> readAll() {
//    return runInSession(session -> {
//      UserDataSetDAO dao = new UserDataSetDAO(session);
//      return dao.readAll();
//    });
//  }
//
//  public void shutdown() {
//    sessionFactory.close();
//  }
//
//  private <R> R runInSession(Function<Session, R> function) {
//    try (Session session = sessionFactory.openSession()) {
//      Transaction transaction = session.beginTransaction();
//      R result = function.apply(session);
//      transaction.commit();
//      return result;
//    }
//  }
//}
