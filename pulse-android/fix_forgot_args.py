with open('app/src/main/java/com/pulse/music/ui/auth/AuthScreens.kt', 'r', encoding='utf-8') as f:
    code = f.read()

code = code.replace(
    'fun ForgotPasswordScreen(\n    onNavigateBack: () -> Unit,', 
    'fun ForgotPasswordScreen(\n    onNavigateToLogin: () -> Unit,'
)
code = code.replace(
    'onNavigateBack()', 
    'onNavigateToLogin()'
)
code = code.replace(
    'TextButton(onClick = onNavigateBack)', 
    'TextButton(onClick = onNavigateToLogin)'
)

with open('app/src/main/java/com/pulse/music/ui/auth/AuthScreens.kt', 'w', encoding='utf-8') as f:
    f.write(code)
