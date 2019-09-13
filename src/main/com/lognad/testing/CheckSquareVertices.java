package com.lognad.testing;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: edangol
 * Date: 13/09/2019 - 11:42
 * Project: utils
 * Description:
 **/
public class CheckSquareVertices {


    public int findMaxSquareSide(List<Point> points) {
        int maxLength = 0;

        List<Point>[] columns = new List[points.size()];

        //  initializing array;
        for (int i = 0; i < points.size(); i++) {
            columns[i] = new ArrayList<>();
        }

        //  creating columns.
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            columns[p.X].add(p);
        }

        //  check for points column-wise
        for (int i = 0; i < columns.length - 1; i++) {
            //  primary col
            List<Point> col = columns[i];

            //  iterate through the points present in the above primary col and get first possible point.
            for (int j = 0; j < col.size() - 1; j++) {
                Point p1 = col.get(j);

                //  iterate through the points present in the above primary col and get second possible point.
                for (int k = j + 1; k < col.size(); k++) {
                    Point p2 = col.get(k);
                    int reqSideLength = Math.abs(p1.Y - p2.Y);

                    //  check above primary col with the rest of the remaining cols.
                    for (int l = i + 1; l < columns.length; l++) {
                        // secondary col
                        List<Point> colToCheck = columns[l];
                        boolean p3Exist = false;
                        boolean p4Exist = false;
                        boolean breakLoop = false;

                        //  iterate through points in the secondary col.
                        for (int m = 0; m < colToCheck.size(); m++) {
                            Point chk = colToCheck.get(m);
                            int dist = Math.abs(chk.X - p1.X);

                            //  break if current required side length of possible square is greater than the side length from the
                            //  primary col - secondary col cause no need to check all the points if the side length are smaller.
                            //  cause square side should be equal
                            if (reqSideLength > dist) break;
                                //  break this loop as well as outer loop if primary col - secondary col is greater than required side
                                //  length of possible square. no point in checking further columns
                            else if (reqSideLength < dist) {
                                breakLoop = true;
                                break;
                            }
                            //  if sides are equal, possible square vertex.
                            else {
                                if (p1.Y == chk.Y) p3Exist = true;
                                else if (p2.Y == chk.Y) p4Exist = true;
                            }
                        }

                        //  break loop for current secondary col.
                        if (breakLoop) break;

                        //  no need to check further down the secondary col if all four vertices of the square is already found.
                        //  check if the currently found square side length is greater than the one we have.
                        if (p3Exist == true && p4Exist == true) {
                            // then we have a square here.
                            if (maxLength < reqSideLength) maxLength = reqSideLength;
                            break;
                        }
                    }

                }
            }
        }


        return maxLength;
    }
}
