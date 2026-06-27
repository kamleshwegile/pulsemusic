with open('app/src/main/java/com/pulse/music/di/NetworkModule.kt', 'r', encoding='utf-8') as f:
    code = f.read()

target = "maxAge(365, java.util.concurrent.TimeUnit.DAYS)"
replacement = "maxAge(30, java.util.concurrent.TimeUnit.DAYS)"

code = code.replace(target, replacement)

with open('app/src/main/java/com/pulse/music/di/NetworkModule.kt', 'w', encoding='utf-8') as f:
    f.write(code)
