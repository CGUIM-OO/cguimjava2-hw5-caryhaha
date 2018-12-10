import java.util.ArrayList;

public class Table {
	private final int MAXPLAYER = 4;
	private Deck dk;
	private Player[] Players;
	private Dealer dlr;
	private int[] pos_betArray;
	private ArrayList<Card> DealerCard=new ArrayList<Card>();
	private ArrayList<Card> Player1Card=new ArrayList<Card>();
	private ArrayList<Card> Player2Card=new ArrayList<Card>();
	private ArrayList<Card> Player3Card=new ArrayList<Card>();
	private ArrayList<Card> Player4Card=new ArrayList<Card>();
	public Table(int nDeck) {
		Deck d = new Deck(nDeck);
		dk = d;
		Players = new Player[MAXPLAYER];
		pos_betArray = new int[MAXPLAYER];
	}
	public void set_player(int pos, Player p) {
		Players[pos] = p;
	}
	public Player[] get_player() {
		return Players;
	}
	public void set_dealer(Dealer d) {
		dlr = d;
	}
	public Card get_face_up_card_of_dealer() {
		ArrayList<Card> a = new ArrayList();
		a = dlr.getOneRoundCard();
		Card c = a.get(1);
		return c;
	}
	private void ask_each_player_about_bets() {
		for(int i = 0;i<4;i++) {
			if(Players[i]!=null) {
				Player p =Players[i];
				p.sayHello();
				p.makeBet();
				pos_betArray[i] = p.makeBet();
			}
		}
	}
	private void distribute_cards_to_dealer_and_players() {
		Player1Card.add(dk.getOneCard(true));
		Player1Card.add(dk.getOneCard(true));
		Players[0].setOneRoundCard(Player1Card);
		Player2Card.add(dk.getOneCard(true));
		Player2Card.add(dk.getOneCard(true));
		Players[1].setOneRoundCard(Player2Card);
		Player3Card.add(dk.getOneCard(true));
		Player3Card.add(dk.getOneCard(true));
		Players[2].setOneRoundCard(Player3Card);
		Player4Card.add(dk.getOneCard(true));
		Player4Card.add(dk.getOneCard(true));
		Players[3].setOneRoundCard(Player4Card);
		DealerCard.add(dk.getOneCard(false));
		DealerCard.add(dk.getOneCard(true));
		dlr.setOneRoundCard(DealerCard);
		System.out.println("Dealer's face up card is " + get_face_up_card_of_dealer().getSuit()+","+get_face_up_card_of_dealer().getRank());
	}
	private void ask_each_player_about_hits() {
		boolean hit=false;
		for(int i=0;i<4;i++) {
			do{
				hit=Players[i].hit_me(this); 
				if(hit){
					ArrayList<Card> a = Players[i].getOneRoundCard();
					a.add(dk.getOneCard(true));
					Players[i].setOneRoundCard(a);
					System.out.print("Hit! ");
					System.out.println(Players[i].getName()+"'s Cards now:");
					for(Card c : a){
						c.printCard();
					}
				}
				else{
					System.out.println(Players[i].getName()+", Pass hit!");
					System.out.println(Players[i].getName()+", Final Card:");
					for(Card c : Players[i].getOneRoundCard()){
						c.printCard();
					}
				}
			}while(hit);
		}
	}
	private void ask_dealer_about_hits() {
		boolean hit=false;
		do{
			hit=dlr.hit_me(this); //this
			if(hit){
				DealerCard.add(dk.getOneCard(true));
				dlr.setOneRoundCard(DealerCard);
				System.out.print("Hit! ");
				System.out.println("Dealer's Cards now:");
				for(Card c : DealerCard){
					c.printCard();
				}
			}
			else{
				System.out.println("Dealer, Pass hit!");
				System.out.println("Dealer, Final Card:");
				for(Card c : DealerCard){
					c.printCard();
				}
			}
		}while(hit);
		System.out.println("Dealer's hit is over!");
	}
	private void calculate_chips() {
		System.out.print("Dealer's card value is " +dlr.getTotalValue() +" ,Cards:");
		dlr.printAllCard();
		for(int i = 0;i<Players.length;i++) {
			System.out.println(Players[i].getName() + " card value is " + Players[i].getTotalValue());
			if(Players[i].getTotalValue()>21&&dlr.getTotalValue()>21) {
				System.out.println(",chips have no change! The Chips now is:"+Players[i].getCurrentChips());
			}
			else if(dlr.getTotalValue()>Players[i].getTotalValue()&&dlr.getTotalValue()<=21) {
				int c = Players[i].getCurrentChips();
				c -= Players[i].makeBet();
				Players[i].setchips(c);
				System.out.println(", Loss "+Players[i].makeBet()+" Chips, the Chips now is: "+c);
			}
			else if(dlr.getTotalValue()>Players[i].getTotalValue()&&dlr.getTotalValue()>21) {
				int c = Players[i].getCurrentChips();
				c += Players[i].makeBet();
				Players[i].setchips(c);
				System.out.println(",Get "+Players[i].makeBet()+" Chips, the Chips now is: "+c);
			}
			else if(dlr.getTotalValue()<Players[i].getTotalValue()&&Players[i].getTotalValue()<=21) {
				int c = Players[i].getCurrentChips();
				c += Players[i].makeBet();
				Players[i].setchips(c);
				System.out.println(",Get "+Players[i].makeBet()+" Chips, the Chips now is: "+c);
			}
			else if(dlr.getTotalValue()<Players[i].getTotalValue()&&Players[i].getTotalValue()>21) {
				int c = Players[i].getCurrentChips();
				c -= Players[i].makeBet();
				Players[i].setchips(c);
				System.out.println(", Loss "+Players[i].makeBet()+" Chips, the Chips now is: "+c);
			}
			else if(dlr.getTotalValue()==Players[i].getTotalValue())
				System.out.println(",chips have no change! The Chips now is:"+Players[i].getCurrentChips());
		}	
	}
	public int[] get_players_bet() {
		return pos_betArray;
	}
	public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}
}
