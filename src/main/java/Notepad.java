import com.sun.tools.javac.util.JCDiagnostic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Notepad {

    private static MyPanel panel = new MyPanel();

    public static void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static BufferedImage getImage() {
        return MyPanel.getImage();
    }

    private static void createAndShowGUI() {
        //System.out.println("Created GUI on EDT? "+ SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Notepad");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(Notepad.panel);
        f.pack();
        //JButton clear_button = new JButton("Clear");
        //clear_button.setPreferredSize(new Dimension(750, 150));
        //f.add(clear_button);
        f.pack();
        f.setVisible(true);
    }
}

class MyPanel extends JPanel {

    private static int x = 50;
    private static int y = 50;
    private static int pen_width = 2;

    private static final int AREA_WIDTH = 750;
    private static final int AREA_HEIGHT = 150;

    private static BufferedImage image =
            new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    public MyPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Rectangle rect = new Rectangle(e.getX(), e.getY(), pen_width, pen_width);
                Graphics2D g2d = (Graphics2D)image.getGraphics();
                g2d.setColor(Color.black);
                g2d.fill(rect);
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Rectangle rect = new Rectangle(e.getX(), e.getY(), pen_width, pen_width);
                Graphics2D g2d = (Graphics2D)image.getGraphics();
                g2d.setColor(Color.black);
                g2d.fill(rect);
                repaint();
            }
        });
    }


    public static BufferedImage getImage() {
        return MyPanel.image;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(AREA_WIDTH, AREA_HEIGHT);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null)
        {
            g.drawImage(image, 0, 0, null);
        }

    }
}
