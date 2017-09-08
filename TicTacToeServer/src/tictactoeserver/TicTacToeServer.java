/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Properties;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfoda
 */
public class TicTacToeServer {
    public static boolean gameWaiting = false;
    public static Game game;
    public static Game.Player playerX;
    public static Socket firstPlayerSocket;
    public static DataInputStream firstPlayerInputStream;
    public static Game.Player playerO;
    public static ServerSocket gameServerSocket;
    public static void main(String[] args) {
        try {
            gameServerSocket = new ServerSocket(5005);
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server Waiting");
        while (true) {            
            try {
                Socket clientSocket = gameServerSocket.accept();
                DataInputStream serverInputStream = new DataInputStream(clientSocket.getInputStream());
                PrintStream serverOutputStream = new PrintStream(clientSocket.getOutputStream());
                String clientRequest = serverInputStream.readLine();
                String[] clientRequestArray = clientRequest.split("/");
                switch (clientRequestArray[0]) {
                    case "login":
                        int id = DatabaseManager.authenticate(clientRequestArray[1], clientRequestArray[2]);
                        if (id > 0) {
                            /*User Found and the password is correct*/
                            serverOutputStream.println("Authorized/" + id + "/" + clientRequestArray[1]);
                            System.out.println("Authorized/" + id + "/" + clientRequestArray[1]);
                        } else if (id == 0) {
                            /*User Found but the password is wrong*/
                            serverOutputStream.println("WrongPassword");
                            System.out.println("WrondPassword");                        
                        } else if (id == -2) {
                            /*User Found but the password is wrong*/
                            serverOutputStream.println("Error");
                            System.out.println("Error");                          
                        } else if (id == -1){
                            /*User Not Found*/
                            serverOutputStream.println("Forbidden");
                            System.out.println("Forbidden");
                        }
                        break;
                    case "register":
                        int registrationResult = DatabaseManager.addUser(clientRequestArray[1], clientRequestArray[2]);
                        switch(registrationResult){
                            case -1:
                                serverOutputStream.println("Duplicate");
                                System.out.println("Duplicate");                            
                                break;
                            case 0:
                                serverOutputStream.println("Error");
                                System.out.println("Error");                                
                                break;
                            case 1:
                                serverOutputStream.println("Successful");
                                System.out.println("Successful");                            
                                break;
                        }
                        break;
                    case "requestGame":
                        System.out.println("Client Requesting Game...");
                        if (!gameWaiting) {
                            game = new Game();                            
                            playerX = game.new Player(clientSocket, 'X', clientRequestArray[1]);
                            gameWaiting = true;
                        } else {
                            playerO = game.new Player(clientSocket, 'O', clientRequestArray[1]);
                            playerX.setOpponent(playerO);
                            playerO.setOpponent(playerX);
                            game.currentPlayer = playerX;
                            playerX.start();
                            playerO.start();
                            gameWaiting = false; 
                        }
                        break;
                    case "recordingRequest":
                        /*Saver ID*/
                        System.out.println(clientRequestArray[1]);
                        /*Game ID*/
                        System.out.println(clientRequestArray[2]);
                        /*Steps Array*/
                        System.out.println(clientRequestArray[3]);
                        String[] stepsArray = clientRequestArray[3].split(",");
                        DatabaseManager.addGameRecord(Integer.parseInt(clientRequestArray[2]), Integer.parseInt(clientRequestArray[1]), stepsArray);                
                        break;
                    case "requestRecords":
                        System.out.println("request ID : "+clientRequestArray[1]);
                        String recordsString = DatabaseManager.getRecord(Integer.parseInt(clientRequestArray[1]));
                        serverOutputStream.println(recordsString);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
}

class DatabaseManager {

    private static final String dbClassName = "com.mysql.jdbc.Driver";
    private static final String CONNECTION
            = "jdbc:mysql://127.0.0.1/tictactoe";
	//Used for registration
    public static int addUser(String name, String pass) {
        try {
            System.out.println(dbClassName);

            Class.forName(dbClassName);

            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");

            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();
            //System.out.println("It works !");
            String queryString = new String("INSERT INTO player (name,password) " + "VALUES ('" + name + "', '" + pass + "');");
            stmt.executeUpdate(queryString);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e){
            /*Duplicate Entry*/
            //e.printStackTrace();
            return 0;
        } catch(CommunicationsException e) {
            //e.printStackTrace();
            return 0;            
        } catch (SQLException e) {
            /*Some Sql Error Occurred*/
            //e.printStackTrace();
            return 0;
        }
        System.out.println("Successful");
        return 1;
    }
	//Used for Login
    public static int authenticate(String n, String ps) {
        try {
            System.out.println(dbClassName);

            Class.forName(dbClassName);

            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");

            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();

            //System.out.println("It works !");
            String queryString = new String("select * from player where name = '" + n + "' and password='" + ps + "' ;");
            ResultSet rs = stmt.executeQuery(queryString);
            /*Credentials valid*/
            while (rs.next()) {
                String id = rs.getString("id");
                System.out.println(id);
                return Integer.parseInt(id);
            }
            /*Check if user exists*/
            queryString = new String("select * from player where name = '" + n + "' ;");
            rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                return 0;
            }
        } catch (ClassNotFoundException cs) {
            //cs.printStackTrace();
        } catch(CommunicationsException e) {
            //e.printStackTrace();
            return -2;            
        } catch (SQLException sl) {
            //sl.printStackTrace();
            return -2;
        }
        /*User doesn't exist*/
        return -1;
    }
	//select all games by user id
    public static void getWinner(int userid) {
        try {
            System.out.println(dbClassName);

            Class.forName(dbClassName);

            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");

            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();

            //System.out.println("It works !");
            String queryString = new String("select g.id, g.winner, g.date from game g ,player p where g.winner=p.id and p.id ='" + userid + "';");
            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                System.out.println(rs.getString("g.winner"));
            }
        } catch (ClassNotFoundException cs) {
            cs.printStackTrace();
        } catch(CommunicationsException e) {
            //e.printStackTrace();
        } catch (SQLException sl) {
            sl.printStackTrace();
        }
    }
	//insert a record into database
    public static void addGameRecord(int gid, int saveid, String[] record) {
        try {
            System.out.println(dbClassName);
            Class.forName(dbClassName);
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");
            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();
            //System.out.println("It works !");
            String queryString = new String("INSERT INTO gameSteps " + "VALUES ('" + gid + "', '" + saveid + "','" + record[0] + "','" + record[1] + "','" + record[2] + "','" + record[3] + "','" + record[4] + "','" + record[5] + "','" + record[6] + "','" + record[7] + "','" + record[8] + "');");
            stmt.executeUpdate(queryString);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException cs) {
            cs.printStackTrace();
        } catch(CommunicationsException e) {
            //e.printStackTrace();
        } catch (SQLException sl) {
            sl.printStackTrace();
        }
    }
	//insert winner in game and returns inserted game id, or -1 on failure
    public static int addWinner(int winid) {
        try {
            System.out.println(dbClassName);

            Class.forName(dbClassName);

            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");

            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();

            String queryString = new String("INSERT INTO game(winner) " + "VALUES ('" + winid + "');");

            stmt.executeUpdate(queryString, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            while(rs.next()) {
                return rs.getInt(1);
            }
            stmt.close();
            c.close();
        } catch (ClassNotFoundException cs) {
            cs.printStackTrace();
        } catch(CommunicationsException e) {
            //e.printStackTrace();
        } catch (SQLException sl) {
            sl.printStackTrace();
        }
        //In Case Insertion Fails
        return -1;
    }
        //insert two players and game id in gamelog
    public static void addPlayer(int gameid, int playerid) {
        try {
            //System.out.println(dbClassName);
            Class.forName(dbClassName);
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");
            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();
            //System.out.println("It works !");
            String queryString = new String("INSERT INTO gameLog(game_id,player_id) " + "VALUES ('" + gameid + "','" + playerid + "');");
            stmt.executeUpdate(queryString);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException cs) {
            cs.printStackTrace();
        } catch(CommunicationsException e) {
            //e.printStackTrace();
        } catch (SQLException sl) {
            sl.printStackTrace();
        }
    }
        //select all by saver id foreign key for user id
    public static String getRecord(int saveid) {
        String resultString = "";
        try {
            System.out.println(dbClassName);
            Class.forName(dbClassName);
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "root");
            Connection c = DriverManager.getConnection(CONNECTION, p);
            Statement stmt = c.createStatement();
            String queryString = new String("SELECT * FROM gamesteps WHERE saver_id= '" + saveid + "';");
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                resultString+= rs.getString(1);
                for (int i = 0; i < 9; i++) {
                    resultString += "/"+rs.getString(i+3);
                }
                resultString+=",";
            }
            System.out.println(resultString);
            return resultString;            
        } catch (ClassNotFoundException cs) {
            cs.printStackTrace();
        } catch(CommunicationsException e) {
            //e.printStackTrace();
            return "";            
        } catch (SQLException sl) {
            sl.printStackTrace();
        }
        return "";
    }

}

class Game {

    public int insertedGameId;
    private Player[] board = {
        null, null, null,
        null, null, null,
        null, null, null};
    /*Game Steps for Recording*/
    public String[] steps = {
        "-1","-1","-1",
        "-1","-1","-1",
        "-1","-1","-1"};
    public int stepCounter = 0;
    Player currentPlayer;

    public boolean hasWinner() {
        return
            (board[0] != null && board[0] == board[1] && board[0] == board[2])
          ||(board[3] != null && board[3] == board[4] && board[3] == board[5])
          ||(board[6] != null && board[6] == board[7] && board[6] == board[8])
          ||(board[0] != null && board[0] == board[3] && board[0] == board[6])
          ||(board[1] != null && board[1] == board[4] && board[1] == board[7])
          ||(board[2] != null && board[2] == board[5] && board[2] == board[8])
          ||(board[0] != null && board[0] == board[4] && board[0] == board[8])
          ||(board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    public boolean boardFilledUp() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean legalMove(int location, Player player) {
        if (player == currentPlayer && board[location] == null) {
            board[location] = currentPlayer;
            steps[stepCounter++] = Integer.toString(location);
            //System.out.println("Step No. " + stepCounter + " : Location " + location);
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    class Player extends Thread {
        char mark;
        int id;
        Player opponent;
        Socket socket;
        BufferedReader input;
        PrintWriter output;

        public Player(Socket socket, char mark, String playerid) {
            this.socket = socket;
            this.mark = mark;
            this.id = Integer.parseInt(playerid);
            try {
                input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + mark);
                //System.out.println("WELCOME " + mark);
                output.println("MESSAGE Waiting for opponent to connect");
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void otherPlayerMoved(int location) {
            output.println("OPPONENT_MOVED " + location);
            //output.println(hasWinner() ? "DEFEAT" : boardFilledUp() ? "TIE" : "");  
            if (hasWinner()) {
                insertedGameId = DatabaseManager.addWinner(this.id);  
                DatabaseManager.addPlayer(insertedGameId, this.id);
                DatabaseManager.addPlayer(insertedGameId, opponent.id);
                //DatabaseManager.addGameRecord(insertedGameId, this.id, steps);
                //DatabaseManager.addGameRecord(insertedGameId, opponent.id, steps);
                output.println("DEFEAT/"+insertedGameId+"/"+String.join(",", steps));
            } else if (boardFilledUp()) {
                insertedGameId = DatabaseManager.addWinner(1);
                DatabaseManager.addPlayer(insertedGameId, this.id);
                DatabaseManager.addPlayer(insertedGameId, opponent.id);
                //DatabaseManager.addGameRecord(insertedGameId, this.id, steps);
                //DatabaseManager.addGameRecord(insertedGameId, opponent.id, steps);                
                output.println("TIE/"+insertedGameId+"/"+String.join(",", steps));
            } else {
                output.println("");
            }
        }

        @Override
        public void run() {
            try {
                // The thread is only started after everyone connects.
                output.println("MESSAGE All players connected");

                // Tell the first player that it is her turn.
                if (mark == 'X') {
                    output.println("MESSAGE Your move");
                }

                // Repeatedly get commands from the client and process them.
                while (true) {
                    String command = input.readLine();
                    if (command.startsWith("MOVE")) {
                        int location = Integer.parseInt(command.substring(5));
                        if (legalMove(location, this)) {
                            output.println("VALID_MOVE");
                            //output.println(hasWinner() ? "VICTORY" : boardFilledUp() ? "TIE" : "");
                            if (hasWinner()) {
                                output.println("VICTORY/"+insertedGameId+"/"+String.join(",", steps));                            
                            } else if (boardFilledUp()) {
                                output.println("TIE/"+insertedGameId+"/"+String.join(",", steps));                                                            
                            } else {
                                output.println("");
                            }                                                                                                           
                        } else {
                            output.println("MESSAGE ?");
                        }
                    } else if (command.startsWith("QUIT")) {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }
    }
}
