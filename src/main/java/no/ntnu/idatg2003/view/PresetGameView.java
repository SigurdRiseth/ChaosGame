package no.ntnu.idatg2003.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2003.controller.PresetGameController;

public class PresetGameView {

  private PresetGameController controller;

  public static Scene getScene() {
    BorderPane content = createContent();
    return new Scene(content, 800, 600);
  }

  private static BorderPane createContent() {
    PresetGameController controller = new PresetGameController();
    BorderPane content = new BorderPane();
    HBox contentBox = new HBox();

    VBox infoBox = createInfoBox();
    infoBox.setSpacing(10);

    content.setCenter(contentBox);
    return content;
  }

  private static VBox createInfoBox() {
    VBox infoBox = new VBox();
    switch (controller.getType()) {
      case "Julia":
          createJuliaInfoBox();
        break;
      case "Affine2D":
          createAffineInfoBox();
        break;
    }
    return infoBox;
  }
}
