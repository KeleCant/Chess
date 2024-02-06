package passoffTests.chessTests;

import chess.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashSet;
import java.util.Set;

import static passoffTests.TestFactory.*;

public class Phase1Failedtests {
    @Nested
    @DisplayName("Valid Move Tests")
    public class ValidMoveTests {
        @Test
        @DisplayName("Check Forces Movement")
        public void forcedMove() {

            var game = getNewGame();
            game.setBoard(loadBoard("""
                    | | | | | | | | |
                    | | | | | | | | |
                    | |B| | | | | | |
                    | | | | | |K| | |
                    | | |n| | | | | |
                    | | | | | | | | |
                    | | | |q| |k| | |
                    | | | | | | | | |
                    """));

            // Knight moves
            ChessPosition knightPosition = getNewPosition(4, 3);
            var validMoves = loadMoves(knightPosition, new int[][]{{3, 5}, {6, 2}});
            assertMoves(game, validMoves, knightPosition);

            // Queen Moves
            ChessPosition queenPosition = getNewPosition(2, 4);
            validMoves = loadMoves(queenPosition, new int[][]{{3, 5}, {4, 4}});
            assertMoves(game, validMoves, queenPosition);
        }


        @Test
        @DisplayName("Piece Completely Trapped")
        public void rookPinnedToKing() {

            var game = getNewGame();
            game.setBoard(loadBoard("""
                    | | | | | | | |Q|
                    | | | | | | | | |
                    | | | | | | | | |
                    | | | | | | | | |
                    | | | |r| | | | |
                    | | | | | | | | |
                    | |k| | | | | | |
                    | | | | | | | | |
                    """));

            ChessPosition position = getNewPosition(4, 4);
            Assertions.assertTrue(game.validMoves(position).isEmpty(),
                    "ChessGame validMoves returned valid moves for a trapped piece");
        }


        @Test
        @DisplayName("Pieces Cannot Eliminate Check")
        public void kingInDanger() {

            var game = getNewGame();
            game.setBoard(loadBoard("""
                    |R| | | | | | | |
                    | | | |k| | | |b|
                    | | | | |P| | | |
                    | | |Q|n| | | | |
                    | | | | | | | | |
                    | | | | | | | |r|
                    | | | | | |p| | |
                    | |q| | | | | | |
                    """));

            //get positions
            ChessPosition kingPosition = getNewPosition(7, 4);
            ChessPosition pawnPosition = getNewPosition(2, 6);
            ChessPosition bishopPosition = getNewPosition(7, 8);
            ChessPosition queenPosition = getNewPosition(1, 2);
            ChessPosition knightPosition = getNewPosition(5, 4);
            ChessPosition rookPosition = getNewPosition(3, 8);


            var validMoves = loadMoves(kingPosition, new int[][]{{6, 5}});

            assertMoves(game, validMoves, kingPosition);

            //make sure teams other pieces are not allowed to move
            Assertions.assertTrue(game.validMoves(pawnPosition).isEmpty(),
                    "ChessGame validMoves returned valid moves for a trapped piece");
            Assertions.assertTrue(game.validMoves(bishopPosition).isEmpty(),
                    "ChessGame validMoves returned valid moves for a trapped piece");
            Assertions.assertTrue(game.validMoves(queenPosition).isEmpty(),
                    "ChessGame validMoves returned valid moves for a trapped piece");
            Assertions.assertTrue(game.validMoves(knightPosition).isEmpty(),
                    "ChessGame validMoves returned valid moves for a trapped piece");
            Assertions.assertTrue(game.validMoves(rookPosition).isEmpty(),
                    "ChessGame validMoves returned valid moves for a trapped piece");
        }


        private void assertMoves(ChessGame game, Set<ChessMove> validMoves, ChessPosition position) {
            var actualMoves = new HashSet<>(game.validMoves(position));
            Assertions.assertEquals(validMoves, actualMoves,
                    "ChessGame validMoves did not return the correct moves");
        }
    }
}
