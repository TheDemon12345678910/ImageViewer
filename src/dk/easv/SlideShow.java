package dk.easv;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.List;

public class SlideShow extends Task<Image> {

    private List<Image> images;
    public SlideShow(List<Image> images) {
        this.images = images;
    }

    @Override
    protected Image call() throws Exception {
       while(true) {
           for (Image i : images) {
               this.updateValue(i);
               this.updateMessage(i.getUrl());
               Thread.sleep(5000);
           }

        }
    }


    public void stopNow() {
        System.exit(0);
    }
}
