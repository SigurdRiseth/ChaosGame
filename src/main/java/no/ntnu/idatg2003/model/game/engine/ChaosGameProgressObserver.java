package no.ntnu.idatg2003.model.game.engine;

public interface ChaosGameProgressObserver extends ChaosGameObserver {

  /**
   * Method to update the observer with the progress of the game.
   *
   * @param progress the progress of the game
   */
  void updateProgress(int progress);

}
