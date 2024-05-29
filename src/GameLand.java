//Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable, KeyListener {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public boolean yarnIntersectsBasket;
    public boolean cuttersIntersectsBasket;

    //declare screen/level booleans
    public boolean startScreen = true;
    public boolean isPlaying = false;
    public boolean gameOver = false;
    public boolean youWin = false;

    //Declare the objects used in the program below
    /** STEP 1: Declare your object and give it a name **/

    public Hero basket;
    public Yarn[] yarnBalls;
    public Scissors[] cutters;
    public int yarnCollected;
    public int yarnDropped;

    /** STEP 2: Declare an image for your object**/

    public Image startPic;
    public Image losePic;
    public Image winPic;
    public Image backgroundPic;
    public Image basketPic;
    public Image yarnPic1;
    public Image yarnPic2;
    public Image yarnPic3;
    public Image yarnPic4;
    public Image scissorPic;
    public Image orangeCatPic;
    public Image pinkCatPic;
    public Image purpleBearPic;
    public Image blueBearPic;


    // Main method definition: PSVM
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has no return type and has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public GameLand() {
        setUpGraphics(); //this calls the setUpGraphics() method

        //create (construct) the objects needed for the game below
        //for each object that has a picture, load in images as well
        /** STEP 3: Construct a specific Hero**/
        //astro = new Hero(400,500, 20, 20);
        //dog = new Hero(300,300,20,20);

        /** STEP 4: load in the image for your object **/
        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.png");
        startPic = Toolkit.getDefaultToolkit().getImage("Crochet Game Title Screen.png");
        losePic = Toolkit.getDefaultToolkit().getImage("Crochet Game You Lose Screen.png");
        winPic = Toolkit.getDefaultToolkit().getImage("Crochet Game Win Screen.png");
        basketPic = Toolkit.getDefaultToolkit().getImage("basket.png");
        yarnPic1 = Toolkit.getDefaultToolkit().getImage("BlueYarn.png");
        yarnPic2 = Toolkit.getDefaultToolkit().getImage("OrangeYarn.png");
        yarnPic3 = Toolkit.getDefaultToolkit().getImage("PinkYarn.png");
        yarnPic4 = Toolkit.getDefaultToolkit().getImage("PurpleYarn.png");
        orangeCatPic = Toolkit.getDefaultToolkit().getImage("orangecat.png");
        pinkCatPic = Toolkit.getDefaultToolkit().getImage("pinkcat.png");
        blueBearPic = Toolkit.getDefaultToolkit().getImage("bluebear.png");
        purpleBearPic = Toolkit.getDefaultToolkit().getImage("purplebear.png");
        scissorPic = Toolkit.getDefaultToolkit().getImage("Scissors.png");
    }// GameLand()

