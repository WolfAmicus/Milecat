package game.engine.cells;

public class ConveyorBelt extends TransportCell {

	private int effect;

	public ConveyorBelt(String name, int effect) {
		super(name, effect);
		this.effect = effect;	}

}