package com.org.covidcare.handlers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.org.covidcare.model.NotificationInfo

/**
 * Created by Aaishwarya v on 9/11/2020.
 */

class DatabaseHandler(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotificationIdDatabase"
        private const val TABLE_NOTIFICATION_ID = "NotificationIdTable"
        private const val KEY_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            ("CREATE TABLE " + TABLE_NOTIFICATION_ID + "("
                    + KEY_ID + " TEXT PRIMARY KEY" + ")")
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NOTIFICATION_ID")
        onCreate(db)
    }

    fun addNotificationIds(notificationInfo: NotificationInfo): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, notificationInfo.notification_id)
        val success = db.insert(TABLE_NOTIFICATION_ID, null, contentValues)
        db.close()
        return success
    }

    fun findId(id: String): Boolean? {
        val query = "SELECT * FROM $TABLE_NOTIFICATION_ID WHERE $KEY_ID = '$id'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        db.close()
        return false
    }
}

