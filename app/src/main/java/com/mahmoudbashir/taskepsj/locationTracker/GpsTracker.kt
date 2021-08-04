package com.mahmoudbashir.taskepsj.locationTracker

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import androidx.constraintlayout.motion.widget.Debug.getLocation





class GpsTracker : Service(), LocationListener {
    private var mContext: Context? = null

    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false

    var location // location
            : Location? = null
    var latitude // latitude
            = 0.0
    var longitude // longitude
            = 0.0

    // The minimum distance to change Updates in meters
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters


    // The minimum time between updates in milliseconds
    private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1 // 1 minute
            ).toLong()

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    fun GpsTracker(context: Context?) {
        mContext = context
        getLocation()
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }
}