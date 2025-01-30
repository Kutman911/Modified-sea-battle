import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    private static final int size = 8;
    private static String[][] outputField = new String[size][size];
    private static String[][] shipsLocations = new String[size][size];
    private static final String water = "🌊";
    private static final String hit = "🎯";
    private static final String missedHit = "⭕";
    private static final String sunk = "☠️";
    private static final String ship = "🚢";
    private static final String[][] shipsSizes = { { "🚢", "🚢", "🚢",  }, { "🚢", "🚢",  }, { "🚢", "🚢",  }, { "🚢",  }, {"🚢",}, { "🚢",  }, { "🚢",  } };
    private static Random random = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Boolean gameContinues = true;
        ArrayList<String> gamers = new ArrayList<>();
        while (gameContinues)
        {
            System.out.println("Welcome to Sea Battle!");
            System.out.println("Please enter your name: ");
            String gamerName = sc.nextLine();
            gamers.add(gamerName); 
            initializeMatrices();
            for(String[] shipSize : shipsSizes) {
                locateShip(shipSize.length);
            }
            int shots = 0;
            showField();

        }
    }
    public static void initializeMatrices()
    {
        String[] letters = {" ", " A ", "B ", "C ", "D ", "E ", " F ", "G"};
        String[] numbers = {" ", "1", "2", "3", "4", "5", "6", "7"};
        for (int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                outputField[i][j] = water;
                shipsLocations[i][j] = water;
            }
        }
        for(int i = 0; i < size; i++)
        {
            outputField[0][i] = letters[i];
            outputField[i][0] = numbers[i];
            shipsLocations[0][i] = letters[i];
            shipsLocations[i][0] = numbers[i];
        }
    }

    public static void locateShip(int shipSize) {
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(1, size);
            int col = random.nextInt(1, size);
            boolean horizontal = random.nextBoolean();

            if (canPlaceShip(row, col, shipSize, horizontal)) {
                for (int i = 0; i < shipSize; i++) {
                    int newRow = horizontal ? row : row + i;
                    int newCol = horizontal ? col + i : col;
                    shipsLocations[newRow][newCol] = ship;
                }
                placed = true;
            }
        }
    }

    public static boolean canPlaceShip(int row, int col, int shipSize, boolean horizontal) {
        for (int i = -1; i < shipSize; i++) {
            for (int j = -1; j < shipSize; j++) {
                int newRow = horizontal ? row + j: row + i;
                int newCol = horizontal ? col + i : col + j;
                if (newRow > 0 && newRow < size && newCol > 0 && newCol < size) {
                    if (shipsLocations[newRow][newCol].equals(ship)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }
    public static void changeField(String[][] field)
    {

    }
    public static void showField()
    {
        for (int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                System.out.print(outputField[i][j] + " ");
            }
            System.out.println();
        }
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