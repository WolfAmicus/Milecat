package game.engine.cells;

import game.engine.Board;
import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}
	@Override
    public void onLand(Monster landingMonster, Monster opponentMonster) {

        super.onLand(landingMonster, opponentMonster);

        if (activated) return;

        // 🔹 same role
        if (landingMonster.getRole() == getRole()) {

            modifyCanisterEnergy(landingMonster, energy);

            for (Monster m : Board.getStationedMonsters()) {
                if (m.getRole() == landingMonster.getRole()) {
                    modifyCanisterEnergy(m, energy);
                }
            }

            activated = true;
        }

        else {

            if (landingMonster.isShielded()) {
                landingMonster.setShielded(false);
                return;
            }

            modifyCanisterEnergy(landingMonster, -energy);

            for (Monster m : Board.getStationedMonsters()) {
                if (m.getRole() == landingMonster.getRole()) {
                    modifyCanisterEnergy(m, -energy);
                }
            }

            activated = true;
        }
    }

    @Override
    public void modifyCanisterEnergy(Monster monster, int value) {
        monster.setEnergy(value);
    }
}