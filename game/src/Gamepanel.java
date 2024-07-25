import java.awt.event.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.AffineTransform;


public class Gamepanel extends JPanel implements ActionListener {

    BufferedImage image;
    BufferedImage roadi;
    BufferedImage yourImage;
    BufferedImage eIs;
    BufferedImage logo;
    ArrayList<BufferedImage> carImages;
    ArrayList<ImageIcon> carIcons;
    BufferedImage[] enys;
    ActionListener carbtnAL;
    static final int SCREENWIDTH = 600;
    static final int SCREENHEIGHT = 600;
    static final int DELAY = 0;
    boolean Running = false;
    Timer timer;
    Random random;
    Clip clip1;
    Clip clip2;
    int sec = 0;
    int speed = 4;
    int yourspeed = 1;
    int ispeed = speed;
    int max = 50;
    int yourx = (SCREENWIDTH/2)-10;
    int youry = SCREENHEIGHT-150;
    int yourix = youry;
    int passed = 0;
    int enysPerV = 4;
    static final int UNITSIZE = 12;
    static final int GAMEUNITS = (SCREENWIDTH*SCREENHEIGHT)/UNITSIZE;
    final int enysx[] = new int[GAMEUNITS];
    final int enysy[] = new int[GAMEUNITS];
    ArrayList<Integer> xlist;
    ArrayList<Integer> ylist;
    int[] ri = new int[100];
    int introsleep = 60*4;
    int ra = 10;
    int imagesnum = 5;
    int treesnum = 5;
    boolean f = false;
    boolean sp = false;
    boolean won = false;
    boolean once = true;
    boolean paused = false;
    boolean mute = false;
    boolean played =  false;
    boolean started = false;
    boolean setted  = false;
    boolean sw = false;
    double ang = 0;
    boolean fang = true;
    int ci = 0;
    boolean cc = false;
    JButton reBtn;
    KeyAdapter AL;
    ArrayList<BufferedImage> trees;
    ArrayList<BufferedImage> treesImages;
    ArrayList<Integer> treesx;
    ArrayList<Integer> treesy;
    ArrayList<JButton> buttons = new ArrayList<>();
    String rootFolder = "C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\cars\\";
    JProgressBar progressBar;
    String bgfile = rootFolder+"bg1.png";
    public Gamepanel(BorderLayout bl) throws IOException {
        super();
        // Convert to BufferedImage for efficiency (optional)
        // progressBar = new JProgressBar(0);
        // progressBar.setValue(0);
        // progressBar.setSize(300, 2);
        // // progressBar.setBounds(100,100,3,2);
        // progressBar.setStringPainted(true);
        // progressBar.setIndeterminate(true);
        carImages = new ArrayList<>();
        carIcons = new ArrayList<>();
        xlist = new ArrayList<>();
        ylist = new ArrayList<>();
        trees = new ArrayList<>();
        treesx = new ArrayList<>();
        treesy = new ArrayList<>();
        treesImages = new ArrayList<>();
        enys = new BufferedImage[imagesnum+1];
        for (int i=0; i <= imagesnum;i++ ){
            String name = rootFolder+"car"+(i+1)+".png";
            System.out.println(name);
            File file = new File(name);
            BufferedImage in = ImageIO.read(file);
            carImages.add(in);
            ImageIcon icon = new ImageIcon(name);
            carIcons.add(icon);
            
            

        }
        for (int i=1;i<=treesnum;i++){
            String name = rootFolder+"tree"+i+".png";
            File file = new File(name);
            BufferedImage in = ImageIO.read(file);
            treesImages.add(in);
            trees.add(in);
            treesx.add(0);
            treesy.add(0);
        }
        
        image = carImages.get(3);
        logo = ImageIO.read(new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\logo.png"));
        eIs = ImageIO.read(new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\cars\\car3.png"));
        File  roadf = new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\cars\\road.png");
        roadi = ImageIO.read(roadf); // Convert to BufferedImage for efficiency (optional)
        xlist = genrange(0, SCREENWIDTH,eIs.getWidth()+20);
        ylist = genrange(1-(SCREENHEIGHT*2), 0,eIs.getHeight()*2);
        System.out.println(xlist);
        System.out.println(ylist);
        random = new Random();
        this.setPreferredSize(new Dimension(SCREENWIDTH,SCREENHEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        
        AL = new myKeyAdapter();
        cc = true;
        String[] songsp = new String[10];
        songsp[0] = "C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\FIRELY.wav";

        try {
            clip1 = playSound(songsp[0],2.0f);
            clip1.loop(Clip.LOOP_CONTINUOUSLY);
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        for (int i =0 ;i <= ra ; i++){
            ri[i] = (int) ((roadi.getHeight())*i);
        }
        for (int i=0; i <= enysPerV;i++ ){
            BufferedImage img2 = carImages.get(random.nextInt(0,enysPerV));
            enys[i] = (BufferedImage) img2;
        }
        sec = introsleep;
        StartGame();
    }
    public void StartGame(){
        // newApple();
        // Running = true;
        // started = true;
        this.addKeyListener(AL);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public ArrayList<Integer> genrange(int start, int stop,int step){
        int listSize = (int)((stop-start)/step);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0;i<listSize;i++){
            list.add(start+(step*i));
        }
        // System.out.println(list);
        return list;
    }

    public boolean has(int[] list,int obj,int len){
        boolean re = false;
        for (int i = 0;i<len;i++){
            if (list[i] == obj){
                re = true;
                break;
            }
        }
        return re;
    }
    
    public static BufferedImage rotate(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), width / 2.0, height / 2.0); // Rotate around center
        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR); // Use bilinear interpolation for smooth scaling
        op.filter(image, rotatedImage);
        return rotatedImage;
      }

    public void moveEnys(){
        int x = 0;
        int y = 0;
        BufferedImage img;
        for (int i=0; i <= enysPerV;i++ ){
            
            if (enysy[i] >= SCREENHEIGHT+eIs.getHeight()){
                img = carImages.get(random.nextInt(0,enysPerV));
                x = xlist.get(random.nextInt(xlist.size()-1));
                y = ylist.get(random.nextInt(ylist.size()-1));
                enysx[i] = x;
                enysy[i] = y;
                enys[i] = (BufferedImage) img;
                passed += 1;
                
            }
            
            else{
                x = enysx[i];
                if (paused == false)
                    {
                        y = enysy[i] + speed;
                        if (enysx[i] == 0){
                            x = xlist.get(random.nextInt(xlist.size()-1));
                        }
                        for(int o=0;o<=enysPerV;o++){
                            
                            if (x == enysx[o]){
                                while (enysy[o]-enys[i].getHeight()-10 <= y && y <= enysy[o]) {
                                    y--;
                                }
                            }
                            
                        }
                }else{
                    y = enysy[i];
                }
                
                
                
                // speed = new Integer(ispeed + passed/10);
            }
            enysx[i] = x;
            enysy[i] = y;
            
            
        }
        int x2 = 0;
        int y2 = 0;
        ArrayList<Integer> xx =  new ArrayList<>();
        xx.add(0);
        
        for (int i=0;i<treesnum;i++){
            if (treesy.get(i) >= SCREENHEIGHT+trees.get(i).getHeight()){
                img = treesImages.get(random.nextInt(0,treesnum));
                xx.add(1, SCREENWIDTH-img.getWidth());
                x2 = xx.get(random.nextInt(0, xx.size()));
                System.out.println(x2);
                y2 = ylist.get(random.nextInt(ylist.size()-1));
            }
            else{
                x2 = treesx.get(i);
                if (!paused){
                    y2 = treesy.get(i) + (int) (((SCREENHEIGHT-100)-youry)/10) +3;
                }
            }
            treesx.set(i, x2);
            treesy.set(i, y2);
        }
    }
    public void cheakEnys(){
        for (int i=0; i <= enysPerV;i++ ){
            if 
                (enysx[i] <= yourx+image.getWidth() && enysx[i]+enys[i].getWidth() >= yourx)
            {
             if ((enysy[i]+enys[i].getHeight() >= youry && enysy[i] <= youry+image.getHeight()))   
                 {   
                    Running = false;
                    
                }
            }
        }
    }
    
    public void draw(Graphics g){
        
        if (Running)
        {
            for (int i =0 ;i < ra ; i++){
                if (ri[i] >= SCREENHEIGHT+20){
                    // System.out.println(ri[i]-roadi.getHeight()); 
                    ri[i] = 1-(roadi.getHeight()-(ri[i]-(SCREENHEIGHT+20)));
                    // ri[i] = 1-((roadi.getHeight()-((ri[i]-SCREENHEIGHT)+50))*(ra-2));
                    // System.out.println(ri[i]);                    
                                       
                    sw = !sw;
                }else{
                    if (paused == false)
                        {
                            if (fang){
                                ang +=0.001;
                                if (ang>=5){
                                    fang = false;
                                }
                            }else{
                                ang -= 0.001;
                                if (ang <= -5){
                                    fang = true;
                                }
                            }
                            ri[i] += (int) (((SCREENHEIGHT-100)-youry)/10) +3;
                        }
                    
                }
            }
            
            for (int i =0 ;i < ra ; i++){
                g.drawImage(roadi, -60, ri[i]-30, null);
            }
            // System.out.println(ri);
            if (sp == false){
                if (youry < yourix){
                    youry += speed;
                }else if(youry > yourix){
                    youry = yourix;
                }}

            g.drawImage(image, yourx, youry, null);
            for (int i=0; i <= enysPerV;i++ ){
                g.drawImage(enys[i], enysx[i], enysy[i], null);
            }
            for (int i=0;i<treesnum;i++){
                g.drawImage(trees.get(i), treesx.get(i), treesy.get(i), null);
            }
            g.setColor(Color.cyan);
            g.setFont(new Font("Courier",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+passed, (SCREENWIDTH - metrics.stringWidth("Score: "+passed))/2, g.getFont().getSize());
            

            if (paused){
                g.setColor(Color.cyan);
                g.setFont(new Font("Courier",Font.BOLD,75));
                FontMetrics metrics33 = getFontMetrics(g.getFont());
                g.drawString("paused", (SCREENWIDTH - metrics33.stringWidth("paused"))/2, SCREENHEIGHT/2);
            }
            if (mute){
                g.setColor(Color.red);
                g.setFont(new Font("Courier",Font.BOLD,20));
                FontMetrics metricsmm = getFontMetrics(g.getFont());
                g.drawString("mute", (SCREENWIDTH - metricsmm.stringWidth("mute"))-10, g.getFont().getSize());
            }
            // g.setColor(Color.cyan);
            // for (int i : xlist){
            //     g.drawLine(i,0,i,SCREENWIDTH);
            // }
            // for (int i : ylist){
            //     g.drawLine(0,i+SCREENHEIGHT*2,SCREENHEIGHT,i+SCREENHEIGHT*2);
            // }

        }else{
            if (started){
            if (won){
                // clip.stop();
                if( played){}else{
                    for (int i=0;i<3;i++){
                        try {
                            clip2 = playSound("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\cheering.wav",6.0f);
                            clip2.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        timer.setDelay(2000);
                    }
                    
                    played = true;
                }
                WON(g);
            }else{
                clip1.stop();
                if( played){}else{
                    for (int i=0;i<3;i++){
                        try {
                            clip2 = playSound("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\carcrash.wav",6.0f);
                            clip2.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        timer.setDelay(2000);
                    }
                    
                    played = true;
                }
                gameOver(g);}
            }else if (setted) {
                intro(g);
            }
            else{
                settings(g);
                // setted = true;
            }
            
        }
        
        // g.dispose();
    }
    public static void pause(int s){
        try {
            Thread.sleep(s);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
    }
    class CAL implements ActionListener {
        BufferedImage d;
        JPanel p;
        JButton self;
        func funct;
        int in;
        CAL(BufferedImage df,JPanel j,JButton self,int in,func f){
            super();
            d = df;
            p = j;
            funct = f;
            this.self = self;
            this.in  = in;
            // System.out.println(in);
            // addKeyListener(AL);
        }
        @Override
        public void actionPerformed(ActionEvent e){
            funct.operate(e);
        }

    }

    public void handleE(KeyEvent e){
        
        if (paused == false){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (yourx <= 0){}else{
                        
                        yourx = yourx - UNITSIZE;}
                    break;
                case KeyEvent.VK_RIGHT:
                if (yourx >= SCREENWIDTH-UNITSIZE*2){}else{
                    yourx = yourx + UNITSIZE;}
                    break;
                case KeyEvent.VK_UP:
                    sp = true;
                    if (youry <= 0){}else{
                        youry -= speed;}
                    break;
                case KeyEvent.VK_DOWN:
                    sp =false;
                    break;
                case KeyEvent.VK_H:
                    for (int i=0; i <= enysPerV;i++ ){
                        if (enysx[i] < yourx+(image.getWidth()/4) && enysx[i] >= yourx-enys[i].getWidth() ){
                           
                            enysx[i] -= UNITSIZE;
                        }
                        else if (enysx[i] > yourx+(image.getWidth()/4)  && enysx[i] <= yourx+enys[i].getWidth()+10 ){
                           
                            enysx[i] += UNITSIZE;
                        }
                    }
                    if (Running){try {
                        playSound("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\horn.wav",6.0f).start();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }}
                    break;
                }
                
        }
        switch(e.getKeyCode()){
            
            case KeyEvent.VK_P:
                paused = !paused;

                break;
            case KeyEvent.VK_M:
                mute = !mute;
                if (mute){
                    clip1.stop();
                }else{
                    clip1.start();
                }
                break;
            
            
        }
    }
    
    public void settings(Graphics g){
        timer.stop();
        if (cc){
            buttons.reversed();
            cc = false;
        }
        try {
            BufferedImage bg1 = ImageIO.read(new File(bgfile));
            g.drawImage(bg1, 0, 0,null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
            for (int i=0;i<carImages.size();i++){
                    BufferedImage e = carImages.get(i);
                    ImageIcon ic = new ImageIcon(e);
                    JButton imageButton = new JButton(ic);
                    imageButton.setBackground(Color.black);
                    imageButton.setText(String.format("car %d -", i));
                    imageButton.setBorder(BorderFactory.createLineBorder(Color.black,1,true));
                    int marginr = 300;
                    imageButton.setMargin(new Insets(marginr, marginr, marginr, marginr));
                    // imageButton.setPreferredSize(new Dimension(100, 50));
                    
                    func f = e2 ->{
                        BufferedImage d = e ;
                        System.out.println("Image Button clicked!");
                        // System.out.println(e2);
                                image = d;
                                for (JButton b : buttons.reversed()){
                                    b.removeAll();
                                    for (ActionListener o : b.getActionListeners()){
                                        b.removeActionListener(o);
                                    }
                                    remove(b);
                                }
                            setted = true;
                            timer.start();
                            return 0;
                    };
                    imageButton.addActionListener( new CAL(e,this , imageButton,i,f ));
                    pause(50);
                    buttons.add(imageButton);
                ;};
            
            buttons.stream().forEach(e -> {
                // System.out.println(e);
                pause(50);
                add(e,BorderLayout.AFTER_LAST_LINE);
            });
            
        g.setColor(Color.cyan);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics33 = getFontMetrics(g.getFont());
        g.drawString("pick a car", ((SCREENWIDTH - metrics33.stringWidth("pick a car"))/2), (SCREENHEIGHT/2)+40);
    }

    public void restart(JButton btn){
        yourx = 20;
        yourix = yourx;
        youry = SCREENHEIGHT/2;
        sec = 0;
        speed = 3;
        yourspeed = 1;
        ispeed = speed;
        played =  false;
        started = false;
        setted  = false;
        btn.removeAll();
        StartGame();
    }

    public void gameOver(Graphics g) {
        try {
            BufferedImage bg1 = ImageIO.read(new File(bgfile));
            g.drawImage(bg1, 0, 0,null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String nd = "Bad Driver";
        g.setColor(Color.red);
        if (passed > max){
            nd = "you tried you are higher than "+max;
            g.setColor(Color.cyan);
        }
        timer.stop();
        
        repaint();
        
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+passed, (SCREENWIDTH - metrics1.stringWidth("Score: "+passed))/2, g.getFont().getSize());
        
        g.setFont(new Font("Courier",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREENWIDTH - metrics.stringWidth("Game Over"))/2, SCREENHEIGHT/2);
        
        
        g.setFont(new Font("Courier",Font.BOLD,20));
        FontMetrics m3 = getFontMetrics(g.getFont());
        g.drawString(nd, (SCREENWIDTH - m3.stringWidth(nd))/2, (SCREENHEIGHT/2)+40);
        
        reBtn = new JButton("restart");
        reBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                restart(reBtn);
            }
            
        });
        add(reBtn,BorderLayout.CENTER);
        
    }
    public void intro(Graphics g){
        try {
            BufferedImage bg1 = ImageIO.read(new File(bgfile));
            g.drawImage(bg1, 0, 0,null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.drawImage(logo, 
        (SCREENWIDTH/2)-(logo.getWidth()/2)
        , (SCREENHEIGHT/5), null);
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+passed, (SCREENWIDTH - metrics1.stringWidth("Score: "+passed))/2, g.getFont().getSize());
        float[] c2 = Color.RGBtoHSB(150, 200, 255, null);
        g.setColor(Color.getHSBColor(c2[0],c2[1],c2[2]));
        g.setFont(new Font("Courier",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("DESDROID", (SCREENWIDTH - metrics.stringWidth("DESDROID"))/2, SCREENHEIGHT/2);
        g.setFont(new Font("Courier",Font.BOLD,20));
        FontMetrics m3 = getFontMetrics(g.getFont());
        g.drawString("Car Driving", (SCREENWIDTH - m3.stringWidth("Car Driving"))/2, (SCREENHEIGHT/2)+40);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics m34 = getFontMetrics(g.getFont());
        g.drawString(""+(sec/60), (SCREENWIDTH - m34.stringWidth(""+(sec/60)))/2, (SCREENHEIGHT/2)+100);
        
        
        if (paused){}else{sec -= 1;}
        if (sec == 0){
            started = true;
            Running = true;
        }
        
    }
    public void WON(Graphics g){
        try {
            BufferedImage bg1 = ImageIO.read(new File(bgfile));
            g.drawImage(bg1, 0, 0,null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.setColor(Color.GREEN);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+passed, (SCREENWIDTH - metrics1.stringWidth("Score: "+passed))/2, g.getFont().getSize());
        
        g.setColor(Color.green);
        g.setFont(new Font("Courier",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("You Won", (SCREENWIDTH - metrics.stringWidth("You Won"))/2, SCREENHEIGHT/2);
        g.setFont(new Font("Courier",Font.BOLD,20));
        FontMetrics m3 = getFontMetrics(g.getFont());
        g.drawString("Cool Driver", (SCREENWIDTH - m3.stringWidth("Cool Driver"))/2, (SCREENHEIGHT/2)+40);

    }


    public static Clip playSound(String soundFilePath, float volumeLevel) throws Exception {
        // Get an AudioInputStream from the sound file
          File file = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float minimumVolume = gainControl.getMinimum();
            float maximumVolume = gainControl.getMaximum();
            volumeLevel = Math.max(minimumVolume, Math.min(volumeLevel, maximumVolume));  // Clamp to valid range
            gainControl.setValue(volumeLevel);
        return clip;
    }

    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            // System.out.println(e);
            handleE(e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Running){
            moveEnys();
            cheakEnys();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
}

