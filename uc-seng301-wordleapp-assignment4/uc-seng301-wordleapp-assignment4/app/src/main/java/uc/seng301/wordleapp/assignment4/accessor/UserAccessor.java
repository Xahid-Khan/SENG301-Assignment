package uc.seng301.wordleapp.assignment4.accessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import uc.seng301.wordleapp.assignment4.model.User;

public class UserAccessor {
    private static final Logger LOGGER = LogManager.getLogger(UserAccessor.class);
    private final SessionFactory sessionFactory;

    /**
     * default constructor
     * 
     * @param sessionFactory the JPA session factory to talk to the persistence
     *                       implementation.
     */
    public UserAccessor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets user from persistence by name
     * 
     * @param name name of user to fetch, cannot be null or blank
     * @return User with given name
     * @throws IllegalArgumentException if name is null or blank
     */
    public User getUserByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name '" + name + "' cannot be null or blank");
        }
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User WHERE userName='" + name + "'", User.class).uniqueResult();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return user;
    }

    /**
     * Gets user from persistence by id
     * 
     * @param userId id of user to fetch, cannot be null
     * @return User with given id
     * @throws IllegalArgumentException if userId is null
     */
    public User getUserById(Long userId) {
        if (null == userId) {
            throw new IllegalArgumentException("cannot retrieve user with null id");
        }
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User WHERE userId =" + userId, User.class)
                    .uniqueResult();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return user;
    }

    /**
     * Saves user to persistence
     * 
     * @param user user to save
     * @return id of saved user
     * @throws IllegalArgumentException if user object is invalid (e.g. missing
     *                                  properties)
     */
    public Long persistUser(User user) throws IllegalArgumentException {
        if (null == user || null == user.getUserName() || user.getUserName().isBlank()) {
            throw new IllegalArgumentException("cannot save null or blank user");
        }

        User existingUser = getUserByName(user.getUserName());
        if (null != existingUser) {
            user.setUserId(existingUser.getUserId());
            return existingUser.getUserId();
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return user.getUserId();
    }

    /**
     * remove given user from persistence by id
     * 
     * @param userId id of user to be deleted
     * @return true if the record is deleted
     */
    public boolean removeUserById(Long userId) {
        User user = getUserById(userId);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return false;
    }

}
