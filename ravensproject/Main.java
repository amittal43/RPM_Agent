package ravensproject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	//public static final String IMG = "images/A.png";
	
		public static void main(String args[]){


			System.out.println("Test run!");

			 BufferedImage img;

		    	
		    try {
		        img = ImageIO.read(new File("A.png"));
		        
		        int[][] pixelData = new int[img.getHeight() * img.getWidth()][3];
		        int[] rgb;

		        int counter = 0;
		        for(int i = 0; i < img.getHeight(); i++){
		            for(int j = 0; j < img.getWidth(); j++){
		                rgb = getPixelData(img, i, j);

		                for(int k = 0; k < rgb.length; k++){
		                    pixelData[counter][k] = rgb[k];
		                }

		                counter++;
		            }
		        }


		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		}

		private static int[] getPixelData(BufferedImage img, int x, int y) {
			int argb = img.getRGB(x, y);

			int rgb[] = new int[] {
			    (argb >> 16) & 0xff, //red
			    (argb >>  8) & 0xff, //green
			    (argb      ) & 0xff  //blue
			};
			System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
			return rgb;

		}

}
