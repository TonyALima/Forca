package forca;

public class Render implements Runnable{

    private final Thread renderThread;
    private boolean isRunning;
    private final Game game;

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Thread getRenderThread() {
        return renderThread;
    }

    public Render(Game game) {
        this.isRunning = true;
        this.renderThread = new Thread(this);
        renderThread.start();
        this.game = game;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime)/ ns;
            lastTime = now;
            if(delta >= 1){
                game.render();
                delta--;
            }
        }
        game.stop();
    }
}
