# ChessGame
A fully functional Java Swing-based chess game implementing standard chess rules, featuring visual piece icons, check/checkmate detection, and user-friendly turn-based gameplay.
Java Chess Game
Overview
This project is a Java-based desktop chess game built using Swing for GUI and object-oriented programming principles for game logic. It supports standard chess rules including move validation, captures, pawn promotion, check, and checkmate detection.

Features
8x8 interactive chessboard with clickable squares.

Chess pieces represented using custom PNG icons.

Accurate chess piece move validation reflecting official chess rules.

Real-time check and checkmate detection with appropriate user notifications.

Turn-based gameplay enforcing alternating moves between white and black.

Modular code architecture separating UI, engine, and validation logic.

Easy-to-understand and maintain code suitable for educational purposes and further enhancements.

Getting Started
Prerequisites
JDK 8 or higher

Eclipse IDE

MySQL database (if extended for DB integration)

MySQL Connector/J (if needed)

Running the Project
Import the project into Eclipse.

Run ChessGameUI.java as a Java application.

Play chess by clicking pieces and target squares.

Receive checkmate notifications automatically.

How It Works
ChessGameUI.java handles the user interface with buttons representing squares and image icons for pieces.

ChessGameEngine.java runs the game logic such as turn management, move validation, check, and checkmate detection.

A PieceValidation class contains move rules for each piece type.

Images are stored in the resource folder and loaded dynamically for display.

Future Enhancements
Add computer AI for single-player.

Online multiplayer capabilities.

More polished and animated UI.

Enhanced move history and undo/redo functionality.
