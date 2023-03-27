package dk.easv;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class SlideShow extends Task<Image> {


    @Override
    protected Image call() throws Exception {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void stopNow() {
        System.exit(0);
    }
}
