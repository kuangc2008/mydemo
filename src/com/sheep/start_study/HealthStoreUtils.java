package com.sheep.start_study;

import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by kuangcheng on 2014/12/29.
 */
public class HealthStoreUtils {

    public static void main(String[] args) {
//        //起手
//        qishou();
//
//        twoAnim();
//
//        //3费
//        threeAnim();
//
//        //4费
//        forAnim();
//
//        //5费
//        fiveAnim();
//
//        //6费
//        sixAnim();
//
//        //抽牌
//        choupai();
//
//        //过牌与大生物



//        byte[] bytes = "&nbsp;".getBytes();
//
//        for(byte b : bytes) {
//            System.out.println("b is " + b + "  " + Integer.toHexString(b));
//        }
//
//        char c = 0x0a;
//        System.out.println(" c is" + c +"xxx");
//
//        try {
//            bytes = "这个".getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//        }
//        for(byte b : bytes) {
//            System.out.println("c is " + b + "  " + Integer.toHexString(b));
//        }

        //
       // System.out.println("monkey is " );
       // System.out.println((87+5) + (73+5) + (73+5) + (71+5) + 26);

        System.out.println(("1->" + (86+93+10)));
        System.out.println(("2->" + (71 + 83+ 86 + 10)));

        System.out.println("total->" + (86+93+71+83+86+20));

    }

    private static void twoAnim() {
        for(int i=0; i<10; i++) {
            float percent1 = (float) getGoalSum(30, i, 5, 1) / getTotalSum(30, 5);
            float percent2 = (float) getGoalSum(30, i, 5, 2) / getTotalSum(30, 5);
            float percent3 = (float) getGoalSum(30, i, 5, 3) / getTotalSum(30, 5);
            System.out.println("2费生物，在2费回合1-2-3张--"+ i + "  percent->" + (percent1 + percent2 +percent3));
        }
    }

    private static void threeAnim() {
        for(int i=0; i<10; i++) {
            float percent1 = (float) getGoalSum(30, i, 6, 1) / getTotalSum(30, 6);
            float percent2 = (float) getGoalSum(30, i, 6, 2) / getTotalSum(30, 6);
            System.out.println("3费生物，在3费回合1-2张--"+ i + "  percent->" + (percent1 + percent2));
        }
    }

    private static void forAnim() {
        for(int i=0; i<10; i++) {
            float percent1 = (float) getGoalSum(30, i, 8, 1) / getTotalSum(30, 8);
            float percent2 = (float) getGoalSum(30, i, 8, 2) / getTotalSum(30, 8);
            System.out.println("4费生物，在5费回合1-2张--"+ i + "  percent->" + (percent1 + percent2));
        }
    }

    private static void qishou() {
        //222， 223，233， 234
        System.out.println("2费");
        int numberPai = 3;
        for(int goalTotalNumber=0; goalTotalNumber<10; goalTotalNumber++) {
//            for(int goalNumber=0; goalNumber<=3; goalNumber++) {
                int internalGoalNum = 0;
                float percent0 = (float) getGoalSum(30, goalTotalNumber, numberPai, internalGoalNum) / getTotalSum(30, numberPai);
                internalGoalNum = 1;
                float percent1 = (float) getGoalSum(30, goalTotalNumber, numberPai, internalGoalNum) / getTotalSum(30, numberPai);
                internalGoalNum = 2;
                float percent2 = (float) getGoalSum(30, goalTotalNumber, numberPai, internalGoalNum) / getTotalSum(30, numberPai);
            internalGoalNum = 3;
            float percent3 = (float) getGoalSum(30, goalTotalNumber, numberPai, internalGoalNum) / getTotalSum(30, numberPai);
            System.out.println( goalTotalNumber + " --10》" + percent1 + "  01->" + percent0 * percent1);
            System.out.println( goalTotalNumber + " --》" + percent2 + "  1 1->" + percent1 * (float) getGoalSum(29, goalTotalNumber-1, 2, internalGoalNum-1) / getTotalSum(29, 2)
                        +"   02-->" + percent0 * percent2);
            System.out.println( goalTotalNumber + "-->" + (percent1 + percent0 * percent1) + "  2-->" + (percent2 + percent1 *(float) getGoalSum(29, goalTotalNumber-1, 2, internalGoalNum-1) / getTotalSum(29, 2) + percent0 * percent2)
                    + "  total-->" +  (percent1 + percent0 * percent1 + percent2 + percent1 *(float) getGoalSum(29, goalTotalNumber-1, 2, internalGoalNum-1) / getTotalSum(29, 2) + percent0 * percent2) );


            System.out.println( goalTotalNumber + "摸牌-->" + (percent1 + percent0 * percent1) + "  2-->" + (percent2 + percent1 *(float) getGoalSum(29, goalTotalNumber-1, 2, internalGoalNum-1) / getTotalSum(29, 2) + percent0 * percent2)
                    + "  total-->" +  (percent1 + percent0 * percent1 + percent2 + percent1 *(float) getGoalSum(29, goalTotalNumber-1, 2, internalGoalNum-1) / getTotalSum(29, 2) + percent0 * percent2) );



//                internalGoalNum = 3;
//                float percent3 = (float) getGoalSum(30, goalTotalNumber, numberPai, internalGoalNum) / getTotalSum(30, numberPai);

//                System.out.println("" + goalNumber + " in " + goalTotalNumber + " --》" + percent);
//            }
            System.out.println();
        }
    }


    private static int getGoalSum(int totalNumber, int goalTotalNumber, int numberPai, int goalNumber) {
        return getTotalSum(goalTotalNumber, goalNumber) * getTotalSum(totalNumber - goalTotalNumber, numberPai - goalNumber);
    }

    private static int getTotalSum(int totalNumber, int numberPai) {
        float result = 1;
        for(int i=0; i<numberPai; i++) {
            result = result * (totalNumber - i);
            result = result/(i+1);
        }
        return (int)result;
    }


    private static void choupai() {
        for(int i=0; i<10; i++) {
            float percent1 = (float) getGoalSum(21, i, 4, 1) / getTotalSum(21, 4);
            float percent2 = (float) getGoalSum(21, i, 4, 2) / getTotalSum(21, 4);
            System.out.println("8费以上，接下来5张，想拿到过牌或大生活1-4张的概率为"+ i + "  percent->" + (percent1 + percent2  ));
        }
    }

    private static void fiveAnim() {
        for(int i=0; i<10; i++) {
            float percent1 = (float) getGoalSum(30, i, 9, 1) / getTotalSum(30, 9);
            float percent2 = (float) getGoalSum(30, i, 9, 2) / getTotalSum(30, 9);
            System.out.println("5费生物，在6费回合1-2张--"+ i + "  percent->" + (percent1 + percent2));
        }
    }



    private static void sixAnim() {
        for(int i=0; i<10; i++) {
            float percent1 = (float) getGoalSum(30, i, 9, 1) / getTotalSum(30, 9);
            System.out.println("6费生物，在6费回合1张--"+ i + "  percent->" + (percent1));
        }
    }
}
