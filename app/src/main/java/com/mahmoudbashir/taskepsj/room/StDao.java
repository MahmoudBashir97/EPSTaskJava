package com.mahmoudbashir.taskepsj.room;

import com.mahmoudbashir.taskepsj.pojo.DataModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface StDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(DataModel model);

    @Query("SELECT * FROM data_table ORDER BY id ASC")
    LiveData<List<DataModel>> getAllDataStored();

}
