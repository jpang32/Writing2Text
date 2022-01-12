import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Skeleton {

    private class FeatureExtraction {

        Skeleton outer;

        public FeatureExtraction(Skeleton outer) {
            this.outer = outer;
        }

        private void normalize (int[] array) {
            int sum = 0;

            for (int value : array) {
                sum += value;
            }
            for (int i = 0; i < array.length; i ++) {
                array[i] /= sum;
            }

        }

        public int[] calculate_horizontal_projection(int[][] image, int k, int thresh) {

            int[] y_coords = new int[4];
            int[] pixel_count = new int[image.length];

            for (int i = 0; i < image.length; i++) {
                int sum = 0;
                for (int j = 0; j < image[0].length; j++) {
                    sum += image[i][j];
                }
                pixel_count[i] = sum;
            }

            normalize(pixel_count);

            return y_coords;
        }

    }

    private FeatureExtraction fe;

    private BufferedImage buff_image;
    private int[][] image;

    public Skeleton(BufferedImage buff_image) {
        this.buff_image = buff_image;
        this.image = buffered_to_array(this.buff_image);

        this.fe = new FeatureExtraction(this);
    }

    private int[][] buffered_to_array(BufferedImage buff_image) {
        // Basically, takes buffer image and translates its data into bytes as one array
        // Assuming this returns it as an array that starts at (0, 0)
        final byte[] pixels = ((DataBufferByte) buff_image.getRaster().getDataBuffer()).getData();
        final int width = buff_image.getWidth();
        final int height = buff_image.getHeight();
        final boolean hasAlphaChannel = buff_image.getAlphaRaster() != null;

        int[][] result = new int[width][height];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

}
