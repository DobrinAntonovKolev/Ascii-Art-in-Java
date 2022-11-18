import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File rabbitPic = new File("Ascii Rabbit.jpg");
        Image image = ImageIO.read(rabbitPic);
        Image scaledImage = image.getScaledInstance(image.getWidth(null) / 4, image.getHeight(null) / 4, Image.SCALE_SMOOTH);
        BufferedImage rabbit = toBufferedImage(scaledImage);
        int rabbitHeight = rabbit.getHeight();
        int rabbitWidth = rabbit.getWidth();
        System.out.println(rabbitHeight + "x" + rabbitWidth);
        char[][] rabbitPixelData = new char[rabbitWidth][rabbitHeight];
        for (int i = 0; i < rabbitWidth; i++) {
            for (int j = 0; j < rabbitHeight; j++) {
                Color color = new Color(rabbit.getRGB(i, j));
                rabbitPixelData[i][j] = colorToAscii(colorToBrightness(color));
            }
        }
        printAscii(rabbitPixelData);
    }

    static char colorToAscii(int brightness) {
        String charsString = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
        //String charsString = "`^\",:;!~+_-?][}{)(|\\/*#&%@$";
        char[] chars = charsString.toCharArray();
        double intervals = 255 / (chars.length);
        double[] nums = new double[chars.length];
        double currentNum = 0;
        for (int i = 0; i < nums.length; i++) {
            nums[i] = currentNum;
            currentNum = currentNum + intervals;
        }
        for (int i = 0; i < nums.length; i++) {
            if (between(brightness, nums[i], nums[i] + intervals)) {
                return chars[i];
            }
        }
        return '^';
    }

    static boolean between(int brightness, double a, double b) {
        if (brightness >= a && brightness < b) {
            return true;
        } else {
            return false;
        }
    }

    static int colorToBrightness(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int brightness = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
        return brightness;
    }

    static void printAscii(char[][] asciiArray) {
        for (int j = 0; j < asciiArray[0].length; j++) {
            for (int i = 0; i < asciiArray.length; i++) {
                if (i == asciiArray.length - 1) {
                    System.out.print(asciiArray[i][j]);
                    System.out.print(asciiArray[i][j] + "\n");
                } else {
                    System.out.print(asciiArray[i][j]);
                    System.out.print(asciiArray[i][j]);
                }
            }
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
