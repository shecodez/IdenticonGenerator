
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
 
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Identicon{
	
	String nameStr ="xx";
	//private float x, y; // x and y Coordinates

	//Default Values
	private int pxPerSq = 20;
	private int grid =5; // 5 x 5
	private int padding = pxPerSq/2;
	private int size = pxPerSq*grid + padding*2;

	private float fill_chance = .5f; //Chance sq IS filled || NOT filled [0, 1]

	private BufferedImage img;

	//Default Constructor
	public Identicon(){
		//this.x =5f;
		//this.y =5f;
	}
	public Identicon(String name){
		nameStr = name;
	}

	//Paint canvas
	private void paintCanvas(){

		//Constructs a BufferedImage.
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

		//Create a graphics which can be used to draw into the buffered img.
		Graphics2D _2d = img.createGraphics();

		//Fill the image with transparent color
		_2d.setColor(new Color(0,0,0,0));
		_2d.fillRect(0,0, size, size);

		//Disposes of this graphics context and
		//releases any system resources that it is using.
        _2d.dispose();

	}

	//Generate Color
	private Color RandomPastelColor(){
		//String rgb;
		Random rand = new Random();
		
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);

		//Mixing random colors with white (255, 255, 255) creates neutral pastels.
		r = (r + 255) / 2;
		g = (g + 255) / 2;
		b = (b + 255) / 2;

		Color color = new Color(r,g,b);
		return color;
	}

	private void FillSq(int x, int y, Color c){
		//img.setRGB(x, y, c.getRGB());
		Graphics2D _2d = img.createGraphics();
		_2d.setColor(c);
		_2d.fillRect((padding+x*pxPerSq),(padding+y*pxPerSq), pxPerSq, pxPerSq);
		_2d.dispose();	
	}

	//Generate Identicon
	public void createIdenticon(){
		
		//Fill canvas BG
		paintCanvas();

		//Generate random pastel color
		Color color = RandomPastelColor();

		//Fill in the squares
		for(int x =0; x <Math.ceil(grid/2); x++){
			for (int y =0; y <grid; y++) {
				if(Math.random() < fill_chance){
					FillSq(x, y, color);

					//Fill Right side symmetrically
					if(x < Math.floor(grid/2)){
						FillSq((grid-1) -x, y, color);
					}
				}
			}
		}
		
		// Save as PNG
		File file = new File(nameStr+"_identicon.png");
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
