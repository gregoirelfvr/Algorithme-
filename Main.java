import java.lang.Math;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.FileWriter;

public class Main {

  public static void main(String[] args) {
    System.out.printf("Grille G(test) :");
    System.out.println("\n");
    int[][] test = new int[3][5];
    test[0] = new int[] { 2, 2, 3, 4, 2 };
    test[1] = new int[] { 6, 5, 1, 2, 8 };
    test[2] = new int[] { 1, 1, 10, 1, 1 };
    afficheMatrice(test);
    afficheGlouton2(test);
    System.out.println("\n");
    /*
     * System.out.printf("Grille G :");
     * System.out.println("\n");
     * int[][] G = matriceRandom();
     * afficheMatrice(G);
     * afficheGlouton2(G);
     */
    System.out.println("\n\nM :");
    afficheM(test, 0);
    // afficheA(test, 0);
    acnpm(calculerMA(test, 0)[0], calculerMA(test, 0)[1]);
    System.out.println("\n\nM :");
    afficheM(test, 1);
    // afficheA(test, 1);
    System.out.println("\n\n :");
    acnpm(calculerMA(test, 1)[0], calculerMA(test, 1)[1]);
    System.out.println("\n\n");
    System.out.println("Ng :");
    afficheLigne(glouton(test));
    System.out.println("\n\n");
    System.out.println("Nmax :");
    afficheLigne(optimal(test));
    System.out.println("\n\n");
    System.out.println("Gain relatif :");
    afficheLignef(gainRelatif(optimal(test), glouton(test)));
    System.out.println("\n\n");
    afficheMatriceRandom(matriceRandom());
    System.out.println("\n\n");
    afficheMatriceRandom(matriceRandom());
    System.out.println("\n\n");
    afficheMatriceRandom(matriceRandom());
    System.out.println("\n\n");
    afficheMatriceRandom(matriceRandomMultiPermu());

    // Q9
    gainRelatif();
  }

  // Q1 -- Code glouton

  public static int glouton(int[][] G, int d) {
    // base
    int aNbPucerons = G[0][d];

    for (int i = 1; i < G.length; i++) {
      if (d == 0) {
        aNbPucerons += Math.max(G[i][d], G[i][d + 1]);
        if (Math.max(G[i][d], G[i][d + 1]) == G[i][d + 1]) {
          d++;
        }
      } else if (d == G[i].length - 1) {
        aNbPucerons += Math.max(G[i][d - 1], G[i][d]);
        if (Math.max(G[i][d], G[i][d - 1]) == G[i][d - 1]) {
          d--;
        }
      } else {
        aNbPucerons += Math.max(G[i][d], Math.max(G[i][d - 1], G[i][d + 1]));
        if (Math.max(G[i][d], Math.max(G[i][d - 1], G[i][d + 1])) == G[i][d + 1]) {
          d++;
        } else if (Math.max(G[i][d], Math.max(G[i][d - 1], G[i][d + 1])) == G[i][d - 1]) {
          d--;
        }
      }
    }

    return aNbPucerons;
  }

  // Q2
  public static int[] glouton(int[][] G) {
    int[] Ng = new int[G[0].length];
    for (int i = 0; i < G[0].length; i++) {
      Ng[i] = glouton(G, i);
    }
    return Ng;
  }

  // ------- TEST ET AFFICHAGE -------------
  public static void afficheGlouton(final int[][] G) {
    System.out.printf("%d", glouton(G, 0));
  }

  public static void afficheGlouton2(final int[][] G) {
    for (int i = 0; i < G[0].length - 1; i++) {
      System.out.printf("%d, ", glouton(G, i));
    }
    System.out.printf("%d]", glouton(G, G[0].length - 1));
  }

