import biuoop.DrawSurface;

import java.awt.Color;

/**
 * A Block object, which has a shape(rectangular) and color.
 */
public class Block implements Collidable, Sprite {
    private Rectangle shape;
    private java.awt.Color color;
    private int number = -1; //initialize.

    /**
     * Constructor.
     *
     * @param rec   : Block's shape.
     * @param color : Block's color.
     */
    public Block(Rectangle rec, java.awt.Color color) {
        this.shape = rec;
        this.color = color;
    }

    /**
     * @return Block's shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the block that we collided with it at collisionPoint with a given velocity.
     * if collided with it from below or above , turn the vertical direction.
     * if collided with it from left or right , turn the horizontal direction.
     * if collided with it from the it's angles , turn the vertical and horizontal directions.
     *
     * @param collisionPoint  where an object hit the block.
     * @param currentVelocity an object velocity.
     * @return new velocity depends on where the object hits the block.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Line upperLine = this.shape.getUpperLine();
        Line lowerLine = this.shape.getLowerLine();
        Line leftLine = this.shape.getLeftLine();
        Line rightLine = this.shape.getRightLine();
        double newDy = 0, newDx = 0;
        //decrease the number of the block when the ball collided with it.
        if (this.number >= 1) {
            this.number = this.number - 1;
        }
        //block's angles.
        if (upperLine.onSegment(collisionPoint) && leftLine.onSegment(collisionPoint)
                || upperLine.onSegment(collisionPoint) && rightLine.onSegment(collisionPoint)
                || leftLine.onSegment(collisionPoint) && lowerLine.onSegment(collisionPoint)
                || rightLine.onSegment(collisionPoint) && lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();

            // hit the block from above or below.
        } else if (upperLine.onSegment(collisionPoint) || lowerLine.onSegment(collisionPoint)) {
            newDy = -1 * currentVelocity.getDy();
            newDx = currentVelocity.getDx();
            // hit the block from left or right.
        } else if (leftLine.onSegment(collisionPoint) || rightLine.onSegment(collisionPoint)) {
            newDy = currentVelocity.getDy();
            newDx = -1 * currentVelocity.getDx();
        }

        //return the new velocity.
        Velocity newVelocity = new Velocity(newDx, newDy);
        return newVelocity;
    }

    /**
     * @param num : the number that will be draw on the middle of the block.
     */
    public void setNumber(int num) {
        this.number = num;
    }

    /**
     * draw the block on the given DrawSurface.
     *
     * @param surface : surface to draw the block on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        Point upperLeft = this.shape.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        surface.fillRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
        surface.setColor(Color.black);
        //frame
        surface.drawRectangle(x, y, (int) this.shape.getWidth(), (int) this.shape.getHeight());
        Rectangle rec = this.shape;
        //draw the number on the middle of the block
        Line recDiameter = new Line(rec.getUpperLeft(), rec.getLowerLine().end());
        Point middle = recDiameter.middle();
        int x1 = (int) middle.getX();
        int y1 = (int) middle.getY();
        //if the number decrease to 0 , draw X .
        if (this.number == 0) {
            surface.drawText(x1, y1, ("X"), 15);
            //draw the number.
        } else if (number != -1) {
            surface.drawText(x1, y1, Integer.toString(this.number), 15);
        }

    }

    /**
     * notify the block that time has passed.
     */
    public void timePassed() {
    }

    /**
     * adding the block to specified game.
     *
     * @param g : the game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

}

