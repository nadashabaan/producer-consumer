import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Gui {

    JLabel l2;
    JLabel l3;
    JLabel l4;
    JLabel l5;
    JLabel l6;
    static JLabel l7;
    static JLabel l8;
    static JLabel l9;
    public Gui() {


        JFrame f = new JFrame();//creating instance of JFrame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton b = new JButton("Start Producer");
        b.setBounds(20, 200, 150, 40);

        f.add(b);//adding button in JFrame

        f.setSize(400, 500);
        f.setLayout(null);
        f.setVisible(true);
        JTextField tf1, tf2, tf3;
        tf1 = new JTextField();
        tf1.setBounds(20, 50, 200, 30);
        tf2 = new JTextField();
        tf2.setBounds(20, 100, 200, 30);
        tf3 = new JTextField();
        tf3.setBounds(20, 150, 200, 30);
        f.add(tf1);
        f.add(tf2);
        f.add(tf3);
        f.setSize(600, 500);

        JLabel l1 = new JLabel();
        l1.setBounds(250, 50, 50, 30);
        l1.setText("N");
        f.add(l1);
        l2 = new JLabel();
        l2.setBounds(250, 100, 200, 30);
        l2.setText("Buffer Size");
        f.add(l2);
        l3 = new JLabel();
        l3.setBounds(250, 150, 200, 30);
        l3.setText("Output File");
        f.add(l3);
        l4 = new JLabel();
        l4.setBounds(20, 250, 250, 30);
        l4.setText("the largest prime number:");
        f.add(l4);
        l5 = new JLabel();
        l5.setBounds(20, 300, 250, 30);
        l5.setText("# of elements (prime number) generated:");
        f.add(l5);
        l6 = new JLabel();
        l6.setBounds(20, 350, 250, 30);
        l6.setText("time elapsed since the start of processing:");
        f.add(l6);
        l7 = new JLabel();
        l7.setBounds(280, 250, 250, 30);
        l7.setText(" ");
        f.add(l7);
        l8 = new JLabel();
        l8.setBounds(280, 300, 250, 30);
        l8.setText(" ");
        f.add(l8);
        l9 = new JLabel();
        l9.setBounds(280, 350, 250, 30);
        l9.setText(" ");
        f.add(l9);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String N = tf1.getText();
                String Buffer = tf2.getText();
                String outputFile = tf3.getText();
                int n = Integer.parseInt(N);
                int buffer = Integer.parseInt(Buffer);
                Q q = new Q(n, buffer, outputFile);
                Thread produce1 = new Thread(new Runnable() {
                    public void run() {
                        new Producer(q);
                    }
                });
                Thread consume1 = new Thread(new Runnable() {
                    public void run() {
                        new Consumer(q);
                    }
                });
                produce1.start();
                consume1.start();
            }
        });


    }
    public static void setbigprime(String big){
        l7.setText(big);
    }
    public static void setPrimeNums(String primeNums){
        l8.setText(primeNums);
    }
    public static void setTime(String Time){
        l9.setText(Time);
    }
}
