
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
        int[][] newPixels = new int[pixels[0].length][pixels.length];
        int vertical_width = pixels[0].length;


        for (int column_index = 0; column_index < vertical_width; column_index++) {
            System.out.println(column_index + " " + vertical_width);
            int[] temp = new int[pixels.length];
            for (int horizontal_index = pixels.length - 1; horizontal_index > 0; horizontal_index--) {
                temp[pixels.length - horizontal_index] = pixels[horizontal_index][column_index];
            }
            newPixels[column_index] = temp;
        }
        return newPixels;
    }

    static int[][] mirror(int[][] pixels) {
        // implement image mirroring (horizontal, left <-> right)
        for (int horizontal_index = 0; horizontal_index < pixels.length; horizontal_index++) {
            int[] temp = pixels[horizontal_index];
            for (int i = pixels[horizontal_index].length - 1; i > 0; i--) {
                temp[pixels[horizontal_index].length - i] = pixels[horizontal_index][i];
            }
            pixels[horizontal_index] = temp;
        }
        return pixels;
    }

    static int[][] gray(int[][] pixels) {
        // optional task (advanced)
        return pixels;
    }
}
