package com.picpay.desafio.android
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.utils.RecyclerViewItemCountAssertion
import com.picpay.desafio.android.utils.androidTestContants.errorResponse
import com.picpay.desafio.android.utils.androidTestContants.serverPort
import com.picpay.desafio.android.utils.androidTestContants.successResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    private val server = MockWebServer()

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }
    }
    @Test
    fun shouldDisplayListItem() {
        server.start(serverPort)

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion())

        server.close()
    }
}