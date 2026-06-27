import re

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'r', encoding='utf-8') as f:
    code = f.read()

# Add Authorization header to the connection
code = code.replace(
    'connection.setRequestProperty("Accept", "application/vnd.github.v3+json")',
    'connection.setRequestProperty("Accept", "application/vnd.github.v3+json")\n            connection.setRequestProperty("Authorization", "token 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")'
)

with open('app/src/main/java/com/pulse/music/update/UpdateManager.kt', 'w', encoding='utf-8') as f:
    f.write(code)
