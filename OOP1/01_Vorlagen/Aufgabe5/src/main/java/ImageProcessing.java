
public class ImageProcessing {
    static int[][] invert(int[][] pixels) {
        // implement image inversion (negate all pixels)
        for (int horizontal_index = 0; horizontal_index < pixels.length; horizontal_index++) {
            for (int vertical_index = 0; vertical_index < pixels[horizontal_index].length; vertical_index++) {
                pixels[horizontal_index][vertical_index] *= -1;
            }
        }
        return pixels;
    }

    static int[][] rotate(int[][] pixels) {
        // implement image rotation (90 degrees to the right)
        int vertical_width = pixels[0].length;
        int[][] newPixels = new int[vertical_width][pixels.length];


        for (int column_index = 0; column_index < vertical_width; column_index++) {
            int[] temp = new int[pixels.length];
            for (int horizontal_index = 0; horizontal_index < pixels.length; horizontal_index++) {
                System.out.println(horizontal_index + " " + vertical_width);

                temp[pixels.length - 1 - horizontal_index] = pixels[horizontal_index][column_index];
            }
            newPixels[column_index] = temp;
        }
        return newPixels;
    }

    static int[][] mirror(int[][] pixels) {
        // implement image mirroring (horizontal, left <-> right)
        int[][] newPixels = new int[pixels.length][pixels[0].length];
        for (int horizontal_index = 0; horizontal_index < pixels.length; horizontal_index++) {
            int[] temp = new int[pixels[horizontal_index].length];
            for (int i = 0; i < pixels[horizontal_index].length; i++) {
                temp[pixels[horizontal_index].length - 1 - i] = pixels[horizontal_index][i];
            }
            newPixels[horizontal_index] = temp;
        }
        return newPixels;
    }

    static int[][] gray(int[][] pixels) {
        // optional task (advanced)

        //get Pixel

        for (int rowI = 0; rowI < pixels.length; rowI++) {
            for (int colI = 0; colI < pixels[rowI].length; colI++) {
                String pixelBitString = Integer.toBinaryString(pixels[rowI][colI]);
                pixelBitString = String.format("%24s", pixelBitString).replace(" ", "0");
                pixelBitString = String.format("%-32s", pixelBitString).replace(" ", "0");

                //red 8-15
                int red = Integer.parseInt(pixelBitString.substring(0, 7), 2);
                //grün 16-23
                int green = Integer.parseInt(pixelBitString.substring(8, 15), 2);

                //rot 24-32
                int blue = Integer.parseInt(pixelBitString.substring(16, 23), 2);
                int average = (red + green + blue) / 3;

                String finalBitString = Integer.toBinaryString(average) + Integer.toBinaryString(average) + Integer.toBinaryString(average) + "00000000";

                pixels[rowI][colI] = Integer.parseInt(finalBitString, 2);

            }
        }
        //get Red, Blue, Green from Pixel
        //calculate average from Pixel
        //calculate new value for Pixel
        //set to pixel
        return pixels;
    }
}
