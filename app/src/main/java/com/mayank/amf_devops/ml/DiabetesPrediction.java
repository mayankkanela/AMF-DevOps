package com.mayank.amf_devops.ml;
import androidx.appcompat.app.AppCompatActivity;

import com.mayank.amf_devops.R;

import java.lang.Math;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DiabetesPrediction {

    public static void main(String[] args) {

        String csvFile = "app\\src\\main\\res\\raw\\diabetes.csv";
        String line = "";
        String cvsSplitBy = ",";

        String[][] a = new String[770][100];
        float[][] h = new float[770][100];
        int i=0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
//                for(int i=0;i<9;i++) {
//                    //System.out.print("" + country[i] + "    " );
//                }
                for(int j=0;j<=8;j++) {
                    a[i][j] = country[j];
                }
//                a[i][0]=country[0];
//                a[i][1]=country[1];
//                a[i][2]=country[2];
//                a[i][3]=country[3];
//                a[i][4]=country[4];
//                a[i][5]=country[5];
//                a[i][6]=country[6];
//                a[i][7]=country[7];
//                a[i][8]=country[8];
                i++;
                System.out.println("");
            }

            for(int e=1;e<i;e++)
            {
                for(int r=0;r<=8;r++){
                    Float c=Float.parseFloat(a[e][r].trim());
                    h[e][r]=c;
                }
            }

            //System.out.println(h[1][5]);

            //input array b

            double b[]=new double[100];
//            for(int p=0;p<=7;p++)
//                b[p]=0;
            b[0]=5;//pregnancy
            b[1]=145;//glucose
            b[2]=70;//bp
            b[3]=35;//skin thickness
            b[4]=0;//insulin
            b[5]=35;//bmi
            b[6]=0.6;//pedigree
            b[7]=50;//age
            double df1[]=new double[1000];
            int size1=1;
            double df2[]=new double[1000];
            int size2=1;
            //System.out.println(i);
            for(int k=1;k<i;k++)
            {
                double ans=0;
                float c=Integer.parseInt(a[k][8].trim());
                if(c==1)
                {
                    for(int z=0;z<=7;z++)
                    {
                        float m=Float.parseFloat(a[k][z].trim());
                        //ans+=b[z]-a[k][z];
                        ans+=Math.pow(b[z]-m, 2);
                    }
                    ans=Math.sqrt(ans);
                    df1[size1]=ans;
                    size1++;
                }
                else
                {
                    for(int z=0;z<=7;z++)
                    {
                        Float m=Float.parseFloat(a[k][z]);
                        ans+=Math.pow(b[z]-m, 2);
                    }
                    ans=Math.sqrt(ans);
                    df2[size2]=ans;
                    size2++;
                }
            }

            //sorting
            for (i = 1; i <= size1-1; i++)
                for (int j = 1; j <= size1-i-1; j++)
                    if (df1[j] > df1[j+1]) {
                        double temp;
                        temp=df1[j];
                        df1[j]=df1[j+1];
                        df1[j+1]=temp;
                    }
            for (i = 1; i <= size2-1; i++)
                for (int j = 1; j <= size2-i-1; j++)
                    if (df2[j] > df2[j+1]) {
                        double temp;
                        temp=df2[j];
                        df2[j]=df2[j+1];
                        df2[j+1]=temp;
                    }
//comparing
            double sum1=0,sum2=0;
                    int k=11;
                    for(int l=1;l<=k;l++){
                        sum1+=df1[l];
                    }
                for(int l=1;l<=k;l++){
                    sum2+=df2[l];
                }
            //System.out.println(sum1);
            //System.out.println(sum2);
                if(sum1<sum2){
                    System.out.println(1);
                }
                else
                    System.out.println(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}