package dk.easv;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SlideShow extends Task<Image> {

    private List<Image> images;
    public SlideShow(List<Image> images) {
        this.images = images;
    }

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
