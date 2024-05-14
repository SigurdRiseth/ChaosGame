package no.ntnu.idatg2003.model.game.engine;

/**
 *
 */

public interface ChaosGameSubject {
  void registerObserver(ChaosGameObserver observer);
  void removeObserver(ChaosGameObserver observer);
  void notifyObservers();

}
