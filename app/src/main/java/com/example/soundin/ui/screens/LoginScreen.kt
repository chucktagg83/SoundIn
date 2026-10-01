package com.example.soundin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundin.R
import com.example.soundin.ui.viewmodel.LoginViewModel
import com.example.soundin.ui.viewmodel.UserSessionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    sessionViewModel: UserSessionViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit

){
    // Snackbar state -- lives here, not inside LoginContent
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope ()

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rememberSession by viewModel.rememberSession.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(" SoundIn ")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }, // end topBar
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ){
        paddingValues ->
        LoginContent(
            paddingValues = paddingValues,
            snackbarHostState = snackbarHostState,
            scope = scope,
            onNavigateToRegister = onNavigateToRegister,
            email = email,
            password = password,
            rememberSession = rememberSession,
            emailError = emailError,
            passwordError = passwordError,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onRememberSessionChange = viewModel::onRememberSessionChange,
            onLoginClick = {
                val isValid = viewModel.onValidateAndLogin()
                scope.launch{
                    if (isValid) {
                        snackbarHostState.showSnackbar(
                            message = "Welcome to SoundIn",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                        sessionViewModel.login(
                            name = "John Doe",
                            email = email
                        )
                        onLoginSuccess()
                    } else{
                        snackbarHostState.showSnackbar(
                            "Please review the marked fields",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )

                    }
                }
            }
        )
    }
}

@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onNavigateToRegister: () -> Unit,
    email: String,
    password: String,
    rememberSession: Boolean,
    emailError: Boolean,
    passwordError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberSessionChange: (Boolean) -> Unit,
    onLoginClick: () -> Unit

) {
    var passwordVisible by remember {mutableStateOf(value = false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues) // Required for scaffold, avoid TopAppBar overlap
            .verticalScroll(rememberScrollState())// allows scrolling on small screen
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(painter = painterResource(id = R.drawable.soundin),
        contentDescription = "Logo")
        /*Text(
            text = "S",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )*/
        Text(
            text = "SoundIn",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {onEmailChange(it)},
            label = {Text("Email Address")},
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text("Invalid email addreess")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = {onPasswordChange(it)},
            label = {Text("Password")},
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text(
                        text = "Minimum 6 Characters")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (passwordVisible){
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {passwordVisible = !passwordVisible}
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth()
        )
        // Remember session Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Remember session",
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = rememberSession,
                onCheckedChange = {onRememberSessionChange (it) }
            )
        }
        // Login Button with validation
        Button(onClick = {
            onLoginClick()
        },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
            // Link to Register
        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register")
        }

    }
}// end LoginContent()




