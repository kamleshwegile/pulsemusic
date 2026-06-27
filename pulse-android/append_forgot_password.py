import sys

with open('app/src/main/java/com/pulse/music/ui/auth/AuthScreens.kt', 'r', encoding='utf-8') as f:
    code = f.read()

if 'fun ForgotPasswordScreen' in code:
    print('Already has ForgotPasswordScreen')
    sys.exit(0)

forgot_password_code = '''
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var step by remember { mutableStateOf(0) } // 0: Email, 1: Code, 2: New Password
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LockReset,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = when(step) {
                    0 -> "Reset Password"
                    1 -> "Enter Verification Code"
                    else -> "Create New Password"
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))

            when (step) {
                0 -> {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (email.isBlank()) {
                                android.widget.Toast.makeText(context, "Please enter your email", android.widget.Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            isLoading = true
                            viewModel.forgotPassword(email) { message ->
                                isLoading = false
                                android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                                if (message.contains("Code sent", ignoreCase = true)) {
                                    step = 1
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text("Send Code", fontSize = 16.sp)
                        }
                    }
                }
                1 -> {
                    Text(
                        text = "We sent a 6-digit code to \",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = code,
                        onValueChange = { code = it },
                        label = { Text("6-Digit Code") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (code.isBlank()) {
                                android.widget.Toast.makeText(context, "Please enter the code", android.widget.Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            isLoading = true
                            viewModel.verifyCode(email, code) { success, message ->
                                isLoading = false
                                if (success) {
                                    step = 2
                                } else {
                                    android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text("Verify Code", fontSize = 16.sp)
                        }
                    }
                }
                2 -> {
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (newPassword.isBlank() || confirmPassword.isBlank()) {
                                android.widget.Toast.makeText(context, "Please fill in all fields", android.widget.Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (newPassword != confirmPassword) {
                                android.widget.Toast.makeText(context, "Passwords do not match", android.widget.Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            isLoading = true
                            viewModel.resetPassword(email, code, newPassword) { success, message ->
                                isLoading = false
                                android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                                if (success) {
                                    onNavigateBack()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text("Reset Password", fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateBack) {
                Text("Back to Login", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
            }
        }
    }
}
'''

code = code + '\n' + forgot_password_code

# Also need to import LockReset
if 'import androidx.compose.material.icons.filled.LockReset' not in code:
    code = code.replace('import androidx.compose.material.icons.filled.Person', 'import androidx.compose.material.icons.filled.Person\nimport androidx.compose.material.icons.filled.LockReset')

with open('app/src/main/java/com/pulse/music/ui/auth/AuthScreens.kt', 'w', encoding='utf-8') as f:
    f.write(code)
