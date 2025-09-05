package com.mycompany.paintbrushproject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PaintBrush extends JFrame {
    private String currentTool = "Freehand";      // current tool before choose.
    private Color currentColor = Color.RED;       // current color before choose.
    private int eraseSize = 10;                   // size of Erase.
    private boolean filledShapes = false;         // Non-Filled

    private ArrayList<ShapeData> shapes = new ArrayList<>();   // List to store all shapes, ShapeData==> is a class build to save all info. of every shape.
    private DrawingPanel drawingPanel;                         // drawingPanel ==> location of actual paint(JPanel). 

    public PaintBrush() {                         // Constractor for Class
        setTitle("Paint Brush Project");          // Name of JFrame 
        setSize(800, 600);                        // Size of JFrame 
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // When user tern off window --> close the programe.

        drawingPanel = new DrawingPanel();        // create object from DrawingPanel
        add(drawingPanel, BorderLayout.CENTER);   // Add component called drawingPanel to JFrame , BorderLayout.CENTER --> to determine the location of component into JFrame.

        JPanel buttonPanel = new JPanel();             // Task Bar contain all buttons.
        JButton freehandBtn = new JButton("Freehand"); // Button to draw in free
        JButton rectBtn = new JButton("Rectangle");    // Button to draw Rectangle
        JButton ovalBtn = new JButton("Oval");         // Button to draw Oval
        JButton lineBtn = new JButton("Line");         // Button to draw Line
        JButton eraseBtn = new JButton("Erase");       // Button to Erase

        JButton redBtn = new JButton("Red");           // Button Red
        JButton blueBtn = new JButton("Blue");         // Button Blue
        JButton greenBtn = new JButton("Green");       // Button Green

        JButton clearBtn = new JButton("Clear All");   // Button to Clear All
        JButton undoBtn = new JButton("Undo");         // Button to Undo
        JButton saveBtn = new JButton("Save");         // Button to Save

        JCheckBox filledCheck = new JCheckBox("Filled Shapes");       // Button to choose the state of drawing (filled or not).
                /* Reactive Action not Polling ==> that means if user click on Btn, change the currentTool to this Btn */
        freehandBtn.addActionListener(e -> currentTool = "Freehand");
        rectBtn.addActionListener(e -> currentTool = "Rectangle");
        ovalBtn.addActionListener(e -> currentTool = "Oval");
        lineBtn.addActionListener(e -> currentTool = "Line");
        eraseBtn.addActionListener(e -> currentTool = "Erase");

        redBtn.addActionListener(e -> currentColor = Color.RED);
        blueBtn.addActionListener(e -> currentColor = Color.BLUE);
        greenBtn.addActionListener(e -> currentColor = Color.GREEN);

        clearBtn.addActionListener(e -> {      // ClearAll Btn
            shapes.clear();                    // Delete all elements that stored in ArrayList from Memory.
            drawingPanel.repaint();            // Repaint the window again.
        });

        undoBtn.addActionListener(e -> {
            if (!shapes.isEmpty()) {                // Check if that shapes is exist or not, to avoid remove empty list.
                shapes.remove(shapes.size() - 1);   // Remove last element from list.
                drawingPanel.repaint();             // Draw the screen without last shape.
            }
        });

        saveBtn.addActionListener(e -> saveDrawing());     // Call the method "saveDrawing()" to take action.

        filledCheck.addActionListener(e -> filledShapes = filledCheck.isSelected());   //filledShapes ==>  Return True (Filled) or False (Border).
 
                    /* Adding all drawing tool buttons, color selectors, and options to the top button panel */
        buttonPanel.add(freehandBtn);
        buttonPanel.add(rectBtn);
        buttonPanel.add(ovalBtn);
        buttonPanel.add(lineBtn);
        buttonPanel.add(eraseBtn);
        buttonPanel.add(redBtn);
        buttonPanel.add(blueBtn);
        buttonPanel.add(greenBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(undoBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(filledCheck);

        add(buttonPanel, BorderLayout.NORTH); // ButtonPanel in thr Top of window.

        setVisible(true);     // tell the JFrame to appear on the screen...as default action is invisible
    }


     class ShapeData {
        String type;
        double x1, y1, x2, y2; 
        Color color;
        int size;
        boolean filled;
          
        /* ShapeData ==> this is a constructor */
        ShapeData(String t, int x1, int y1, int x2, int y2, Color c, int size, boolean filled, int panelW, int panelH) {
            type = t;                              // Assign the shape type (Rectangle, Oval, Line, etc.)
                    /* Convert the absolute coordinates (x1, y1, x2, y2) into relative values based on the panel's width and height. 
                               This allows shapes to scale correctly if the panel size changes 
                    */
            this.x1 = x1 / (double) panelW;
            this.y1 = y1 / (double) panelH;
            this.x2 = x2 / (double) panelW;
            this.y2 = y2 / (double) panelH;
            color = c;
            this.size = size;
            this.filled = filled;
        }

        void draw(Graphics g, int panelW, int panelH) {
             // Convert relative coordinates  to actual pixel positions based on panel size
            int realX1 = (int) (x1 * panelW);
            int realY1 = (int) (y1 * panelH);
            int realX2 = (int) (x2 * panelW);
            int realY2 = (int) (y2 * panelH);

            switch (type) {
                case "Freehand":
                    g.drawLine(realX1, realY1, realX2, realY2);  // Freehand drawing: draw a line between two points
                    break;
                case "Rectangle":                                // Rectangle: calculate top-left corner and width/height, then draw filled or outline
                    int x = Math.min(realX1, realX2);
                    int y = Math.min(realY1, realY2);
                    int w = Math.abs(realX2 - realX1);
                    int h = Math.abs(realY2 - realY1);
                    if (filled) g.fillRect(x, y, w, h);
                    else g.drawRect(x, y, w, h);
                    break;
                case "Oval":                                   // Oval: calculate bounding box and draw filled or outline
                    x = Math.min(realX1, realX2);
                    y = Math.min(realY1, realY2);
                    w = Math.abs(realX2 - realX1);
                    h = Math.abs(realY2 - realY1);
                    if (filled) g.fillOval(x, y, w, h);
                    else g.drawOval(x, y, w, h);
                    break;
                case "Line":                                     // Line: draw a straight line between two points
                    g.drawLine(realX1, realY1, realX2, realY2);
                    break;
                case "Erase":                                     // Erase: draw a filled rectangle at the point to simulate erasing
                    g.fillRect(realX1 - size / 2, realY1 - size / 2, size, size);
                    break;
            }
        }
    }
     
    class DrawingPanel extends JPanel {              // extends from JPanel to can be draw inside it.
        private int startX, startY, endX, endY;      // Variables to store the start and end coordinates of shapes
        private boolean isDrawing = false;           // Flag to check if user is currently drawing or not
        private ShapeData previewShape = null;       // Temporary shape for showing preview while dragging mouse. not store in shapes

        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {           // Add a mouse listener to handle press and release events
                public void mousePressed(MouseEvent e) {
                                /* Save the starting point when mouse is pressed */
                    startX = e.getX();                      
                    startY = e.getY();
                    isDrawing = true;                       // User started drawing
                    previewShape = null;                    // Clear preview shape since we’re starting fresh
                }

                public void mouseReleased(MouseEvent e) {
                    if (isDrawing) {                       // Only execute if the user was drawing
                               /* Save the end coordinates when mouse is released */
                        endX = e.getX();
                        endY = e.getY();
                        
                                /* Add a new shape contain all information like (color,size,...) to the list if tool is not Erase or Freehand */
                        if (!currentTool.equals("Erase") && !currentTool.equals("Freehand")) {
                            shapes.add(new ShapeData(
                                    currentTool,
                                    startX, startY, endX, endY,
                                    currentColor, eraseSize, filledShapes,
                                    getWidth(), getHeight()
                            ));
                        }
                        previewShape = null;           // Clear preview shape since we’re starting fresh
                        isDrawing = false;             // User ended drawing
                        repaint();                     // to update JPanel and show the new shape
                    }
                }
            });
 
            addMouseMotionListener(new MouseMotionAdapter() {              // Add a mouse motion listener to detect dragging
                public void mouseDragged(MouseEvent e) {                   // Called whenever the mouse is dragged
                                /* Update current positions of mouse */
                    endX = e.getX();
                    endY = e.getY();

                    if (currentTool.equals("Freehand")) {
                        shapes.add(new ShapeData(
                                "Freehand",
                                startX, startY, endX, endY,
                                currentColor, eraseSize, filledShapes,
                                getWidth(), getHeight()
                        ));
                              /* Update start point for the next shape to continue drawing */
                        startX = endX;
                        startY = endY;
                    } 
                    else if (currentTool.equals("Erase")) {
                        shapes.add(new ShapeData(
                                "Erase",
                                endX, endY, eraseSize, eraseSize,
                                getBackground(), eraseSize, false,
                                getWidth(), getHeight()
                        ));
                    } 
                    else {    // For other tools like Rectangle or Oval, we create a temporary preview shape
                        previewShape = new ShapeData(
                                currentTool,
                                startX, startY, endX, endY,
                                currentColor, eraseSize, filledShapes,
                                getWidth(), getHeight()
                        );
                    }
                    repaint();                   // Repaint the panel to show the updated drawing or preview
                }
            });
        }

        public void paintComponent(Graphics g) {         // Method responsible for drawing on the panel
            super.paintComponent(g);                     // Clear the panel before drawing new content
            for (ShapeData s : shapes) {                 // Loop through all saved shapes
                g.setColor(s.color);                     // Set the color for the current shape
                s.draw(g, getWidth(), getHeight());      // Draw the shape on the panel
            }

            
            if (previewShape != null) {                        // Check if a preview shape exists   ===> Draw it.
                g.setColor(previewShape.color);
                previewShape.draw(g, getWidth(), getHeight());
            }
        }
    }

    private void saveDrawing() {            
                     // Create a new image the same size as the drawing panel, supporting transparency (ARGB) ---> A = Alpha (0 -- 255)
    BufferedImage image = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();        // Get a Graphics2D object to draw on the image
    drawingPanel.paint(g2d);                        // Paint everything from the drawing panel onto the image
    g2d.dispose();                                  // Release resources used by Graphics2D

    JFileChooser fileChooser = new JFileChooser();             // Create a file chooser dialog for the user to select where to save the image
    fileChooser.setDialogTitle("Save Drawing");                // Set the title of the file chooser dialog
    fileChooser.setSelectedFile(new File("drawing.png"));      // default file name is "drawing.png"

    int userSelection = fileChooser.showSaveDialog(this);      // Show the save dialog and get the user's selection

    if (userSelection == JFileChooser.APPROVE_OPTION) {        // if user click on Save Btn.   
        File fileToSave = fileChooser.getSelectedFile();

        
        if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
        }

        try {
            ImageIO.write(image, "png", fileToSave);
            JOptionPane.showMessageDialog(this, "Saved as " + fileToSave.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving image.");
        }
    }
}

    public static void main(String[] args) {
        new PaintBrush();
    }
}
