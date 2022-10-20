import javax.sql.rowset.spi.SyncProvider;
import java.sql.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Spliterator;

public class StudentPlayer extends Player{

    private LinkedList<Node> nextNode = new LinkedList<Node>();
    private int alphaValue = 0;
    private int betaValue = 0;

    class Node {
        private int depth = 0;

        int value;
        Board board;
        ArrayList<Node> children;

        public Node(int value, Board board){
            this.value = value;
            this.board = new Board(board);
        }

        private void buildTree(Board board, int lastPlayer, int depth){
            depth++;
            System.out.print(depth + " ");
            if(depth < 6){
                if(!(board.getWinner() == 1 || board.getWinner() == 2)){
                    for (var x : board.getValidSteps()){
                        Board tempBoard = new Board(board);
                        tempBoard.step(lastPlayer, x);
                        Node tempNode = new Node(lastPlayer, tempBoard);

                        tempNode.buildTree(tempBoard, lastPlayer, depth);

                        children = new ArrayList<Node>();
                        children.add(tempNode);
                    }
                }
            }
        }

        private int miniMax(int alpha, int beta){
            if (depth == 0)
                return ;

            if (depth % 2 == 0){
                value = Integer.MIN_VALUE;
                for (Node child : children){
                    value = Math.max(value, child.miniMax(alpha, beta));
                    alpha = Math.max(alpha, value);
                    if(alpha <= beta)
                        break;
                }

                return value;
            }

            else {
                value = Integer.MAX_VALUE;
                for (Node child : children){
                    value = Math.min(value, child.miniMax(alpha, beta));
                    beta = Math.min(beta, value);
                    if(alpha <= beta)
                        break;
                }
                return value;
            }
        }

        private int evaluateNode(){


            return 0;
        }

    }



    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {
        Node root;

        root = new Node(0, board);
        root.miniMax(alphaValue, betaValue);

        root.buildTree(board, board.getLastPlayerIndex(), 0);
        return 0;
    }
}
