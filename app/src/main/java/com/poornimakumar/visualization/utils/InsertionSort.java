package com.poornimakumar.visualization.utils;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Arrays;

/**
 * Created by poornimakumar on 1/15/18.
 */

public class InsertionSort extends AsyncTask<Void, DataPoint, String> {
    GraphView graph;
    BarGraphSeries<DataPoint> mSeries1;
    DataPoint[] arr;
    public InsertionSort(GraphView graph, BarGraphSeries<DataPoint> mSeries1, DataPoint[] arr){
        this.graph = graph;
        this.mSeries1 = mSeries1;
        this.arr = arr;

    }
    @Override
    protected String doInBackground(Void... voids) {
        sort(arr);
        return null;
    }

    @Override
    protected void onProgressUpdate(DataPoint... values) {
        //super.onProgressUpdate(values);
        Log.d("Handling class", "event update called");
        graph.removeAllSeries();
        mSeries1 = new BarGraphSeries<>(values);
        graph.addSeries(mSeries1);
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        Log.d("Async task","Done updating UI changes");
    }

    public void publish(DataPoint[] arr){
        Log.d("Progress publish","Insertion Publishing progress now...");
        publishProgress(arr);
    }

    void sort(DataPoint[] arr)
    {
        int n = arr.length;
        for (int i=1; i<n; ++i)
        {
            DataPoint key = arr[i];
            int j = i-1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j>=0 && arr[j].getY() > key.getY())
            {
                SystemClock.sleep(1);
                arr[j+1] = new DataPoint(j,arr[j].getY());
                publish(arr);
                j = j-1;
            }
            SystemClock.sleep(1);
            arr[j+1] = new DataPoint(j+1,key.getY());
            publish(arr);
        }
    }
}
