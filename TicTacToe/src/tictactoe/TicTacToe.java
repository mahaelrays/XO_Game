/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

//import java.io.DataInputStream;
import java.io.IOException;
//import java.io.PrintStream;
import java.net.InetAddress;
//import java.net.Socket;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.controlsfx.control.StatusBar;

/**
 *
 * @author mahaelrays
 */
public class TicTacToe extends Application {

    /*records*/
    String serverReplywithRecords[];
    /*Top Button Bar*/
    ButtonBar topBar;
    static double xOffset;
    static double yOffset;

    //Socket mySocket;
    //ServerListener myServerListener;
    //PrintStream myPrintStream;
    int stepCount;
    /*Game Board Scene Attributes*/
    BorderPane gameBoardRootPane;
    GridPane gameBoardCellPane;
    Button[] gameBoardCellArray;
    Scene gameBoardScene;


    /*Game Menu Scene Attributes*/
    BorderPane gameMenuRootPane;
    BorderPane prizeMenuRootPane;
    Scene gameMenuScene;
    Label gameMenuHelloLabel;
    Button gameMenuSingleModeButton;
    Button gameMenuTwoPlayersButton;
    Button gameMenuNetworkGameButton;
    GridPane gameMenuGridPane;

    /*Game Menu Level Scene Attributes*/
    BorderPane gameMenuLevelRootPane;
    Scene gameMenuLevelScene;
    Button gameMenuEasyButton;
    Button gameMenuMediumButton;
    Button gameMenuHardButton;
    GridPane gameMenuLevelGridPane;

    /*Login Page Scene Attributes*/
    BorderPane loginPageRootPane;
    Scene loginPageScene;
    Label loginPageUsernameLabel;
    TextField loginPageUsernameField;
    Label loginPagePasswordLabel;
    PasswordField loginPagePasswordField;
    Button loginPageLoginButton;
    Button loginPageSignupButton;
    Image loginPageImage;
    Image imago;
    Image imagx;
    ImageView loginPageImageView;
    GridPane loginPage;
    int flage;

    /*Network Mode Home Scene*/
    BorderPane networkMenuRootPane;
    Scene networkMenuScene;
    GridPane networkMenuGridPane;
    Button networkMenuStartGameButton;
    Button networkMenuRecordsButton;
    Button playMusic;
    /*Network Mode Home Scene*/
    //  Stage primaryStage;
    /**
     * *****************************
     */
    int arrStep[];
    int arrGameButton[];
//    int arrConner[];
    char winner;
    int computernum;
    boolean hard;
    boolean meduim;
    boolean easy;

    /**
     * *****************************
     */
    /*Networking Setup*/
    Socket clientSocket;
    DataInputStream clientInputStream;
    PrintStream clientOutputStream;
    int TTTNetworkId;
    String TTTNetworkName;
    boolean isNetworkGame = false;
    Image networkGameIcon;
    Image networkGameOpponentIcon;
    Button clickedButton;
    String serverMessage;
    StatusBar networkGameStatusBar;
    Thread networkGameThread;
    /////audio
    URL resource;
    Media audioMedia;
    MediaPlayer audioMediaPlayer;
    boolean audioFlage;
////video
    MediaPlayer prize;
    MediaView mediaView;
    boolean vedioFlage;

