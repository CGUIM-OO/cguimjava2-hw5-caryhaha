public class Card{
	private Suit suit;
	private int rank; //1~13
	/**
	 * @param s suit
	 * @param r rank
	 */
	enum Suit {Club,Diamond,Heart,Spade};
	
	public Card(Suit s,int r){
		suit=s;
		rank=r;
	}
	public void printCard(){
				System.out.println(getSuit()+","+getRank());
		}

	public Suit getSuit(){
		return suit;
	}
	public int getRank(){
		return rank;
	}
}
