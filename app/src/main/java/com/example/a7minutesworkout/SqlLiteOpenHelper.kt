package com.example.a7minutesworkout

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// TODO(Step 21.1 : Creating the database for Inserting the date of completion of the 7 minute workout.)
// START
/**
 * Create a helper object to create, open, and/or manage a database.
 * This method always returns very quickly.  The database is not actually
 * created or opened until one of getWritableDatabase or
 * getReadableDatabase is called.
 *
 * @param context to use for locating paths to the the database
 * @param name of the database file, or null for an in-memory database
 * @param factory to use for creating cursor objects, or null for the default
 * @param version number of the database (starting at 1); if the database is older,
 *     #onUpgrade will be used to upgrade the database; if the database is
 *     newer, #onDowngrade will be used to downgrade the database
 */

class SqlLiteOpenHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION){

    /**
     * This override function is used to execute when the class is called once where the database tables are created.
     */

    override fun onCreate(db: SQLiteDatabase){
        val CREATE_HISTORY_TABLE = ("CREATE TABLE " + // Fix: Added space after "CREATE TABLE"
                TABLE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_COMPLETED_DATE
                + " TEXT)")     // Create history table Query.
        db.execSQL(CREATE_HISTORY_TABLE)    //Executing the create table query.
    }

    /**
     * This function is called when the database version is changed.
     */

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")   //It drops the existing history table
        onCreate(db)    // Calls the Oncreate function so all the updated tables will be created.
    }

    // TODO(Step 21.3 : Creating a function where the passed Date will be inserted into the Database Table.)
    // START
    /**
     * Function is used to insert the date in Database History table.
     */

    fun addDate(date: String){
        val values =
            ContentValues() // Creates an empty set of values using the default initial size
        values.put(
            COLUMN_COMPLETED_DATE,
            date
        )   //Putting the value to the column along with the value.
        val db =
            this.writableDatabase   // Create and/or open database that will be used for reading and writing .
        db.insert(TABLE_HISTORY, null, values)      //Insert query is return
        db.close()  // Database is closed after insertion.
    }

    //TODO(Step 22.1 - Getting the list of completed dates from the History Table.)
    /**
     * Function return the list of history table data.
     */

    @SuppressLint("Range")
    fun getAllCompletedDatesList(): ArrayList<String> {
        val list = ArrayList<String>()  // ArrayList is initialized
        val db =
            this.readableDatabase   //Create and/or open a database that will be used for reading and writing
        // Runs the provider SQL and returns a cursor over the result set.
        // Query for selecting all the data from the history table.
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        // Move the cursor to the next row.
        while (cursor.moveToNext()){
            //Returns the zero-based index for the given column name, or -1 if the column doesn't exist
            list.add(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))    // value is added in the list
        }
        cursor.close()  //Cursor is closed after its used.
        return list //list is returned.
    }

    companion object{
        private const val DATABASE_VERSION = 1  //Database version
        private const val DATABASE_NAME = "SevenMinutesWorkout.db"  // Name of the database
        private const val TABLE_HISTORY = "history"     // Table Name
        private const val COLUMN_ID = "_id"     // Column Id
        private const val COLUMN_COMPLETED_DATE = "completed_date"  //Column for Completed date
    }
}