    @Override
    public void start(Stage primaryStage) {

        /**
         * *****Initialize the step counter****
         */
        hard = false;
        meduim = false;
        easy = false;
        audioFlage = false;
        flage = 0;
        stepCount = 1;
        arrStep = new int[9];
        arrGameButton = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        /**
         * *****GameBoard Scene Creation*******
         */
        //audio
        resource = getClass().getResource("a.mp3");
        audioMedia = new Media(resource.toString());
        audioMediaPlayer = new MediaPlayer(audioMedia);

        /*Initialize the Grid Pane and add the 9 cell to it*/
        gameBoardCellPane = new GridPane();
        gameBoardCellArray = new Button[9];

        imago = new Image(getClass().getResourceAsStream("o.png"), 75, 75, true, true);
        imagx = new Image(getClass().getResourceAsStream("x.png"), 75, 75, true, true);
        //GameBoard gameBoard = new GameBoard(gameBoardCellArray);
        gameBoardCellPane.setPadding(new Insets(35, 35, 5, 50));

        gameBoardRootPane = new BorderPane();
        gameBoardRootPane.setCenter(gameBoardCellPane);

        prizeMenuRootPane = new BorderPane();

        gameBoardScene = new Scene(gameBoardRootPane, 410, 410);

        gameBoardScene.getStylesheets().add(getClass().getResource("tictactoe.css").toString());
        gameBoardRootPane.getStyleClass().add("borderPane");

        /**
         * *****GameMenu Scene Creation*******
         */
        gameMenuRootPane = new BorderPane();
        gameMenuScene = new Scene(gameMenuRootPane, 410, 410);
        gameMenuHelloLabel = new Label("Welcome");
        gameMenuHelloLabel.setFont(Font.font("Cambria", 30));
        gameMenuSingleModeButton = new Button("Arcade Mode");
        gameMenuSingleModeButton.setPrefWidth(200);
        gameMenuSingleModeButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
      //      gameBoardRootPane.setTop(topBar);
//            AiGame computerAndHumman = new AiGame();
//            computerAndHumman.AiGamePlay(primaryStage);
            //   primaryStage.setScene(gameBoardScene);
            gameMenuLevelRootPane.setTop(topBar);
            primaryStage.setScene(gameMenuLevelScene);
        });
        gameMenuTwoPlayersButton = new Button("Battle Mode");
        gameMenuTwoPlayersButton.setPrefWidth(200);
        gameMenuTwoPlayersButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            gameBoardRootPane.setTop(topBar);
            GameBoard gameBoard = new GameBoard(gameBoardCellArray);
            gameBoard.gamePlay(primaryStage);
            primaryStage.setScene(gameBoardScene);
        });
        gameMenuNetworkGameButton = new Button("Network Mode");
        gameMenuNetworkGameButton.setPrefWidth(200);
        gameMenuNetworkGameButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            loginPageRootPane.setTop(topBar);
            primaryStage.setScene(loginPageScene);
        });
        /**/
        networkMenuRootPane = new BorderPane();
        networkMenuScene = new Scene(networkMenuRootPane, 410, 410);
        networkMenuGridPane = new GridPane();
        networkMenuGridPane.setVgap(28);
        networkMenuGridPane.setHgap(100);
        networkMenuGridPane.setHalignment(gameMenuHelloLabel, HPos.CENTER);
        networkMenuRecordsButton = new Button("My Records");
        networkMenuRecordsButton.setPrefWidth(200);
        networkMenuRecordsButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            gameBoardRootPane.setBottom(null);
            this.showMyRecords(primaryStage);
        });
        networkMenuStartGameButton = new Button("Start Game");
        networkMenuStartGameButton.setPrefWidth(200);
        networkMenuStartGameButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            isNetworkGame = true;
            GameBoard NetworkGameBoard = new GameBoard(gameBoardCellArray);
            gameBoardRootPane.setTop(topBar);
            networkGameStatusBar = new StatusBar();
            networkGameStatusBar.setStyle("-fx-border-radius: 5");
            networkGameStatusBar.setMaxHeight(20);
            gameBoardRootPane.setBottom(networkGameStatusBar);
            primaryStage.setScene(gameBoardScene);
            this.startNetworkGame(gameBoardCellArray, networkGameStatusBar, primaryStage);
        });
        networkMenuGridPane.add(networkMenuStartGameButton, 1, 4);
        networkMenuGridPane.add(networkMenuRecordsButton, 1, 5);
        networkMenuRootPane.setCenter(networkMenuGridPane);
        networkMenuScene.getStylesheets().add(getClass().getResource("tictactoe.css").toString());
        networkMenuRootPane.getStyleClass().add("borderPane");
        networkMenuGridPane.getStyleClass().add("gameMenuGridPane");
        networkMenuStartGameButton.getStyleClass().add("ipad");
        networkMenuRecordsButton.getStyleClass().add("ipad");
        /**/
        gameMenuGridPane = new GridPane();
        gameMenuGridPane.setVgap(15);
        gameMenuGridPane.setHgap(100);
        gameMenuGridPane.add(gameMenuHelloLabel, 1, 1);
        GridPane.setHalignment(gameMenuHelloLabel, HPos.CENTER);
        gameMenuGridPane.add(gameMenuSingleModeButton, 1, 2);
        gameMenuGridPane.add(gameMenuTwoPlayersButton, 1, 3);
        gameMenuGridPane.add(gameMenuNetworkGameButton, 1, 4);
        gameMenuRootPane.setCenter(gameMenuGridPane);
        gameMenuScene.getStylesheets().add(getClass().getResource("tictactoe.css").toString());
        gameMenuRootPane.getStyleClass().add("borderPane");
        gameMenuGridPane.getStyleClass().add("gameMenuGridPane");
        gameMenuSingleModeButton.getStyleClass().add("ipad");
        gameMenuTwoPlayersButton.getStyleClass().add("ipad");
        gameMenuNetworkGameButton.getStyleClass().add("ipad");

        //GameMenuLevel Scene Creation          
        gameMenuLevelRootPane = new BorderPane();
        gameMenuLevelScene = new Scene(gameMenuLevelRootPane, 410, 410);
        
        gameMenuEasyButton = new Button("Easy Level");
        gameMenuEasyButton.setPrefWidth(200);
        gameMenuEasyButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            gameBoardRootPane.setTop(topBar);
            easy=true;
            hard=false;
            meduim=false;
            AiGame computerAndHumman = new AiGame();
            computerAndHumman.AiGamePlay(primaryStage);

            primaryStage.setScene(gameBoardScene);
        });

        gameMenuMediumButton = new Button("Medium Level");
        gameMenuMediumButton.setPrefWidth(200);
        gameMenuMediumButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            gameBoardRootPane.setTop(topBar);
            easy=false;
            hard=false;
            meduim=true;
            AiGame computerAndHumman = new AiGame();
            computerAndHumman.AiGamePlay(primaryStage);
            primaryStage.setScene(gameBoardScene);
        });

        gameMenuHardButton = new Button("Hard Level");
        gameMenuHardButton.setPrefWidth(200);
        gameMenuHardButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            gameBoardRootPane.setTop(topBar);
            easy=false;
            hard=true;
            meduim=false;
            AiGame computerAndHumman = new AiGame();
            computerAndHumman.AiGamePlay(primaryStage);
            primaryStage.setScene(gameBoardScene);
        });

        //*************************************//
        gameMenuLevelGridPane = new GridPane();
        gameMenuLevelGridPane.setVgap(30);
        gameMenuLevelGridPane.setHgap(100);
        gameMenuLevelGridPane.add(gameMenuEasyButton, 1, 2);
        gameMenuLevelGridPane.add(gameMenuMediumButton, 1, 3);
        gameMenuLevelGridPane.add(gameMenuHardButton, 1, 4);
        gameMenuLevelRootPane.setCenter(gameMenuLevelGridPane);
        gameMenuLevelScene.getStylesheets().add(getClass().getResource("tictactoe.css").toString());
        gameMenuLevelRootPane.getStyleClass().add("borderPane");//////////////
        gameMenuLevelGridPane.getStyleClass().add("gameMenuGridPane");///////////
        gameMenuEasyButton.getStyleClass().add("ipad");
        gameMenuMediumButton.getStyleClass().add("ipad");
        gameMenuHardButton.getStyleClass().add("ipad");

        /**
         * *****Login Page Scene Creation*******
         */
        loginPageRootPane = new BorderPane();

        loginPageScene = new Scene(loginPageRootPane, 410, 410);

        loginPageUsernameLabel = new Label("Username");
        loginPageUsernameLabel.setFont(Font.font("Cambria", 25));

        loginPageUsernameField = new TextField();
        loginPageUsernameField.setMinSize(80, 30);

        loginPagePasswordLabel = new Label("Password");
        loginPagePasswordLabel.setFont(Font.font("Cambria", 25));
        loginPagePasswordField = new PasswordField();
        loginPagePasswordField.setMinSize(80, 30);

        loginPageLoginButton = new Button("Login");
        loginPageLoginButton.setMinWidth(115);
        loginPageLoginButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            if (this.login() == 1) {
                networkMenuRootPane.setTop(topBar);
                primaryStage.setScene(networkMenuScene);
            }
        });

        loginPageSignupButton = new Button("Sign Up");
        loginPageSignupButton.setMinWidth(115);
        loginPageSignupButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            this.register();
        });
        loginPageImage = new Image(getClass().getResourceAsStream("1.png"));
        loginPageImageView = new ImageView();
        loginPageImageView.setImage(loginPageImage);
        loginPageImageView.setFitWidth(200);
        loginPageImageView.setPreserveRatio(true);
        loginPageImageView.setSmooth(true);
        loginPage = new GridPane();
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(loginPageUsernameLabel, 1, 1, 2, 1);
        GridPane.setHalignment(loginPageUsernameLabel, HPos.CENTER);
        loginPage.add(loginPageUsernameField, 3, 1, 3, 1);
        loginPage.add(loginPagePasswordLabel, 1, 2, 2, 1);
        GridPane.setHalignment(loginPagePasswordLabel, HPos.CENTER);
        loginPage.add(loginPagePasswordField, 3, 2, 3, 1);
        loginPage.add(loginPageLoginButton, 2, 3, 2, 1);
        loginPage.add(loginPageSignupButton, 4, 3, 1, 1);
        /**/
        Image closeButtonImage = new Image(getClass().getResourceAsStream("exit.png"), 18, 18, true, true);
        topBar = this.addButtonBarWithExitButton(new ImageView(closeButtonImage), primaryStage);
        topBar.setStyle("-fx-background-color: \n"
                + "linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n"
                + "linear-gradient(#020b02, #3a3a3a),\n"
                + "linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\n"
                + "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\n"
                + "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);");
        /**/
        gameMenuRootPane.setTop(topBar);
        loginPageRootPane.setBottom(loginPage);
        BorderPane.setMargin(loginPage, new Insets(0, 0, 10, 30));
        loginPageRootPane.setCenter(loginPageImageView);
        loginPageScene.getStylesheets().add(getClass().getResource("tictactoe.css").toString());
        loginPageRootPane.getStyleClass().add("borderPane");
        loginPageLoginButton.getStyleClass().add("ipad");
        loginPageSignupButton.getStyleClass().add("ipad");
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(gameMenuScene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    public ButtonBar addButtonBarWithExitButton(ImageView label, Stage thisStage) {
        /*Horizontal Button Box*/
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(3, 10, 3, 0));
        buttonBar.setButtonMinWidth(15);
        /*A button on the right*/
        Button rightButton = new Button();
        rightButton.setGraphic(label);
        rightButton.getStyleClass().add("ipadSmall");
        ButtonBar.setButtonData(rightButton, ButtonData.RIGHT);
        /*A button on the right and play Music*/
        //audio
        playMusic = new Button("Music");
        playMusic.getStyleClass().add("ipadSmall");
        ButtonBar.setButtonData(playMusic, ButtonData.NEXT_FORWARD.LEFT);
        playMusic.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            if (audioFlage == true) {
                audioMediaPlayer.pause();
                audioFlage = false;
            } else {
                audioMediaPlayer.play();
                audioFlage = true;
            }

        });

        Button homeButton = new Button("Home");

        homeButton.getStyleClass().add("ipadSmall");
        ButtonBar.setButtonData(homeButton, ButtonData.LEFT);
        homeButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            // gameMenuRootPane.setTop(topBar);
            // thisStage.setScene(gameMenuScene);
            stepCount = 1;
            flage = 0;
            computernum = 0;
            for (int j = 0; j < 9; j++) {
                arrStep[j] = 0;
            }
            thisStage.setScene(gameMenuScene);
            gameMenuRootPane.setTop(topBar);
            gameBoardRootPane.setBottom(null);
            GameBoard gameBoard = new GameBoard(gameBoardCellArray);
            gameBoard.gamePlay(thisStage);
        });
        buttonBar.getButtons().addAll(homeButton, rightButton, playMusic);
        rightButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {

            }
            Platform.exit();
        });
        /*Handling Drag and Drop*/
        buttonBar.setOnMousePressed((MouseEvent event) -> {
            TicTacToe.xOffset = thisStage.getX() - event.getScreenX();
            TicTacToe.yOffset = thisStage.getY() - event.getScreenY();
        });
        buttonBar.setOnMouseDragged((MouseEvent event) -> {
            thisStage.setX(event.getScreenX() + TicTacToe.xOffset);
            thisStage.setY(event.getScreenY() + TicTacToe.yOffset);
        });
        return buttonBar;
    }

    public class GameController {

        char win;

        public GameController() {
            win = 'n';

        }

        public char winnerPlayer(int[] arrStep) {

            if (arrStep[0] != 0 && arrStep[1] != 0 && arrStep[2] != 0) {
                /*check vertical*/
                if ((arrStep[0] % 2 == 0) && (arrStep[1] % 2 == 0) && (arrStep[2] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[0] % 2 != 0) && (arrStep[1] % 2 != 0) && (arrStep[2] % 2 != 0)) {
                    win = 'x';

                }

            }
            if (arrStep[3] != 0 && arrStep[4] != 0 && arrStep[5] != 0) {
                if ((arrStep[3] % 2 == 0) && (arrStep[4] % 2 == 0) && (arrStep[5] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[3] % 2 != 0) && (arrStep[4] % 2 != 0) && (arrStep[5] % 2 != 0)) {
                    win = 'x';

                }
            }
            if (arrStep[6] != 0 && arrStep[7] != 0 && arrStep[8] != 0) {
                if ((arrStep[6] % 2 == 0) && (arrStep[7] % 2 == 0) && (arrStep[8] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[6] % 2 != 0) && (arrStep[7] % 2 != 0) && (arrStep[8] % 2 != 0)) {
                    win = 'x';

                }
            }
            /*check horizontal*/
            if (arrStep[0] != 0 && arrStep[3] != 0 && arrStep[6] != 0) {
                if ((arrStep[0] % 2 == 0) && (arrStep[3] % 2 == 0) && (arrStep[6] % 2 == 0)) {
                    win = 'o';

                }

                if ((arrStep[0] % 2 != 0) && (arrStep[3] % 2 != 0) && (arrStep[6] % 2 != 0)) {
                    win = 'x';

                }
            }
            if (arrStep[1] != 0 && arrStep[4] != 0 && arrStep[7] != 0) {
                if ((arrStep[1] % 2 == 0) && (arrStep[4] % 2 == 0) && (arrStep[7] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[1] % 2 != 0) && (arrStep[4] % 2 != 0) && (arrStep[7] % 2 != 0)) {
                    win = 'x';

                }
            }
            if (arrStep[2] != 0 && arrStep[5] != 0 && arrStep[8] != 0) {
                if ((arrStep[2] % 2 == 0) && (arrStep[5] % 2 == 0) && (arrStep[8] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[2] % 2 != 0) && (arrStep[5] % 2 != 0) && (arrStep[8] % 2 != 0)) {
                    win = 'x';

                }
            }
            /*check diagonal*/
            if (arrStep[0] != 0 && arrStep[4] != 0 && arrStep[8] != 0) {
                if ((arrStep[0] % 2 == 0) && (arrStep[4] % 2 == 0) && (arrStep[8] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[0] % 2 != 0) && (arrStep[4] % 2 != 0) && (arrStep[8] % 2 != 0)) {
                    win = 'x';

                }
            }
            if (arrStep[2] != 0 && arrStep[4] != 0 && arrStep[6] != 0) {
                if ((arrStep[2] % 2 == 0) && (arrStep[4] % 2 == 0) && (arrStep[6] % 2 == 0)) {
                    win = 'o';

                }
                if ((arrStep[2] % 2 != 0) && (arrStep[4] % 2 != 0) && (arrStep[6] % 2 != 0)) {
                    win = 'x';

                }
            }

            return win;
        }

    }

