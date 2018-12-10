import java.util.ArrayList;
import java.util.Random;

public class Deck{
	private ArrayList<Card> cards;
	private ArrayList<Card> usedCard;
	private ArrayList<Card> openCard;
	public int nUsed;
	Random ran = new Random();
	public Deck(int nDeck){
		cards = new ArrayList<Card>();
		usedCard = new ArrayList<Card>();
		openCard = new ArrayList<Card>();
		for(int x=1;x<= nDeck;x++) {
			for(int y=1;y<=4;y++) {
				for(int z=1;z<=13;z++) {
					if(y == 1) {
						Card c = new Card(Card.Suit.Club,z);
						cards.add(c);
					}
					else if (y == 2) {
						Card c = new Card(Card.Suit.Diamond,z);
						cards.add(c);
					}
					else if (y == 3) {
						Card c = new Card(Card.Suit.Heart,z);
						cards.add(c);
					}
					else if (y == 4) {
						Card c = new Card(Card.Suit.Spade,z);
						cards.add(c);
					}
				}
			}
		}
		shuffle();
	}
	public void printDeck(){
		//Hint: print all items in ArrayList<Card> cards, 
		//TODO: please implement and reuse printCard method in Card class (5 points)
		for(int i = 0;i < cards.size();i++) 
		{
			Card c = cards.get(i);
			c.printCard();
		}

	}
	public ArrayList<Card> getAllCards(){
		return cards;
	}
	public void shuffle() {
	    nUsed = 0;
	    cards.addAll(usedCard);
	    usedCard.clear();
	    openCard.clear();
	    for(int i = 0;i < cards.size() ;i++) 
	    {
	    int tmp = ran.nextInt(cards.size());
	      Card take =cards.get(tmp);
	      cards.set(tmp,cards.get(i));
	      cards.set(i,take);     
	    }
	 }
	public Card getOneCard(boolean isOpened) {
		if(cards.size() == 0) {
			shuffle();
		}
		Card c = cards.get(0);
		cards.remove(0);
		usedCard.add(c);
		if(isOpened = true) {
			openCard.add(c);
		}
		nUsed ++;
		return c;
	}
	 public ArrayList<Card> getOpenedCard(){
		return openCard;
	 }
}