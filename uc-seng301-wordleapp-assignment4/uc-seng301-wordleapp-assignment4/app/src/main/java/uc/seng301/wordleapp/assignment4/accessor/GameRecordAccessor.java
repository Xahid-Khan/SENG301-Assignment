package uc.seng301.wordleapp.assignment4.accessor;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import uc.seng301.wordleapp.assignment4.model.GameRecord;

/**
 * This class offers helper methods for saving, removing, and fetching game
 * records from persistence
 * {@link GameRecord} entities
 */
public class GameRecordAccessor {
    private static final Logger LOGGER = LogManager.getLogger(GameRecordAccessor.class);
    private final SessionFactory sessionFactory;

    /**
     * default constructor
     * 
     * @param sessionFactory the JPA session factory to talk to the persistence
     *                       implementation.
     */
    public GameRecordAccessor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get game object from persistence layer
     * 
     * @param gameRecordId id of game record to fetch, cannot be null
     * @return Gamerecord with id given if it exists in database, no user object
     * @throws IllegalArgumentException if a null gameRecordId is passed
     */
    public GameRecord getGameRecordById(Long gameRecordId) {
        if (null == gameRecordId) {
            throw new IllegalArgumentException("cannot retrieve game record with null id");
        }
        GameRecord gameRecord = null;
        try (Session session = sessionFactory.openSession()) {
            gameRecord = session.createQuery("FROM GameRecord WHERE gameRecordId =" + gameRecordId, GameRecord.class)
                    .uniqueResult();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return gameRecord;
    }

    /**
     * Gets gamerecord and associated user from persistence layer
     * 
     * @param gameRecordId id of game record to fetch
     * @return Gamerecord with id given if it exists in database with user object,
     *         cannot be null
     * @throws IllegalArgumentException if a null gameRecordId is passed
     */
    public GameRecord getGameRecordAndUserById(Long gameRecordId) {
        if (null == gameRecordId) {
            throw new IllegalArgumentException("cannot retrieve gameRecord with null id");
        }
        GameRecord gameRecord = null;
        try (Session session = sessionFactory.openSession()) {
            gameRecord = session.createQuery("FROM GameRecord WHERE gameRecordId =" + gameRecordId, GameRecord.class)
                    .uniqueResult();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return gameRecord;
    }

    /**
     * Gets top 10 gamerecords from database (in ascending number of guesses)
     * 
     * @return top 10 gamerecords
     */
    public List<GameRecord> getHighscores() {
        List<GameRecord> highscores = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            highscores = session.createQuery("FROM GameRecord ORDER BY numGuesses ASC", GameRecord.class)
                    .setMaxResults(10).list();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return highscores;
    }

    /**
     * Save given gamerecord to persistence
     * 
     * @param gameRecord gamerecord to be saved
     * @return The id of the persisted gamerecord
     */
    public Long persistGameRecord(GameRecord gameRecord) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(gameRecord);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return gameRecord.getGameRecordId();
    }

    /**
     * remove given gamerecord from persistence by id
     * 
     * @param gameRecordId id of gamerecord to be deleted
     * @return true if the record is deleted
     */
    public boolean deleteGameRecordById(Long gameRecordId) throws IllegalArgumentException {
        GameRecord gameRecord = getGameRecordById(gameRecordId);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(gameRecord);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            LOGGER.error("Unable to open session or transaction", e);
        }
        return false;
    }

}
