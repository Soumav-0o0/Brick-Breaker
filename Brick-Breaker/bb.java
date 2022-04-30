import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
public class bb extends JPanel
{
    static JFrame f;
    static bb obj;
    static int r=5; 
    static int d=r*2;
    //ball
    static int x1 =150;
    static int y1 =150;
    //paddle
    static int x2 =160;
    static int y2 =380;
    //bricks
    static int x3=12;
    static int y3=96;
    Rectangle[] brick= new Rectangle[30];
    int dx = 1;
    int dy = 1;
    int cc=0;
    int width=0;
    int height=0;
    boolean out=false;
    boolean done= false;
    String status;
    String status2;
    static boolean right = false;
    static boolean left = false;
    Rectangle ball = new Rectangle(x1,y1,d,d);
    Rectangle bat = new Rectangle(x2,y2,40,5);
    public static void main()
    {
        f=new JFrame("Brick Breaker"); 
        obj= new bb();
        f.setSize(350,425);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        f.getContentPane().setBackground(Color.BLACK);
        f.add(obj);//brick_breaker is a child class of JPanel and so obj of brick_breaker added to f means JPanel gets added to frame.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setFocusable(true);
        obj.requestFocus();
        obj.addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e) 
                {
                    int kc=e.getKeyCode();
                    if(kc==KeyEvent.VK_LEFT)
                    {
                        left=true; 
                    }
                    if(kc==KeyEvent.VK_RIGHT)
                    {
                        right=true; 
                    }

                }

                public void keyReleased(KeyEvent e) 
                {
                    int kc=e.getKeyCode();
                    if(kc==KeyEvent.VK_LEFT)
                    {
                        left=false; 
                    }
                    if(kc==KeyEvent.VK_RIGHT)
                    {
                        right=false; 
                    }

                }

                public void keyTyped(KeyEvent argO)
                {

                }
            });
        new bb();
    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0,0,350,450);
        g.setColor(Color.blue);
        g.fillOval(ball.x,ball.y,ball.width,ball.height);
        g.setColor(Color.green);
        g.fillRect(bat.x,bat.y,bat.width,bat.height);
        g.setColor(Color.red);
        for(int i=0;i<brick.length;i++)
        {
            if(brick[i]!=null)
            {
                g.fill3DRect(brick[i].x,brick[i].y,brick[i].width,brick[i].height,true);
            }
        }
        if(out == true || done ==  true)
        {
            Font ff=new Font("Arial",Font.BOLD, 20);
            g.setFont(ff);
            g.drawString(status,80,150);
            g.drawString(status2,95,175);
            out=false;
        }
    }
    
    public bb() 
    {
        Thread thread = new Thread() 
            {
                public void run()
                {
                    for(int i=0;i<brick.length;i++)
                    {
                        brick[i]=new Rectangle(x3,y3,29,10);
                        if(i==9)
                        {
                            x3=-19;
                            y3=108;
                        }
                        if(i==19)
                        {
                            x3=-19;
                            y3=120;
                        }
                        x3=x3+31;
                    }
                    while(true)
                    {
                        for(int i=0;i<brick.length;i++)
                        {
                            if(brick[i]!=null)
                            {
                                if(brick[i].intersects(ball))
                                {
                                    brick[i]=null;
                                    dy=-dy;
                                    cc++;
                                }
                            }
                        }
                        if(cc==brick.length)
                        {
                            done=true;
                            status = "You won the game";
                            status2= "Your Score = "+cc;
                            repaint();
                            break;
                        }
                        width = 325;
                        height = 390;
                        ball.x = ball.x + dx;
                        ball.y = ball.y + dy;
                        if (ball.x < 0)
                        {
                            dx = -dx; 
                            ball.x = r;
                        }
                        if (ball.x > width) 
                        {
                            dx = -dx;
                            ball.x = width - r;
                        }
                        if (ball.y < 0) 
                        {
                            dy = -dy;
                            ball.y = r;
                        }
                        else if (ball.y > height) 
                        {
                            /*dy = -dy;
                            y1 = height - r;*/
                            out=true;
                            status = "You Lost the game";
                            status2= "Your Score = "+cc;
                            repaint();
                            break;
                        }
                        repaint();
                        if(left==true)
                        {
                            bat.x=bat.x-1;
                            right=false;
                            repaint();
                        }
                        if(right==true)
                        {
                            bat.x=bat.x+1;
                            left=false;
                            repaint();
                        }
                        if(bat.x<=2)
                        {
                            bat.x=2;
                        }
                        else if(bat.x>=292)
                        {
                            bat.x=292;
                        }
                        if(ball.intersects(bat))
                        {
                            dy=dy-1;
                        }
                        try
                        {
                            Thread.sleep(7);
                        }
                        catch(InterruptedException ex)
                        {
                        }
                    }
                }
            };
        thread.start();
    }
}