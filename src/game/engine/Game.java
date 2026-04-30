package game.engine;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;

		// Noureldin Hany Hassan --- Milestone 2 Constructor Additions --- create problems for milestone 1
        ArrayList<Monster> stationed = new ArrayList<>(allMonsters);
        stationed.remove(player);
        stationed.remove(opponent);
        
        // this.board.setStationedMonsters(stationed);
        // this.board.initializeBoard(DataLoader.readCells());
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}

	//...........................................................................................................................................

	// Hana Mohamed
	private Monster getCurrentOpponent() {
		 if (current == opponent) 
		        return player;
		 else   {
			 return opponent;
		 }
	     }
	 private int rollDice() {
		 return (int) (Math.random() * 6) + 1; 
	 }
	 Monster getWinner() {
		 if (player.getPosition() == Constants.WINNING_POSITION 
		            && player.getEnergy() >= Constants.WINNING_ENERGY) {
		        return player;
		    }

		  else if (opponent.getPosition() == Constants.WINNING_POSITION 
		            && opponent.getEnergy() >= Constants.WINNING_ENERGY) {
		        return opponent;
		    }
		  else { 
		 return null;
		}
	 }
	 
	 //Noureldin Hany Hassan... 
	 private void switchTurn() {
		    // If current is player, it becomes opponent. If it was opponent, it becomes player. 
		    if (current == player) {
		        current = opponent;
		    } else {
		        current = player;
		    }
		}

		private boolean checkWinCondition(Monster monster) {
		    // A monster wins if they reach the winning position AND have enough energy. 
		    return monster.getPosition() == Constants.WINNING_POSITION 
		           && monster.getEnergy() >= Constants.WINNING_ENERGY;
		}

		public void playTurn() throws InvalidMoveException {
		    if (current.isFrozen()) {
		        current.setFrozen(false); 
		        switchTurn(); 
		        return; 
		    }

		    int roll = rollDice(); 

		    board.moveMonster(current, roll, getCurrentOpponent());
		    switchTurn(); 
		}

		public void usePowerup() throws OutOfEnergyException {
        if (current.getEnergy() < Constants.POWERUP_COST) {
           
            throw new OutOfEnergyException();
        }

        current.setEnergy(current.getEnergy() - Constants.POWERUP_COST); 
        current.executePowerupEffect(getCurrentOpponent()); 
    }



	//...........................................................................................................................................
	
	
}