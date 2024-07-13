import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.*;

public class Animation {
    public static void main(String[] args)
    {
        int rec = 5;

        JFrame frame = new JFrame("Animation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1980,1060);

        JPanel[] panel = new JPanel[2000];
        for(int h = 0; h<2000 ;h++)
        {
            panel[h] = new JPanel();
            panel[h].setSize(rec,rec );
            panel[h].setBackground(Color.GREEN);
            panel[h].setLayout(null);
            frame.add(panel[h]);
        }

        JPanel[] panel1 = new JPanel[2000];
        for(int h = 0; h<2000 ;h++)
        {
            panel1[h] = new JPanel();
            panel1[h].setSize(rec, rec);
            panel1[h].setBackground(Color.RED);
            panel1[h].setLayout(null);
            frame.add(panel1[h]);
        }

        JPanel[] panel2 = new JPanel[2000];
        for(int h = 0; h<2000 ;h++)
        {
            panel2[h] = new JPanel();
            panel2[h].setSize(rec, rec);
            panel2[h].setBackground(Color.BLUE);
            panel2[h].setLayout(null);
            frame.add(panel2[h]);
        }

        JPanel[] panel3 = new JPanel[2000];
        for(int h = 0; h<2000 ;h++)
        {
            panel3[h] = new JPanel();
            panel3[h].setSize(rec, rec);
            panel3[h].setBackground(Color.BLACK);
            panel3[h].setLayout(null);
            frame.add(panel3[h]);
        }

        JPanel[] panel4 = new JPanel[2000];
        for(int h = 0; h<2000 ;h++)
        {
            panel4[h] = new JPanel();
            panel4[h].setSize(rec, rec);
            panel4[h].setBackground(Color.orange);
            panel4[h].setLayout(null);
            frame.add(panel4[h]);
        }

        JPanel axis = new JPanel();
        axis.setBounds(0,530,1980,10);
        axis.setLayout(null);
        axis.setBackground(Color.GRAY);
        frame.add(axis);




        ActionListener listener = new ActionListener()
        {
            float y = 530;
            float k = 530;
            float j = 530;
            float t = 530;

            float fo = 530;
            float x = 0;
            float f = 20;

            int i = 0;
            public void actionPerformed(ActionEvent e)
            {

                y=((float)sin(x*2*3.14*5)*100) ;
                j=((float)-sin(x*2*3.14*10)*100) ;
                k=((float)cos(x*2*3.14*15)*100) ;
                t=((float)-cos(x*2*3.14*20)*100) ;

                fo = y+j+k+t + 530;

                x+=1;

                panel[i].setLocation((int)x,(int)y + 530);
                panel1[i].setLocation((int)x,(int)k+ 530);
                panel2[i].setLocation((int)x,(int)j+ 530);
                panel3[i].setLocation((int)x,(int)t+ 530);


                panel4[i].setLocation((int)x,(int)fo);
                i++;
            }};

        frame.setLayout(null);

        Timer timer = new Timer(    3, listener);
        timer.start();


        frame.setVisible(true);
    }
}
