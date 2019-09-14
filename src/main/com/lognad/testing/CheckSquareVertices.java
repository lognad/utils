package com.lognad.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: edangol
 * Date: 13/09/2019 - 11:42
 * Project: utils
 * Description:
 **/
public class CheckSquareVertices {
    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();

        //  square
        points.add(new Point(104, 4));
        points.add(new Point(4, 4));
        points.add(new Point(4, 104));
        points.add(new Point(104, 104));

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

        //  square
        points.add(new Point(21, 1));
        points.add(new Point(1, 21));
        points.add(new Point(1, 1));
        points.add(new Point(21, 21));


        CheckSquareVertices squareVertices = new CheckSquareVertices();
        int maxSideLength = squareVertices.findMaxSquareSide(points);

        System.out.println("MAXIMUM SIDE LENGTH FROM POSSIBLE SQUARES FROM GIVEN POINTS: " + maxSideLength);
    }


    public int findMaxSquareSide(List<Point> points) {
        int maxLength = 0;

        //  creating columns.
        HashMap<Integer, List<Point>> map = new HashMap<>();

        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            if (map.keySet().contains(new Integer(p.X))) {
                map.get(p.X).add(p);
            } else {
                List<Point> col = new ArrayList<>();
                col.add(p);
                map.put(new Integer(p.X), col);
            }
        }

        //  columns.
        Object[] columns = map.values().toArray();
        System.out.println("COLUMNS SIZE: " + columns.length);


        //  check for points column-wise
        for (int i = 0; i < columns.length - 1; i++) {
            //  primary col
            List<Point> col = (List<Point>) columns[i];
            //  skip if contains only one point.
            if (col.size() < 2) continue;

            //  iterate through the points present in the above primary col and get first possible point.
            for (int j = 0; j < col.size() - 1; j++) {
                Point p1 = col.get(j);
                System.out.println("p1: " + p1.toString());

                //  iterate through the points present in the above primary col and get second possible point.
                for (int k = j + 1; k < col.size(); k++) {
                    Point p2 = col.get(k);
                    System.out.println("\tp2: " + p2.toString());
                    int reqSideLength = Math.abs(p1.Y - p2.Y);
                    //  skip checking for point p2 if side[p1, p2] is smaller than the maxLength.
                    if (reqSideLength <= maxLength) break;

                    //  check above primary col with the rest of the remaining cols.
                    for (int l = i + 1; l < columns.length; l++) {
                        // secondary col
                        List<Point> colToCheck = (List<Point>) columns[l];
                        //  skip if contains only one point.
                        if (colToCheck.size() < 2) continue;

                        boolean p3Exist = false;
                        boolean p4Exist = false;
                        boolean breakLoop = false;

                        //  iterate through points in the secondary col.
                        for (int m = 0; m < colToCheck.size(); m++) {
                            Point chk = colToCheck.get(m);
                            int dist = Math.abs(chk.X - p1.X);

                            //  break if current required side length of possible square is greater than the side length from the
                            //  (primary col - secondary col) cause no need to check all the points if the side length are smaller.
                            //  cause square side should be equal
                            if (reqSideLength > dist) break;
                                //  break this loop as well as outer loop if (primary col - secondary col) is greater than required side
                                //  length of possible square. no point in checking further columns
                            else if (reqSideLength < dist) {
                                breakLoop = true;
                                break;
                            }
                            //  if sides are equal, possible square vertex.
                            else {
                                if (p1.Y == chk.Y) {
                                    System.out.println("\t\tp3: " + chk.toString());
                                    p3Exist = true;
                                } else if (p2.Y == chk.Y) {
                                    System.out.println("\t\t\tp4: " + chk.toString());
                                    p4Exist = true;
                                }
                            }

                            //  no need to check further down the secondary col if all four vertices of the square is already found.
                            //  check if the currently found square side length is greater than the one we have.
                            if (p3Exist == true && p4Exist == true) {
                                // then we have a square here.
                                if (maxLength < reqSideLength)
                                    maxLength = reqSideLength;

                                breakLoop = true;
                                break;
                            }
                        }

                        //  break loop for current secondary col.
                        if (breakLoop) break;
                    }

                }
            }
        }

        return maxLength;
    }
}
