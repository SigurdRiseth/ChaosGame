package no.ntnu.idatg2003.ui;

public class Ui {
  public static void print(int[][] canvas) {
    for (int i = 0; i < canvas.length; i++) {
      for (int j = 0; j < canvas[i].length; j++) {
        if (canvas[i][j] == 1) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }
}
