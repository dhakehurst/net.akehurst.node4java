package net.akehurst.node4java.nodesystem.common;

import net.akehurst.node4java.api.Console;

public class ConsoleDefault implements Console {

    @Override
    public void log(final String value) {
        // TODO Auto-generated method stub
        System.out.println(value);
    }

}
