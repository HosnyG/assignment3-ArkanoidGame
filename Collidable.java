/**
 * A Collidable interface.
 * things that can be collided with.
 * @author : Ganaiem Hosny
 */
public interface Collidable {

    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param collisionPoint : where the collision occur.
     * @param currentVelocity : object velocity.
     * @return the new velocity expected after the hit
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}