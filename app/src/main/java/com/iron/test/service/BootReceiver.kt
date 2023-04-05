package com.iron.test.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.iron.test.db.BootEvent
import com.iron.test.db.BootEventDao
import com.iron.test.db.DataBase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BootReceiver: BroadcastReceiver() {

    @Inject
    lateinit var dao: BootEventDao

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            saveEvent(System.currentTimeMillis())
        }
    }

    private fun saveEvent(time: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.addEvent(BootEvent(time))
        }
    }
}