import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class GameImpl implements Game {

    private final Player redPlayer; // jogador das cartas vermelhas
    private final Player bluePlayer; // jogador das cartas azuis
    private Spot[][] board; // matriz de posicoes
    private Card tableCard; // carta da mesa
    private Card[] cards; // cartas no jogo

    private boolean redTurn; //turno 

    /**
     * Construtor que inicia o jogo com as informações básicas
     */
    public GameImpl() {
        this.board = Spot.createBoard(5);
        this.cards = Card.createCards();

        this.redPlayer = new Player("Red", Color.RED, this.cards[0], this.cards[1]);
        this.bluePlayer = new Player("Blue", Color.BLUE, this.cards[2], this.cards[3]);

        this.tableCard = this.cards[4];

        if (this.cards[4].getColor().equals(Color.RED)) {
            redTurn = true;
        } else
            redTurn = false;

    }

    public GameImpl(String redName, String blueName){
        this.board = Spot.createBoard(5);
        this.cards = Card.createCards();

        this.redPlayer = new Player(redName, Color.RED, this.cards[0], this.cards[1]);
        this.bluePlayer = new Player(blueName, Color.BLUE, this.cards[2], this.cards[3]);

        this.tableCard = this.cards[4];
        
        if (this.cards[4].getColor().equals(Color.RED)) {
            redTurn = true;
        } else
            redTurn = false;
    }

    public GameImpl(String redName, String blueName, Card[] cards){
        this.board = Spot.createBoard(5);

        List<Card> cardsList = new ArrayList<Card>(Arrays.asList(cards));

        if (cardsList.contains(null)) {
            throw new IllegalArgumentException("Cards cannot be null");
        }

        Collections.shuffle(cardsList);

        this.cards = cardsList.subList(0, 5).toArray(new Card[5]);

        this.redPlayer = new Player(redName, Color.RED, this.cards[0], this.cards[1]);
        this.bluePlayer = new Player(blueName, Color.BLUE, this.cards[2], this.cards[3]);

        this.tableCard = this.cards[4];

        if (this.cards[4].getColor().equals(Color.RED)) {
            redTurn = true;
        } else
            redTurn = false;
    }

    public Color getSpotColor(Position position) {
        return this.board[position.getRow()][position.getCol()].getColor();
    }

    public Piece getPiece(Position position) {
        return this.board[position.getRow()][position.getCol()].getPiece();

    }

    public Card getTableCard() {
        return tableCard;

    }

    public Player getCurrentPlayer() {
        Player currentPlayer = redTurn ? redPlayer : bluePlayer;
        return currentPlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    /**
     * Método que move uma peça
     * 
     * @param card       A carta de movimento que será usada
     * 
     * @param cardMove   Para onde ele irá movimentar
     * 
     * @param currentPos A posição da peça que ira ser movida
     * 
     * @exception IncorrectTurnOrderException Caso não seja a vez de um jogador
     *                                        fazer um movimento
     * @exception IllegalMovementException    Caso uma peça seja movida para fora do
     *                                        tabuleiro ou para uma posição onde já
     *                                        tem uma peça da mesma cor
     * @exception InvalidCardException        Caso uma carta que não está na mão do
     *                                        jogador seja usada
     * @exception InvalidPieceException       Caso uma peça que não está no
     *                                        tabuleiro seja usada
     */

    public void makeMove(Card card, Position cardMove, Position currentPos)
            throws IncorrectTurnOrderException, IllegalMovementException, InvalidCardException, InvalidPieceException {
        if (board[currentPos.getRow()][currentPos.getCol()].getPiece() == null) {
            // Nao ha nenhuma peca nessa posicao
            return;
        }
        // Verificar qual o jogador
        Player currentPlayer = getCurrentPlayer();
                    
        // verifica se é o jogador da vez
        if (board[currentPos.getRow()][currentPos.getCol()].getPiece() != null && !board[currentPos.getRow()][currentPos.getCol()].getPiece().getColor().equals(currentPlayer.getPieceColor())) {
            throw new IncorrectTurnOrderException("Não é a vez desse jogador.");
        }

        // verifica se a carta é valida
        if (!card.equals(currentPlayer.getCards()[0]) && !card.equals(currentPlayer.getCards()[1])) {
            throw new InvalidCardException("O jogador nao possui essa carta");
        }

        // verifica se a peca a ser movida é valida
        if (board[currentPos.getRow()][currentPos.getCol()].getPiece() != null && 
            !board[currentPos.getRow()][currentPos.getCol()].getPiece().Alive()) {
            throw new InvalidPieceException("Peça fora do tabulero");
        }
        
        // Verificar se o movimento é válido
        Position[] possibleMoves = card.getPositions();
        boolean validMove = false;
        for (Position move : possibleMoves) {
            if (move.equals(cardMove)) {
                validMove = true;
                break;
            }
        }
        if (!validMove) {
            throw new IllegalMovementException("O movimento selecionado não é válido para a carta escolhida.");
        }

        int movRow = cardMove.getRow();
        int movCol = cardMove.getCol();

        // mivimentaçao das pecas azuis sao invertidas no tabuleiro
        if (currentPlayer.getPieceColor().equals(Color.BLUE)) {
            movRow = movRow * (-1);
            movCol = movCol * (-1);
        }

        int currentRow = currentPos.getRow();
        int currentCol = currentPos.getCol();

        int destRow = currentRow + movRow;
        int destCol = currentCol + movCol;

        if ((destRow < 0 || destRow > 4) || (destCol < 0 || destCol > 4)) {
            throw new IllegalMovementException("Movimento excede o tabuleiro");
        }

        // Movimentacao da peca ou captura
        Piece movedPiece = board[currentRow][currentCol].getPiece();
        // libera o espaço antigo
        board[currentPos.getRow()][currentPos.getCol()].releaseSpot();
        Spot finalSpot = board[destRow][destCol];
        finalSpot.occupySpot(new Piece(movedPiece.getColor(), movedPiece.isMaster()));
        
        // altera a vez do jogador
        redTurn = !redTurn;
      
        //troca a carta do jogador que fez a jogada com a do tabuleiro
        currentPlayer.swapCard(card, this.tableCard);
        this.tableCard = card;
        
        return;

    }

    public boolean checkVictory(Color color) {

        // Verificar se o jogador da cor especificada possui o mestre do oponente
        Player currentPlayer = color.equals(Color.RED) ? redPlayer : bluePlayer;
        Player opponentPlayer = color.equals(Color.RED) ? bluePlayer : redPlayer;

        // Verificar se o mestre do jogador da cor especificada está no templo oposto
        if (currentPlayer.getPieceColor().equals(Color.RED)) {

            if (board[0][2].getPiece() != null && board[0][2].getPiece().isMaster() && board[0][2].getPiece().getColor().equals(Color.RED))
                return true; // O mestre do vermelho esta no templo azul
        } else {
            if (board[4][2].getPiece() != null && board[4][2].getPiece().isMaster() && board[4][2].getPiece().getColor().equals(Color.BLUE))
                return true; // O mestre do azul esta no templo vermelho
        }

        boolean masterAlive = false;
        
        // Verificar se todas as peças do oponente estão capturadas ou bloqueadas
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board[row][col].getPiece();
                if (piece != null) {
                    if (piece.getColor().equals(opponentPlayer.getPieceColor()) && piece.isMaster()) {
                        masterAlive = true; // mestre esta vivo
                    }
                }
            }

        }
        // Todas as condições de vitória foram atendidas
        if ((!masterAlive)) {
            return true;
        }
        return false;
    }

    public void printBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Spot spot = board[row][col];
                Piece piece = spot.getPiece();

                if (piece != null) {
                    if (piece.getColor().equals(Color.RED) && !piece.isMaster()) {
                        System.out.print("r ");
                    } else if (piece.getColor().equals(Color.BLUE) && !piece.isMaster()) {
                        System.out.print("b ");
                    } else if ((piece.getColor().equals(Color.RED) && piece.isMaster())) {
                        System.out.print("R ");
                    } else if ((piece.getColor().equals(Color.BLUE) && piece.isMaster())) {
                        System.out.print("B ");
                    }
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
}