  public static void afficheLigne(final int[] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.printf("[%d] ", A[i]);
    }
  }

  public static void affichegR(final int[] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.printf("%d", A[i]);
    }
  }

  public static void afficheLignef(final float[] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.printf("[%f] ", A[i]);
    }
  }

  public static void afficheMatrice(final int[][] A) {
    for (int i = A.length - 1; -1 < i; i--) {
      System.out.printf("G[%d] : ", i);
      ;
      for (int j = 0; j < A[i].length; j++) {
        System.out.printf("[%d] ", A[i][j]);
      }
      System.out.println("\n");
    }
    System.out.printf("Valeurs des chemins gloutons depuis les cases (0,%d): [", A[0].length - 1);
  }

  public static int[][] matriceRandom() {
    int lignes = (int) (Math.random() * 10) + 2; // 2 à 12
    int colonnes = (int) (Math.random() * 10) + 2; // 2 à 12
    int[][] G = new int[lignes][colonnes];
    for (int i = 0; i < lignes; i++) {
      for (int j = 0; j < colonnes; j++) {
        G[i][j] = (int) (Math.random() * (lignes + colonnes));
      }
    }
    return G;
  }

  public static int[][] matriceRandomMulti() {
    int lignes = (int) (Math.random() * 10) + 2; // 2 à 12
    int colonnes = (int) (Math.random() * 10) + 2; // 2 à 12
    int[][] G = new int[lignes][colonnes];
    for (int i = 0; i < lignes; i++) {
      for (int j = 0; j < colonnes; j++) {
        G[i][j] = (int) (Math.random() * (lignes * colonnes));
      }
    }
    return G;
  }

  public static int[][] matriceRandomMultiPermu() {
    int lignes = (int) (Math.random() * 10) + 2; // 2 à 12
    int colonnes = (int) (Math.random() * 10) + 2; // 2 à 12
    int[] tableauOriginal = new int[lignes * colonnes];
    for (int i = 0; i < tableauOriginal.length; i++) {
      tableauOriginal[i] = i;
    }
    permutationAleatoire(tableauOriginal);
    int[][] G = new int[lignes][colonnes];
    for (int i = 0; i < tableauOriginal.length; i++) {
      G[i / colonnes][i % colonnes] = tableauOriginal[i];
    }
    return G;
  }

  static int[] permutationAleatoire(int[] T) {
    int n = T.length;
    // Calcule dans T une permutation aléatoire de T et retourne T
    Random rand = new Random(); // bibliothèque java.util.Random
    for (int i = n; i > 0; i--) {
      int r = rand.nextInt(i); // r est au hasard dans [0:i]
      permuter(T, r, i - 1);
    }
    return T;
  }

  static void permuter(int[] T, int i, int j) {
    int ti = T[i];
    T[i] = T[j];
    T[j] = ti;
  }

  public static void afficheM(final int[][] G, final int indice) {
    int[][] M = calculerMA(G, indice)[0];
    System.out.printf("\nProgrammation dynamique, case de depart (0, %d): \n\n", indice);
    for (int i = M.length - 1; -1 < i; i--) {
      System.out.printf("M[%d] : ", i);
      for (int j = 0; j < M[i].length; j++) {
        System.out.printf("[%d] ", M[i][j]);
      }
      System.out.println("\n");
    }
  }

  public static void afficheA(final int[][] G, final int indice) {
    int[][] M = calculerMA(G, indice)[1];
    System.out.printf("\nTableau A : \n\n", indice);
    for (int i = M.length - 1; -1 < i; i--) {
      System.out.printf("A[%d] : ", i);
      for (int j = 0; j < M[i].length; j++) {
        System.out.printf("[%d] ", M[i][j]);
      }
      System.out.println("\n");
    }
  }

  public static void afficheMatriceRandom(final int[][] G) {
    System.out.printf("\nG aleatoire : \n\n");
    for (int i = G.length - 1; -1 < i; i--) {
      System.out.printf("G[%d] : ", i);
      for (int j = 0; j < G[i].length; j++) {
        System.out.printf("[%d] ", G[i][j]);
      }
      System.out.println("\n");
    }
  }

  // Q3
  // ......m(0,c) = -1 else if {c == d} ==> m(0,d) = G[0][d]
  // ......m(l,c) = G[l,c] + max(m(l-1,c), m(l-1,c-1),
  // m(l-1,c+1))........(heredite)

  // Q4
  public static int[][][] calculerMA(int[][] G, int d) {
    int lignes = G.length;
    int colonnes = G[0].length;
    int[][] M = new int[lignes][colonnes];
    int[][] A = new int[lignes][colonnes];

    // initialisation de la recurrence
    for (int i = 0; i < colonnes; i++) {
      M[0][i] = -1;
      A[0][i] = -1;
      if (i == d) {
        M[0][i] = G[0][d];
      }
    }
    // heredite

    for (int i = 1; i < lignes; i++) {
      for (int j = 0; j < colonnes; j++) {
        int mil = M[i - 1][j];// on peut toujours voir la valeur d'en dessous
        int gauche = -1;
        int droite = -1;

        if (j < colonnes - 1) {
          droite = M[i - 1][j + 1];
        } // on ne regarde la valeur que si elle est dans le tableau
        if (0 < j) {
          gauche = M[i - 1][j - 1];
        } // de meme

        M[i][j] = -1;// initialisation du tableau
        A[i][j] = -1;
        int vA = -1;
        int max = Math.max(mil, Math.max(gauche, droite));
        // remplissage de M[i][j]
        if (max == mil) {
          M[i][j] = max;
          vA = j;
        } else if (max == gauche) {
          M[i][j] = max;
          vA = j - 1;

        } else if (max == droite) {
          M[i][j] = max;
          vA = j + 1;

        }

        if (M[i][j] != -1) {
          M[i][j] += G[i][j];
          A[i][j] = vA;
        }
      }
    }
    int[][][] MA = { M, A };
    return MA;
  }

  // donnee dans l'ennonce
  static public void acnpm(int[][] M, int[][] A) {
    int L = M.length;
    int cStar = argMax(M[L - 1]); // colonne d’arriv ́ee du chemin
    // max.d’origine(0,d)
    acnpm(A, L - 1, cStar); // affichage du chemin maximum de (0,d) `a
    // (L-1,cStar)
    System.out.printf("Valeur : %d", M[L - 1][cStar]);
  }

  static public int argMax(int[] tab) {
    int max = tab[0];
    int arg = 0;
    for (int i = 1; i < tab.length; i++) {
      if (tab[i] > max) {
        max = tab[i];
        arg = i;
      }
    }
    return arg;
  }

  // Q5
  static public void acnpm(int[][] A, int l, int c) {
    if (l == 0) {
      System.out.printf("Un chemin de valeur maximum : (0, %d) ", c);
      return;
    }
    acnpm(A, l - 1, A[l][c]);
    System.out.printf("(%d, %d) ", l, c);
  }

  // Q6
  static public int optimal(int[][] G, int d) {
    int[][][] MA = calculerMA(G, d);
    int[][] M = MA[0];
    int L = M.length;
    return M[L - 1][argMax(M[L - 1])];
  }

  // Q7
  public static int[] optimal(int[][] G) {
    int[] Ng = new int[G[0].length];
    for (int i = 0; i < G[0].length; i++) {
      Ng[i] = optimal(G, i);
    }
    return Ng;
  }

  // Q8
  static public float[] gainRelatif(int[] Nmax, int[] Ng) {
    float[] gainRelatif = new float[Nmax.length];
    for (int i = 0; i < Nmax.length; i++) {
      gainRelatif[i] = ((Nmax[i] * 1f - Ng[i]) * 1f / Ng[i] * 1f);
    }
    return gainRelatif;
  }

  // Q9

  public static void gainRelatif() {
    float[] vide = new float[1];
    histo(vide, true);
    for (int i = 0; i < 10000; i++) {
      int[][] G = matriceRandomMultiPermu();
      int[] Nmax = optimal(G);
      int[] Ng = glouton(G);
      float[] gainRelatif = gainRelatif(Nmax, Ng);
      histo(gainRelatif, false);
    }
  }

  public static void histo(final float[] gR, boolean del) {
    String csvFilePath = "DR.csv";
    try {
      FileWriter csvWriter = new FileWriter(csvFilePath, !del);
      for (int i = 0; i < gR.length; i++) {
        csvWriter.write("" + gR[i] + "\n");
      }
      csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
