package game.engine;

import java.util.ArrayList;
import java.util.Collections;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.exceptions.InvalidMoveException;
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

		//.............. break milestone 1 test
		// setCardsByRarity();
    	// reloadCards();
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
        int specialIndex = 0;

        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (i % 2 == 0) {
                setCell(i, new Cell("Rest Cell")); 
            } else {
                setCell(i, specialCells.get(specialIndex));
                specialIndex++;
            }
        }

        for (int boardIndex : Constants.CARD_CELL_INDICES) {
            setCell(boardIndex, new CardCell("Card Cell"));
        }

       
        for (int boardIndex : Constants.CONVEYOR_CELL_INDICES) {
            setCell(boardIndex, specialCells.get(specialIndex));
            specialIndex++;
        }

        for (int boardIndex : Constants.SOCK_CELL_INDICES) {
            setCell(boardIndex, specialCells.get(specialIndex));
            specialIndex++;
        }

       
        for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length; i++) {
            int boardIndex = Constants.MONSTER_CELL_INDICES[i];
            Monster m = stationedMonsters.get(i);
            m.setPosition(boardIndex); 
            setCell(boardIndex, new MonsterCell("Monster Cell", m));
        }
    }
    
    public void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException {
        int oldPos = currentMonster.getPosition();
        currentMonster.move(roll);
        
        Cell landedCell = getCell(currentMonster.getPosition());
        landedCell.onLand(currentMonster, opponentMonster);
        
        if (currentMonster.getPosition() == opponentMonster.getPosition()) {
            currentMonster.setPosition(oldPos);
            throw new InvalidMoveException(); 
        }
        
        if (currentMonster.isConfused()) {
            currentMonster.decrementConfusion();
        }
        if (opponentMonster.isConfused()) {
            opponentMonster.decrementConfusion();
        }

        updateMonsterPositions(currentMonster, opponentMonster);
    }
    
    private void updateMonsterPositions(Monster player, Monster opponent) {
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            Cell c = getCell(i);
            if (!(c instanceof MonsterCell)) {
                c.setMonster(null);
            }
        }
        
        getCell(player.getPosition()).setMonster(player);
        getCell(opponent.getPosition()).setMonster(opponent);
    }
   
	//...........................................................................................................................................

}