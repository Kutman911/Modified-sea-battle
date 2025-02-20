import java.util.*;

public class Main {
    private static final int size = 8;
    private static final String[][] outputField = new String[size][size];
    private static final String[][] shipsLocations = new String[size][size];
    private static final String water = "🌊";
    private static final String hit = "🎯";
    private static final String missedHit = "⭕";
    private static final String sunk = "☠️";
    private static final String ship = "🚢";
    private static final String[][] shipsSizes = { { "🚢", "🚢", "🚢" }, { "🚢", "🚢" }, { "🚢", "🚢" }, { "🚢" }, { "🚢" }, { "🚢" }, { "🚢" } };
    private static final Random random = new Random();
    static ArrayList<String> players = new ArrayList<>();
    static ArrayList<Integer> shots = new ArrayList<>();
    static ArrayList<int[]> shipCoordinates = new ArrayList<>();
    static ArrayList<Boolean> shipOrientations = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean gameContinues = true;
        while (gameContinues) {
            System.out.println("Welcome to Sea Battle!");
            System.out.println("Please enter your name: ");
            String gamerName = sc.nextLine();
            players.add(gamerName);
            initializeMatrices();
            for (String[] shipSize : shipsSizes) {
                locateShip(shipSize.length);
            }
            int numberShots = playGame(sc);
            showField();
            shots.add(numberShots);
            shipCoordinates.clear();
            shipOrientations.clear();
            showPlayersScores();

            System.out.print("Do you want to play again? NO or YES: ");
            String choice = sc.nextLine().trim().toUpperCase();
            while(!choice.equals("NO") && !choice.equals("YES")) {
                System.out.println("Please enter  NO or YES: ");
                choice = sc.nextLine().trim().toUpperCase();
            }
            if ("NO".equals(choice)) {
                gameContinues = false;
            }
        }
        sc.close();
    }

    public static int playGame(Scanner sc) {
        int shots = 0;
        while (checkShips()) {
            showField();
            int[] coordinates = getPlayerInput(sc);
            if (shipsLocations[coordinates[0]][coordinates[1]].equals(ship)) {
                shipsLocations[coordinates[0]][coordinates[1]] = hit;
                outputField[coordinates[0]][coordinates[1]] = hit;
                clearScreen();
                System.out.println("Hit!");
                shots++;
                checkAndMarkSunkShips();
            } else if (shipsLocations[coordinates[0]][coordinates[1]].equals(hit) || shipsLocations[coordinates[0]][coordinates[1]].equals(sunk)) {
                clearScreen();
                System.out.println("Was already hit, try again");
            } else {
                clearScreen();
                System.out.println("Miss");
                shipsLocations[coordinates[0]][coordinates[1]] = missedHit;
                outputField[coordinates[0]][coordinates[1]] = missedHit;
                shots++;
            }
        }
        return shots;
    }

    static void checkAndMarkSunkShips() {
        for (int i = 0; i < shipCoordinates.size(); i++) {
            int[] coords = shipCoordinates.get(i);
            boolean isHorizontal = shipOrientations.get(i);
            int row = coords[0];
            int col = coords[1];
            int shipSize = shipsSizes[i].length;

            if (isShipSunk(row, col, shipSize, isHorizontal)) {
                markShipAsSunk(row, col, shipSize, isHorizontal);
            }
        }
    }

    static boolean isShipSunk(int row, int col, int size, boolean isHorizontal) {
        for (int i = 0; i < size; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            if (!shipsLocations[r][c].equals(hit)) {
                return false;
            }
        }
        return true;
    }

    static void markShipAsSunk(int row, int col, int size, boolean isHorizontal) {
        for (int i = 0; i < size; i++) {
            int r = isHorizontal ? row : row + i;
            int c = isHorizontal ? col + i : col;
            shipsLocations[r][c] = sunk;
            outputField[r][c] = sunk;
        }
    }

    public static boolean checkShips() {
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (shipsLocations[i][j].equals(ship)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void initializeMatrices() {
        String[] letters = { " ", " A ", "B", " C", " D", " E ", "F ", " G" };
        String[] numbers = { " ", "1", "2", "3", "4", "5", "6", "7" };
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                outputField[i][j] = water;
                shipsLocations[i][j] = water;
            }
        }
        for (int i = 0; i < size; i++) {
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
                shipCoordinates.add(new int[] { row, col });
                shipOrientations.add(horizontal);
                placed = true;
            }
        }
    }

    public static boolean canPlaceShip(int row, int col, int shipSize, boolean horizontal) {
        for (int i = 0; i < shipSize; i++) {
            int newRow = horizontal ? row : row + i;
            int newCol = horizontal ? col + i : col;

            if (newRow >= size || newCol >= size || !shipsLocations[newRow][newCol].equals(water)) {
                return false;
            }

            for (int r = newRow - 1; r <= newRow + 1; r++) {
                for (int c = newCol - 1; c <= newCol + 1; c++) {
                    if (r >= 1 && r < size && c >= 1 && c < size && shipsLocations[r][c].equals(ship)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int[] getPlayerInput(Scanner sc) {
        while (true) {
            System.out.println("Enter the coordinates (e.g., B3), or type 'exit' to quit:");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.equals("EXIT")) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }

            if (!input.matches("^[A-G][1-7]$")) {
                System.out.println("Invalid input format. Use a letter A-G followed by a number 1-7.");
                continue;
            }

            char columnChar = input.charAt(0);
            int col = columnChar - 'A' + 1;
            int row = Character.getNumericValue(input.charAt(1));
            return new int[] { row, col };
        }
    }

    public static void showField() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(outputField[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void showPlayersScores() {
        System.out.println("Results: ");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i) + "'s number of shots: " + shots.get(i));
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
        System.out.flush();
    }
}