import java.awt.event.*;
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
    int speed = 3;
    int yourspeed = 1;
    int ispeed = speed;
    int max = 200;
    int yourx = 20;
    int yourix = yourx;
    int youry = SCREENHEIGHT/2;
    int passed = 0;
    int enysPerV = 5;
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
    boolean f = false;
    boolean sp = false;
    boolean won = false;
    boolean once = true;
    boolean paused = false;
    boolean mute = false;
    boolean played =  false;
    boolean started = false;
    boolean setted  = false;
    int ci = 0;
    boolean cc = false;
    JButton reBtn;
    KeyAdapter AL;
    ArrayList<JButton> buttons = new ArrayList<>();
    String rootFolder = "C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\cars\\";

    public Gamepanel() throws IOException {
        super();
        // Load the image
        // Convert to BufferedImage for efficiency (optional)

        carImages = new ArrayList<>();
        carIcons = new ArrayList<>();
        xlist = new ArrayList<>();
        ylist = new ArrayList<>();
        enys = new BufferedImage[imagesnum+1];
        for (int i=0; i <= imagesnum;i++ ){
            String name = rootFolder+"car"+(i+1)+".png";
            System.out.println(name);
            File file = new File(name);
            // file.setReadable(true);
            carImages.add(ImageIO.read(file));
            ImageIcon icon = new ImageIcon(name);
            carIcons.add(icon);
            

        }
        image = carImages.get(3);
        logo = ImageIO.read(new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\logo.png"));
        eIs = ImageIO.read(new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\cars\\car3.png"));
        File  roadf = new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\road.png");
        roadi = ImageIO.read(roadf); // Convert to BufferedImage for efficiency (optional)
        xlist = genrange(SCREENWIDTH, SCREENWIDTH*3,eIs.getWidth()*2);
        ylist = genrange(0, SCREENHEIGHT,eIs.getHeight()+20);
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
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        for (int i =0 ;i <= ra ; i++){
            ri[i] = (int) (roadi.getWidth())*i;
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
    

    public void moveEnys(){
        int x,y;
        BufferedImage img;
        for (int i=0; i <= enysPerV;i++ ){
            if (enysx[i] <= Integer.parseInt(String.format("-%d",eIs.getWidth()))){
                img = carImages.get(random.nextInt(0,enysPerV));
                x = xlist.get(random.nextInt(xlist.size()-1));
                y = ylist.get(random.nextInt(ylist.size()-1));
                enysx[i] = x;
                enysy[i] = y;
                enys[i] = (BufferedImage) img;
                passed += 1;
            }else{
                if (paused == false)
                    {
                        x = enysx[i] - speed;
                }else{
                    x = enysx[i];
                }
                y = enysy[i];
                
                enysx[i] = x;
                enysy[i] = y;
                // speed = new Integer(ispeed + passed/10);
            }
            
            if (passed > max){
                // won = true;
                // Running = false;
            }
        }
    }
    public void cheakEnys(){
        for (int i=0; i <= enysPerV;i++ ){
            if (enysx[i] <= yourx+image.getWidth()-10 && enysx[i] > yourx){
                if (enysy[i] >= youry-image.getHeight() && enysy[i] <= youry+image.getHeight()){
                    
                    Running = false;
                    
                }
            }
        }
    }
    
    public void draw(Graphics g){
        
        if (Running)
        {
            
            for (int i =0 ;i < ra ; i++){
                if (ri[i] <= Integer.parseInt(String.format("-%d",roadi.getWidth()))){
                    ri[i] = (int) roadi.getWidth()/2;
                }else{
                    if (paused == false)
                        {
                            ri[i] -= (int) (yourx/10) +3;
                        }
                    
                }
            }
            for (int i =0 ;i < ra ; i++){
                g.drawImage(roadi, ri[i], 0, null);
            }
            // System.out.println(ri);
            if (sp == false){
                if (yourx > yourix){
                    yourx -= speed;
                }else if(yourx < yourix){
                    yourx = yourix;
                }}

            g.drawImage(image, yourx, youry, null);
            for (int i=0; i <= enysPerV;i++ ){
                g.drawImage(enys[i], enysx[i], enysy[i], null);
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
                            // TODO Auto-generated catch block
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
                            // TODO Auto-generated catch block
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
                case KeyEvent.VK_UP:
                    if (youry <= 0){}else{
                        
                        youry = youry - UNITSIZE;}
                    break;
                case KeyEvent.VK_DOWN:
                if (youry >= SCREENHEIGHT-UNITSIZE*2){}else{
                    youry = youry + UNITSIZE;}
                    break;
                case KeyEvent.VK_SPACE:
                    sp = true;
                    yourx += speed;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    sp =false;
                    break;
                case KeyEvent.VK_H:
                    for (int i=0; i <= enysPerV;i++ ){
                        if (enysy[i] < youry+(image.getHeight()/4) && enysy[i] >= youry-enys[i].getHeight() ){
                           
                            enysy[i] -= UNITSIZE;
                        }
                        else if (enysy[i] > youry+(image.getHeight()/4)  && enysy[i] <= youry+enys[i].getHeight()+10 ){
                           
                            enysy[i] += UNITSIZE;
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


/*BufferedImage@34b5ee6: type = 6 ColorModel: #pixelBits = 32 numComponents = 4 color space = java.awt.color.ICC_ColorSpace@9325f3f
 transparency = 3 has alpha = true isAlphaPre = false ByteInterleavedRaster: width = 119 height = 66 #numDataElements 4 dataOff[0] = 3 */
/*BufferedImage@1acd3b84: type = 6 ColorModel: #pixelBits = 32 numComponents = 4 color space = java.awt.color.ICC_ColorSpace@796a366e
 transparency = 3 has alpha = true isAlphaPre = false ByteInterleavedRaster: width = 137 height = 65 #numDataElements 4 dataOff[0] = 3*/

    
    public void settings(Graphics g){
        timer.stop();
        if (cc){
            buttons.reversed();
            cc = false;
        }
            for (int i=0;i<carImages.size();i++){
                    BufferedImage e = carImages.get(i);
                    JButton imageButton = new JButton(new ImageIcon(e));
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
                    buttons.add(imageButton);
                ;};
            
            buttons.stream().forEach(e -> {
                // System.out.println(e);
                add(e);
            });
            
        g.setColor(Color.cyan);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics33 = getFontMetrics(g.getFont());
        g.drawString("pick a car", (SCREENWIDTH - metrics33.stringWidth("pick a car"))/2, SCREENHEIGHT/2);
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
       
        String nd = "Bad Driver";
        timer.stop();
        
        repaint();
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+passed, (SCREENWIDTH - metrics1.stringWidth("Score: "+passed))/2, g.getFont().getSize());
        
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREENWIDTH - metrics.stringWidth("Game Over"))/2, SCREENHEIGHT/2);
        
        
        g.setFont(new Font("Courier",Font.BOLD,20));
        FontMetrics m3 = getFontMetrics(g.getFont());
        g.drawString(nd, (SCREENWIDTH - m3.stringWidth(nd))/2, (SCREENHEIGHT/2)+40);
        
        try {
            reBtn = new JButton(new ImageIcon(ImageIO.read(new File("C:\\Users\\MC DESMOND\\dextop\\java\\game\\game\\src\\re.png"))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            reBtn = buttons.get(0);
            e.printStackTrace();
        }
        
        reBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                restart(reBtn);
            }
            
        });
        add(reBtn);
        
    }
    public void intro(Graphics g){
        g.drawImage(logo, 
        (SCREENWIDTH/2)-(logo.getWidth()/2)
        , (SCREENHEIGHT/5), null);
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+passed, (SCREENWIDTH - metrics1.stringWidth("Score: "+passed))/2, g.getFont().getSize());
        g.setColor(Color.cyan);
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



/* 
public class Gamepanel extends JPanel implements ActionListener {
    
    static final int SCREENWIDTH = 600;
    static final int SCREENHEIGHT = 600;
    static final int UNITSIZE = 12;
    static final int DELAY = 70;
    static final int GAMEUNITS = (SCREENWIDTH*SCREENHEIGHT)/UNITSIZE;
    final int X[] = new int[GAMEUNITS];
    final int Y[] = new int[GAMEUNITS];
    int bodyParts = 10;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean Running = false;
    Timer timer;
    Random random;

    Gamepanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREENWIDTH,SCREENHEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        StartGame();
    }
    public void StartGame(){
        newApple();
        Running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if (Running)
        {
            
            // for (int i=0;i<SCREENHEIGHT/UNITSIZE;i++){
            //     // g.setColor(Color.cyan);
            //     g.drawLine(i*UNITSIZE,0,i*UNITSIZE,SCREENHEIGHT);
            //     g.drawLine(0,i*UNITSIZE,SCREENWIDTH,i*UNITSIZE);
            // }
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNITSIZE, UNITSIZE);

        for (int i=0;i<bodyParts;i++){
            if (i==0){
                g.setColor(Color.cyan);
                g.fillRect(X[i], Y[i], UNITSIZE, UNITSIZE);
            }
            else{
                g.setColor(new Color(45,180,0));
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(X[i], Y[i], UNITSIZE, UNITSIZE);
            }
        }
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREENWIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
        g.dispose();
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREENWIDTH/UNITSIZE))*UNITSIZE;
        appleY = random.nextInt((int)(SCREENHEIGHT/UNITSIZE))*UNITSIZE;
    }
    public void move (){
        for (int i=bodyParts;i>0;i--){
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch(direction){
            case 'U':
                Y[0] = Y[0] - UNITSIZE;
                break;
            case 'D':
                Y[0] = Y[0] + UNITSIZE;
                break;
            case 'L':
                X[0] = X[0] - UNITSIZE;
                break;
            case 'R':
                X[0] = X[0] + UNITSIZE;
                break;
        }
    }
    public void checkApple(){
        if ((X[0] == appleX) && (Y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        for (int i=bodyParts;i>0;i--){
            if ((X[0] == X[i]) && (Y[0] == Y[i])){
                Running = false;
                break;
            }
        }
        if (X[0] < 0){
            Running = false;
        }
        if (X[0] > SCREENWIDTH){
            Running = false;
        }
        if (Y[0] < 0){
            Running = false;
        }
        if (Y[0] > SCREENHEIGHT){
            Running = false;
        }
        if (!Running){
            timer.stop();
        }

    

    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREENWIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        
        g.setColor(Color.red);
        g.setFont(new Font("Courier",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREENWIDTH - metrics.stringWidth("Game Over"))/2, SCREENHEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class GameThread extends Thread{
        public void run(){}
        
    }
    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    StartGame();
                    break;
            }
        }
    }
}
*/