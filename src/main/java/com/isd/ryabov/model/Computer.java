package com.isd.ryabov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Computer {

    private final Random random;
    private final List<String> moves;

    public Computer(Set<String> moves) {
        this.random = new Random();
        this.moves = convertMoves(moves);
    }

    private List<String> convertMoves(Set<String> moves) {
        return new ArrayList<>(moves);
    }

    public String getComputerMove() {
        return moves.get(random.nextInt(moves.size()));
    }
}
