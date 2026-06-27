with open('app/src/main/java/com/pulse/music/di/NetworkModule.kt', 'r', encoding='utf-8') as f:
    code = f.read()

target1 = "val cacheSize = Long.MAX_VALUE // Unlimited cache size"
replacement1 = "val cacheSize = 1000L * 1024L * 1024L * 1024L // 1 Terabyte (Effectively Unlimited)"

code = code.replace(target1, replacement1)

with open('app/src/main/java/com/pulse/music/di/NetworkModule.kt', 'w', encoding='utf-8') as f:
    f.write(code)
