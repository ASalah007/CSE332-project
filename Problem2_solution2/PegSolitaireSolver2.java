import java.util.*;

public class PegSolitaireSolver2 {
    final private static boolean RIGHT = true;
    final private static boolean LEFT = false;
    private Set<Long> memory;
    BoardTree.Board initialBoard;
    BoardTree BT;

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            int BOARD_SIZE;
            System.out.print("The board size (have to be even number or 3 to be solved) = ");
            BOARD_SIZE = sc.nextInt();
            while (!(BOARD_SIZE == 3 || BOARD_SIZE % 2 == 0)) {
                System.out.println("board size have to be even number or 3");
                System.out.print("The board size = ");
                BOARD_SIZE = sc.nextInt();
            }
            System.out.print("The position of the empty slot from 1 to " + BOARD_SIZE + " is ");
            int BLANK_INIT_POS = sc.nextInt();

            PegSolitaireSolver2 pss = new PegSolitaireSolver2(BOARD_SIZE, BLANK_INIT_POS);
            ArrayList<ArrayList<BoardTree.Board>> soln = pss.getSolution();

            BoardTree.showCompleteSolution(soln, BOARD_SIZE - 1);
        }
    }

    public PegSolitaireSolver2(int BOARD_SIZE, int BLANK_INIT_POS) {
        solveBoard(BOARD_SIZE, BLANK_INIT_POS);
    }

    public void solveBoard(int BOARD_SIZE, int BLANK_INIT_POS) {
        ArrayList<Integer> boardALInt = new ArrayList<>();

        for (int i = 0; i <= BOARD_SIZE; i++) {
            boardALInt.add(i, 1);
        }
        boardALInt.set(BLANK_INIT_POS, 0);

        initialBoard = new BoardTree.Board(boardALInt);
        BT = new BoardTree(initialBoard);
        memory = new TreeSet<>();
        memory.add(initialBoard.getBoardINT());


        solveBoard(initialBoard);

    }

    public void solveBoard(BoardTree.Board node) {
        // try move left
        int BOARD_SIZE = node.getBoardAL().size() - 1;
        BoardTree.Board tryBoard;
        ArrayList<Integer> tryAL;

        for (int i = 3; i <= BOARD_SIZE; i++) {
            if (validateMove(node.getBoardAL(), i, LEFT)) {
                tryAL = new ArrayList<>(node.getBoardAL());
                move(tryAL, i, LEFT);
                tryBoard = new BoardTree.Board(tryAL);
                if (!memory.contains(tryBoard.getBoardINT())) {
                    node.addChild(tryBoard);
                    memory.add(tryBoard.getBoardINT());
                    solveBoard(tryBoard);
                }
            }
        }
        for (int i = 1; i <= BOARD_SIZE - 2; i++) {
            if (validateMove(node.getBoardAL(), i, RIGHT)) {
                tryAL = new ArrayList<>(node.getBoardAL());
                move(tryAL, i, RIGHT);
                tryBoard = new BoardTree.Board(tryAL);
                if (!memory.contains(tryBoard.getBoardINT())) {
                    node.addChild(tryBoard);
                    memory.add(tryBoard.getBoardINT());
                    solveBoard(tryBoard);
                }
            }
        }
    }

    private boolean validateMove(ArrayList<Integer> board, int pos, boolean direction) {
        if (direction == LEFT) {
            return board.get(pos) == 1
                    && board.get(pos - 1) == 1 && board.get(pos - 2) == 0;
        } else {
            return board.get(pos) == 1
                    && board.get(pos + 1) == 1 && board.get(pos + 2) == 0;
        }
    }

    private ArrayList<Integer> move(ArrayList<Integer> board, int pos, boolean direction) {
        if (direction == LEFT) {
            board.set(pos, 0);
            board.set(pos - 1, 0);
            board.set(pos - 2, 1);
        } else {
            board.set(pos, 0);
            board.set(pos + 1, 0);
            board.set(pos + 2, 1);
        }
        return board;
    }

    private ArrayList<ArrayList<BoardTree.Board>> getSolution() {
        return BT.getSolutionAL();
    }
}
