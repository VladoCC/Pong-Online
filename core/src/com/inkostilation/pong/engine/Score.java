package com.inkostilation.pong.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Score {

    private final int maxScoreValue = 5;
    private Map<PlayerRole, Integer> points = new HashMap<>();

    public Score() {
        points.put(PlayerRole.FIRST, 0);
        points.put(PlayerRole.SECOND, 0);
    }

    public int getPlayerScore(PlayerRole role) {
        return points.get(role);
    }

    public int getMaxScoreValue() {
        return maxScoreValue;
    }

    public void addPlayerScore(PlayerRole role, int add)
    {
        int val = points.get(role);
        points.replace(role, val + add);
    }

    public long getMaxValueCount() {
        return points.values()
                .stream()
                .filter(o -> o >= getMaxValueCount())
                .count();
    }

    public List<PlayerRole> getMaxedPlayers() {
        return points.entrySet()
                .stream()
                .filter(o -> o.getValue() >= getMaxValueCount())
                .map(o -> o.getKey())
                .collect(Collectors.toList());
    }
}
