package com.example.catalist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.catalist.db.model.CatDao
import com.example.catalist.db.model.CatDbModel
import com.example.catalist.db.model.CatDbPhoto


@Database(
    entities = [
        CatDbModel::class,
        CatDbPhoto::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = [],
)
@TypeConverters(
    JsonTypeConvertor::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

}
