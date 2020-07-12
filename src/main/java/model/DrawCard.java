package model;

import java.util.List;

public class DrawCard extends CardBaseResponse{
	private static final long serialVersionUID = 829231865295544116L;
	
	private List<Card> cards;

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}
