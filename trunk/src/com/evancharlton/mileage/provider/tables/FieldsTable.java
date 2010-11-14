package com.evancharlton.mileage.provider.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.evancharlton.mileage.dao.Dao;
import com.evancharlton.mileage.dao.Field;
import com.evancharlton.mileage.provider.FillUpsProvider;

public class FieldsTable extends ContentTable {
	// make sure it's globally unique
	private static final int FIELDS = 30;
	private static final int FIELD_ID = 31;

	private static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.evancharlton.fields";
	private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.evancharlton.field_id";

	/**
	 * All saved field templates
	 */
	public static final String URI_PATH = "fields/";
	public static final Uri URI = Uri.withAppendedPath(FillUpsProvider.BASE_URI, URI_PATH);

	public static String[] getFullProjectionArray() {
		return new String[] {
				BaseColumns._ID,
				Field.TITLE,
				Field.DESCRIPTION,
				Field.TYPE
		};
	}

	@Override
	protected Class<? extends Dao> getDaoType() {
		return Field.class;
	}

	@Override
	public String getDefaultSortOrder() {
		return Field.TITLE + " asc";
	}

	@Override
	public String getTableName() {
		return "fields";
	}

	@Override
	public String getType(int type) {
		switch (type) {
			case FIELDS:
				return CONTENT_TYPE;
			case FIELD_ID:
				return CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public String[] init(boolean isUpgrade) {
		// FIXME - Hardcoded strings = bad!
		return new String[] {
			new InsertBuilder().add(Field.TITLE, "Comment").add(Field.DESCRIPTION, "Comment about your fillup.").build()
		};
	}

	@Override
	public long insert(int type, SQLiteDatabase db, ContentValues initialValues) {
		switch (type) {
			case FIELDS:
				return db.insert(getTableName(), null, initialValues);
		}
		return -1L;
	}

	@Override
	public boolean query(int type, Uri uri, SQLiteQueryBuilder queryBuilder) {
		switch (type) {
			case FIELDS:
				queryBuilder.setTables(getTableName());
				queryBuilder.setProjectionMap(buildProjectionMap(getFullProjectionArray()));
				return true;
			case FIELD_ID:
				queryBuilder.setTables(getTableName());
				queryBuilder.setProjectionMap(buildProjectionMap(getFullProjectionArray()));
				queryBuilder.appendWhere(BaseColumns._ID + " = " + uri.getPathSegments().get(1));
				return true;
		}
		return false;
	}

	@Override
	public void registerUris() {
		FillUpsProvider.registerUri(this, URI_PATH, FIELDS);
		FillUpsProvider.registerUri(this, URI_PATH + "#", FIELD_ID);
	}

	@Override
	public int update(int match, SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (selection == null) {
			selection = "";
		}
		if (selectionArgs == null) {
			selectionArgs = new String[0];
		}
		switch (match) {
			case FIELD_ID:
				String query = Field._ID + " = ?" + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")");
				String[] args = new String[selectionArgs.length + 1];
				args[0] = values.getAsString(Field._ID);
				for (int i = 0; i < selectionArgs.length; i++) {
					args[i + 1] = selectionArgs[i];
				}
				return db.update(getTableName(), values, query, args);
		}
		return -1;
	}
}
