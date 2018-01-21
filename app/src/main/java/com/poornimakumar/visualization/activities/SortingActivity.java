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
import com.poornimakumar.visualization.R;
import com.poornimakumar.visualization.utils.HeapSort;
import com.poornimakumar.visualization.utils.InsertionSort;
import com.poornimakumar.visualization.utils.MergeSort;
import com.poornimakumar.visualization.utils.QuickSort;
import com.poornimakumar.visualization.utils.SelectionSort;

import java.util.Random;

public class SortingActivity extends AppCompatActivity {
    //sorting variables
    private BarGraphSeries<DataPoint> mSeries1;
    private DataPoint[] arrayToSort, pausedArrayToSort;
    private GraphView graph;
    private String type, algorithm;
    private int range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        //get intent data

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            type = extras.getString("type");
            range = extras.getInt("range");
            algorithm = extras.getString("algorithm");
        }

        //initialise graphview
        graph = (GraphView) findViewById(R.id.sortingGraphView1);
        mSeries1 = new BarGraphSeries<>(generateData(type, range));
        graph.addSeries(mSeries1);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);

        //setting a sleep delay of 2 seconds before sorting visualization begins
        SystemClock.sleep(2000);

        //starting sort task , parameter passed is the DataPoint[] array (dataset) of the graph
        startSortingTask(arrayToSort);


//        graph2.getViewport().setXAxisBoundsManual(true);
//        graph2.getViewport().setMinX(0);
//        graph2.getViewport().setMaxX(40);

    }
    private DataPoint[] generateData(String type, int range) {
        //int r = new Random().nextInt(101);
        DataPoint[] values = new DataPoint[range];

        for (int i=0; i<range; i++) {
            double x = i;
            double y=-1;
            if(type.equals("Ascending"))
                y = (new Random().nextInt(2)+i) % range;
            else if(type.equals("Descending"))
                y = (new Random().nextInt(2)+range-i) % range;
            else
                y = new Random().nextInt() % range;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        arrayToSort = values;
        return values;
    }

    private void startSortingTask(DataPoint[] arrayToSort){
        if(algorithm.equals("Merge Sort"))
            new MergeSort(this,graph,mSeries1,arrayToSort).execute();
        else if(algorithm.equals("Heap Sort"))
            new HeapSort(this,graph,mSeries1,arrayToSort).execute();
        else if(algorithm.equals("Selection Sort"))
            new SelectionSort(this,graph,mSeries1,arrayToSort).execute();
        else if(algorithm.equals("Insertion Sort"))
            new InsertionSort(this,graph,mSeries1,arrayToSort).execute();
        else if(algorithm.equals("Quick Sort"))
            new QuickSort(this,graph,mSeries1,arrayToSort).execute();
    }

    @Override
    public void onResume() {
          super.onResume();
//        startSortingTask(pausedArrayToSort);

    }



    @Override
    public void onPause() {
        //mHandler.removeCallbacks(mTimer2);
            super.onPause();
//        pausedArrayToSort = arrayToSort;
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
    private class SortTask extends AsyncTask<Void, DataPoint, String> {
        private String code;
        private DataPoint[] array;
        public SortTask(String code, DataPoint[] array){
            this.code = code;
            this.array = array;
        }
        @Override
        protected String doInBackground(Void... params) {
            switch(code){
                case "BUBBLE" : {
                    for (int i = 0; i < array.length-1; i++) {
                        for (int j = 0; j < array.length - i - 1; j++) {
                            if (array[j].getY() > array[j + 1].getY()) {
                                Log.d("Sorting happening","swapping "+array[j]+" with "+array[j+1]);
                                double temp = array[j].getY();
                                array[j] = new DataPoint((double) j, array[j + 1].getY());
                                array[j + 1] = new DataPoint((double) j + 1, temp);
                            }
                            publishProgress(array);
                            SystemClock.sleep(1);
                        }
                    }
                }break;

                case "MERGE" :{

                    //publishProgress(m.sort(array,0,array.length-1));
                }break;
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
