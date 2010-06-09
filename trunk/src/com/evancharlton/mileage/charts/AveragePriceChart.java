package com.evancharlton.mileage.charts;

import android.database.Cursor;

import com.evancharlton.mileage.R;
import com.evancharlton.mileage.dao.Vehicle;

public class AveragePriceChart extends PriceChart {
	@Override
	protected String getAxisTitle() {
		return getString(R.string.stat_avg_price);
	}

	@Override
	protected void processCursor(LineChartGenerator generator, Cursor cursor, Vehicle vehicle) {
		int num = 0;
		while (cursor.isAfterLast() == false) {
			if (generator.isCancelled()) {
				break;
			}
			addPoint(cursor.getLong(0), cursor.getDouble(1));
			generator.update(num++);
			cursor.moveToNext();
		}
	}
}