//    public class GameReplay {
//
//        public void replay(int[] arrStep) {
//            int count = 1;
//            int step = 0;
//            for (int i = 10; i > count;) {
//                if (arrStep[step] == count) {
//                    // drawBord(step, count);
//                    count++;
//                    step = 0;
//                }
//                step++;
//            }
//        }
//
//    }
    public class GameBoard {

        public GameBoard(Button[] gameBoardCellArray) {
            for (int i = 0; i < 9; i++) {
                gameBoardCellArray[i] = new Button();
                gameBoardCellArray[i].setFocusTraversable(false);
                gameBoardCellArray[i].setMinSize(100, 100);

                if (i < 3) {
                    gameBoardCellPane.add(gameBoardCellArray[i], 15 + 2 * i, 1);
                } else if (i < 6) {
                    gameBoardCellPane.add(gameBoardCellArray[i], 9 + 2 * i, 2);
                } else {
                    gameBoardCellPane.add(gameBoardCellArray[i], 3 + 2 * i, 3);
                }
            }
        }

        public void gamePlay(Stage primaryStage) {
            flage = 0;
            stepCount = 1;
            for (int j = 0; j < 9; j++) {
                arrStep[j] = 0;
            }
            for (int i = 0; i < 9; i++) {
                final Button eventRegisteringMiddleMan = gameBoardCellArray[i];
                final int arrCount = arrGameButton[i];
                eventRegisteringMiddleMan.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                    //myPrintStream.println("Moved");
                    if (stepCount <= 9) {
                        if (stepCount % 2 == 0) {
                            int indexNumber = arrCount - 1;
                            if (arrStep[indexNumber] == 0) {
                                eventRegisteringMiddleMan.setGraphic(new ImageView(imago));
                                arrStep[indexNumber] = stepCount;
                                stepCount++;
                                eventRegisteringMiddleMan.setDisable(true);
                                eventRegisteringMiddleMan.setStyle("-fx-opacity: 1.0");

                                flage++;
                            }
                        } else {
                            int indexNumber = arrCount - 1;
                            if (arrStep[indexNumber] == 0) {
                                eventRegisteringMiddleMan.setGraphic(new ImageView(imagx));
                                arrStep[indexNumber] = stepCount;
                                stepCount++;
                                eventRegisteringMiddleMan.setDisable(true);
                                eventRegisteringMiddleMan.setStyle("-fx-opacity: 1.0");

                                flage++;
                            }
                        }
                    }

                    AlertWinner alertWinner = new AlertWinner();
                    alertWinner.AlertWinnerPlayer(primaryStage);
                    if (flage == 9) {
                        stepCount = 1;
                        flage = 0;
                        for (int j = 0; j < 9; j++) {
                            arrStep[j] = 0;
                        }

                        alertWinner.AlertDraw(primaryStage);
                    }

                });

            }

        }
    }

    public class AiGame {

        public AiGame() {
            new GameBoard(gameBoardCellArray);
        }

        public void AiGamePlay(Stage primaryStage) {
            flage = 0;
            computernum = 0;
            stepCount = 1;
            for (int j = 0; j < 9; j++) {
                arrStep[j] = 0;
            }
            for (int i = 0; i < 9; i++) {

                final Button eventRegisteringMiddleMan = gameBoardCellArray[i];
                final int arrCount = arrGameButton[i];

                eventRegisteringMiddleMan.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {

                    if (stepCount <= 9) {

                        int indexNumber = arrCount - 1;
                        if (arrStep[indexNumber] == 0) {

                            eventRegisteringMiddleMan.setGraphic(new ImageView(imagx));
                            gameBoardCellArray[indexNumber].setDisable(true);
                            gameBoardCellArray[indexNumber].setStyle("-fx-opacity: 1.0");

                            arrStep[indexNumber] = stepCount;
                            stepCount++;
                            AlertWinner alertWinner = new AlertWinner();
                            alertWinner.AlertWinnerPlayer(primaryStage);
                            flage++;
                        }

                    }
                    if (flage < 9) {
                        if(easy==true)
                            computernum = computer1();
                        if(meduim==true)
                            computernum = computer2();
                        if(hard==true)
                            computernum = computer();

                        int indexNumber = computernum;
                        if (arrStep[indexNumber] == 0) {

                            gameBoardCellArray[computernum].setGraphic(new ImageView(imago));

                            arrStep[indexNumber] = stepCount;

                            gameBoardCellArray[indexNumber].setDisable(true);
                            gameBoardCellArray[indexNumber].setStyle("-fx-opacity: 1.0");

                            stepCount++;

                            AlertWinner alertWinner = new AlertWinner();
                            alertWinner.AlertWinnerPlayer(primaryStage);
                            flage++;
                        }
                    } else if (flage == 9) {
                        AlertWinner alertWinner = new AlertWinner();

                        stepCount = 1;
                        flage = 0;
                        for (int j = 0; j < 9; j++) {
                            arrStep[j] = 0;
                        }

                        alertWinner.AlertDraw(primaryStage);

                    }

                });

            }

        }

        public int computer1() {

            /////////////level one 
            int randomNum;

            do {
                randomNum = (int) (Math.floor(Math.random() * 9));
            } while (arrStep[randomNum] != 0);

            return randomNum;

        }

        public int computer2() {
            //////////////////level two 

            int randomNum = 0;
            int y[] = new int[4];
            int z = 0;
            int arrConner[] = new int[]{1, 3, 7, 9};
            for (int i = 0; i < 4; i++) {

                if (arrStep[arrConner[i] - 1] == 0) {

                    y[z] = arrConner[i];
                    z++;

                }

            }
            if (y[0] > 0) {

                //  System.out.println("z" + y[0]);
                return (y[0] - 1);
            } else {
                do {
                    randomNum = (int) (Math.floor(Math.random() * 9));
                } while (arrStep[randomNum] != 0);

            }
            return randomNum;

        }

        public int computer() {
//////////////////////////level three
            int randomNum = 0;
            if (flage == 1) {
                do {
                    randomNum = (int) (Math.floor(Math.random() * 9));
                } while (arrStep[randomNum] != 0);
            }
//123
            if ((arrStep[0] % 2 != 0 && arrStep[1] % 2 != 0) || (arrStep[0] % 2 != 0 && arrStep[2] % 2 != 0) || (arrStep[1] % 2 != 0 && arrStep[2] % 2 != 0)) {
                for (int i = 0; i < 3; i++) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //456
            if ((arrStep[3] % 2 != 0 && arrStep[4] % 2 != 0) || (arrStep[3] % 2 != 0 && arrStep[5] % 2 != 0) || (arrStep[4] % 2 != 0 && arrStep[5] % 2 != 0)) {
                for (int i = 3; i < 6; i++) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //789
            if ((arrStep[6] % 2 != 0 && arrStep[7] % 2 != 0) || (arrStep[6] % 2 != 0 && arrStep[8] % 2 != 0) || (arrStep[7] % 2 != 0 && arrStep[8] % 2 != 0)) {
                for (int i = 6; i < 9; i++) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //147
            if ((arrStep[0] % 2 != 0 && arrStep[3] % 2 != 0) || (arrStep[0] % 2 != 0 && arrStep[6] % 2 != 0) || (arrStep[6] % 2 != 0 && arrStep[3] % 2 != 0)) {
                for (int i = 0; i < 7; i += 3) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //258
            if ((arrStep[1] % 2 != 0 && arrStep[4] % 2 != 0) || (arrStep[1] % 2 != 0 && arrStep[7] % 2 != 0) || (arrStep[4] % 2 != 0 && arrStep[7] % 2 != 0)) {
                for (int i = 1; i < 8; i += 3) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //369
            if ((arrStep[2] % 2 != 0 && arrStep[5] % 2 != 0) || (arrStep[2] % 2 != 0 && arrStep[8] % 2 != 0) || (arrStep[5] % 2 != 0 && arrStep[8] % 2 != 0)) {
                for (int i = 2; i < 9; i += 3) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //159
            if ((arrStep[0] % 2 != 0 && arrStep[4] % 2 != 0) || (arrStep[4] % 2 != 0 && arrStep[8] % 2 != 0) || (arrStep[0] % 2 != 0 && arrStep[8] % 2 != 0)) {
                for (int i = 0; i < 9; i += 4) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            //357
            if ((arrStep[2] % 2 != 0 && arrStep[4] % 2 != 0) || (arrStep[2] % 2 != 0 && arrStep[6] % 2 != 0) || (arrStep[4] % 2 != 0 && arrStep[6] % 2 != 0)) {
                for (int i = 2; i < 7; i += 2) {

                    if (arrStep[i] == 0) {
                        randomNum = i;
                        return randomNum;
                    }
                }
            }
            do {
                randomNum = (int) (Math.floor(Math.random() * 9));
            } while (arrStep[randomNum] != 0);
            return randomNum;
        }

    }

    public class AlertWinner {

        public void AlertWinnerPlayer(Stage primaryStage) {
            GameController player = new GameController();
            winner = player.winnerPlayer(arrStep);
            if (winner != 'n') {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Tic Tac Toe");
                alert.setHeaderText("Player " + winner + " Winner");
                alert.setContentText("Do you Want Play Again ?");
                ButtonType buttonTypeOne = new ButtonType("Show Video");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne) {
                    audioMediaPlayer.pause();
                    primaryStage.setScene(gameMenuScene);
                    gameMenuRootPane.setTop(topBar);
                    GameBoard gameBoard = new GameBoard(gameBoardCellArray);
                    gameBoard.gamePlay(primaryStage);
                    prize = new MediaPlayer(new Media(getClass().getResource("video.mp4").toExternalForm()));
                    mediaView = new MediaView(prize);
                    BorderPane panal = new BorderPane();
                    panal.setCenter(mediaView);
                    Scene scene = new Scene(panal, 400, 300);
                    Stage stage = new Stage(StageStyle.UNIFIED);
                    stage.setScene(scene);
                    stage.show();
                    prize.play();
                    stepCount = 1;
                    flage = 0;
                    computernum = 0;
                    for (int j = 0; j < 9; j++) {
                        arrStep[j] = 0;
                    }
                } else if (result.get() == buttonTypeCancel) {
                    stepCount = 1;
                    flage = 0;
                    computernum = 0;
                    for (int j = 0; j < 9; j++) {
                        arrStep[j] = 0;
                    }
                    primaryStage.setScene(gameMenuScene);
                    gameMenuRootPane.setTop(topBar);
                    GameBoard gameBoard = new GameBoard(gameBoardCellArray);
                    gameBoard.gamePlay(primaryStage);
                }

            }

        }

        public void AlertDraw(Stage primaryStage) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Draw !!");
            alert.setContentText("Do you Want Play Again ?");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeCancel) {
                stepCount = 1;
                flage = 0;
                computernum = 0;
                for (int j = 0; j < 9; j++) {
                    arrStep[j] = 0;
                }
                primaryStage.setScene(gameMenuScene);
                gameMenuRootPane.setTop(topBar);
                GameBoard gameBoard = new GameBoard(gameBoardCellArray);
                gameBoard.gamePlay(primaryStage);
            }

        }
    }

    public static void showAlert(String message, int type) {
        Alert myAlert;
        if (type == 1) {
            myAlert = new Alert(AlertType.ERROR);
            myAlert.setTitle("Tic Tac Toe");
            myAlert.setHeaderText("Error");
            myAlert.setContentText(message);
            myAlert.showAndWait();
        } else if (type == 2) {
            myAlert = new Alert(AlertType.INFORMATION);
            myAlert.setTitle("Tic Tac Toe");
            myAlert.setHeaderText("Information");
            myAlert.setContentText(message);
            myAlert.showAndWait();
        }
    }

    public int login() {
        try {
            if (loginPageUsernameField.getText().length() < 4) {
                TicTacToe.showAlert("Please Enter a correct username!!", 1);
            } else if (loginPagePasswordField.getText().length() < 6) {
                TicTacToe.showAlert("Please Enter a correct password!!", 1);
            } else {
                String loginRequest = "login/" + loginPageUsernameField.getText() + "/" + loginPagePasswordField.getText();
                clientSocket = new Socket(InetAddress.getLocalHost(), 5005);
                clientOutputStream = new PrintStream(clientSocket.getOutputStream());
                clientInputStream = new DataInputStream(clientSocket.getInputStream());
                clientOutputStream.println(loginRequest);
                String[] serverReply = clientInputStream.readLine().split("/");
                switch (serverReply[0]) {
                    /*Successfully Logged In*/
                    case "Authorized":
                        TTTNetworkId = Integer.parseInt(serverReply[1]);
                        TTTNetworkName = serverReply[2];
                        //TicTacToe.showAlert("Welcome " + TTTNetworkName + " ID: " + TTTNetworkId, 2);
                        //this.startNetworkGame();
                        return 1;
                    case "WrongPassword":
                        TicTacToe.showAlert("Wrong Password", 1);
                        break;
                    case "Forbidden":
                        TicTacToe.showAlert("Not a Registered User", 1);
                        break;
                    case "Error":
                        TicTacToe.showAlert("Error Occurred!! Please try again later.", 1);
                        break;
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            TicTacToe.showAlert("Connection Error", 1);
            //Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*Failure to login*/
        return 0;
    }

    public void register() {
        try {
            if (loginPageUsernameField.getText().length() < 6) {
                TicTacToe.showAlert("Username must be at least 6 characters long", 1);
            } else if (loginPagePasswordField.getText().length() < 6) {
                TicTacToe.showAlert("Password must be at least 6 characters long", 1);
            } else {
                String registrationRequest = "register/" + loginPageUsernameField.getText() + "/" + loginPagePasswordField.getText();
                clientSocket = new Socket(InetAddress.getLocalHost(), 5005);
                clientOutputStream = new PrintStream(clientSocket.getOutputStream());
                clientInputStream = new DataInputStream(clientSocket.getInputStream());
                clientOutputStream.println(registrationRequest);
                String[] serverReply = clientInputStream.readLine().split("/");
                switch (serverReply[0]) {
                    case "Duplicate":
                        TicTacToe.showAlert("Username exists!!", 1);
                        break;
                    case "Error":
                        TicTacToe.showAlert("Some Error Ocurred! Please try again later.", 1);
                        break;
                    case "Successful":
                        TicTacToe.showAlert("Registeration Successful", 2);
                        break;
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            TicTacToe.showAlert("Connection Error", 1);
            //Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void startNetworkGame(Button[] gameBoardCellArray, StatusBar networkGameStatusBar, Stage thisStage) {
        for (int i = 0; i < 9; i++) {
            final Button eventRegisteringMiddleman = gameBoardCellArray[i];
            eventRegisteringMiddleman.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                clickedButton = eventRegisteringMiddleman;
                int moveIndex = Arrays.asList(this.gameBoardCellArray).indexOf(eventRegisteringMiddleman);
                clientOutputStream.println("MOVE " + moveIndex);
            });
        }
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), 5005);
            clientOutputStream = new PrintStream(clientSocket.getOutputStream());
            clientInputStream = new DataInputStream(clientSocket.getInputStream());
            String gameRequest = "requestGame/" + this.TTTNetworkId;
            clientOutputStream.println(gameRequest);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            serverMessage = clientInputStream.readLine();
            if (serverMessage.startsWith("WELCOME")) {
                char mark = serverMessage.charAt(8);
                if (mark == 'X') {
                    networkGameIcon = imagx;
                    networkGameOpponentIcon = imago;
                } else {
                    networkGameIcon = imago;
                    networkGameOpponentIcon = imagx;
                }
            }
            networkGameThread = new Thread(() -> {
                while (true) {
                    try {
                        serverMessage = clientInputStream.readLine();
                        if (serverMessage.startsWith("VALID_MOVE")) {
                            Platform.runLater(() -> {
                                networkGameStatusBar.setText("Valid move, please wait");
                                clickedButton.setGraphic(new ImageView(networkGameIcon));
                            });
                        } else if (serverMessage.startsWith("OPPONENT_MOVED")) {
                            int loc = Integer.parseInt(serverMessage.substring(15));
                            Platform.runLater(() -> {
                                gameBoardCellArray[loc].setGraphic(new ImageView(networkGameOpponentIcon));
                                networkGameStatusBar.setText("Opponent moved, your turn");
                            });
                        } else if (serverMessage.startsWith("VICTORY")) {
                            Platform.runLater(() -> {
                                networkGameStatusBar.setText("You win");
                                //System.out.println(serverMessage.substring(8));
                                showRecordingAlert("You've Won", serverMessage.substring(8), thisStage);
                            });
                            break;
                        } else if (serverMessage.startsWith("DEFEAT")) {
                            Platform.runLater(() -> {
                                networkGameStatusBar.setText("You lose");
                                //System.out.println(serverMessage.substring(7));
                                showRecordingAlert("You've Lost", serverMessage.substring(7), thisStage);
                            });
                            break;
                        } else if (serverMessage.startsWith("TIE")) {
                            Platform.runLater(() -> {
                                networkGameStatusBar.setText("You tied");
                                //System.out.println(serverMessage.substring(4));
                                showRecordingAlert("You've Tied", serverMessage.substring(4), thisStage);
                            });
                            break;
                        } else if (serverMessage.startsWith("MESSAGE")) {
                            Platform.runLater(() -> {
                                networkGameStatusBar.setText(serverMessage.substring(8));
                            });
                        }
                    } catch (IOException ex) {
                        //Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                }
                clientOutputStream.println("QUIT");
                //clientSocket.close();
            });
            networkGameThread.start();
        } catch (IOException ex) {
            Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }

    public Alert showRecordingAlert(String message, String stepsArray, Stage thisStage) {
        Alert myAlert = new Alert(AlertType.INFORMATION);
        myAlert.setTitle("Tic Tac Toe - Save Game?");
        myAlert.setHeaderText("Game Over");
        myAlert.setContentText(message + "! Do You want to save the record?");
        ButtonType save = new ButtonType("Save Record", ButtonData.CANCEL_CLOSE);
        ButtonType cancel = new ButtonType("Go Back", ButtonData.CANCEL_CLOSE);
        myAlert.getButtonTypes().setAll(save, cancel);
        Optional<ButtonType> result = myAlert.showAndWait();
        if (result.get() == save) {
            try {
                clientSocket = new Socket(InetAddress.getLocalHost(), 5005);
                clientOutputStream = new PrintStream(clientSocket.getOutputStream());
                clientInputStream = new DataInputStream(clientSocket.getInputStream());
                clientOutputStream.println("recordingRequest/" + TTTNetworkId + "/" + stepsArray);
            } catch (UnknownHostException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
            networkMenuRootPane.setTop(topBar);
            networkMenuRootPane.setBottom(null);
            thisStage.setScene(networkMenuScene);
        } else if (result.get() == cancel) {
            networkMenuRootPane.setTop(topBar);
            networkMenuRootPane.setBottom(null);
            thisStage.setScene(networkMenuScene);
        }
        return myAlert;
    }

    public void showMyRecords(Stage thisStage) {
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), 5005);
            clientOutputStream = new PrintStream(clientSocket.getOutputStream());
            clientInputStream = new DataInputStream(clientSocket.getInputStream());
            clientOutputStream.println("requestRecords/" + TTTNetworkId);
            String serverreply = clientInputStream.readLine();
            if (serverreply.length() == 0) {
                TicTacToe.showAlert("No Records Available!!", 1);
            } else {
                serverReplywithRecords = serverreply.split(",");
                this.createRecordsScene(thisStage);
            }
        } catch (IOException e) {
            TicTacToe.showAlert("Connection Error", 1);
        }
    }

    public void createRecordsScene(Stage thisStage) {
        int i;
        BorderPane recordsHomeRootPane = new BorderPane();
        Scene recordsHomeScene = new Scene(recordsHomeRootPane, 410, 410);
        recordsHomeScene.getStylesheets().add(getClass().getResource("tictactoe.css").toString());
        recordsHomeRootPane.setTop(topBar);
        //ArrayList<Button> recordButtonsArrayList = new ArrayList<>();
        ScrollPane recordButtonsScrollPane = new ScrollPane();
        recordButtonsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        recordButtonsScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        recordButtonsScrollPane.setPadding(new Insets(15, 15, 15, 15));
        GridPane recordButtonsGridPane = new GridPane();
        recordButtonsGridPane.setVgap(15);
        recordButtonsGridPane.setHgap(15);
        for (i = 0; i < serverReplywithRecords.length; i++) {
            Button recordButton = new Button("Game No. " + serverReplywithRecords[i].split("/")[0]);
            recordButton.getStyleClass().add("ipad");
            recordButton.setPrefWidth(200);
            recordButtonsGridPane.add(recordButton, 1, i + 1);
            final String eventRegisteringMiddleMan = serverReplywithRecords[i].split("/", 2)[1];
            recordButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                this.playRecord(eventRegisteringMiddleMan, thisStage);
            });
        }
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("ipad");
        backButton.setPrefWidth(200);
        backButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            networkMenuRootPane.setTop(topBar);
            thisStage.setScene(networkMenuScene);
        });
        recordButtonsGridPane.add(backButton, 1, i + 1);
        recordButtonsScrollPane.setContent(recordButtonsGridPane);
        recordButtonsScrollPane.setPadding(new Insets(5, 5, 5, 80));
        recordsHomeRootPane.setCenter(recordButtonsScrollPane);
        recordsHomeRootPane.getStyleClass().add("borderPane");
        thisStage.setScene(recordsHomeScene);
    }

    public void playRecord(String recordSteps, Stage thisStage) {
        String[] recordStepsArray = recordSteps.split("/");
        new GameBoard(gameBoardCellArray);
        gameBoardRootPane.setTop(topBar);
        thisStage.setScene(gameBoardScene);
        new Thread(() -> {
            try {
                int replayCounter = 0;
                while (replayCounter < 9) {
                    Thread.sleep(1500);
                    if (Integer.parseInt(recordStepsArray[replayCounter]) == -1) {
                        break;
                    }
                    final boolean turn = replayCounter % 2 == 0;
                    final Button eventRegisteringMiddleMan = gameBoardCellArray[Integer.parseInt(recordStepsArray[replayCounter])];
                    Platform.runLater(() -> {
                        eventRegisteringMiddleMan.setGraphic(turn ? new ImageView(imagx) : new ImageView(imago));
                    });
                    replayCounter++;
                }
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                //Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.runLater(() -> {
                TicTacToe.this.createRecordsScene(thisStage);
                //networkMenuRootPane.setTop(topBar);
                //thisStage.setScene(networkMenuScene);
            });
        }).start();
    }

}
