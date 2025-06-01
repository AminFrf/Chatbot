        package org.aryanoor.app;

import org.aryanoor.services.IAM;
import org.aryanoor.services.OpenRouterChat;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class GUI extends JFrame implements ActionListener {

    private String apiUrl;
    private String apiKey;
    private static final String CONFIG_FILE = "config.properties";
//    private JTextField loginUserName = new JTextField();
//    private JPasswordField loginPassword = new JPasswordField();
//    private JTextField regesterUserName = new JTextField();
//    private JTextField regesterPassword = new JTextField();
    JTextField usernameText = new JTextField(25);
    JPasswordField passwordText = new JPasswordField(25);
    JPanel loginPanel = new JPanel();
    JLabel welcome;
    JPanel regesterPanel;
    JTextArea textArea;
    JTextField textField;

    public GUI() {
        try {
            loadConfig();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }

        setTitle("Chatbot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        loginPage();
        setVisible(true);
    }

    private void loadConfig() throws IOException {
        Properties properties = new Properties();
        if (Files.exists(Paths.get(CONFIG_FILE))) {
            List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE));
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    if (parts[0].trim().equalsIgnoreCase("apiUrl")) {
                        apiUrl = parts[1].trim();
                    } else if (parts[0].trim().equalsIgnoreCase("apiKey")) {
                        apiKey = parts[1].trim();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Configuration file not found. Please create 'config.properties' with apiUrl and apiKey.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void loginPage(){
        //remove(currentPanel);
        welcome = new JLabel("Welcome to Chatbot",  JLabel.CENTER);


        loginPanel = new JPanel(new GridBagLayout());
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        usernameText = new JTextField(25);
        passwordText = new JPasswordField(25);
        JButton loginButton = new JButton("Login");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);
        JButton registerButton = new JButton("dont have an acc?!");
        registerButton.setActionCommand("registerPage");
        registerButton.addActionListener(this);



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 3;
        loginPanel.add(usernameText, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 3;
        loginPanel.add(passwordText, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(registerButton, gbc);

        this.add(loginPanel, BorderLayout.CENTER);
        this.add(welcome, BorderLayout.NORTH);

        repaint();
        revalidate();
    }

    public void registerPage(){

        regesterPanel = new JPanel(new GridBagLayout());
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
      //  JLabel confirmPasswordLabel = new JLabel("Confirm Password: ");
        //JPasswordField confirmPasswordText = new JPasswordField(25);
        usernameText = new JTextField(25);
        passwordText = new JPasswordField(25);
        JButton registerButton = new JButton("Register");
        registerButton.setActionCommand("register");
        registerButton.addActionListener(this);
        JButton loginButton = new JButton("have an acc?!");
        loginButton.setActionCommand("loginPage");
        loginButton.addActionListener(this);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        regesterPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 3;
        regesterPanel.add(usernameText, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        regesterPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 3;
        regesterPanel.add(passwordText, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;


        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 3;

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 1;
        regesterPanel.add(registerButton, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 2; gbc.gridy = 2;
        gbc.gridwidth = 2;
        regesterPanel.add(loginButton, gbc);


        this.add(regesterPanel, BorderLayout.CENTER);
        this.add(welcome, BorderLayout.NORTH);

        repaint();
        revalidate();
    }

    private void chatBotPanel(){
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(600, 50));
        titlePanel.setBackground(new Color(23, 23, 23));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.green));


        JLabel titleLabel = new JLabel("Chatbot",  JLabel.LEFT);
        titleLabel.setForeground(new Color(24, 172, 24));
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));

        titlePanel.add(titleLabel);

        this.add(titlePanel, BorderLayout.NORTH);
        JPanel chatPanel = new JPanel();
        chatPanel.setPreferredSize(new Dimension(600, 300));
        textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textArea.setPreferredSize(new Dimension(600, 300));
        textArea.setBorder(BorderFactory.createLineBorder(Color.green));
        textArea.setText("Welcome to Chatbot\n");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        chatPanel.add(textArea, BorderLayout.CENTER);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        //this.getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.add(chatPanel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(600, 50));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 40));
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JButton sendButton = new JButton("Send");
        sendButton.setActionCommand("send");
        sendButton.addActionListener(this);
        sendButton.setPreferredSize(new Dimension(100,40));
        textPanel.add(textField);
        textPanel.add(sendButton);

        this.add(textPanel, BorderLayout.SOUTH);

        pack();
        repaint();
        revalidate();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI();
        });
    }

    public void actionPerformed(ActionEvent e) {
        String username;
        String password;
        switch (e.getActionCommand()) {
            case "login":
                username = usernameText.getText();
                password = passwordText.getText();
                if (username.isBlank() || password.isBlank()) {
                    JOptionPane.showMessageDialog(this,"Please enter correct user name or password", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                IAM user = new IAM(username, password);
                try {
                    if(user.login(username , password)){
                        JOptionPane.showMessageDialog(this, "You have successfully logged in!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        getContentPane().removeAll();
                        chatBotPanel();

                    }

                    break;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,"Please enter correct user name or password", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            case "registerPage":
                getContentPane().removeAll();
                registerPage();
                break;
            case "loginPage":
                getContentPane().removeAll();
                loginPage();
                break;
            case "register":
                username = usernameText.getText();
                password = passwordText.getText();
                IAM newUser = new IAM(username, password);
                try {
                    newUser.signUp();
                    JOptionPane.showMessageDialog(this,"User has been signed up successfully.","Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                getContentPane().removeAll();
                loginPage();
                break;
            case "send":
                OpenRouterChat chatBot = new OpenRouterChat(apiUrl, apiKey);
                String question = textField.getText();
                textField.setText("");
                textArea.append("You :"+ question + "\n");
                textArea.append("Thinking...\n");
                revalidate();
                repaint();

                try {
                    String response = chatBot.sendChatRequest(question);
                    textArea.append(response);
                    repaint();
                    revalidate();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,"Can't receive massage from bot", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
        }
    }


}