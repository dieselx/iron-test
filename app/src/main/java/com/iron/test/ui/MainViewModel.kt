package com.iron.test.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iron.test.db.BootEventDao
import com.iron.test.push.PushManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pushManger: PushManager,
    private val dao: BootEventDao
): ViewModel() {

    val eventData = ObservableField<String>()

    init {
        launchDelayTimer()
    }

    private fun launchDelayTimer() {
        viewModelScope.launch {
            flow {
                while (true) {
                    emit(dao.getEvents())
                    delay(TimeUnit.MINUTES.toMillis(15))
                }
            }
                .map { it.map { it.time } }
                .onEach(::onReceive)
                .collect()

        }
    }

    private fun onReceive(data: List<Long>) {
        if (data.isEmpty()) {
            val message = "No boots detected"
            eventData.set(message)
            pushManger.showPush(message)
            return
        }
        val pushMessage = if (data.size > 1) {
            val diff = data.takeLast(2).let { abs(it[0] - it[1]) }
            "Last boots time delta = $diff"
        } else {
            "The boot was detected with the timestamp = ${data.last()}"
        }
        pushManger.showPush(pushMessage)
        var text = ""
        data.forEachIndexed { index, time ->
            text += "${index + 1} • $time\n"
        }
        eventData.set(text)
    }
}