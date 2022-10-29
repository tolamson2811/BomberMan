package uet.oop.bomberman.utils;

import uet.oop.bomberman.still_objects.StillObject;
import uet.oop.bomberman.entities.Entity;

/**
 * CHECK VA CHẠM GIỮA VẬT THỂ VÀ CÁC OBJECT KHÁC.
 */
public class Collision {
    public static boolean checkCollision(Entity entity1, Object object) {
        int left_a,left_b;
        int right_b,right_a;
        int bottom_a,bottom_b;
        int top_a,top_b;

        if(object instanceof Entity) {
            Entity entity2 = (Entity) object;
            left_a = entity1.getX();
            right_a = entity1.getX() + entity1.getW();
            top_a = entity1.getY();
            bottom_a = entity1.getY() + entity1.getH();

            left_b = entity2.getX();
            right_b = entity2.getX() + entity2.getW();
            top_b = entity2.getY();
            bottom_b = entity2.getY() + entity2.getH();
        }else {
            StillObject object2 = (StillObject) object;
            left_a = entity1.getX();
            right_a = entity1.getX() + entity1.getW();
            top_a = entity1.getY();
            bottom_a = entity1.getY() + entity1.getH();

            left_b = object2.getX();
            right_b = object2.getX() + object2.getW();
            top_b = object2.getY();
            bottom_b = object2.getY() + object2.getH();
        }

        // Case 1: size object 1 < size object 2
        if (left_a > left_b && left_a < right_b)
        {
            if (top_a > top_b && top_a < bottom_b)
            {
                return true;
            }
        }

        if (left_a > left_b && left_a < right_b)
        {
            if (bottom_a > top_b && bottom_a < bottom_b)
            {
                return true;
            }
        }

        if (right_a > left_b && right_a < right_b)
        {
            if (top_a > top_b && top_a < bottom_b)
            {
                return true;
            }
        }

        if (right_a > left_b && right_a < right_b)
        {
            if (bottom_a > top_b && bottom_a < bottom_b)
            {
                return true;
            }
        }

        // Case 2: size object 1 < size object 2
        if (left_b > left_a && left_b < right_a)
        {
            if (top_b > top_a && top_b < bottom_a)
            {
                return true;
            }
        }

        if (left_b > left_a && left_b < right_a)
        {
            if (bottom_b > top_a && bottom_b < bottom_a)
            {
                return true;
            }
        }

        if (right_b > left_a && right_b < right_a)
        {
            if (top_b > top_a && top_b < bottom_a)
            {
                return true;
            }
        }

        if (right_b > left_a && right_b < right_a)
        {
            if (bottom_b > top_a && bottom_b < bottom_a)
            {
                return true;
            }
        }

        // Case 3: size object 1 = size object 2
        return top_a == top_b && (left_a == left_b || right_b == right_a ) && bottom_a == bottom_b;
    }
}

