package com.isd.ryabov;

import com.isd.ryabov.console.ConsoleMenu;
import com.isd.ryabov.logic.GameRule;
import com.isd.ryabov.model.Computer;
import com.isd.ryabov.security.HMACUtil;
import com.isd.ryabov.security.SecurityKeyUtil;

import java.util.*;

public class Game {

    private static Scanner scanner;
    private static GameRule gameRule;
    private static ConsoleMenu consoleMenu;
    private static Computer computer;
    private static HMACUtil hmacUtil;
    private static SecurityKeyUtil securityKeyUtil;

    public static void main(String[] args) {
        init(args);

        for (;;) {
            String computerMove = computer.getComputerMove();
            String key = securityKeyUtil.generateKey();
            String computerMoveHMAC = hmacUtil.generateHMAC(computerMove, key);
            consoleMenu.printHMAC(computerMoveHMAC);

            String playerInput;
            do {
                consoleMenu.printNewLine();
                consoleMenu.printMenu();
                consoleMenu.printMessageBeforePlayerMove();
                playerInput = scanner.nextLine();

                if (consoleMenu.isExitOptionValue(playerInput)) System.exit(0);
                if (consoleMenu.isHelpOptionValue(playerInput)) consoleMenu.printTable();
            } while (!consoleMenu.isMoveOptionValue(playerInput));

            String playerMove = consoleMenu.getMoveByOptionValue(playerInput);
            consoleMenu.printPlayerMove(playerMove);
            consoleMenu.printComputerMove(computerMove);
            consoleMenu.printGameResult(gameRule.getGameResult(playerMove, computerMove));
            consoleMenu.printHMACKey(key);
            consoleMenu.printNewLine();
        }
    }

    private static void init(String[] args) {
        try {
            validateGameArgs(args);
            initGameObjects(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void validateGameArgs(String[] args) throws IllegalArgumentException {
        String message = "";
        if (args.length < 3) {
            message += " - The number of parameters is less than 3" + System.lineSeparator();
        } else if (args.length % 2 == 0) {
            message += " - The number of parameters is even" + System.lineSeparator();
        }
        if (Arrays.stream(args).distinct().count() != args.length) message += " - Parameters contain duplicates" + System.lineSeparator();
        if (!message.isEmpty()) {
            throw new IllegalArgumentException("ERROR: The game isn't running." + System.lineSeparator() +
                    message +
                    "ATTENTION:" + System.lineSeparator() +
                    " - The number of parameters must be greater than or equal to 3" + System.lineSeparator() +
                    " - The number of parameters must be odd" + System.lineSeparator() +
                    " - Parameters must not contain duplicates");
        }
    }

    private static void initGameObjects(String[] args) throws Exception {
        try {
            scanner = new Scanner(System.in);
            gameRule = new GameRule(args);
            consoleMenu = new ConsoleMenu(gameRule);
            computer = new Computer(gameRule.getMoves());
            hmacUtil = new HMACUtil();
            securityKeyUtil = new SecurityKeyUtil();
        } catch (Exception e) {
            throw new Exception("ERROR: The game isn't running. An unexpected exception occurred while loading game parameters", e);
        }
    }
}