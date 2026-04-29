package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	//steal energy from opponent
	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		 int stolen = Math.min(10, opponentMonster.getEnergy()); //(SCHEMER STEAL = 10)

	        opponentMonster.setEnergy(opponentMonster.getEnergy() - stolen); //Remove energy from opponent
	        this.setEnergy(this.getEnergy() + stolen); //Set new value
	}
	private int stealEnergyFrom(Monster target) {
		int stolen = Math.min(Constants.SCHEMER_STEAL, target.getEnergy());

	    target.setEnergy(target.getEnergy() - stolen);

	    return stolen;
	}
}
