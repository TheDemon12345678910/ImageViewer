package dk.easv;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SinglePresentaion implements Initializable {
    @FXML private ImageView imageView;
    @FXML private Label lblCurrentImage;
    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choseImages();
    }

    private void choseImages(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.setInitialDirectory(new File("..\\ImageViewer\\data\\Images"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty()) {
            files.forEach((File f) ->
                    images.add(new Image(f.toURI().toString())));
            displayImage();
        }
    }

    public void displayImage() {

        if (!images.isEmpty()) {
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getLblCurrentImage() {
        return lblCurrentImage;
    }

    public void setLblCurrentImage(Label lblCurrentImage) {
        this.lblCurrentImage = lblCurrentImage;
    }

    public List<Image> getImages() {
        return images;
    }

    public int getCurrentImageIndex() {
        return currentImageIndex;
    }

    public void setCurrentImageIndex(int currentImageIndex) {
        this.currentImageIndex = currentImageIndex;
    }

    public void closeWindow(){
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }
}
