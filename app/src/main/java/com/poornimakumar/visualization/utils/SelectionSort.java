package com.poornimakumar.visualization.utils;

/**
 * Created by poornimakumar on 1/15/18.
 */

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;


public class SelectionSort extends AsyncTask<Void, DataPoint, String> {
    GraphView graph;
    BarGraphSeries<DataPoint> mSeries1;
    DataPoint[] arr;
    public SelectionSort(GraphView graph, BarGraphSeries<DataPoint> mSeries1, DataPoint[] arr){
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
        Log.d("Progress publish","Selection Publishing progress now...");
        publishProgress(arr);
    }

    void sort(DataPoint arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j].getY() < arr[min_idx].getY())
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            SystemClock.sleep(1);
            double temp = arr[min_idx].getY();
            arr[min_idx] = new DataPoint(min_idx,arr[i].getY());
            arr[i] = new DataPoint(i,temp);
            publish(arr);
        }
        SystemClock.sleep(1);
    }
}

