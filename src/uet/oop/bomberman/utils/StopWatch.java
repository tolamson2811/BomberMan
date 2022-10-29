package uet.oop.bomberman.utils;

public class StopWatch {
    private long startTime = 0;
    private long endTime = 0;
    private boolean running= false;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    public void stop() {
        this.endTime = System.currentTimeMillis();
        this.running = false;
    }

    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (endTime - startTime);
        }
        return elapsed;
    }
}
