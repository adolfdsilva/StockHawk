package com.sam_chordas.android.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sam_chordas.android.stockhawk.Constants;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by Audi on 20/11/16.
 */

public class StockDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private String symbol;
    private static final int LOADER_ID = 0;
    private GraphView graph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        symbol = getIntent().getExtras().getString(Constants.SYMBOL);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        graph = (GraphView) findViewById(R.id.graph);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.BIDPRICE},
                QuoteColumns.SYMBOL + " = ?",
                new String[]{symbol},
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        fillGraph(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    void fillGraph(Cursor cursor) {
        if (cursor != null) {
            int dataCount = cursor.getCount();
            if (!cursor.moveToFirst())
                return;

            DataPoint[] dataPoints = new DataPoint[dataCount];

            for (int i = 0; i < dataCount; i++) {
                float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex(QuoteColumns.BIDPRICE)));
                Constants.debug("Price: " + price + " Point: " + (i + 1));
                DataPoint point = new DataPoint(i + 1, price);
                dataPoints[i] = point;
                cursor.moveToNext();
            }
            cursor.close();

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            graph.addSeries(series);
        }

    }
}
