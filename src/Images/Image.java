package Images;

import javafx.scene.image.ImageView;

public class Image {

   public ImageView getImage(String name, String color){

       ImageView img = new ImageView("Images/"+color+name+".png");
       img.setFitHeight(75);
       img.setFitWidth(75);

       return img;
    }
}
