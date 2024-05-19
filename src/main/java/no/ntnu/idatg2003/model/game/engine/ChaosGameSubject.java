package no.ntnu.idatg2003.model.game.engine;

/**
 * Interface for registering, removing and notifying observers of the ChaosGame class.
 *
 * @author Sigurd Riseth
 */
public interface ChaosGameSubject {

  /**
   * Method to register an observer.
   * @param observer The observer to register.
   */
  void registerObserver(ChaosGameObserver observer);

  /**
   * Method to remove an observer.
   * @param observer The observer to remove.
   */
  void removeObserver(ChaosGameObserver observer);

  /**
   * Method to notify all observers.
   */
  void notifyObservers();

}
