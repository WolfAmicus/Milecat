package game.engine.cells;

import game.engine.Constants;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class ContaminationSock extends TransportCell implements CanisterModifier {

	private int effect;

	public ContaminationSock(String name, int effect) {
		super(name, effect);
		this.effect = effect;
	}
	@Override
    public void transport(Monster monster) {

        int newPos = monster.getPosition() + effect;

        if (newPos < 0) newPos = 0;

        monster.setPosition(newPos);

        // shield logic
        if (monster.isShielded()) {
            monster.setShielded(false);
            return;
        }

        monster.setEnergy(-Constants.SLIP_PENALTY);
    }

    @Override
    public void modifyCanisterEnergy(Monster monster, int value) {
        monster.setEnergy(value);
    }

}
