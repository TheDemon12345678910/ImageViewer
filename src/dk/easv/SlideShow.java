package dk.easv;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.List;

public class SlideShow extends Task<Image> {

    private List<Image> images;
    private int mill;
    public SlideShow(List<Image> images, int mill) {
        this.images = images;
        this.mill = mill;
    }

    @Override
    protected Image call() throws Exception {
       while(true) {
           for (Image i : images) {
               this.updateValue(i);
               this.updateMessage(i.getUrl());
               Thread.sleep(mill);
           }

        }
    }


    public void stopNow() {
        System.exit(0);
    }
}
