import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.Random;

/**
 * A Game.
 * hold the sprites and the collidables, and will be in charge of the animation.
 *
 * @author : Ganaiem Hosny
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 600;

    /**
     * Constructor.
     * <p>
     * initialize the environment and sprites
     */
    public Game() {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
    }

    /**
     * add collidable to the game's environment.
     *
     * @param c : collidable to be add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add Sprite to the game's sprites.
     *
     * @param s : Sprite to be add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * creating surroundingBlocks in the game , all are equal in width and color.
     *
     * @param screenWidth  : screen width.
     * @param screenHeight : screen height.
     * @param thickness    : blocks width.
     * @param color        : blocks color
     * @param number       : blocks number
     * @return surrounding Blocks array .
     */
    private Block[] surroundingBlocks(int screenWidth, int screenHeight, double thickness, java.awt.Color color
            , int number) {
        //initialize the array of the four blocks.
        Block[] surroundingBlocks = new Block[4];
        //creating the blocks according to screen lengths.
        surroundingBlocks[0] = new Block(new Rectangle(new Point(0, 0), screenWidth, thickness), color);
        surroundingBlocks[1] = new Block(new Rectangle(new Point(thickness, screenHeight - thickness)
                , screenWidth - thickness * 2, thickness), color);
        surroundingBlocks[2] = new Block(new Rectangle(new Point(0, thickness), thickness
                , screenHeight - thickness), color);
        surroundingBlocks[3] = new Block(new Rectangle(new Point(screenWidth - thickness, thickness), thickness
                , screenHeight - thickness), color);

        // adding the blocks to the game and give him a number.
        for (int i = 0; i < surroundingBlocks.length; i++) {
            surroundingBlocks[i].addToGame(this);
            surroundingBlocks[i].setNumber(number);
        }
        return surroundingBlocks;
    }

    /**
     * Initialize a new game: create the Blocks and two balls (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        Point ball1Center = new Point(600, 600);
        Color ball1Color = Color.white;
        Velocity ball1Velocity = new Velocity(6, 6);
        int ball1Radius = 8;
        Point ball2Center = new Point(500, 600);
        Color ball2Color = Color.white;
        Velocity ball2Velocity = new Velocity(6, 6);
        int ball2Radius = 8;
        double surroundingBlocksThickness = 30;
        Color surroundingBlocksColor = Color.darkGray;
        int surroundingBlocksnum = 1;
        int blocksRowsNum = 6;
        int blocksInFirstRow = 12;
        double blockWidth = 50;
        double blockHeight = 30;
        double firstBlockYlocation = 150;
        double paddleWidth = 140;
        double paddleHeight = 35;
        double paddleMove = 14;
        Point paddleUpperleft = new Point(200, SCREENHEIGHT - surroundingBlocksThickness - paddleHeight);
        Color paddleColor = new Color(1.0f, 0.699f, 0.000f);

        this.gui = new GUI("Arkanoid Game", SCREENWIDTH, SCREENHEIGHT);
        biuoop.KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        Ball ball1 = new Ball(ball1Center, ball1Radius, ball1Color);
        Ball ball2 = new Ball(ball2Center, ball2Radius, ball2Color);
        ball1.setBoundaries(0, SCREENHEIGHT, SCREENWIDTH, 0);
        ball2.setBoundaries(0, SCREENHEIGHT, SCREENWIDTH, 0);
        ball1.setVelocity(ball1Velocity);
        ball2.setVelocity(ball2Velocity);
        ball1.addToGame(this);
        ball2.addToGame(this);
        ball1.setEnvironment(this.environment);
        ball2.setEnvironment(this.environment);
        //create tall blocks surrounding the screen
        this.surroundingBlocks(SCREENWIDTH, SCREENHEIGHT, surroundingBlocksThickness, surroundingBlocksColor
                , surroundingBlocksnum);

        /*
        create a blocks in pattern like stairs,
        top row that start with two hits and the others with one hit .
         */
        Block[][] stairs = new Block[blocksRowsNum][];
        for (int i = 0; i < blocksRowsNum; i++, blocksInFirstRow--) {
            Random rand = new Random();
            int number;
            Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            for (int j = 0; j < blocksInFirstRow; j++) {
                if (i == 0) {
                    number = 2;
                } else {
                    number = 1;
                }
                stairs[i] = new Block[blocksInFirstRow];
                Point upperLeft = new Point(SCREENWIDTH - (blockWidth * (j + 1)) - surroundingBlocksThickness
                        , firstBlockYlocation + (blockHeight * i));
                stairs[i][j] = new Block(new Rectangle(upperLeft, blockWidth, blockHeight), randomColor);
                stairs[i][j].addToGame(this);
                stairs[i][j].setNumber(number);
            }
        }

        //create a paddle
        Paddle paddle = new Paddle(new Rectangle(paddleUpperleft, paddleWidth, paddleHeight), paddleColor
                , keyboard, paddleMove);
        paddle.setBoundaries(surroundingBlocksThickness, SCREENWIDTH);
        paddle.addToGame(this);
    }

    /**
     * run the game , start the animation loop.
     */
    public void run() {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            Color backgroundColor = new Color(0.098f, 0.098f, 0.439f);
            d.setColor(backgroundColor);
            //background.
            d.fillRectangle(0, 0, SCREENWIDTH, SCREENHEIGHT);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}