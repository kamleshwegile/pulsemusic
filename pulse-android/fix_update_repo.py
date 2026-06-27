import re

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'r', encoding='utf-8') as f:
    code = f.read()

# Change repo to Pulse-Music-Releases
code = code.replace(
    'private const val GITHUB_REPO = "Pulse-Music-backend"',
    'private const val GITHUB_REPO = "Pulse-Music-Releases"'
)

# Remove the Authorization header
code = re.sub(r'\n\s*connection\.setRequestProperty\("Authorization", "token[^"]+"\)', '', code)

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'w', encoding='utf-8') as f:
    f.write(code)
