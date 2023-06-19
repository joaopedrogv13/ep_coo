/**
 * Classe que contém informações e ações básicas relacionadas aos jogadores
 */
public class Player {
    private String name;
    private Color pieceColor; // cor das pecas do jogador
    private Card[] cards; // cartas na mao do jogador

    /**
     * Construtor que define informações básicas do jogador
     * 
     * @param name       Nome do jogador
     * @param pieceColor Cor das peças do jogador
     * @param cards      Cartas na mão do jogador
     */
    public Player(String name, Color pieceColor, Card[] cards) {
        this.name = name;
        this.pieceColor = pieceColor;
        this.cards = cards;
    }

    /**
     * Construtor que define informações básicas do jogador
     * 
     * @param name       Nome do jogador
     * @param pieceColor Cor das peças do jogador
     * @param card1      A primeira carta na mão do jogador
     * @param card2      A segunda carta na mão do jogador
     */
    public Player(String name, Color pieceColor, Card card1, Card card2) {
        this.name = name;
        this.pieceColor = pieceColor;
        Card[] cards = { card1, card2 };
        this.cards = cards;
    }

    /**
     * Método que devolve o nome do jogador(a)
     * 
     * @return String com o nome do jogador(a)
     */
    public String getName() {
        return name;
    }

    /**
     * Método que devolve a cor das peças do jogador
     * 
     * @return Enum Color com a cor das peças do jogador
     */
    public Color getPieceColor() {
        return pieceColor;
    }

    /**
     * Método que devolve as cartas da mão do jogador
     * 
     * @return Booleano (class card) true para caso seja um mestre e false caso contrário
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * Método que troca uma carta da mão por outra carta (idealmente da mesa)
     * 
     * @param oldCard A carta que será substituída
     * @param newCard A carta que irá substituir
     * @exception InvalidCardException Caso a carta não esteja na mão do jogador
     *                                 e/ou na mesa
     */

     //obs talvez tenha que trocar a card no array cads tambem
    protected void swapCard(Card oldCard, Card newCard) throws InvalidCardException {
        // Verifica se a carta antiga está na mão do jogador
        /* if (oldCard != card1 || oldCard != card2) {
            throw new InvalidCardException("A carta antiga não está na mão do jogador.");
        } */

        // Realiza a troca de cartas
        if (oldCard.equals(this.cards[0])) {
            this.cards[0] = newCard;
        } else {
            this.cards[1] = newCard;
        }
    }

}

        