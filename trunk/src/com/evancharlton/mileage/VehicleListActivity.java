package com.evancharlton.mileage;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import com.evancharlton.mileage.dao.Vehicle;
import com.evancharlton.mileage.provider.tables.VehiclesTable;

public class VehicleListActivity extends BaseListActivity {
	private static final int MENU_TYPES = 1;
	private static final int MENU_CREATE = 2;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_CREATE, Menu.NONE, R.string.add_vehicle);
		menu.add(Menu.NONE, MENU_TYPES, Menu.NONE, R.string.edit_vehicle_types);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_TYPES:
				startActivity(new Intent(this, VehicleTypeListActivity.class));
				return true;
			case MENU_CREATE:
				startActivity(new Intent(this, VehicleActivity.class));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected String[] getFrom() {
		return new String[] {
				Vehicle.TITLE,
				Vehicle.DESCRIPTION
		};
	}

	@Override
	protected Uri getUri() {
		return VehiclesTable.BASE_URI;
	}

	@Override
	public void onItemClick(long id) {
		loadItem(id, VehicleActivity.class);
	}
}