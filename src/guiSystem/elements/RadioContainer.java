package guiSystem.elements;

import java.util.ArrayList;
import java.util.List;

public class RadioContainer {
	private List<ToggleButton> elems;
	private int selected;
	
	public RadioContainer(ToggleButton... elems) {
		this.elems = new ArrayList<>();
		for (int i = 0; i < elems.length; this.elems.add(elems[i++]));
		selected = -1;
	}
	
	public void addToggle(ToggleButton btn) {
		elems.add(btn);
	}
	
	public void handle(int i) {
		if (selected == i) {
			selected = -1;
		}
		if (selected != -1) {
			elems.get(selected).toggleOff();
		}
		selected = i;
	}
	

}
