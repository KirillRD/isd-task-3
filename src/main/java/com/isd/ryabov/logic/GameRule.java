package com.isd.ryabov.logic;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class GameRule {

    public enum Outcome {
        WIN,
        DRAW,
        LOSE
    }

    private final Map<String, Map<String, Outcome>> rules;

    public GameRule (String[] moves) {
        this.rules = generateRules(moves);
    }

    private Map<String, Map<String, Outcome>> generateRules(String[] moves) {
        return IntStream.range(0, moves.length).collect(LinkedHashMap::new, (a,i) -> a.put(moves[i],
                IntStream.range(0, moves.length).collect(LinkedHashMap::new, (b,j) -> b.put(
                        moves[j],
                        i == j ? Outcome.DRAW : (j + moves.length - i) % moves.length <= moves.length/2 ? Outcome.LOSE : Outcome.WIN
                ), Map::putAll)
        ), Map::putAll);
    }

    public Outcome getGameResult(String playerMove, String computerMove) {
        return rules.get(playerMove).get(computerMove);
    }

    public Set<String> getMoves() {
        return rules.keySet();
    }

    public Map<String, Map<String, Outcome>> getRules() {
        return rules;
    }
}
