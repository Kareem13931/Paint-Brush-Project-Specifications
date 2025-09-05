# Paint-Brush-Project-Specifications


Paint Brush is a Java Swing (JFrame) application that allows users to draw basic shapes, doodle freely, and use different colors and line strokes. The application also supports clearing drawings, erasing parts, and undoing actions for enhanced user experience.

🚀 Features
Colors

Draw using Red, Green, or Blue

User selects a color before drawing

Shapes

Draw Rectangle, Oval, or Line by dragging from start to end points

Shapes are drawn dynamically while dragging

Free Hand Drawing

Doodle freely by dragging the mouse with the Free Hand button

Erasing and Clearing

Eraser: erase parts of the drawing

Clear All: remove all drawings from the canvas

Filled Shapes

Filled Checkbox: if checked, shapes are filled with selected color; otherwise, shapes are outlined

Bonus Features

Undo Button: revert previous actions

Save Button: save drawings as an image file

Open Button: load an image into the drawing area

🛠️ Technologies Used

Java (JDK 8+)

Swing (JFrame, JPanel, JButton, JCheckBox)

Event-driven programming for mouse and button interactions

File I/O for saving and loading images

📦 Project Structure
PaintBrush/
├── src/
│   ├── Main.java           # Entry point
│   ├── PaintPanel.java     # Drawing canvas and logic
│   ├── Shape.java          # Shape classes and data
│   └── Tools.java          # Color, Eraser, and Free Hand functionalities
├── images/                 # Sample images for Open functionality
├── README.md               # Project documentation
└── LICENSE                 # MIT License

🚀 Getting Started
Clone the Repository
git clone https://github.com/yourusername/paint-brush.git

Run the Project

Open the project in IDE (NetBeans, Eclipse, IntelliJ)

Compile and run Main.java

Start drawing with colors, shapes, and freehand tools

💻 Usage

Select a color before drawing

Choose a shape or freehand mode

Use Eraser to remove parts of the drawing

Use Clear All to remove everything

Optional: Undo, Save, and Open for better usability

📝 Future Improvements

Add custom colors and line thickness

Support multiple layers for complex drawings

Implement advanced undo/redo stack

Add keyboard shortcuts for faster drawing

🤝 Contributing

Contributions are welcome!

Submit pull requests for improvements

Report bugs or feature requests via Issues

📄 License

This project is licensed under the MIT License – see the LICENSE file for details.

👏 Acknowledgments

Inspired by basic paint applications for learning Java Swing

Built as a learning project for GUI and event-driven programming

Thanks to open-source Java tutorials and communities
