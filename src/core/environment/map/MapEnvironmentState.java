package core.environment.map;

import java.util.HashMap;

import core.agent.Agent;
import core.agent.EnvironmentState;
import core.util.datastructure.Pair;

/**
 * @author Ciaran O'Reilly
 * 
 */
public class MapEnvironmentState implements EnvironmentState {
	private java.util.Map<Agent, Pair<String, Double>> agentLocationAndTravelDistance = new HashMap<>();

	public MapEnvironmentState() {

	}

	public String getAgentLocation(Agent a) {
		Pair<String, Double> locAndTDistance = agentLocationAndTravelDistance
				.get(a);
		if (null == locAndTDistance) {
			return null;
		}
		return locAndTDistance.getFirst();
	}

	public Double getAgentTravelDistance(Agent a) {
		Pair<String, Double> locAndTDistance = agentLocationAndTravelDistance
				.get(a);
		if (null == locAndTDistance) {
			return null;
		}
		return locAndTDistance.getSecond();
	}

	public void setAgentLocationAndTravelDistance(Agent a, String location,
			Double travelDistance) {
		agentLocationAndTravelDistance.put(a, new Pair<String, Double>(
				location, travelDistance));
	}
}
