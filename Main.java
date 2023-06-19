import java.util.Scanner;

public class Main {

    static int[] transformCoordinates(int row, int col, Color currentPlayerColor) {
        int[] transformedCoords = new int[2];
        
        if (currentPlayerColor.equals(Color.RED)) {
            transformedCoords[0] = row;
            transformedCoords[1] = col;
        } else {
            transformedCoords[0] = 5 - 1 - row;
            transformedCoords[1] = 5 - 1 - col;
        }
        
        return transformedCoords;
    }

    static void printPlayerHand(Player player) {
        System.out.println("Mao do jogador " + player.getName());
        Card card1 = player.getCards()[0];
        printCardMoves(card1);
        Card card2 = player.getCards()[1];
        printCardMoves(card2);
    }

    static void printCardMoves (Card card) {
        System.out.println("Carta " + card.getName() + " | " + " cor " + card.getColor());
        for (int i = 0; i < card.getPositions().length; i++) {
            System.out.println("(" + card.getPositions()[i].getRow() + ", " + card.getPositions()[i].getCol() + ")");
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Criar novo jogo
        Game game = new GameImpl();
        

        Player redPlayer = game.getRedPlayer();
        Player bluePlayer = game.getBluePlayer();

        Card tableCard;
        /* tableCard = game.getTableCard();
        Player currentPlayer = game.getCurrentPlayer();
        game.printBoard();
        
        game.checkVictory(currentPlayer.getPieceColor());
        game.checkVictory(currentPlayer.getPieceColor()); */
        //começando o jogo
        while (true) {
            tableCard = game.getTableCard();
            System.out.println();
            System.out.println("Carta da mesa: ");
            printCardMoves(tableCard);

            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("Turno do jogador " + currentPlayer.getName());
            
            printPlayerHand(currentPlayer);
            game.printBoard();
            System.out.println("Escolha uma carta (0 ou 1) para jogar");
            int cardIndex = in.nextInt();
            Card currentCard = currentPlayer.getCards()[cardIndex];

            System.out.println("Escolha a peca que sera movida (forneca as coordenadas de 0 a 4)");
            int pieceRow = in.nextInt();
            int pieceCol = in.nextInt();
            // int[] coordinates = transformCoordinates(pieceRow, pieceCol, currentPlayer.getPieceColor());
            // Position currentPosition = new Position(coordinates[0], coordinates[1]);
            Position currentPosition = new Position(pieceRow, pieceCol);
            Piece currentPiece = game.getPiece(currentPosition);

            System.out.println("Escolha o movimento a ser efetuado pela carta " + currentCard.getName());
            printCardMoves(currentCard);
            int moveIndex = in.nextInt();
            Position destPos = currentCard.getPositions()[moveIndex];

            game.makeMove(currentCard, destPos, currentPosition);
            game.printBoard();
            if (game.checkVictory(currentPlayer.getPieceColor())) {
                System.out.println("vitoria do jogador " + currentPlayer.getPieceColor());
                break;
            }
        }
    }
}
