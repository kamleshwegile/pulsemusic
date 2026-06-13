package com.pulse.music.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import com.pulse.music.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val token by viewModel.token.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(token) {
        if (!token.isNullOrEmpty()) {
            onNavigateToHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Button(
                    onClick = { 
                        viewModel.login(email, password, onNavigateToHome, onError = {
                            Toast.makeText(context, "incorrect password", Toast.LENGTH_SHORT).show()
                        }) 
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val googleIdOption = GetGoogleIdOption.Builder()
                                    .setFilterByAuthorizedAccounts(false)
                                    .setServerClientId(context.getString(R.string.default_web_client_id))
                                    .setAutoSelectEnabled(false)
                                    .build()

                                val request = GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()

                                val credentialManager = CredentialManager.create(context)
                                val result = credentialManager.getCredential(context, request)
                                
                                val credential = result.credential
                                if (credential is com.google.android.libraries.identity.googleid.GoogleIdTokenCredential) {
                                    val idToken = credential.idToken
                                    viewModel.socialLogin("google", idToken, onNavigateToHome, { error ->
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    })
                                } else if (credential is androidx.credentials.CustomCredential && credential.type == com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                    val googleIdTokenCredential = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.data)
                                    val idToken = googleIdTokenCredential.idToken
                                    viewModel.socialLogin("google", idToken, onNavigateToHome, { error ->
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    })
                                } else {
                                    Toast.makeText(context, "Unexpected type of credential", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(context, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continue with Google")
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                val callbackManager = remember { com.facebook.CallbackManager.Factory.create() }
                DisposableEffect(Unit) {
                    com.facebook.login.LoginManager.getInstance().registerCallback(
                        callbackManager,
                        object : com.facebook.FacebookCallback<com.facebook.login.LoginResult> {
                            override fun onSuccess(result: com.facebook.login.LoginResult) {
                                viewModel.socialLogin("facebook", result.accessToken.token, onNavigateToHome, { err -> 
                                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                                })
                            }
                            override fun onCancel() {}
                            override fun onError(error: com.facebook.FacebookException) {
                                Toast.makeText(context, "Facebook error: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    onDispose {
                        com.facebook.login.LoginManager.getInstance().unregisterCallback(callbackManager)
                    }
                }
                
                val facebookLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
                    contract = com.facebook.login.LoginManager.getInstance().createLogInActivityResultContract(callbackManager, null)
                ) { }

                Button(
                    onClick = {
                        facebookLauncher.launch(listOf("email", "public_profile"))
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
                ) {
                    Text("Continue with Facebook", color = MaterialTheme.colorScheme.onBackground)
                }

                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Don't have an account? Sign up")
                }

                TextButton(
                    onClick = onNavigateToForgotPassword,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Forgot Password?")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Button(
                    onClick = { 
                        viewModel.register(username, email, password, onNavigateToHome, onError = { errorMsg ->
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                        }) 
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign Up")
                }

                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Already have an account? Login")
                }
            }
        }
    }
}
