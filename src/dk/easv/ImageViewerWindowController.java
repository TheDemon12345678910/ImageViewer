package dk.easv;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


import static java.lang.Thread.sleep;

public class ImageViewerWindowController implements Initializable {

    private SlideShow slideShow, slideShow1, slideShow2;
    private final List<Image> images = new ArrayList<>();
    @FXML
    private Label lblCurrentImage;
    private int currentImageIndex = 0;
    private SinglePresentaion singlePresentaion1, singlePresentaion2;
    private ScheduledExecutorService executorService;

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
                    images.add(new Image(f.toURI().toString())));
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction() {
        if (t.isAlive()) {
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


    public void displayImage() {

        if (!images.isEmpty()) {
            imageView.setImage(images.get(currentImageIndex));
        }
    }


    public void handleBtnSlideShow() {
        slideShow = new SlideShow(images, 5000);
        slideShow.valueProperty().addListener((obs, o, n) -> imageView.setImage(n));
        slideShow.messageProperty().addListener((obs, o, n) -> lblCurrentImage.setText(n));

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(slideShow);
        es.shutdown();

    }


    public void handleBtnSlideShowSTOP() {
        slideShow.cancel(true);
    }

    public void handlMultieBtnSlideShow(ActionEvent actionEvent) {
        singlePresentaion1 = openWIWindow();
        singlePresentaion2 = openWIWindow();

        slideShow1 = new SlideShow(singlePresentaion1.getImages(), 20000);
        slideShow1.valueProperty().addListener((obs, o, n) -> singlePresentaion1.getImageView().setImage(n));
        slideShow1.messageProperty().addListener((obs, o, n) -> singlePresentaion1.getLblCurrentImage().setText(n));

        slideShow2 = new SlideShow(singlePresentaion2.getImages(), 20000);
        slideShow2.valueProperty().addListener((obs, o, n) -> singlePresentaion2.getImageView().setImage(n));
        slideShow2.messageProperty().addListener((obs, o, n) -> singlePresentaion2.getLblCurrentImage().setText(n));

        executorService = Executors.newScheduledThreadPool(2);

        executorService.scheduleWithFixedDelay(slideShow1, 0, 20, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(slideShow2, 10, 20, TimeUnit.SECONDS);

        //executorService.shutdown();

    }

    private SinglePresentaion openWIWindow(){
        SinglePresentaion singlePresentaion = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SinglePresentaion.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            singlePresentaion = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("presentation");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singlePresentaion;
    }

    public void handleMultiBtnSlideShowSTOP(ActionEvent actionEvent) {
        executorService.shutdown();

        slideShow1.cancel(true);

        slideShow2.cancel(true);

        singlePresentaion1.closeWindow();
        singlePresentaion2.closeWindow();

    }
}
