package dk.easv;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

public class ImageViewerWindowController implements Initializable {
    private final List<Image> images = new ArrayList<>();
    @FXML
    private Button btnSlideShow;
    private int currentImageIndex = 0;

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;
    private Thread t;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        t = new Thread(() -> {
            while (true) {
                try {
                    handleBtnNextAction();
                    System.out.println("ImgNum: " + currentImageIndex);
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void handleBtnLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.setInitialDirectory(new File("..\\ImageViewer\\data\\Images"));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty()) {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction() {
        if(t.isAlive()) {
            t.interrupt();
            System.out.println("Stop thread");
        }
        if (!images.isEmpty()) {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction() {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage() {
        if (!images.isEmpty()) {
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    public void handleBtnSlideShow(ActionEvent event) throws InterruptedException {
        t.start();
        /*
        SlideShow slideShow = new SlideShow(images);
        boolean bool = true;
        //while(bool) {
        for (int i = 0; i < 10; i++) {
            //Thread.sleep(3000);
            handleBtnNextAction();
            System.out.println("new image");
            //Platform.runLater(()->displayImage());
        }
         */
        //SlideShow slideShow = new SlideShow();
        //ExecutorService executorService = Executors.newFixedThreadPool(2);
        //executorService.submit(slideShow);
        //int i = 0;
        //if (i == 0) {
        //slideShow.stopNow();
        //}
    }
}