package guiSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guiSystem.animations.Animation;
import guiSystem.animations.CustomAnimation;
import settings.Constants;

public class NavigationSystem {
	private static final float NAVIGATION_TIME = (float)Constants.get("NAVIGATION_TIME");

	
	/** from set, to set, animations associated with transition */
	private Map<String, Map<String, List<Animation>>> transitionAnimations;
	private Map<String, Map<String, List<Animation>>> transitionAnimationsReversed;
	private Map<String, NavigationSet> navs;
	private String active;

	public NavigationSystem() {
		navs = new HashMap<>();
		transitionAnimations = new HashMap<>();
		transitionAnimationsReversed = new HashMap<>();
	}
	
	public void addNav(NavigationSet nav) {
		if (navs.isEmpty()) active = nav.getName();
		navs.put(nav.getName(), nav);
	}
	
	public NavigationSet getNav(String name) {
		return navs.get(name);
	}

	/**
	 * @return the active
	 */
	public boolean isActive(String name) {
		return active.contains(name);
	}

	/**
	 * @param to the active to set
	 */
	public void switchTo(String to) {
		String from = active;
		if (active.contains(";")) from = active.split(";")[1];
		if (from.equals(to)) return;
		getNav(to).setVisible(true);
		this.active = active + ";" + to;
		transitionAnimations.get(from).get(to).forEach(anim->anim.playRestart());
		transitionAnimationsReversed.get(from).get(to).forEach(anim->anim.playReversedRestart());
	}
	
	/**
	 * only one way
	 * @param from
	 * @param to
	 */
	public void addStateTransition(String from, String to) {
		Animation fromTo = new CustomAnimation(NAVIGATION_TIME, f->{});
		fromTo.setOnFinished(()->{
			active = to;
			getNav(from).setVisible(false);
		});
		addTransitionChecked(from,to,fromTo);
	}
	
	/**
	 * ensure the animation is not edited after it is added to this navigation system.
	 * TODO edit the system to store hald the animations, simly playing some in reverse instead
	 * 	of copying them as a new reversed animation.
	 * @param from
	 * @param to
	 * @param anim
	 */
	public void addTransition(String from, String to, Animation anim) {
		addTransitionChecked(from, to, anim);
		addReversedTransitionChecked(to, from, anim);
	}
	
	private void addTransitionChecked(String from, String to, Animation anim) {
		if (!transitionAnimations.containsKey(from)) transitionAnimations.put(from, new HashMap<>());
		if (!transitionAnimations.get(from).containsKey(to)) transitionAnimations.get(from).put(to, new ArrayList<>());
		transitionAnimations.get(from).get(to).add(anim);
	}
	
	private void addReversedTransitionChecked(String from, String to, Animation anim) {
		if (!transitionAnimationsReversed.containsKey(from)) transitionAnimationsReversed.put(from, new HashMap<>());
		if (!transitionAnimationsReversed.get(from).containsKey(to)) transitionAnimationsReversed.get(from).put(to, new ArrayList<>());
		transitionAnimationsReversed.get(from).get(to).add(anim);
	}
	
	public Collection<NavigationSet> getSets() {
		return navs.values();
	}
	
}
