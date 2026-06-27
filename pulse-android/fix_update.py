import re

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'r', encoding='utf-8') as f:
    code = f.read()

code = code.replace(
    'if (GITHUB_OWNER == "YOUR_GITHUB_USERNAME") {',
    'if (true) { // Forced test mode for demonstration'
)

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'w', encoding='utf-8') as f:
    f.write(code)
