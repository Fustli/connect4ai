import java.util.ArrayList;

public class StudentPlayer extends Player {

    class Node {
        private int d = 0;
        int v;
        Board b;
        ArrayList<Node> children;

        public Node(int value, Board board) {
            this.v = value;
            this.b = new Board(board);
            this.children = new ArrayList<>();
        }
            private void buildTree (Board board,int depth){
            if (board.gameEnded()) {
                this.d = 6;
                return;
            }

            depth++;
            this.d = depth;
            int lastPlayer = board.getLastPlayerIndex();

            if (lastPlayer == 1)
                lastPlayer = 2;
            else
                lastPlayer = 1;

            if (depth < 7) {
                for (var x : board.getValidSteps()) {
                    Board tempBoard = new Board(board);
                    tempBoard.step(lastPlayer, x);
                    Node tempNode = new Node(lastPlayer, tempBoard);

                    tempNode.buildTree(tempBoard, depth);
                    children.add(tempNode);
                }
            }
        }
        private int miniMax(int alpha, int beta) {
            if (d == 6)
                return evaluate();

            if (d % 2 == 0) {
                v = Integer.MIN_VALUE;
                for (Node child : children) {
                    v = Math.max(v, child.miniMax(alpha, beta));
                    alpha = Math.max(alpha, v);
                    if (alpha <= beta)
                        break;
                }
                return v;
            }
            else {
                v = Integer.MAX_VALUE;
                for (Node child : children) {
                    v = Math.min(v, child.miniMax(alpha, beta));
                    beta = Math.min(beta, v);
                    if (alpha <= beta)
                        break;
                }
                return v;
            }
        }

        private int evaluate() {
            int value = 0;

            if (b.gameEnded()) {
                if (b.getWinner() == 1)
                    value += 1000;
                if(b.getWinner() == 2)
                    value -= 1000;
            }

            value -= c3(2) * 100 + check2(2) * 2;
            value += c3(1) * 100 + check2(1) * 2;

            return value;
        }

        private boolean canMove(int row, int column) {
            if ((row < 0) || (column < 0) || (row > 5) || (column > 6) && b.getState()[row][column] != 0 ) {
                return false;
            }
            return true;
        }

        private int c3(int player) {
            int times = 0;

            //row
            for (int i = 5; i >= 0; i--) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i, k + 3)) {
                            if (b.getState()[i][k] == b.getState()[i][k + 1] && b.getState()[i][k] == b.getState()[i][k + 2]  && b.getState()[i][k] == player)
                                times++;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            //column
            for (int i = 5; i >= 0; i--) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i - 3, k)) {
                            if (b.getState()[i][k] == b.getState()[i - 1][k] && b.getState()[i][k] == b.getState()[i - 2][k] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            //diagonal
            for (int i = 0; i < 6; i++) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i + 3, k + 3)) {
                            if (b.getState()[i][k] == b.getState()[i + 1][k + 1] && b.getState()[i][k] == b.getState()[i + 2][k + 2] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            //diagonal * -1
            for (int i = 0; i < 6; i++) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i - 3, k + 3)) {
                            if (b.getState()[i][k] == b.getState()[i - 1][k + 1] && b.getState()[i][k] == b.getState()[i - 2][k + 2] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            return times;
        }

        private int check2(int player){
            int times = 0;
            //row
            for (int i = 5; i >= 0; i--) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i, k + 2)) {
                            if (b.getState()[i][k] == b.getState()[i][k + 1] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            //column
            for (int i = 5; i >= 0; i--) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i - 2, k)) {
                            if (b.getState()[i][k] == b.getState()[i - 1][k] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            //diagonal
            for (int i = 0; i < 6; i++) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i + 2, k + 2)) {
                            if (b.getState()[i][k] == b.getState()[i + 1][k + 1] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            //digonal * -1
            for (int i = 0; i < 6; i++) {
                for (int k = 0; k < 7; k++) {
                    try {
                        if (canMove(i - 2, k + 2)) {
                            if (b.getState()[i][k] == b.getState()[i - 1][k + 1] && b.getState()[i][k] == player) {
                                times++;
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
            return times;
        }
    }

    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {
        int totalValue;
        int alphaValue = Integer.MAX_VALUE;
        int betaValue = Integer.MIN_VALUE;

        Node root = new Node(0, board);

        root.buildTree(board, 0);

        totalValue = root.miniMax(alphaValue, betaValue);

        for (Node node : root.children) {
            if (node.v == totalValue||node.b.gameEnded()) {

                return node.b.getLastPlayerColumn();
            }
        }
        return 0;
    }
}
