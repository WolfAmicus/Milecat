package game.engine;

import java.util.ArrayList;
import java.util.Collections;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.monsters.Monster;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
	}
	
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public static ArrayList<Card> getCards() {
		return cards;
	}
	
	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}



	//...........................................................................................................................................

		// Hana Mohamed
	private int[] indexToRowCol(int index) {
	    int row ;  
	    row = index/10;
	    int col;
        
	    if (row % 2 == 0) {
	        col = index % 10;
	    }
	     else {
	        col = 9 - (index % 10);
	    }
	    return new int[] {row, col};
   }
   private Cell getCell(int index) {
	   int[]  RC  = indexToRowCol(index);
	    return boardCells[RC[0]][RC [1]];
   }
   private void setCell(int index, Cell cell) {
	   int[] RC = indexToRowCol(index);
	   boardCells[RC[0]][RC[1]] = cell;
   }
   private void setCardsByRarity() {
	   ArrayList<Card> expandedCards = new ArrayList<Card>();

	    for (int i = 0; i < originalCards.size(); i++) {
	        Card card = originalCards.get(i);
	        int r = card.getRarity();

	        for (int j = 0; j < r; j++) {
	            expandedCards.add(card);
	         }
	      }
	      originalCards = expandedCards;
	}
   static void reloadCards() {
	   cards = new ArrayList<Card>();

	    for (int i = 0; i < originalCards.size(); i++) {
	      cards.add(originalCards.get(i));
	    }
	    Collections.shuffle(cards);
   }
   
   static Card drawCard() {
		    if (cards.isEmpty()) {
		        reloadCards();
		    }
		    return cards.remove(0);
		}

   // Noureldin Hany Hassan	
   public void initializeBoard(ArrayList<Cell> specialCells) {
	    
	    for (int i = 0; i < 100; i++) {
	        if (i % 2 == 0) {
	            setCell(i, new Cell()); 
	        } else {
	            setCell(i, new DoorCell()); 
	        }
	    }

	    int specialIndex = 0;

	   
	    for (int boardIndex : Constants.CARD_CELL_INDICES) {
	        setCell(boardIndex, specialCells.get(specialIndex));
	        specialIndex++;
	    }

	  
	    for (int boardIndex : Constants.CONVEYOR_BELT_INDICES) {
	        setCell(boardIndex, specialCells.get(specialIndex));
	        specialIndex++;
	    }

	    for (int boardIndex : Constants.CONTAMINATION_SOCK_INDICES) {
	        setCell(boardIndex, specialCells.get(specialIndex));
	        specialIndex++;
	    }

	   
	    for (int boardIndex : Constants.MONSTER_CELL_INDICES) {
	        setCell(boardIndex, specialCells.get(specialIndex));
	        specialIndex++;
	    }

	    
	    for (Monster m : stationedMonsters) {
	        int pos = m.getPosition(); 
	        Cell targetCell = getCell(pos); 
	        targetCell.setLandingMonster(m); 
	    }
	}
   
	//...........................................................................................................................................
	




	
}
