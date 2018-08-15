package net.akehurst.node4java.nodesystem.graal;

import java.util.Properties;

import net.akehurst.node4java.api.NodeSystem;
import net.akehurst.node4java.nodesystem.common.NodeSystemAbstract;

public class NodeSystemOnGraalVM extends NodeSystemAbstract {

    public static NodeSystem create(final Properties configuration) {

        return new NodeSystemOnGraalVM(configuration);
    }

    protected NodeSystemOnGraalVM(final Properties configuration) {
        super(configuration);
    }

}
