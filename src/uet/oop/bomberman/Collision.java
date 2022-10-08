package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;

public class Collision {
    public boolean checkCollision(Entity entity1, Entity entity2) {
        int left_a = entity1.getX();
        int right_a = entity1.getX() + entity1.getW();
        int top_a = entity1.getY();
        int bottom_a = entity1.getY() + entity1.getH();

        int left_b = entity2.getX();
        int right_b = entity2.getX() + entity2.getW();
        int top_b = entity2.getY();
        int bottom_b = entity2.getY() + entity2.getH();

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
        return top_a == top_b && right_a == right_b && bottom_a == bottom_b;
    }
}

