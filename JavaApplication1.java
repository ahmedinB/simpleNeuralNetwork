/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.lang.Math;
import java.util.ArrayList;
//import org.jfree.chart.JFreeChart;


/**
 *
 * @author A
 */


public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    } 
    
    public static double sigmoid_p(double x){
        return sigmoid(x) * (1-sigmoid(x));
    }
    
    public static ArrayList train(double[][] data){
        double w1 = Math.random();
        double w2 = Math.random();
        double b = Math.random();
        
        int iterations = 10000;
        double learning_rate = 0.1;
        ArrayList costs=new ArrayList();
        
        for (int i=0;i<iterations;i++){
            int ri = (int) Math.floor(Math.random()*data.length);
            double[] point = data[ri];
            
            double z= point[0]*w1 + point[1]*w2 + b;
            double pred = sigmoid(z); // NN prediction
            
            double target = point[2];
            
//            cost for current random point
            double cost = Math.pow(pred-target, 2);
            
//            print the cost over all data points every 1k iters
            if (i%100 == 0){
                double c = 0;

                for (int j=0; j<data.length; j++){
                    double[] p = data[j];
                    double p_pred = sigmoid(w1 * p[0] + w2 * p[1] + b);
                    c+= Math.pow(p_pred - p[2], 2);
                }
                costs.add(c);
                
            }
            
            double dcost_dpred = 2 * (pred - target);
            double dpred_dz = sigmoid_p(z);
            
            double dz_dw1 = point[0];
            double dz_dw2 = point[1];
            double dz_db = 1;    
            
            double dcost_dz = dcost_dpred * dpred_dz;
            
            double dcost_dw1 = dcost_dz * dz_dw1;
            double dcost_dw2 = dcost_dz * dz_dw2;
            double dcost_db = dcost_dz * dz_db;
            
            w1 = w1 - learning_rate * dcost_dw1;
            w2 = w2 - learning_rate * dcost_dw2;
            b = b - learning_rate * dcost_db;
        }
        System.out.println("Costs: " + costs);
        ArrayList resp = new ArrayList();
//        resp.add(costs);
//        JFreeChart chart = ChartFactory.createXYLineChart(costs);

        resp.add(w1);
        resp.add(w2);
        resp.add(b);
        return resp;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        double[][] data ={{3,   1.5, 1},
            {2,   1,   0},
            {4,   1.5, 1},
            {3,   1,   0},
            {3.5, .5,  1},
            {2,   .5,  0},
            {5.5,  1,  1},
            {1,    1,  0}};
        double[] mystery_flower = {4.5, 1};
        ArrayList train= train(data);
        double w1 = (double) train.get(0);
        double w2 = (double) train.get(1);
        double b = (double) train.get(2);
        System.out.println("W1: " + w1 + "W2: " + w2 + "B: " + b);
        double z = w1 * mystery_flower[0] + w2 * mystery_flower[1] + b; 
        double pred = sigmoid(z);
        
        System.out.println("mystery flower prediction:" + pred);
    }
    
}
