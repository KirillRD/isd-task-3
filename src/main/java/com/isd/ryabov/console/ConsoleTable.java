package com.isd.ryabov.console;

import com.github.freva.asciitable.AsciiTable;
import com.isd.ryabov.logic.GameRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ConsoleTable {

    private static final String DESCRIPTION = "Player\\Computer";

    private final String table;

    public ConsoleTable(Map<String, Map<String, GameRule.Outcome>> rules) {
        this.table = generateTable(rules);
    }

    private String generateTable(Map<String, Map<String, GameRule.Outcome>> rules) {
        List<String> header = Stream.concat(Stream.of(DESCRIPTION), rules.keySet().stream()).toList();

        List<List<String>> data = rules.keySet().stream().collect(
                ArrayList::new,
                (row, move) -> row.add(Stream.concat(
                        Stream.of(move),
                        rules.get(move).values().stream().map(GameRule.Outcome::name)
                ).toList()),
                List::addAll
        );

        return AsciiTable.getTable(
                header.toArray(String[]::new),
                data.stream().map(array -> array.toArray(String[]::new)).toArray(String[][]::new)
        );
    }

    public String getTable() {
        return table;
    }
}
