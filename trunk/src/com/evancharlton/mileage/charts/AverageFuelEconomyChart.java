package com.evancharlton.mileage.charts;

import android.database.Cursor;

import com.evancharlton.mileage.R;
import com.evancharlton.mileage.dao.Vehicle;

public class AverageFuelEconomyChart extends FuelEconomyChart {
	@Override
	protected String getAxisTitle() {
		return getString(R.string.stat_avg_economy);
	}

	@Override
	protected void processCursor(LineChartGenerator generator, Cursor cursor, Vehicle vehicle) {
		int num = 0;
		while (cursor.isAfterLast() == false) {
			if (generator.isCancelled()) {
				break;
			}
			if (num > 0) {
				addPoint(cursor.getLong(0), cursor.getDouble(1));
			}
			generator.update(num++);
			cursor.moveToNext();
		}
	}
}
