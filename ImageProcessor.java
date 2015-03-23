/*
Programming Assignment (Question 1,2,4):

Student Name: Wong Chun Lok, Michael
Student ID: 54024795

Student Name: Yue Ka Chun, Elton
Student ID: 54059550

Student Name: Li King Yiu, Calvin
Student ID: 54057821

Student Name: Chan To Yeung, Joshua
Student ID: 54059330
*/

package imageprocessor;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class ImageProcessor
{	
	// The BufferedImage class describes an Image with an accessible buffer of image data
	public static BufferedImage convert(Image img)
	{
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(img, 0, 0, null);
		bg.dispose();
		return bi;
	}
	
	public static BufferedImage cloneImage(BufferedImage img)
	{
		BufferedImage resultImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster WR1 = Raster.createWritableRaster(img.getSampleModel(),null);
      WritableRaster WR2 = img.copyData(WR1);
		resultImg.setData(WR2);
		return resultImg;
	}
	
	// A method to convert color image to grayscale image
	public static BufferedImage toGrayScale(Image img)
	{
		// Convert image from type Image to BufferedImage
		BufferedImage bufImg = convert(img);
		
		// Scan through each row of the image
		for(int j=0; j<bufImg.getHeight(); j++)
		{
			// Scan through each columns of the image
			for(int i=0; i<bufImg.getWidth(); i++)
			{
				// Returns an integer pixel in the default RGB color model
				int values=bufImg.getRGB(i,j);
				// Convert the single integer pixel value to RGB color
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();		// get red value
				int green = oldColor.getGreen();	// get green value
				int blue = oldColor.getBlue();	// get blue value
				
				// Convert RGB to grayscale using formula
				// gray = 0.299 * R + 0.587 * G + 0.114 * B
				double grayVal = 0.299*red + 0.587*green + 0.114*blue;
				
				// Assign each channel of RGB with the same value
				Color newColor = new Color((int)grayVal, (int)grayVal, (int)grayVal);
				
				// Get back the integer representation of RGB color
				// and assign it back to the original position
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		// return back the resulting image in BufferedImage type
		return bufImg;
	}
	
	public static BufferedImage invertImage(Image img)
	{		
		BufferedImage bufImg = convert(img);
				
		for(int j = 0; j < bufImg.getHeight(); j++)
		{
			for(int i = 0; i < bufImg.getWidth(); i++)
			{
				int values = bufImg.getRGB(i,j);				
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();
				int green = oldColor.getGreen();
				int blue = oldColor.getBlue();
				
				Color newColor = new Color(255-red, 255 - green, 255 - blue);
				
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		return bufImg;
	}
	
	public static BufferedImage brighteningImage(Image img, int nBrightness)
	{
		BufferedImage bufImg = convert(img);
				
		for(int j = 0; j < bufImg.getHeight(); j++)
		{
			for(int i = 0; i < bufImg.getWidth(); i++)
			{
				int values = bufImg.getRGB(i,j);				
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();
				int green = oldColor.getGreen();
				int blue = oldColor.getBlue();
				
				int newRed = (red + nBrightness > 255) ? 255:red + nBrightness;
				int newGreen = (green + nBrightness > 255) ? 255:green + nBrightness;
				int newBlue = (blue + nBrightness > 255) ? 255:blue + nBrightness;

				newRed = (newRed < 0) ? 0:newRed;
				newGreen = (newGreen < 0) ? 0:newGreen;
				newBlue = (newBlue < 0) ? 0:newBlue;
				
				Color newColor = new Color(newRed, newGreen, newBlue);
				
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		return bufImg;	
	}
	
	public static BufferedImage offsetFilter(Image img, Point[][] offset)
	{
		// Temporary! This is only for us to make the program runnable
		return null;
	}
	
	public static BufferedImage offsetFilterAbs(Image img, Point[][] offset)
	{
		// Temporary! This is only for us to make the program runnable
		return null;
	}
	
	public static BufferedImage randomPixelMovement(Image img, int nDegree)
	{
        BufferedImage bufImg= convert(img);
		
        //get the value of width and height
		int width = bufImg.getWidth();
		int height = bufImg.getHeight();
		BufferedImage resultImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Random randomGen = new Random();
		
		//using for loop to move the pixel of y-axis 
		for(int newY = 0;newY < bufImg.getHeight(); newY++)
		{
			
			//using for loop to move the pixel of x-axis
			for(int newX = 0;newX < bufImg.getWidth(); newX++)
			{
				int random1 = randomGen.nextInt(nDegree);
				int offsetX = random1- nDegree/2;
				int random2 = randomGen.nextInt(nDegree);
				int offsetY = random2 - nDegree/2;
				int x = newX + offsetX;
				int y = newY + offsetY;
				
				//if x less than width and x greater than or equal to 0, move the pixel
				x = (x < width && x >= 0)? x:newX;
				
				//if y less than height and y greater than or equal to 0, move the pixel
				y = (y < height && y >= 0)? y:newY;
				int value = bufImg.getRGB(x, y);
				resultImage.setRGB(newX, newY, value);
				
			}
		}
		
		//return the resulting image      
		return resultImage;
		
	}
	
	public static BufferedImage spinning(Image img, double fDegree)
	{
        BufferedImage bufImg = convert(img);
        
        //get the width of the image
        int width = bufImg.getWidth();   
		
        //get the height of the image
		int height = bufImg.getHeight();
		
		//create a resulting image
		BufferedImage resultImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
              
			  //create all the elementary varibles
              int midX,midY,trueX,trueY, x, y; 
              double theta = 0.0, radius = 0.0;   
              
              //get the centre of the image by calculating midX and midY
              midX = width/2; 
              midY = height/2;
              
              //use two for-loops to get and implement the colour for each pixel
              for(int newY = 0; newY < height; newY++) 
		{
                for(int newX = 0; newX < width; newX++)
			{
                       trueX = newX - midX;
                       trueY = newY - midY;
                       theta = Math.atan2(trueY, trueX);
                       radius = Math.sqrt(Math.pow(trueX, 2) + Math.pow(trueY, 2));
                       
                       //x stores the original x coordinate 
                       x = midX + (int) (radius * Math.cos(theta + fDegree * radius));  
                       
                       //y stores the original y coordinate
                       y = midY + (int) (radius * Math.sin(theta + fDegree * radius));  
                      
                       //check if x and y are valid and then assign the the coordinate to newX and newY
                       x = (x < width && x >= 0)? x:newX; 
                       y= (y < height && y >= 0)? y:newY;
                        
                       //get the colour of each pixel
                       int value = bufImg.getRGB(x, y); 
                       
                       //set the colour for individual pixel
			  resultImage.setRGB(newX, newY, value);  
                     

                        }    
                }
                
        //return the resulting image      
		return resultImage;
	}
	
	public static void RGBtoHSV (int R, int G, int B, float[] HSV) {
		
		//R,G,B in [0,255]
		float H = 0, S = 0, V = 0;
		float cMax = 255.0f;
		int cHi = Math.max(R,Math.max(G,B));
		int cLo = Math.min(R,Math.min(G,B));
		int cRng = cHi - cLo;
		
		//compute value V
		V = cHi / cMax;
		
		//compute saturation S
		if (cHi > 0)
			S = (float) cRng / cHi;

		//compute hue H
		if (cRng > 0) {
			float rr = (float)(cHi - R) / cRng;
			float gg = (float)(cHi - G) / cRng;
			float bb = (float)(cHi - B) / cRng;
			float hh;
			if (R == cHi)
				hh = bb - gg;
			else if (G == cHi)
				hh = rr - bb + 2.0f;
			else
				hh = gg - rr + 4.0f;
			if (hh < 0)
				hh= hh + 6;
			H = hh / 6;
		}
		
		if (HSV == null)
			HSV = new float[3];
		HSV[0] = H; HSV[1] = S; HSV[2] = V;
	}
	
	public static int HSVtoRGB (float h, float s, float v) {
		
		//h,s,v in [0,1]
		float rr = 0, gg = 0, bb = 0;
		float hh = (6 * h) % 6;                 
		int   c1 = (int) hh;                     
		float c2 = hh - c1;
		float x = (1 - s) * v;
		float y = (1 - (s * c2)) * v;
		float z = (1 - (s * (1 - c2))) * v;	
		switch (c1) {
			case 0: rr=v; gg=z; bb=x; break;
			case 1: rr=y; gg=v; bb=x; break;
			case 2: rr=x; gg=v; bb=z; break;
			case 3: rr=x; gg=y; bb=v; break;
			case 4: rr=z; gg=x; bb=v; break;
			case 5: rr=v; gg=x; bb=y; break;
		}
		int N = 256;
		int r = Math.min(Math.round(rr*N),N-1);
		int g = Math.min(Math.round(gg*N),N-1);
		int b = Math.min(Math.round(bb*N),N-1);
		int rgb = ((r&0xff)<<16) | ((g&0xff)<<8) | b&0xff; 
		return rgb;
	}
	
	public static BufferedImage preservingPartColor(Image img, boolean[][] mask, int colorVal, int rgValue, int rbValue, int gbValue)
	{
		// Temporary! This is only for us to make the program runnable
		return null;
	}
	
	public static char[][] imageToASCII(Image img)
	{       
            
			//convert the image to grayscale  
			BufferedImage bufImg = toGrayScale(img);  
            
			//get the width and height of the image
			int width = bufImg.getWidth();   
                     int height = bufImg.getHeight();
            
            //These varibles are used to store the value of the colour model
            int C1, C2, C3; 
              
            //create an array to store the output of each pixel  
            char[][] imageToASCII = new char[height][width]; 
            for(int i = 0; i < height; i++){
                  for(int j = 0; j < width; j++){
                	  //get the colour value of each pixel
                	  int value = bufImg.getRGB(j, i); 
                	  
                	  //get the value in the Blue sector of RGB model
                	  C1 = value & 255;      
                	  
                	  //get the value in the Green sector of RGB model
                	  C2 = (value >> 8) & 255; 
                	  
                	  //get the value in the Red sector of RGB model
                	  C3 = (value >> 16) & 255;
                      
                	  //calculate the average gray value
                	  int g = (C1 + C2 + C3) / 3; 
                    
                	  //check the given conditions and get the relative output  
                	  if (g >= 230)                         
                            imageToASCII[i][j] = ' ';
                	  else if (g >= 200)
                              imageToASCII[i][j] = '.';
                	  else if (g >= 180)
                              imageToASCII[i][j] = '*';
                	  else if (g>= 160)
                              imageToASCII[i][j] = ':';
                	  else if (g >= 130)
                              imageToASCII[i][j] = 'o';
                	  else if (g >= 100)
                              imageToASCII[i][j] = '&';
                	  else if (g >= 70)
                              imageToASCII[i][j] = '8';
                	  else if (g >= 50)
                              imageToASCII[i][j] = '#';
                	  else
                              imageToASCII[i][j] = '@';
                    
                  }
              }
         
        //return the array
		return imageToASCII; 
	}	
}
