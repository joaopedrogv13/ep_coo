import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que contém informações das cartas
 */
public class Card {

    private String name;
    private Color color;
    private Position[] positions;

    /**
     * Construtor que define os principais atributos de uma cara
     * 
     * @param name      Nome da carta
     * @param color     Cor da carta
     * @param positions Todas as posições relativas de movimento
     */
    public Card(String name, Color color, Position[] positions) {
        this.name = name;
        this.color = color;
        this.positions = positions;
    }

    /**
     * Método que devolve o nome da carta
     * 
     * @return String que contém o nome da carta
     */
    public String getName() {
        return name;
    }

    /**
     * Método que devolve a cor da carta
     * 
     * @return Enum Color que contém a cor da carta
     */
    public Color getColor() {
        return color;
    }

    /**
     * Método que devolve todas as possíveis posições relativas de movimento.
     * A posição atual da peça é o ponto de origem (0,0). Uma carta possui as
     * possíveis posições de movimento em relação ao ponto de origem.
     * 
     * @return Um array de Position contendo todas as possíveis posições de
     *         movimento em relação ao ponto de origem
     */
    public Position[] getPositions() {
        return positions;
    }

    /**
     * Método que cria todas as cartas do jogo, embaralha-as e devolve as 5 que
     * serão utilizadas na partida.
     * 
     * @return Vetor de cartas com todas as cartas do jogo
     */
    public static Card[] createCards() {
        Card[] cards = new Card[5];

        List<Card> allCards = new ArrayList<Card>();

        Position[] tigerPositions = {
                new Position(-2, 0),
                new Position(1, 0),
        };
        allCards.add(new Card("Tiger", Color.BLUE, tigerPositions));

        Position[] dragonPositions = {
                new Position(-1, -2),
                new Position(-1, 2),
                new Position(1, 1),
                new Position(1, -1)
        };
        allCards.add(new Card("Dragon", Color.RED, dragonPositions));

        Position[] frogPositions = {
                new Position(0, -2),
                new Position(1, 1),
                new Position(-1, -1)
        };
        allCards.add(new Card("Frog", Color.RED, frogPositions));

        Position[] rabbitPositions = {
                new Position(0, 2),
                new Position(1, -1),
                new Position(-1, 1)
        };
        allCards.add(new Card("Rabbit", Color.BLUE, rabbitPositions));

        Position[] crabPositions = {
                new Position(-1, 0),
                new Position(0, 2),
                new Position(0, -2)
        };
        allCards.add(new Card("Crab", Color.BLUE, crabPositions));

        Position[] elephantPositions = {
                new Position(0, -1),
                new Position(0, 1),
                new Position(-1, -1),
                new Position(-1, 1)
        };
        allCards.add(new Card("Elephant", Color.RED, elephantPositions));

        Position[] goosePositions = {
                new Position(0, -1),
                new Position(0, 1),
                new Position(-1, -1),
                new Position(1, 1)
        };
        allCards.add(new Card("Goose", Color.BLUE, goosePositions));

        Position[] roosterPositions = {
                new Position(0, 1),
                new Position(0, -1),
                new Position(1, -1),
                new Position(-1, 1)
        };
        allCards.add(new Card("Rooster", Color.RED, roosterPositions));

        Collections.shuffle(allCards);
        for (int i = 0; i < cards.length; i++) {
            cards[i] = allCards.get(i);
        }

        return cards;
    }
}