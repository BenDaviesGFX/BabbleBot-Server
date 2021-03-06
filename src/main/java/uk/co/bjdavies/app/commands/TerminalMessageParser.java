package uk.co.bjdavies.app.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BabbleBot, open-source Discord Bot
 * Licence: GPL V3
 * Author: Ben Davies
 * Class Name: TerminalParser.java
 * Compiled Class Name: TerminalParser.class
 * Date Created: 31/01/2018
 */

public class TerminalMessageParser implements MessageParser
{

    /**
     * This will parse the string inputted the by the user.
     *
     * @param message - The raw inputted message.
     * @return CommandContext
     */
    @Override
    public CommandContext parseString(String message)
    {
        return new CommandContext(parseCommandName(message), parseParams(message), parseValue(message), "Terminal");
    }


    /**
     * This will parse the value of the command if there is one.
     *
     * @param message - The raw inputted message.
     * @return String
     */
    private String parseValue(String message)
    {
        Matcher matcher = getParameterMatcher(message);
        message = message.replace(parseCommandName(message), "");
        while (matcher.find())
        {
            message = message.replace(matcher.group(), "");
        }
        message = message.trim();
        return message;
    }


    /**
     * This will parse the command name from the message.
     *
     * @param message - The raw inputted message.
     * @return String
     */
    private String parseCommandName(String message)
    {
        int indexOfFirstSpace = message.indexOf(" ");

        if (indexOfFirstSpace == -1)
        {
            return message;
        } else
        {
            return message.substring(0, indexOfFirstSpace).trim();
        }
    }


    /**
     * This will parse the parameters from the inputted message
     *
     * @param message - The raw inputted message.
     * @return Map(String, String)
     */
    private Map<String, String> parseParams(String message)
    {
        Map<String, String> params = new HashMap<>();
        Matcher matcher = getParameterMatcher(message);

        while (matcher.find())
        {
            String name = matcher.group(1);
            String value = matcher.group(2).replaceAll("\"", "");
            params.put(name, value);
        }
        return params;
    }


    /**
     * This will parse the parameters in the message and return a Matcher.
     *
     * @param message - The raw inputted message.
     * @return Matcher
     */
    private Matcher getParameterMatcher(String message)
    {
        String parameterRegex = "-([a-zA-Z0-9]+)=(([a-zA-Z0-9]+)|(\"([a-zA-Z0-9/]+)\"))";

        Pattern pattern = Pattern.compile(parameterRegex);

        return pattern.matcher(message);
    }
}