//*******************************************************************************
//User Method Section
// put your code to do things here.
    public void startGame(){
        basket = new Hero(600,550,-10,0);
        yarnBalls = new Yarn[8];
        for(int i=0; i<yarnBalls.length; i=i+1){
            int randX = (int)(Math.random()*1000);
            int randDy = (int)(Math.random()*5)+1;
            yarnBalls[i] = new Yarn(randX,0,0,randDy);
        }
        cutters = new Scissors[5];
        System.out.println(cutters[1]);
        for(int i=0; i<cutters.length; i=i+1){
            int randX = (int)(Math.random()*1000);
            cutters[i] = new Scissors(randX, 0, 0, 2);
        }
    }
    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        while (true) {
            moveThings();  //move all the game objects
            collisions(); //checks for rec intersections
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }
    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of your objects below:
        /** STEP 5: draw the image of your object to the screen**/

        if(startScreen==true&&isPlaying == false&&gameOver==false){
            g.drawImage(startPic, 0,0,1000,700, null);
        }

        if(isPlaying == true) {
            g.drawImage(backgroundPic, 0, 0, 1000, 700, null);
            g.drawImage(basketPic, basket.xpos, basket.ypos, basket.width, basket.height, null);

            if(yarnCollected > 10){
                g.drawImage(orangeCatPic, 670,400,75,75,null);
            }

            if(yarnCollected > 20){
                g.drawImage(pinkCatPic, 750,400,75,75,null);
            }

            if(yarnCollected > 30){
                g.drawImage(blueBearPic, 650,480,100,75,null);
            }

            if(yarnCollected > 40){
                g.drawImage(purpleBearPic, 720,480,125,75,null);
            }

            if(yarnBalls!=null){
                for (int i = 0; i < 2; i = i + 1) {
                    if (yarnBalls[i].isAlive) {
                        g.drawImage(yarnPic1, yarnBalls[i].xpos, yarnBalls[i].ypos, yarnBalls[i].width, yarnBalls[i].height, null);
                    }
                }
                for (int i = 2; i < 4; i = i + 1) {
                    if (yarnBalls[i].isAlive) {
                        g.drawImage(yarnPic2, yarnBalls[i].xpos, yarnBalls[i].ypos, yarnBalls[i].width, yarnBalls[i].height, null);
                    }
                }

                for (int i = 4; i < 6; i = i + 1) {
                    if (yarnBalls[i].isAlive) {
                        g.drawImage(yarnPic3, yarnBalls[i].xpos, yarnBalls[i].ypos, yarnBalls[i].width, yarnBalls[i].height, null);
                    }
                }

                for (int i = 6; i < 8; i = i + 1) {
                    if (yarnBalls[i].isAlive) {
                        g.drawImage(yarnPic4, yarnBalls[i].xpos, yarnBalls[i].ypos, yarnBalls[i].width, yarnBalls[i].height, null);
                    }
                }
            }
            if(cutters!=null){
                for(int i=0; i<cutters.length; i=i+1){
                    g.drawImage(scissorPic, cutters[i].xpos, cutters[i].ypos, cutters[i].width, cutters[i].height, null);
                }
            }


//            if(startScreen==true){
//                g.drawString("press space bar to start", 400,300);
//                //g.drawImage()
//            }

//            if(yarnCollected > 10){
//                g.drawImage(orangeCatPic, 670,400,75,75,null);
//            }
//
//            if(yarnCollected > 20){
//                g.drawImage(pinkCatPic, 750,400,75,75,null);
//            }
//
//            if(yarnCollected > 30){
//                g.drawImage(blueBearPic, 650,480,100,75,null);
//            }
//
//            if(yarnCollected > 40){
//                g.drawImage(purpleBearPic, 720,480,125,75,null);
//            }


        }

        if(gameOver == true){
            //paint game over image to the screen
            g.drawImage(losePic, 0,0,1000,700, null);

        }

        if(youWin == true){
            isPlaying =false;
            gameOver = false;
            startScreen = false;
            g.drawImage(winPic, 0,0,1000,700, null);

        }

        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();
        bufferStrategy.show();
    }

    public void moveThings() {
        //call the move() method code from your object class
        if(yarnBalls!=null){
            for(int i=0; i<yarnBalls.length; i=i+1){
                yarnBalls[i].move();
            }
        }
        if(yarnCollected > 50){
            youWin = true;
        }
        if(cutters!=null){
            for(int i=0; i<cutters.length; i=i+1){
                cutters[i].move();
            }
        }
        if(basket!=null){
            basket.move();
        }
    }

    public void collisions(){
        if(yarnBalls!=null){
            for(int i=0; i<yarnBalls.length; i=i+1){
                if (yarnBalls[i].rec.intersects(basket.rec) && yarnIntersectsBasket == false) {
                    yarnIntersectsBasket = true;
                    yarnCollected = yarnCollected + 1;
                    System.out.println("Yarn collected: "+ yarnCollected);
                    int randX = (int) (Math.random() * 1000);
                    int randDy = (int) ((Math.random() * 5)+1);
                    yarnBalls[i].xpos = randX;
                    yarnBalls[i].ypos =-100;
                    yarnBalls[i].dy = randDy;
                    yarnBalls[i].isAlive = false;
                }
                if(yarnBalls[i].rec.intersects(basket.rec)==false){
                    yarnIntersectsBasket=false;
                }

                if(yarnBalls[i].ypos > 700){
                    yarnDropped = yarnDropped +1;
                    System.out.println("yarn dropped: " + yarnDropped);
                    int randX = (int) (Math.random() * 1000);
                    int randDy = (int) (Math.random() * 5)+1;
                    yarnBalls[i].xpos = randX;
                    yarnBalls[i].ypos = 0;
                    yarnBalls[i].dy = randDy;
                    yarnBalls[i].isAlive = true;
                }

//                if(yarnBalls[i].rec.intersects(basket.rec)){
//                    yarnBalls[i].isAlive = false;
//                    yarnIntersectsBasket = false;
//                    System.out.println(yarnCollected);
//                }

                if(yarnDropped > 8){
                    gameOver = true;
                    isPlaying = false;
                }
            }
        }

        if(cutters!=null){
            for(int i=0; i<cutters.length; i=i+1){
                if(cutters[i].rec.intersects(basket.rec) &&cuttersIntersectsBasket ==false){
                    int randX = (int) (Math.random() * 1000);
                    int randDy = (int) ((Math.random() * 5)+1);
                    cutters[i].xpos = randX;
                    cutters[i].ypos =-100;
                    cutters[i].dy = randDy;
                    cutters[i].isAlive = true;
                }

                if(cutters[i].ypos>700){
                    if(yarnDropped>0){
                        yarnDropped = yarnDropped -1;
                        System.out.println("yarn dropped: " + yarnDropped);
                    }
                    int randX = (int) (Math.random() * 1000);
                    int randDy = (int) (Math.random() * 5)+1;
                    cutters[i].xpos = randX;
                    cutters[i].ypos = 0;
                    cutters[i].dy = randDy;
                    cutters[i].isAlive = true;
                }


            }
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(this);
        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //probably will stay empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        //System.out.println("Key: " + key + ", KeyCode: " + keyCode);
        if(keyCode==37){//left arrow key
            basket.leftPressed = true;
        }
        if(keyCode==39){//right arrow key
            basket.rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        if(keyCode==37){//left arrow key
            basket.leftPressed = false;
        }
        if(keyCode==39){//right arrow key
            basket.rightPressed = false;
        }
        if(keyCode==32){
            if(startScreen == true){
                startScreen = false;
                isPlaying = true;
                startGame();
            }
        }
    }
}