package com.lognad;

import com.lognad.testing.CheckSquareVertices;
import com.lognad.testing.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void MaxSquareLength() {
        List<Point> points = new ArrayList<>();

        points.add(new Point(1, 4));
        points.add(new Point(2, 3));
        points.add(new Point(3, 4));
        points.add(new Point(4, 4));
        points.add(new Point(1, 2));
        points.add(new Point(3, 2));
        points.add(new Point(4, 1));
        points.add(new Point(1, 1));
        points.add(new Point(3, 1));
        points.add(new Point(7, 1));
        points.add(new Point(9, 1));


        CheckSquareVertices squareVertices = new CheckSquareVertices();
        int maxSideLength = squareVertices.findMaxSquareSide(points);

        System.out.println("MAXIMUN SIDE LENGTH FROM POSSIBLE SQUARES FROM GIVEN POINTS: " + maxSideLength);
        Assert.assertEquals(maxSideLength, 3);
    }

}
