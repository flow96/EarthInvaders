package de.flutze.network;

public class StandAloneServerStarter {

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        Thread.sleep(200);
        new Client();
    }
}
