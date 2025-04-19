package com.example.pdfgenerator.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pdfgenerator.R
import com.example.pdfgenerator.ui.MainActivity
import com.example.pdfgenerator.ui.compose.CustomToolbarScreen
import com.example.pdfgenerator.ui.theme.PdfGeneratorTheme
import com.example.pdfgenerator.viewmodel.UserCredentialsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    val LoginScreen = "login"
    val CreateUser = "createUser"
    val userCredentialsViewModel: UserCredentialsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userCredentialsViewModel.loadCurrentUserCredentials()
        enableEdgeToEdge()
        setContent {
            PdfGeneratorTheme {
                val state = rememberScrollState()
                val navController = rememberNavController()
                val title = rememberSaveable {
                    mutableStateOf("")
                }
                LaunchedEffect(Unit) { state.animateScrollTo(100) }
                Scaffold(
                    topBar = {
                        CustomToolbarScreen(navController, title.value)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = LoginScreen
                    ) {
                        composable(route = LoginScreen) {
                            title.value = stringResource(R.string.title_Login)
                            val userAuthentication =
                                userCredentialsViewModel.activeUserStatus.collectAsState()
                            if (userAuthentication.value != null) {
                                startActivity(
                                    Intent(
                                        LocalContext.current,
                                        MainActivity::class.java
                                    )
                                )
                            }
                            LoginScreen(viewModel = userCredentialsViewModel) { }
                        }
                        composable(route = CreateUser) {
                            title.value = stringResource(R.string.title_create_user)

                        }
                    }
                }
            }
        }
    }
}

class UseCaseResult<T> {
    var isLoading: Boolean = false
    var resultCode: Int = 0
    var errorCode: Int = 0
    var data: T? = null
}

