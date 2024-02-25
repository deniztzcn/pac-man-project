import com.sun.source.tree.WhileLoopTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MenuWindow extends JFrame {
    private JButton startGame;
    private JButton showScore;
    private JButton exit;
    public MenuWindow() {
        setTitle("Pac-Man");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().setBackground(Color.black);
        startGame = createButton("Start Game");
        showScore = createButton("High Scores");
        showScore.setPreferredSize(new Dimension(150,50));
        exit = createButton("Exit");
        ImageIcon imgIcon = new ImageIcon("game_images/PacmanMenu.jpg");
        Image img = imgIcon.getImage();
        Image scaledImg = img.getScaledInstance(150,100,Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel topMenu = new JLabel(scaledIcon);

        gbc.gridx = 3;
        gbc.gridy = 0;
        add(topMenu,gbc);
        gbc.weighty = 0.2;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(startGame,gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(showScore,gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(exit,gbc);
        startGame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(GameWindow::new);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startGame.setBackground(Color.white);
                startGame.setOpaque(true);
                startGame.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startGame.setBackground(null);
                startGame.setForeground(Color.WHITE);
            }
        });

        showScore.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(ScoreListWindow::new);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                showScore.setBackground(Color.white);
                showScore.setForeground(Color.black);
                showScore.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showScore.setBackground(null);
                showScore.setForeground(Color.white);
            }
        });
        exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setBackground(Color.white);
                exit.setForeground(Color.black);
                exit.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.setBackground(null);
                exit.setForeground(Color.white);
            }
        });

        pack();
        setSize(610,710);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    private JButton createButton(String text){
        JButton button = new JButton(text);
        button.setBorder(null);
        button.setMargin(new Insets(0,0,0,0));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150,50));
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFont(new Font(Font.SERIF,Font.BOLD,15));
        return button;
    }
}
