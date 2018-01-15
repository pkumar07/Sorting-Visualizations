package com.poornimakumar.visualization.activities;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.poornimakumar.visualization.R;
import com.poornimakumar.visualization.fragments.SortingFragment;

import java.util.Random;

public class SortingActivity extends AppCompatActivity {
    //sorting variables

    private Runnable mTimer1;
    private Runnable mTimer2;
    private BarGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph2LastXValue = 5d;
    private DataPoint[] arrayToSort;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        Fragment sortingFragment = new SortingFragment();
//        fragmentTransaction.add(R.id.sorting_fragment,sortingFragment);
//        fragmentTransaction.commit();
        graph = (GraphView) findViewById(R.id.sortingGraphView1);
        mSeries1 = new BarGraphSeries<>(generateData());
        graph.addSeries(mSeries1);

        SystemClock.sleep(2000);
        new SortTask().execute(arrayToSort);
//        GraphView graph2 = (GraphView) findViewById(R.id.sortingGraphView2);
//        mSeries2 = new LineGraphSeries<>();
//        graph2.addSeries(mSeries2);
//        graph2.getViewport().setXAxisBoundsManual(true);
//        graph2.getViewport().setMinX(0);
//        graph2.getViewport().setMaxX(40);

    }
    private DataPoint[] generateData() {
        int count = 30;
        //int r = new Random().nextInt(101);
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double y = new Random().nextInt() % 100;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        arrayToSort = values;
        return values;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mTimer1 = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("Array elements are: ","One by one: "+arrayToSort.toString());
//                int n = arrayToSort.length;
//                for(DataPoint d: arrayToSort)
//                    Log.d("Array element: "," is: "+d);
//                for (int i = 0; i < n-1; i++) {
//                    for (int j = 0; j < n - i - 1; j++) {
//                        if (arrayToSort[j].getY() > arrayToSort[j + 1].getY()) {
//                            Log.d("Sorting happening","swapping "+arrayToSort[j]+" with "+arrayToSort[j+1]);
//                            double temp = arrayToSort[j].getY();
//                            arrayToSort[j] = new DataPoint((double) j, arrayToSort[j + 1].getY());
//                            arrayToSort[j + 1] = new DataPoint((double) j + 1, temp);
//                            //mHandler.sendEmptyMessage(0);
//                            SystemClock.sleep(1000);
//                        }
//                    }
//
//                }
//            }
//        };
    }



    @Override
    public void onPause() {
        //mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

    private void bubbleSort(int[] arr){

            int n = arr.length;
            for (int i = 0; i < n-1; i++)
                for (int j = 0; j < n-i-1; j++)
                    if (arr[j] > arr[j+1])
                    {
                        // swap temp and arr[i]
                        int temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                    }
        }
    private class SortTask extends AsyncTask<DataPoint, DataPoint, String> {
        @Override
        protected String doInBackground(DataPoint... arrayToSort) {
            for (int i = 0; i < arrayToSort.length-1; i++) {
                for (int j = 0; j < arrayToSort.length - i - 1; j++) {
                    if (arrayToSort[j].getY() > arrayToSort[j + 1].getY()) {
                        Log.d("Sorting happening","swapping "+arrayToSort[j]+" with "+arrayToSort[j+1]);
                        double temp = arrayToSort[j].getY();
                        arrayToSort[j] = new DataPoint((double) j, arrayToSort[j + 1].getY());
                        arrayToSort[j + 1] = new DataPoint((double) j + 1, temp);

                    }
                    publishProgress(arrayToSort);
                    SystemClock.sleep(1);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            Log.d("Async task","Done updating UI changes");
        }

        @Override
        protected void onProgressUpdate(DataPoint... values) {
            //super.onProgressUpdate(values);
            Log.d("Handling class", "event update called");
            graph.removeAllSeries();
            mSeries1 = new BarGraphSeries<>(values);
            graph.addSeries(mSeries1);
        }
    }

}