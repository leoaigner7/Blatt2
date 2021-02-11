package de.thd.systemdesign.p2p.config;

public class NodeConfig {
    String node = "";
    String name = "";
    int port;
    boolean master = false;
    public NodeConfig(boolean master) {
        this.master = master;
    }

    public String getNode() {
        return node;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public void setNode(String node) {
        this.setName(node.substring(0, node.lastIndexOf(":")));
    }

    private void setNode() {
        this.node = String.format("%s:%d", name, port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
        this.setNode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setNode();
    }
}