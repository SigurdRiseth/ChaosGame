package no.ntnu.idatg2003.game_engine;

import no.ntnu.idatg2003.game_engine.ChaosGameObserver;

public interface ChaosGameSubject {
  void registerObserver(ChaosGameObserver observer);
  void removeObserver(ChaosGameObserver observer);
  void notifyObservers();

}
