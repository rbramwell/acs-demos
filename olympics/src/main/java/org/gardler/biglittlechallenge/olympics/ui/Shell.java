package org.gardler.biglittlechallenge.olympics.ui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.gardler.biglittlechallenge.core.model.Card;
import org.gardler.biglittlechallenge.core.model.Hand;
import org.gardler.biglittlechallenge.core.ui.AbstractUI;
import org.gardler.biglittlechallenge.olympics.model.Deck;
import org.gardler.biglittlechallenge.olympics.model.Player;
import org.gardler.biglittlechallenge.olympics.tournament.Event;
import org.gardler.biglittlechallenge.olympics.tournament.Tournament;

public class Shell extends AbstractUI {

	private Tournament tournament;
	private List<Player> players;

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Shell() {
		super();
	}
	
	public void run() throws IOException {
		print("Welcome to the Olympics");
		while (true) {
			displayMainMenu();
			int choice = getChoice("\nSelect option: ");
			switch (choice) {
			case 1:
				print(tournament.toString());
				break;
			case 2:
				runTournament();
				break;
			case 9: 
				quit();
			default:
				print("Invalid selection");
			}
		}
	}

	private void runTournament() {
		int choice;
		Iterator<Event> events = tournament.getEvents().iterator();
		while (events.hasNext()) {
			Event event = events.next();
			displayEventMenu(event);
			choice = getChoice("\nSelect option: ");
			switch (choice) {
			case 1:
				play(event);
				break;
			case 9:
				quit();
			default:
				print("Invalid selection");
			}
		}
	}

	private void quit() {
		print("Thanks for playing.");
		System.exit(1);
	}

	protected int getChoice(String msg) {
		while (true) {
			inputFlush();
			print(msg);
			try {
				return Integer.valueOf(inString().trim()).intValue();
			}

			catch (NumberFormatException e) {
				System.out.println("Invalid input. Not an integer");
			}
		}
	}

	protected void print(String msg) {
		System.out.println(msg);
		System.out.flush();
	}

	protected void displayMainMenu() {
		print("\nOptions");
		print("=======");
		print("\t1. Describe Tournament");
		print("\t2. Start Tournament");
		print("\t9. Quit game");
	}
	
	protected void displayEventMenu(Event event) {
		print("\nOptions");
		print("=======");
		print("\t1. Play hand - " + event.getName());
		print("\t9. Quit game");
	}

	public static String inString() {
		int aChar;
		String result = "";
		boolean finished = false;

		while (!finished) {
			try {
				aChar = System.in.read();
				if (aChar < 0 || (char) aChar == '\n')
					finished = true;
				else if ((char) aChar != '\r')
					result = result + (char) aChar; // Enter into string
			} catch (java.io.IOException e) {
				System.out.println("Input error");
				finished = true;
			}
		}
		return result;
	}

	// Ensure the input stream is empty
	protected static void inputFlush() {
		@SuppressWarnings("unused")
		int dummy;
		@SuppressWarnings("unused")
		int bAvail;

		try {
			while ((System.in.available()) != 0)
				dummy = System.in.read();
		} catch (java.io.IOException e) {
			System.out.println("Input error");
		}
	}

	public void play(Event event) {
		event.playHand(players);
	}

	@Override
	public Card selectCard(org.gardler.biglittlechallenge.core.model.Player player, Hand hand) {
		print("\nYour hand currently contains the following cards:");
		
		Deck deck = (Deck) player.getDeck();
		
		Iterator<Card> cards = player.getDeck().getCards().values().iterator();
		while (cards.hasNext()) {
			Card card = cards.next();
			print("\t" + card.toString());
		}
		
		while (true) {
			print("Enter the name of the card you wish to play:");
			String key = inString();
			
			if (deck.getCards().containsKey(key)) {	
				return deck.getCharacter(key);
			} else {
				print("That's not a valid card name.");
			}
		}
	}
}
