package com.isd.ryabov.console;

import com.isd.ryabov.logic.GameRule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConsoleMenu {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String MENU_HEADER = "Available moves:" + NEW_LINE;
    private static final int INITIAL_MOVE_OPTION_VALUE = 1;
    private static final String MENU_SEPARATOR = " - ";
    private static final String EXIT_SYMBOL = "0";
    private static final String HELP_SYMBOL = "?";
    private static final String EXIT_OPTION = EXIT_SYMBOL + MENU_SEPARATOR + "Exit";
    private static final String HELP_OPTION = HELP_SYMBOL + MENU_SEPARATOR + "Help";
    private static final String MENU_FOOTER = EXIT_OPTION + NEW_LINE + HELP_OPTION;
    private static final String MESSAGE_BEFORE_PLAYER_MOVE = "Enter your move: ";
    private static final String PLAYER_MOVE_MESSAGE = "Your move: ";
    private static final String COMPUTER_MOVE_MESSAGE = "Computer move: ";
    private static final String HMAC_MESSAGE = "HMAC: ";
    private static final String HMAC_KEY_MESSAGE = "HMAC key: ";
    private static final String WIN_MESSAGE = "You win :)";
    private static final String DRAW_MESSAGE = "Draw :|";
    private static final String LOSE_MESSAGE = "You lose :(";

    private static final Map<GameRule.Outcome, String> gameResultMessages = Map.ofEntries(
            Map.entry(GameRule.Outcome.WIN, WIN_MESSAGE),
            Map.entry(GameRule.Outcome.DRAW, DRAW_MESSAGE),
            Map.entry(GameRule.Outcome.LOSE, LOSE_MESSAGE)
    );

    private final GameRule gameRule;
    private final Map<String, String> movesByOptionValue;
    private final String menu;
    private final ConsoleTable consoleTable;

    public ConsoleMenu(GameRule gameRule) {
        this.gameRule = gameRule;
        this.movesByOptionValue = generateMovesByOptionValue();
        this.menu = generateMenu();
        this.consoleTable = new ConsoleTable(gameRule.getRules());
    }

    private Map<String, String> generateMovesByOptionValue() {
        AtomicInteger optionValue = new AtomicInteger(INITIAL_MOVE_OPTION_VALUE);
        return gameRule.getMoves().stream().collect(Collectors.toMap(key -> {
            key = String.valueOf(optionValue.get());
            optionValue.set(optionValue.get() + 1);
            return key;
        }, value -> value));
    }

    private String generateMenu() {
        StringBuilder stringBuilder = new StringBuilder(MENU_HEADER);
        movesByOptionValue.forEach((optionValue, move) -> {
            stringBuilder.append(optionValue).append(MENU_SEPARATOR).append(move).append(NEW_LINE);
        });
        stringBuilder.append(MENU_FOOTER);
        return stringBuilder.toString();
    }

    public String getMoveByOptionValue(String optionValue) {
        return movesByOptionValue.get(optionValue);
    }

    public boolean isMoveOptionValue(String optionValue) {
        return movesByOptionValue.containsKey(optionValue);
    }

    public boolean isExitOptionValue(String optionValue) {
        return optionValue.equals(EXIT_SYMBOL);
    }

    public boolean isHelpOptionValue(String optionValue) {
        return optionValue.equals(HELP_SYMBOL);
    }

    public void printMenu() {
        System.out.println(menu);
    }

    public void printTable() {
        System.out.println(consoleTable.getTable());
    }

    public void printMessageBeforePlayerMove() {
        System.out.print(MESSAGE_BEFORE_PLAYER_MOVE);
    }

    public void printPlayerMove(String move) {
        System.out.println(PLAYER_MOVE_MESSAGE + move);
    }

    public void printComputerMove(String move) {
        System.out.println(COMPUTER_MOVE_MESSAGE + move);
    }

    public void printGameResult(GameRule.Outcome outcome) {
        System.out.println(gameResultMessages.get(outcome));
    }

    public void printHMAC(String hmac) {
        System.out.println(HMAC_MESSAGE + hmac);
    }

    public void printHMACKey(String hmacKey) {
        System.out.println(HMAC_KEY_MESSAGE + hmacKey);
    }

    public void printNewLine() {
        System.out.println();
    }
}
