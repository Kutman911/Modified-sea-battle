import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    private static final int size = 8;
    private static String[][] outputField = new String[size][size];
    private static String[][] shipsLocations = new String[size][size];
    private static final String water = "⬜";
    private static final String hit = "❌";
    private static final String missedHit = "⭕";
    private static final String sunk = "S";
    private static final String ship = "Ship";
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Boolean gameContinues = true;
        while (gameContinues)
        {
            System.out.println("Welcome to Sea Battle!");
            System.out.println("Please enter your name: ");
            String gamerName = sc.nextLine();
        }
        

    }
    public static String[][] createField()
    {
        String[][] field = new String[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                field[i][j] = water;
            }
        }
        return field;
    }
    public static String[][] changeField(String[][] field)
    {

    }
    public static void showField()
    {

    }
    public static int[][] placeShips()
    {

    }
    public static void clearScreen()
    {
        System.out.println("\033[H\033[2J");
        for(int i = 0; i < 50; i++)
        {
            System.out.println();
        }
        System.out.flush();
    }
}