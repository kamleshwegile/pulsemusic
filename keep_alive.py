import time
import requests

urls = [
    'https://pulse-music-backend-fallback-1.onrender.com/api/v1/health',
    'https://pulse-music-backend-fallback-2.onrender.com/api/v1/health'
]

while True:
    for url in urls:
        try:
            requests.get(url, timeout=10)
        except Exception:
            pass
    time.sleep(840) # 14 minutes
